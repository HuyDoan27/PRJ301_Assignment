/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package feature;

import controller.BaseRBACController;
import dal.DBContext;
import dal.PlanDBContext;
import dal.ProductDBContext;
import dal.ScheduleCampainDBContext;
import data.Product;
import data.ScheduleCampain;
import data.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
public class AssignWorkPlan extends BaseRBACController {

    private DBContext<ScheduleCampain> dbContext;

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        int planId = Integer.parseInt(req.getParameter("planId"));
//        Connection connection = null;
//        PreparedStatement stm = null;
//
//        try {
//            if (dbContext == null) {
//                dbContext = new DBContext<ScheduleCampain>() {
//                    @Override
//                    public void create(ScheduleCampain model) {
//                        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//                    }
//                };
//            }
//            dbContext.connection.setAutoCommit(false);
//
//            ScheduleCampainDBContext scheduleDB = new ScheduleCampainDBContext();
//
//            Map<String, Integer> preAssignedQuantities = scheduleDB.getPreAssignedQuantities(planId);
//            Map<String, String[]> parameterMap = req.getParameterMap();
//            List<ScheduleCampain> schedulesToInsert = new ArrayList<>();
//            List<ScheduleCampain> schedulesToUpdate = new ArrayList<>();
//
//            for (String key : parameterMap.keySet()) {
//                if (key.startsWith("quantity")) {
//                    // Sử dụng regex để tách chính xác camid, date, shift, pid
//                    Pattern pattern = Pattern.compile("quantity\\[(\\d+)\\]\\[(\\d{4}-\\d{2}-\\d{2})\\]\\[(K\\d)\\]\\[(\\d+)\\]");
//                    Matcher matcher = pattern.matcher(key);
//
//                    if (matcher.matches()) {
//                        try {
//                            // Lấy các giá trị từ nhóm regex
//                            int camid = Integer.parseInt(matcher.group(1));
//                            String date = matcher.group(2);
//                            String shift = matcher.group(3);
//                            int pid = Integer.parseInt(matcher.group(4));
//
//                            // Tạo key kiểm tra cho preAssignedQuantities
//                            String checkKey = camid + "_" + date + "_" + shift + "_" + pid;
//
//                            String quantityStr = req.getParameter(key);
//                            if (quantityStr != null && !quantityStr.trim().isEmpty()) {
//                                try {
//                                    int quantity = Integer.parseInt(quantityStr.trim());
//                                    if (quantity > 0) {
//                                        ScheduleCampain schedule = new ScheduleCampain();
//                                        schedule.setCamid(camid);
//                                        schedule.setDate(Date.valueOf(date));
//                                        schedule.setShift(shift);
//                                        schedule.setQuantity(quantity);
//
//                                        if (preAssignedQuantities.containsKey(checkKey)) {
//                                            // Nếu đã có dữ liệu trước đó, kiểm tra xem giá trị có thay đổi không
//                                            int existingQuantity = preAssignedQuantities.get(checkKey);
//                                            if (existingQuantity != quantity) {
//                                                // Nếu khác, thêm vào danh sách để update
//                                                schedulesToUpdate.add(schedule);
//                                            }
//                                        } else {
//                                            // Nếu chưa có dữ liệu trước đó, thêm vào danh sách để insert
//                                            schedulesToInsert.add(schedule);
//                                        }
//                                    }
//                                } catch (NumberFormatException e) {
//                                    System.err.println("Invalid quantity for key: " + key + " - Value: '" + quantityStr + "'");
//                                    e.printStackTrace();
//                                }
//                            } else {
//                                System.out.println("Skipping empty or null quantity for key: " + key + " - Value: '" + quantityStr + "'");
//                            }
//                        } catch (Exception e) {
//                            System.err.println("Error processing key: " + key);
//                            e.printStackTrace();
//                        }
//                    } else {
//                        System.err.println("Invalid key format: " + key);
//                    }
//                }
//            }
//
//            // Insert các giá trị mới vào dtb
//            int insertCount = scheduleDB.insertSchedulesToDatabase(schedulesToInsert);
//
//            // Update các giá trị đã thay đổi vào dtb
//            int updateCount = scheduleDB.updateSchedulesInDatabase(schedulesToUpdate);
//
//            dbContext.connection.commit();
//
//            String successMessage = "Đã thêm " + insertCount + " bản ghi mới. "
//                    + "Đã cập nhật " + updateCount + " bản ghi.";
//
//            req.setAttribute("successMessage", successMessage);
//            req.getRequestDispatcher("../view/listPlanDetail.jsp").forward(req, resp);
//
//        } catch (SQLException e) {
//            if (connection != null) {
//                try {
//                    connection.rollback();
//                    System.err.println("Transaction rolled back due to an error.");
//                } catch (SQLException rollbackEx) {
//                    rollbackEx.printStackTrace();
//                }
//            }
//            e.printStackTrace();
//        } finally {
//            try {
//                if (connection != null) {
//                    connection.setAutoCommit(true);
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String planIdStr = req.getParameter("plid");
//        int planId = Integer.parseInt(planIdStr);
//        PlanDBContext planDB = new PlanDBContext();
//        ScheduleCampainDBContext scheduleDB = new ScheduleCampainDBContext();
//
//        Map<String, Integer> preAssignedQuantities = scheduleDB.getPreAssignedQuantities(planId);
//        List<String> dateRange = planDB.getDateRangeByPlanId(planId);
//        List<Product> productList = planDB.getProductsByPlanId(planId);
//
//        req.setAttribute("planId", planIdStr);
//        req.setAttribute("preAssignedQuantities", preAssignedQuantities);
//        req.setAttribute("dateRange", dateRange);
//        req.setAttribute("productList", productList);
//        req.getRequestDispatcher("../view/insertSchedule_demo.jsp").forward(req, resp);
//    }

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        int planId = Integer.parseInt(req.getParameter("planId"));
        Connection connection = null;
        PreparedStatement stm = null;

