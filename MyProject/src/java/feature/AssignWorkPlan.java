/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package feature;

import controller.BaseRBACController;
import dal.PlanDBContext;
import dal.ProductDBContext;
import data.Product;
import data.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Admin
 */
public class AssignWorkPlan extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String planIdStr = req.getParameter("plid");
        //int planId = Integer.parseInt(planIdStr);
        PlanDBContext planDB = new PlanDBContext();
        ProductDBContext productDB = new ProductDBContext();

        List<String> dateRange = planDB.getDateRangeByPlanId(3);
        List<Product> productList = productDB.getProductsByPlanId(3);

        req.setAttribute("dateRange", dateRange);
        req.setAttribute("productList", productList);
        req.getRequestDispatcher("../view/insertSchedule_demo.jsp").forward(req, resp);
    }
}
