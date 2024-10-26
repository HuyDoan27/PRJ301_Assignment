/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.Department;
import data.Employee;
import data.Feature;
import data.User;
import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class UserDBContext extends DBContext<User> {
    
     public User getUserByEidUid(int eid, int uid) {
        User user = null;

        // Câu lệnh SQL để truy vấn dữ liệu từ cơ sở dữ liệu
        String sql = "SELECT e.eid, e.ename, u.uid, u.username, u.[password], u.isLocked, " +
                     "d.dept_id, d.dept_name, " +
                     "f.feature_id, f.feature_name " +
                     "FROM [dbo].[Employee] e " +
                     "JOIN [dbo].[Employee_User] eu ON eu.eid = e.eid " +
                     "JOIN [dbo].[User] u ON u.uid = eu.uid " +
                     "LEFT JOIN [dbo].[Department] d ON e.dept_id = d.dept_id " +
                     "LEFT JOIN [dbo].[Feature] f ON u.feature_id = f.feature_id " +
                     "WHERE e.eid = ? AND u.uid = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Thiết lập các tham số cho câu truy vấn
            pstmt.setInt(1, eid);
            pstmt.setInt(2, uid);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Khởi tạo đối tượng User từ dữ liệu kết quả
                    user = new User();
                    user.setUid(rs.getInt("uid"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setIsLocked(rs.getBoolean("isLocked"));

                    // Khởi tạo đối tượng Employee bên trong User
                    Employee employee = new Employee();
                    employee.setEid(rs.getInt("eid"));
                    employee.setEname(rs.getString("ename"));
                    user.setEmployees(employee);

                    // Khởi tạo đối tượng Department bên trong User
                    Department dept = new Department();
                    dept.setDid(rs.getInt("dept_id"));
                    dept.setDname(rs.getString("dept_name"));
                    user.setDepts(dept);

                    // Khởi tạo đối tượng Feature bên trong User (nếu có)
                    Feature feature = new Feature();
                    feature.setFid(rs.getInt("feature_id"));
                    feature.setFname(rs.getString("feature_name"));
                    user.setFeatures(feature);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT e.eid, e.ename, e.salaryLevel, u.[uid], u.username, u.[password], \n"
                + "       d.dname, f.fid, f.fname, u.isLocked\n"
                + "FROM [dbo].[Employee] e\n"
                + "    LEFT JOIN [dbo].[Employee_User] eu ON e.eid = eu.eid\n"
                + "    LEFT JOIN [dbo].[User] u ON u.uid = eu.uid\n"
                + "    LEFT JOIN [dbo].[Department] d ON d.did = e.did\n"
                + "    LEFT JOIN [dbo].[DepartmentFeature] df ON df.did = d.did\n"
                + "    LEFT JOIN [dbo].[Feature] f ON f.fid = df.fid\n"
                + "ORDER BY e.eid ASC, d.did ASC, f.fid ASC;";

        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                // Tạo một đối tượng User mới cho mỗi dòng dữ liệu
                User cUser = new User();
                cUser.setUid(rs.getInt("uid"));
                cUser.setUsername(rs.getString("username"));
                cUser.setPassword(rs.getString("password"));
                cUser.setIsLocked(rs.getBoolean("isLocked"));

                // Thêm Department cho người dùng
                Department cDept = new Department();
                cDept.setDname(rs.getString("dname"));
                cUser.setDepts(cDept);

                // Thêm Feature cho người dùng
                Feature cFeature = new Feature();
                cFeature.setFid(rs.getInt("fid"));
                cFeature.setFname(rs.getString("fname"));
                cUser.setFeatures(cFeature);

                // Thêm Employee cho người dùng
                Employee cEmployee = new Employee();
                cEmployee.setEid(rs.getInt("eid"));
                cEmployee.setEname(rs.getString("ename"));
                cEmployee.setSalaryLevel(rs.getString("salaryLevel"));
                cUser.setEmployees(cEmployee);

                // Thêm người dùng vào danh sách
                users.add(cUser);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return users;
    }

    public ArrayList<Department> getDepts(String username) {
        ArrayList<Department> depts = new ArrayList<>();
        String sql = "SELECT e.eid,u.uid, u.username,u.[password],d.did, d.dname, f.fid,f.fname,f.url FROM [dbo].[User] u\n"
                + "inner join [dbo].[Employee_User] eu on eu.uid = u.uid\n"
                + "inner join [dbo].[Employee] e on e.eid = eu.eid\n"
                + "inner join [dbo].[Department] d on d.did = e.did\n"
                + "inner join [dbo].[DepartmentFeature] df on df.did = d.did\n"
                + "inner join [dbo].[Feature] f on  f.fid = df.fid\n"
                + "where u.username = ? and u.isLocked = 'false'\n"
                + "order by d.did asc , f.fid asc";
        PreparedStatement stm = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            Department cdept = new Department();
            cdept.setDid(-1);
            while (rs.next()) {
                int did = rs.getInt("did");
                if (did != cdept.getDid()) {
                    cdept = new Department();
                    cdept.setDid(did);
                    cdept.setDname(rs.getString("dname"));
                    depts.add(cdept);
                }
                Feature f = new Feature();
                f.setFid(rs.getInt("fid"));
                f.setFname(rs.getString("fname"));
                f.setUrl(rs.getString("url"));
                cdept.getFeatures().add(f);
                f.setDept(depts);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return depts;
    }

    public User get(String username, String password) {
        String sql = "SELECT [uid]\n"
                + "      ,[username]\n"
                + "      ,[password]\n"
                + "      ,[isLocked]\n"
                + "  FROM [MyProject].[dbo].[User]\n"
                + "Where [username] = ? AND [password] = ? AND isLocked ='false'";
        PreparedStatement stm = null;
        User user = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setIsLocked(rs.getBoolean("isLocked"));
                user.setUsername(username);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return user;
    }

    public User insertUser(String username, String password) throws SQLException {
        String insertSql = "INSERT INTO [dbo].[User] ([username], [password], [isLocked]) VALUES (?, ?, ?)";
        String selectSql = "SELECT TOP 1 [uid]\n"
                + "  FROM [dbo].[User]\n"
                + "  WHERE [username] = ? \n"
                + "  ORDER BY [uid] DESC";

        User user = new User();

        try (PreparedStatement insertStm = connection.prepareStatement(insertSql); PreparedStatement selectStm = connection.prepareStatement(selectSql)) {

            // Chèn User
            insertStm.setString(1, username);
            insertStm.setString(2, password);
            insertStm.setInt(3, 1);
            insertStm.executeUpdate();

            // Lấy ID của user vừa được thêm
            selectStm.setString(1, username);
            try (ResultSet rs = selectStm.executeQuery()) {
                if (rs.next()) {
                    user.setUid(rs.getInt("uid"));
                }
            }

            user.setUsername(username);
            user.setPassword(password);
            user.setIsLocked(true);

            return user;
        } catch (SQLException ex) {
            throw new SQLException("Lỗi khi chèn User: " + ex.getMessage(), ex);
        }
    }

    public void insertEmployee_User(int eid, int uid) throws SQLException {
        String sql = "INSERT INTO [dbo].[Employee_User] ([eid], [uid], [isAction]) VALUES (?, ?, ?)";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, eid);  // Chèn Employee ID
            stm.setInt(2, uid);  // Chèn UID đã lấy từ insertUser
            stm.setInt(3, 0); // isAction có thể là giá trị mặc định, ở đây giả định là 0

            stm.executeUpdate(); // Thực thi lệnh chèn dữ liệu

            System.out.println("Chèn vào Employee_User thành công!");
        } catch (SQLException ex) {
            throw new SQLException("Lỗi khi chèn Employee_User: " + ex.getMessage(), ex);
        }
    }

    public void insertUserFeature(int uid, int eid, String ename) throws SQLException {
        EmployeeDBContext employeeDB = new EmployeeDBContext(); // Truyền kết nối hiện tại
        Employee employee = employeeDB.getAEmployee(eid, ename);

        if (employee != null && employee.getFeatures() != null) {
            ArrayList<Feature> features = employee.getFeatures();

            if (features == null || features.isEmpty()) {
                System.out.println("User không có Feature nào để gán, bỏ qua bước chèn vào UserFeature.");
                return; // Không có Feature để chèn, thoát hàm
            }

            String sql = "INSERT INTO [dbo].[UserFeature] ([uid], [fid]) VALUES (?, ?)";

            try (PreparedStatement stm = connection.prepareStatement(sql)) {
                for (Feature feature : features) {
                    int fid = feature.getFid();

                    // Kiểm tra xem Feature ID có hợp lệ trước khi chèn
                    if (!isValidFeature(fid)) {
                        System.out.println("Feature ID " + fid + " không tồn tại trong bảng Feature. Bỏ qua.");
                        continue;
                    }
                    stm.setInt(1, uid);  // Sử dụng UID vừa được chèn từ hàm insertUser
                    stm.setInt(2, fid);
                    stm.executeUpdate(); // Chèn dữ liệu vào UserFeature
                }
            } catch (SQLException ex) {
                throw new SQLException("Lỗi khi chèn UserFeature: " + ex.getMessage(), ex);
            }
        } else {
            System.out.println("Không tìm thấy Employee hoặc không có Feature.");
        }
    }

    // Hàm kiểm tra Feature ID có tồn tại trong bảng Feature không
    private boolean isValidFeature(int fid) throws SQLException {
        String sqlCheck = "SELECT COUNT(*) FROM [dbo].[Feature] WHERE fid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sqlCheck)) {
            stm.setInt(1, fid);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Trả về true nếu Feature ID tồn tại
                }
            }
        }
        return false; // Trả về false nếu Feature ID không tồn tại
    }

    public void deleteUserFeature(int uid) throws SQLException {
        String sql = "DELETE FROM [dbo].[UserFeature]\n"
                + "      WHERE [uid] = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, uid);
            stm.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Lỗi khi xóa UserFeature: " + ex.getMessage(), ex);
        }
    }

    public void deleteEmployeeUser(int uid) throws SQLException {
        String sql = "DELETE FROM [dbo].[Employee_User]\n"
                + "      WHERE [uid] = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, uid);
            stm.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Lỗi khi xóa Employee_User: " + ex.getMessage(), ex);
        }
    }

    public void deleteUser(int uid) throws SQLException {
        String sql = "DELETE FROM [dbo].[User]\n"
                + "      WHERE [uid] = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, uid);
            stm.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Lỗi khi xóa User: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void create(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
