/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.Plan;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class PlanDBContext extends DBContext<Plan> {

    public void insertPlan(Plan plan) {
        String sql_insert = "INSERT INTO Plan ( start, end, did) VALUES ( ?, ?, ?)";
        String sql_get = "SELECT * FROM Plan WHERE plid = ?";

        PreparedStatement stm_insert = null;
        PreparedStatement stm_select = null;
        ResultSet generatedKeys = null;
        try {
            connection.setAutoCommit(false);
            stm_insert = connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS);

            stm_insert.setDate(1, plan.getStart_day());
            stm_insert.setDate(2, plan.getEnd_day());
            stm_insert.setInt(3, plan.getDid().getDid());
            stm_insert.executeUpdate();

            generatedKeys = stm_insert.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1); // Lấy plid
                plan.setPlid(generatedId); // Cập nhật plid cho đối tượng plan
            }

            stm_select = connection.prepareStatement(sql_get);
            stm_select.setInt(1, plan.getPlid());

            ResultSet rs = stm_select.executeQuery();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (stm_insert != null) {
                    stm_insert.close();
                }
                if (stm_select != null) {
                    stm_select.close();
                }
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void create(Plan model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
