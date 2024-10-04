/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.Department;
import data.Employee;
import data.Feature;
import data.User;
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
                int uid = rs.getInt("uid");
                User cUser = findUserById(users, uid);

                if (cUser == null) {
                    cUser = new User();
                    cUser.setUid(uid);
                    cUser.setUsername(rs.getString("username"));
                    cUser.setPassword(rs.getString("password"));
                    cUser.setIsLocked(rs.getBoolean("isLocked"));
                    cUser.setDepts(new ArrayList<>());  // Khởi tạo danh sách Departments
                    cUser.setFeatures(new ArrayList<>());  // Khởi tạo danh sách Features
                    cUser.setEmployees(new ArrayList<>());  // Khởi tạo danh sách Employees
                    users.add(cUser);
                }

                // Thêm Department cho người dùng
                Department cDept = new Department();
                cDept.setDid(rs.getInt("did"));
                cDept.setDname(rs.getString("dname"));
                cUser.getDepts().add(cDept);

                // Thêm Feature cho người dùng
                Feature cFeature = new Feature();
                cFeature.setFid(rs.getInt("fid"));
                cFeature.setFname(rs.getString("fname"));
                cUser.getFeatures().add(cFeature);

                // Thêm Employee cho người dùng
                Employee cEmployee = new Employee();
                cEmployee.setEid(rs.getInt("eid"));
                cEmployee.setEname(rs.getString("ename"));
                cEmployee.setSalaryLevel(rs.getString("salaryLevel"));
                cUser.getEmployees().add(cEmployee);
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

    private User findUserById(ArrayList<User> users, int uid) {
        for (User user : users) {
            if (user.getUid() == uid) {
                return user;
            }
        }
        return null;
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

    @Override
    public void create(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
