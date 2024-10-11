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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class UserDBContext extends DBContext<User> {

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT e.eid,e.ename,e.salaryLevel,u.uid, u.username,u.[password], d.dname,f.fid,f.fname, u.isLocked FROM [dbo].[User] u\n"
                + " inner join [dbo].[Employee_User] eu on eu.uid = u.uid\n"
                + " inner join [dbo].[Employee] e on e.eid = eu.eid\n"
                + " inner join [dbo].[Department] d on d.did = e.did\n"
                + " inner join [dbo].[DepartmentFeature] df on df.did = d.did\n"
                + " inner join [dbo].[Feature] f on  f.fid = df.fid\n"
                + " order by d.did asc , f.fid asc";

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

//    public void insertUser(User user) {
//        String sql = "INSERT INTO [dbo].[User]\n"
//                + "           ([username]\n"
//                + "           ,[password]\n"
//                + "           ,[isLocked])\n"
//                + "     VALUES\n"
//                + "           (?>\n"
//                + "           ,?>\n"
//                + "           ,?)";
//        
//        PreparedStatement stm = null;
//        try {
//            connection.setAutoCommit(false);
//            stm = connection.prepareStatement(sql);
//            stm.setString(1, user.getUsername());
//            stm.setString(2, user.getPassword());
//            stm.setInt(3, plan.getDid().getDid()); // Sử dụng trực tiếp getDid() để lấy giá trị int
//            stm.executeUpdate();
//
//            // Lấy khóa tự động sinh ra
//            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    plan.setPlid(generatedKeys.getInt(1)); // Giả sử bạn có một phương thức setPlid trong Plan
//                } else {
//                    throw new SQLException("Inserting plan failed, no ID obtained.");
//                }
//            }
//
//            connection.commit(); // Commit transaction
//
//        } catch (SQLException ex) {
//            try {
//                connection.rollback(); // Rollback transaction nếu có lỗi
//            } catch (SQLException rollbackEx) {
//                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, "Rollback failed: " + rollbackEx.getMessage(), rollbackEx);
//            }
//            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, "Error inserting plan: " + ex.getMessage(), ex);
//        } finally {
//            try {
//                if (stm != null) {
//                    stm.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

    @Override
    public void create(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
