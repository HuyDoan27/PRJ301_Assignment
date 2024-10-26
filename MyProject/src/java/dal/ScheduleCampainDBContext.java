/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.ScheduleCampain;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ScheduleCampainDBContext extends DBContext<ScheduleCampainDBContext> {

    public Map<String, Integer> getPreAssignedQuantities(int planId) {
        Map<String, Integer> assignedQuantities = new HashMap<>();
        String sql = "SELECT sc.camid, sc.date, sc.shift, sc.quantity, pc.pid \n"
                + "FROM [dbo].[ScheduleCampain] sc \n"
                + "JOIN [dbo].[PlanCampainn] pc ON sc.camid = pc.camid \n"
                + "WHERE pc.plid = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, planId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int camid = rs.getInt("camid");
                String date = rs.getDate("date").toString();
                String shift = rs.getString("shift"); // K1, K2, K3
                int quantity = rs.getInt("quantity");
                int pid = rs.getInt("pid");
                // Tạo khóa duy nhất dựa trên camid, ngày, và shift
                String key = camid + "_" + date + "_" + shift + "_" + pid;
                assignedQuantities.put(key, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignedQuantities;
    }

    public int insertSchedulesToDatabase(List<ScheduleCampain> schedules) {
        String sql = "INSERT INTO [dbo].[ScheduleCampain]\n"
                + "           ([camid]\n"
                + "           ,[date]\n"
                + "           ,[shift]\n"
                + "           ,[quantity])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement stm = null;
        int insertCount = 0;

        try {
            stm = connection.prepareStatement(sql);
            for (ScheduleCampain schedule : schedules) {
                stm.setInt(1, schedule.getCamid());
                stm.setDate(2, schedule.getDate());
                stm.setString(3, schedule.getShift());
                stm.setInt(4, schedule.getQuantity());

                int rowsInserted = stm.executeUpdate();
                if (rowsInserted > 0) {
                    insertCount++; // Đếm số bản ghi được chèn thành công
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error inserting schedules into the database.", e);
        } finally {
            try {
                if (stm != null) {
                    stm.close(); // Đảm bảo đóng PreparedStatement
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return insertCount;
    }

    public int updateSchedulesInDatabase(List<ScheduleCampain> schedules) {
        String sql = "UPDATE [dbo].[ScheduleCampain] SET [quantity] = ? WHERE camid = ? AND date = ? AND shift = ?";
        PreparedStatement stm = null;
        int updateCount = 0;
        try {
            stm = connection.prepareStatement(sql);
            for (ScheduleCampain schedule : schedules) {
                stm.setInt(1, schedule.getQuantity());
                stm.setInt(2, schedule.getCamid());
                stm.setDate(3, schedule.getDate());
                stm.setString(4, schedule.getShift());

                int rowsAffected = stm.executeUpdate();
                if (rowsAffected > 0) {
                    updateCount++; // Đếm số bản ghi được cập nhật thành công
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating schedules in the database.", e);
        } finally {
            try {
                if (stm != null) {
                    stm.close(); // Đảm bảo đóng PreparedStatement
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return updateCount;
    }

    @Override
    public void create(ScheduleCampainDBContext model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
