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

    @Override
    public void create(Plan model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean isDidExists(int did) {
        String sql = "SELECT COUNT(*) FROM Department WHERE did = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, did);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu did tồn tại
            }
        } catch (SQLException e) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, e);
        }
        return false; // Trả về false nếu did không tồn tại
    }

    public void insertPlan(Plan plan) {
        String sql = "INSERT INTO [dbo].[Plan]\n"
                + "           ([start]\n"
                + "           ,[end]\n"
                + "           ,[did])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setDate(1, plan.getStart_day());
            stm.setDate(2, plan.getEnd_day());
            stm.setInt(3, plan.getDid().getDid()); // Sử dụng trực tiếp getDid() để lấy giá trị int
            stm.executeUpdate();

            // Lấy khóa tự động sinh ra
            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    plan.setPlid(generatedKeys.getInt(1)); // Giả sử bạn có một phương thức setPlid trong Plan
                } else {
                    throw new SQLException("Inserting plan failed, no ID obtained.");
                }
            }

            connection.commit(); // Commit transaction

        } catch (SQLException ex) {
            try {
                connection.rollback(); // Rollback transaction nếu có lỗi
            } catch (SQLException rollbackEx) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, "Rollback failed: " + rollbackEx.getMessage(), rollbackEx);
            }
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, "Error inserting plan: " + ex.getMessage(), ex);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
