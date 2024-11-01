/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package feature;

import dal.PlanCampainDBContext;
import dal.PlanDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class UpdatePlan extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int plid = Integer.parseInt(req.getParameter("plid"));
            Date startDate = Date.valueOf(req.getParameter("startDate"));
            Date endDate = Date.valueOf(req.getParameter("endDate"));
            int did = Integer.parseInt(req.getParameter("did"));

            String[] productIds = req.getParameterValues("productIds");
            Map<Integer, Integer> productQuantities = new HashMap<>();
            Map<Integer, Integer> productEfforts = new HashMap<>();

            // Duyệt qua từng ID sản phẩm và lấy số lượng, công sức tương ứng từ form
            if (productIds != null) {
            for (String productIdStr : productIds) {
                int productId = Integer.parseInt(productIdStr);

                // Lấy giá trị quantity và effort từ form
                String quantityStr = req.getParameter("quantity" + productId);
                String effortStr = req.getParameter("effort" + productId);

                // Chỉ xử lý nếu cả quantity và effort đều không rỗng và hợp lệ
                if (quantityStr != null && !quantityStr.isEmpty() && effortStr != null && !effortStr.isEmpty()) {
                    try {
                        int quantity = Integer.parseInt(quantityStr);
                        int effort = Integer.parseInt(effortStr);

                        // Lưu giá trị vào các map
                        productQuantities.put(productId, quantity);
                        productEfforts.put(productId, effort);
                    } catch (NumberFormatException e) {
                        // Nếu giá trị không phải số, bỏ qua sản phẩm này
                        System.out.println("Invalid quantity or effort for productId " + productId);
                    }
                } else {
                    // Bỏ qua sản phẩm nếu thiếu quantity hoặc effort
                    System.out.println("Skipping productId " + productId + " due to missing quantity or effort.");
                }
            }
        }

            PlanDBContext planDB = new PlanDBContext();
            PlanCampainDBContext planCampainDB = new PlanCampainDBContext();

            //update vao bang Plan truoc
            planDB.updatePlan(plid, startDate, endDate, did);
            
            //thuc hien cac thay doi ben bang PlanCampain
            for (Integer pid : productQuantities.keySet()) {
                int quantity = productQuantities.get(pid);
                int estimatedEffort = productEfforts.get(pid);

                // Kiểm tra xem sản phẩm đã có chưa
                if (planCampainDB.isProductInPlanCampain(plid, pid)) {
                    // Nếu sản phẩm đã có, thực hiện update
                    planCampainDB.updateProductInPlan(plid, pid, quantity, estimatedEffort);
                } else {
                    // Nếu sản phẩm chưa có, thực hiện insert
                    planCampainDB.insertProductInPlan(plid, pid, quantity, estimatedEffort);
                }
            }

            resp.sendRedirect("../view/KHSX.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Failed to update the plan. Please try again.");
            req.getRequestDispatcher("../view/KHSX.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