        try {
            if (dbContext == null) {
                dbContext = new DBContext<ScheduleCampain>() {
                    @Override
                    public void create(ScheduleCampain model) {
                        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }
                };
            }
            dbContext.connection.setAutoCommit(false);

            ScheduleCampainDBContext scheduleDB = new ScheduleCampainDBContext();

            Map<String, Integer> preAssignedQuantities = scheduleDB.getPreAssignedQuantities(planId);
            Map<String, String[]> parameterMap = req.getParameterMap();
            List<ScheduleCampain> schedulesToInsert = new ArrayList<>();
            List<ScheduleCampain> schedulesToUpdate = new ArrayList<>();

            for (String key : parameterMap.keySet()) {
                if (key.startsWith("quantity")) {
                    // Sử dụng regex để tách chính xác camid, date, shift, pid
                    Pattern pattern = Pattern.compile("quantity\\[(\\d+)\\]\\[(\\d{4}-\\d{2}-\\d{2})\\]\\[(K\\d)\\]\\[(\\d+)\\]");
                    Matcher matcher = pattern.matcher(key);

                    if (matcher.matches()) {
                        try {
                            // Lấy các giá trị từ nhóm regex
                            int camid = Integer.parseInt(matcher.group(1));
                            String date = matcher.group(2);
                            String shift = matcher.group(3);
                            int pid = Integer.parseInt(matcher.group(4));

                            // Tạo key kiểm tra cho preAssignedQuantities
                            String checkKey = camid + "_" + date + "_" + shift + "_" + pid;

                            String quantityStr = req.getParameter(key);
                            if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                                try {
                                    int quantity = Integer.parseInt(quantityStr.trim());
                                    if (quantity > 0) {
                                        ScheduleCampain schedule = new ScheduleCampain();
                                        schedule.setCamid(camid);
                                        schedule.setDate(Date.valueOf(date));
                                        schedule.setShift(shift);
                                        schedule.setQuantity(quantity);

                                        if (preAssignedQuantities.containsKey(checkKey)) {
                                            // Nếu đã có dữ liệu trước đó, kiểm tra xem giá trị có thay đổi không
                                            int existingQuantity = preAssignedQuantities.get(checkKey);
                                            if (existingQuantity != quantity) {
                                                // Nếu khác, thêm vào danh sách để update
                                                schedulesToUpdate.add(schedule);
                                            }
                                        } else {
                                            // Nếu chưa có dữ liệu trước đó, thêm vào danh sách để insert
                                            schedulesToInsert.add(schedule);
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    System.err.println("Invalid quantity for key: " + key + " - Value: '" + quantityStr + "'");
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("Skipping empty or null quantity for key: " + key + " - Value: '" + quantityStr + "'");
                            }
                        } catch (Exception e) {
                            System.err.println("Error processing key: " + key);
                            e.printStackTrace();
                        }
                    } else {
                        System.err.println("Invalid key format: " + key);
                    }
                }
            }

            // Insert các giá trị mới vào dtb
            int insertCount = scheduleDB.insertSchedulesToDatabase(schedulesToInsert);

            // Update các giá trị đã thay đổi vào dtb
            int updateCount = scheduleDB.updateSchedulesInDatabase(schedulesToUpdate);

            dbContext.connection.commit();

            String successMessage = "Đã thêm " + insertCount + " bản ghi mới. "
                    + "Đã cập nhật " + updateCount + " bản ghi.";

            req.setAttribute("successMessage", successMessage);
            req.getRequestDispatcher("../view/listPlanDetail.jsp").forward(req, resp);

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    System.err.println("Transaction rolled back due to an error.");
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        String planIdStr = req.getParameter("plid");
        int planId = Integer.parseInt(planIdStr);
        PlanDBContext planDB = new PlanDBContext();
        ScheduleCampainDBContext scheduleDB = new ScheduleCampainDBContext();

        Map<String, Integer> preAssignedQuantities = scheduleDB.getPreAssignedQuantities(planId);
        List<String> dateRange = planDB.getDateRangeByPlanId(planId);
        List<Product> productList = planDB.getProductsByPlanId(planId);

        req.setAttribute("planId", planIdStr);
        req.setAttribute("preAssignedQuantities", preAssignedQuantities);
        req.setAttribute("dateRange", dateRange);
        req.setAttribute("productList", productList);
        req.getRequestDispatcher("../view/insertSchedule_demo.jsp").forward(req, resp);
    }
}
