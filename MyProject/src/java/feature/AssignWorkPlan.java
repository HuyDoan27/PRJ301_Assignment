/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package feature;

import controller.BaseRBACController;
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
public class AssignWorkPlan extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int planId = Integer.parseInt(req.getParameter("planId"));
        ScheduleCampainDBContext scheduleDB = new ScheduleCampainDBContext();

        Map<String, Integer> preAssignedQuantities = scheduleDB.getPreAssignedQuantities(planId);
        Map<String, String[]> parameterMap = req.getParameterMap();
        List<ScheduleCampain> schedulesToInsert = new ArrayList<>();
        List<ScheduleCampain> schedulesToUpdate = new ArrayList<>();

        // In ra tất cả các tham số và giá trị để kiểm tra dữ liệu được gửi lên từ form
        for (String key : parameterMap.keySet()) {
            String[] values = parameterMap.get(key);
            System.out.println("Received key: " + key + " - Values: " + Arrays.toString(values));
        }

        for (String key : parameterMap.keySet()) {
            if (key.startsWith("quantity")) {
                // Sử dụng regex để tách chính xác camid, date, shift, pid
                Pattern pattern = Pattern.compile("quantity\\[(\\d+)\\]\\[(\\d{4}-\\d{2}-\\d{2})\\]\\[(K\\d)\\]\\[(\\d+)\\]");
                Matcher matcher = pattern.matcher(key);

                if (matcher.matches()) {
                    try {
                        // Lấy các giá trị từ nhóm regex
                        int camid = Integer.parseInt(matcher.group(1)); // camid
                        String date = matcher.group(2); // date
                        String shift = matcher.group(3); // shift: K1, K2, K3
                        int pid = Integer.parseInt(matcher.group(4)); // pid

                        // Tạo key kiểm tra cho preAssignedQuantities
                        String checkKey = camid + "_" + date + "_" + shift + "_" + pid;
                        
                        // Kiểm tra và chuyển đổi quantity từ request
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

        // Insert các giá trị mới vào cơ sở dữ liệu
        scheduleDB.insertSchedulesToDatabase(schedulesToInsert);

        // Update các giá trị đã thay đổi vào cơ sở dữ liệu
        scheduleDB.updateSchedulesInDatabase(schedulesToUpdate);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String planIdStr = req.getParameter("plid");
        //int planId = Integer.parseInt(planIdStr);
        PlanDBContext planDB = new PlanDBContext();
        ScheduleCampainDBContext scheduleDB = new ScheduleCampainDBContext();

        Map<String, Integer> preAssignedQuantities = scheduleDB.getPreAssignedQuantities(2);
        List<String> dateRange = planDB.getDateRangeByPlanId(2);
        List<Product> productList = planDB.getProductsByPlanId(2);

        req.setAttribute("planId", "2");
        req.setAttribute("preAssignedQuantities", preAssignedQuantities);
        req.setAttribute("dateRange", dateRange);
        req.setAttribute("productList", productList);
        req.getRequestDispatcher("../view/insertSchedule_demo.jsp").forward(req, resp);
    }
}
