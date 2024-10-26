/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import data.Department;
import data.Employee;
import data.Feature;
import data.User;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class EmployeeDBContext extends DBContext<Employee> {

    public ArrayList<Department> getDepts(String eid) {
        ArrayList<Department> depts = new ArrayList<>();
        String sql = "SELECT e.eid,u.uid, u.username,u.[password],d.did, d.dname, f.fid,f.fname,f.url FROM [dbo].[User] u\n"
                + "inner join [dbo].[Employee_User] eu on eu.uid = u.uid\n"
                + "inner join [dbo].[Employee] e on e.eid = eu.eid\n"
                + "inner join [dbo].[Department] d on d.did = e.did\n"
                + "inner join [dbo].[DepartmentFeature] df on df.did = d.did\n"
                + "inner join [dbo].[Feature] f on  f.fid = df.fid\n"
                + "where e.eid = ? and u.isLocked = 'false'\n"
                + "order by d.did asc , f.fid asc";
        PreparedStatement stm = null;
        Employee employee = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, eid);
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

    public Employee getAEmployee(int eid, String ename) {
        String sql = "SELECT e.[eid],e.[ename],e.[did],eu.uid,f.fid,f.fname,f.[url]\n"
                + "  FROM [dbo].[Employee] e\n"
                + "  LEFT JOIN [dbo].[Employee_User]eu on e.eid = eu.eid\n"
                + "  LEFT JOIN [dbo].[DepartmentFeature] df on e.did = df.did\n"
                + "  LEFT JOIN [dbo].[Feature] f on f.fid = df.fid\n"
                + "where e.eid = ? and e.ename like ?";

        // Đối tượng Employee để lưu kết quả
        Employee employee = null;
        PreparedStatement stm = null;
        ArrayList<Feature> features = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, eid);
            stm.setString(2, "%" + ename + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                if (employee == null) {
                    employee = new Employee();
                    employee.setEid(rs.getInt("eid"));
                    employee.setEname(rs.getString("ename"));
                    employee.setDid(rs.getInt("did"));
                }

                // Lấy thông tin Feature và kiểm tra trùng lặp trước khi thêm
                int fid = rs.getInt("fid");
                boolean featureExists = false;
                for (Feature f : features) {
                    if (f.getFid() == fid) {
                        featureExists = true;
                        break;
                    }
                }
                if (!featureExists) {
                    Feature f = new Feature();
                    f.setFid(fid);
                    f.setFname(rs.getString("fname"));
                    f.setUrl(rs.getString("url"));
                    features.add(f); // Thêm Feature nếu chưa có trong danh sách
                }

                // Lấy thông tin User và kiểm tra trùng lặp trước khi thêm
                int uid = rs.getInt("uid");
                boolean userExists = false;
                for (User u : users) {
                    if (u.getUid() == uid) {
                        userExists = true;
                        break;
                    }
                }
                if (!userExists) {
                    User u = new User();
                    u.setUid(uid);
                    users.add(u); // Thêm User nếu chưa có trong danh sách
                }
            }

            // Gán danh sách Features và Users cho Employee
            if (employee != null) {
                employee.setFeatures(features);
                employee.setUser(users);
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
        return employee;
    }



    @Override
    public void create(Employee model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
