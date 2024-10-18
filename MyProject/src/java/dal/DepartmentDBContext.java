/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.Department;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class DepartmentDBContext extends DBContext<Department> {

    public ArrayList<Department> get(String type) {
        ArrayList<Department> depts = new ArrayList<>();

        String sql = "SELECT [did], [dname], [type] FROM [dbo].[Department] WHERE type = ?";

        PreparedStatement stm = null;
        try {
            if (connection == null) {
                System.out.println("Database connection is null.");
                return depts;
            }

            stm = connection.prepareStatement(sql);
            stm.setString(1, type);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Department d = new Department();
                d.setDid(rs.getInt("did"));
                d.setDname(rs.getString("dname"));
                d.setType(rs.getString("type"));
                depts.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("An SQL exception occurred: " + ex.getMessage());
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (depts.isEmpty()) {
            System.out.println("No departments found for the specified type.");
        }
        return depts;
    }

    @Override
    public void create(Department model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
