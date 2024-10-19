/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.Plan;
import data.PlanCampain;
import java.sql.*;
import java.util.ArrayList;
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
            String sql_insert_plan = "INSERT INTO [Plan]\n"
                    + "           ([start]\n"
                    + "           ,[end]\n"
                    + "           ,[did])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?)";
            String sql_select_plan = "SELECT @@IDENTITY as plid";
            String sql_insert_campain = "INSERT INTO [PlanCampaign]\n"
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
            String sql = "SELECT P.plid, P.start, P.[end], "
                    + "(SELECT SUM(PC.quantity) FROM [dbo].[PlanCampainn] PC WHERE PC.plid = P.plid) AS total_quantity, "
                    + "SUM(CASE WHEN SC.date <= ? THEN WS.quantity ELSE 0 END) AS cumulative_quantity "
                    + "FROM [dbo].[Plan] P "
                    + "JOIN [dbo].[PlanCampainn] PC ON P.plid = PC.plid "
                    + "JOIN [dbo].[ScheduleCampain] SC ON PC.camid = SC.camid "
                    + "JOIN [dbo].[WorkerSchedule] WS ON SC.scid = WS.scid "
                    + "WHERE SC.date <= ? "
                    + "GROUP BY P.plid, P.start, P.[end];";

            stm = connection.prepareStatement(sql);

            Date inputDate = Date.valueOf(inputDateStr);

            stm.setDate(1, inputDate); // Tham số thứ nhất
            stm.setDate(2, inputDate); // Tham số thứ hai

            try (ResultSet rs = stm.executeQuery()) {
                // Duyệt qua kết quả truy vấn
                while (rs.next()) {
                    Plan p = new Plan();

                    p.setPlid(rs.getInt("plid"));
                    p.setStart_day(Date.valueOf(rs.getString("start")));
                    p.setEnd_day(Date.valueOf(rs.getString("end")));
                    p.setTotalQuantity(rs.getInt("total_quantity"));
                    p.setCumulativeQuantity(rs.getInt("cumulative_quantity"));
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
}
