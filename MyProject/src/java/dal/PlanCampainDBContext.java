/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.Plan;
import data.PlanCampain;
import data.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class PlanCampainDBContext extends DBContext<PlanCampain> {

    public boolean isProductInPlanCampain(int plid, int pid) {
        String sql = "SELECT COUNT(*) FROM PlanCampainn WHERE plid = ? AND pid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {

            stm.setInt(1, plid);
            stm.setInt(2, pid);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateProductInPlan(int plid, int pid, int quantity, int estimatedEffort) {
        String sql = "UPDATE PlanCampainn SET quantity = ?, estimatedeffort = ? WHERE plid = ? AND pid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {

            stm.setInt(1, quantity);
            stm.setInt(2, estimatedEffort);
            stm.setInt(3, plid);
            stm.setInt(4, pid);

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertProductInPlan(int plid, int pid, int quantity, int estimatedEffort) {
        String sql = "INSERT INTO PlanCampainn (plid, pid, quantity, estimatedeffort) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {

            stm.setInt(1, plid);
            stm.setInt(2, pid);
            stm.setInt(3, quantity);
            stm.setInt(4, estimatedEffort);

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlanCampain> getPlanCampainByPid(int planID) {
        List<PlanCampain> planCampainList = new ArrayList<>();
        String sql = "SELECT [camid], [plid], [pid], [quantity], [estimatedeffort] "
                + "FROM [dbo].[PlanCampainn] "
                + "WHERE plid = ?";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, planID);
            ResultSet resultSet = stm.executeQuery();

            while (resultSet.next()) {
                // Tạo đối tượng Plan
                Plan plan = new Plan(); // Giả sử có constructor và setter cho Plan
                plan.setPlid(resultSet.getInt("plid")); // Cài đặt ID hoặc các thuộc tính khác cho Plan

                // Tạo đối tượng PlanCampain
                PlanCampain planCampain = new PlanCampain();
                planCampain.setCamid(resultSet.getInt("camid"));
                planCampain.setPlan(plan); // Set đối tượng Plan
                planCampain.setQuantity(resultSet.getInt("quantity"));
                planCampain.setEstimatedeffort(resultSet.getFloat("estimatedeffort"));

                // Lấy thông tin sản phẩm
                Product product = getProductById(resultSet.getInt("pid")); // Giả sử có phương thức này
                planCampain.setProduct(product); // Set product vào PlanCampain

                // Thêm vào danh sách
                planCampainList.add(planCampain);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return planCampainList;
    }

    @Override
    public void create(PlanCampain model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private Product getProductById(int pid) {
        Product product = null;
        String sql = "SELECT * FROM [dbo].[Product] WHERE pid = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, pid);
            ResultSet resultSet = stm.executeQuery();

            if (resultSet.next()) {
                product = new Product(); // Khởi tạo sản phẩm
                product.setId(resultSet.getInt("pid")); // Giả sử có phương thức này
                // Cài đặt thêm thông tin sản phẩm nếu cần
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

}
