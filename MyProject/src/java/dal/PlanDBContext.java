/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.Plan;
import data.PlanCampain;
import data.Product;
import data.ScheduleCampain;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    public void insertPlan(Plan plan) {
        try {
            connection.setAutoCommit(false);
            String sql_insert_plan = "INSERT INTO [dbo].[Plan]\n"
                    + "           ([start]\n"
                    + "           ,[end]\n"
                    + "           ,[did])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?)";
            String sql_select_plan = "SELECT @@IDENTITY as plid";
            String sql_insert_campain = "INSERT INTO [PlanCampainn]\n"
                    + "           ([plid]\n"
                    + "           ,[pid]\n"
                    + "           ,[quantity]\n"
                    + "           ,[estimatedeffort])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement stm_insert_plan = connection.prepareStatement(sql_insert_plan);
            stm_insert_plan.setDate(1, plan.getStart_day());
            stm_insert_plan.setDate(2, plan.getEnd_day());
            stm_insert_plan.setInt(3, plan.getDept().getDid());
            stm_insert_plan.executeUpdate();

            PreparedStatement stm_select_plan = connection.prepareStatement(sql_select_plan);
            ResultSet rs = stm_select_plan.executeQuery();
            if (rs.next()) {
                plan.setPlid(rs.getInt("plid"));
            }
            for (PlanCampain campain : plan.getCampains()) {
                PreparedStatement stm_insert_campain = connection.prepareStatement(sql_insert_campain);
                stm_insert_campain.setInt(1, plan.getPlid());
                stm_insert_campain.setInt(2, campain.getProduct().getId());
                stm_insert_campain.setInt(3, campain.getQuantity());
                stm_insert_campain.setFloat(4, campain.getEstimatedeffort());
                stm_insert_campain.executeUpdate();
            }
            connection.commit();

        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<Plan> planList(String inputDateStr) {
        ArrayList<Plan> plans = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            String sql = "SELECT P.plid, \n"
                    + "       P.start, \n"
                    + "       P.[end], \n"
                    + "       (SELECT SUM(PC.quantity) \n"
                    + "        FROM [dbo].[PlanCampainn] PC \n"
                    + "        WHERE PC.plid = P.plid) AS total_quantity, \n"
                    + "       SUM(CASE \n"
                    + "               WHEN SC.date <= ? THEN A.quantity \n"
                    + "               ELSE 0 \n"
                    + "           END) AS cumulative_quantity\n"
                    + "FROM [dbo].[Plan] P\n"
                    + "LEFT JOIN [dbo].[PlanCampainn] PC ON P.plid = PC.plid\n"
                    + "LEFT JOIN [dbo].[ScheduleCampain] SC ON PC.camid = SC.camid\n"
                    + "LEFT JOIN [dbo].[WorkerSchedule] WS ON SC.scid_new = WS.scid\n"
                    + "LEFT JOIN [dbo].[Attendent] A ON WS.wsid = A.wsid\n"
                    + "GROUP BY P.plid, P.start, P.[end];";

            stm = connection.prepareStatement(sql);

            Date inputDate = Date.valueOf(inputDateStr);

            stm.setDate(1, inputDate); // Tham số thứ nhất

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Plan p = new Plan();

                    p.setPlid(rs.getInt("plid"));
                    p.setStart_day(Date.valueOf(rs.getString("start")));
                    p.setEnd_day(Date.valueOf(rs.getString("end")));
                    p.setTotalProductQuantities(getProductsByPlanId(rs.getInt("plid")));
                    p.setCumulativeProductQuantities(getCumulativeProductQuantitiesByPlanId(rs.getInt("plid")));

                    // Xác định trạng thái của kế hoạch
                    if (p.getEnd_day().before(inputDate)) {
                        p.setStatus("late");
                    } else if (p.getStart_day().before(inputDate) && p.getEnd_day().after(inputDate)) {
                        p.setStatus("on-going");
                    } else {
                        p.setStatus("not started");
                    }
                    plans.add(p);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (plans == null || plans.isEmpty()) {
            System.out.println("No plans were found.");
        }
        return plans;
    }

    public List<Product> getProductsByPlanId(int planId) {
        List<Product> productList = new ArrayList<>();
        try {
            String sql = "SELECT pc.camid, p.pid, p.pname, pc.quantity\n"
                    + "FROM [dbo].[Product] p \n"
                    + "JOIN [dbo].[PlanCampainn] pc ON p.pid = pc.pid \n"
                    + "WHERE pc.plid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, planId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setCamid(rs.getInt("camid"));
                p.setId(Integer.parseInt(rs.getString("pid")));
                p.setName(rs.getString("pname"));
                p.setQuantity(rs.getInt("quantity"));
                productList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> getCumulativeProductQuantitiesByPlanId(int planId) {
        List<Product> cumulativeList = new ArrayList<>();
        try {
            String sql = "SELECT p.pid, p.pname, SUM(a.quantity) as cumulativeQuantity \n"
                    + "FROM [dbo].[Product] p \n"
                    + "left JOIN [dbo].[PlanCampainn] pc ON p.pid = pc.pid \n"
                    + "left JOIN [dbo].[ScheduleCampain] sc ON pc.camid = sc.camid \n"
                    + "left JOIN [dbo].[WorkerSchedule] ws ON sc.scid_new = ws.scid \n"
                    + "left JOIN [dbo].[Attendent] a ON a.wsid = ws.wsid \n"
                    + "WHERE pc.plid = ?\n"
                    + "GROUP BY p.pid, p.pname";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, planId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("pid"));
                p.setName(rs.getString("pname"));
                p.setQuantity(rs.getInt("cumulativeQuantity")); // Lấy số lượng lũy kế
                cumulativeList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cumulativeList;
    }

    public List<String> getDateRangeByPlanId(int planId) {
        List<String> dateRange = new ArrayList<>();
        try {
            String sql = "SELECT [start], [end] FROM [dbo].[Plan] WHERE plid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, planId);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                LocalDate startDate = rs.getDate("start").toLocalDate();
                LocalDate endDate = rs.getDate("end").toLocalDate();

                // Tính toán khoảng ngày từ ngày bắt đầu đến ngày kết thúc
                while (!startDate.isAfter(endDate)) {
                    dateRange.add(startDate.toString()); // Định dạng yyyy-MM-dd
                    startDate = startDate.plusDays(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dateRange;
    }

}
