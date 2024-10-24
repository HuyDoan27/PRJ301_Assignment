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

    public void insertSchedulesToDatabase(List<ScheduleCampain> schedules) {
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

        try {
            connection.setAutoCommit(false);

            stm = connection.prepareStatement(sql);
            for (ScheduleCampain schedule : schedules) {
                stm.setInt(1, schedule.getCamid());
                stm.setDate(2, schedule.getDate());
                stm.setString(3, schedule.getShift());
                stm.setInt(4, schedule.getQuantity());

                // Chèn từng bản ghi với executeUpdate
                stm.executeUpdate();
            }

            connection.commit(); // Xác nhận giao dịch, tất cả các bản ghi sẽ được chèn
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Nếu có lỗi, hủy bỏ giao dịch
                    System.out.println("Transaction rolled back due to an error.");
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (stm != null) {
                    stm.close(); // Đóng PreparedStatement
                }
                if (connection != null) {
                    connection.setAutoCommit(true); // Khôi phục lại chế độ auto-commit
                    connection.close(); // Đóng kết nối
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateSchedulesInDatabase(List<ScheduleCampain> schedules) {
        String sql = "UPDATE [dbo].[ScheduleCampain] SET quantity = ? WHERE camid = ? AND date = ? AND shift = ? AND pid = ?";
        try {
            // Tắt chế độ auto-commit để thực hiện commit thủ công
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement(sql);

            for (ScheduleCampain schedule : schedules) {
                stm.setInt(1, schedule.getQuantity());
                stm.setInt(2, schedule.getCamid());
                stm.setDate(3, schedule.getDate());
                stm.setString(4, schedule.getShift());

                // Sử dụng executeUpdate thay vì addBatch
                int rowsAffected = stm.executeUpdate();
                if (rowsAffected == 0) {
                    System.err.println("No rows updated for Schedule: " + schedule);
                }
            }

            // Thực hiện commit tất cả các cập nhật
            connection.commit();
            System.out.println("All updates committed successfully.");

        } catch (SQLException e) {
            // Nếu có lỗi, rollback lại tất cả các thay đổi
            try {
                if (connection != null) {
                    connection.rollback();
                    System.err.println("Transaction rolled back due to an error.");
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // Bật lại chế độ auto-commit
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void create(ScheduleCampainDBContext model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
