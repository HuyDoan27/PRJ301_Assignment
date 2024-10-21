/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package feature;

import controller.BaseRBACController;
import dal.DepartmentDBContext;
import dal.PlanDBContext;
import dal.ProductDBContext;
import data.Department;
import data.Plan;
import data.PlanCampain;
import data.Product;
import data.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ProductionPlanCreate extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        ProductDBContext dbProduct = new ProductDBContext();
        DepartmentDBContext dbDept = new DepartmentDBContext();
        req.setAttribute("products", dbProduct.list());
        req.setAttribute("depts", dbDept.get("WS"));
        req.getRequestDispatcher("../view/KHSX.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        String[] pids = req.getParameterValues("pid");
        if (pids == null || pids.length == 0) {
            resp.getWriter().println("Không có sản phẩm nào được chọn.");
            return;
        }

        Plan plan = new Plan();
        plan.setStart_day(Date.valueOf(req.getParameter("from")));
        plan.setEnd_day(Date.valueOf(req.getParameter("to")));

        Department d = new Department();
        d.setDid(Integer.parseInt(req.getParameter("did")));

        plan.setDept(d);
        plan.setCampains(new ArrayList<>());

        for (String pid : pids) {
            Product p = new Product();
            p.setId(Integer.parseInt(pid));

            PlanCampain c = new PlanCampain();
            c.setProduct(p);
            String raw_quantity = req.getParameter("quantity" + pid);
            String raw_effort = req.getParameter("effort" + pid);
            c.setQuantity(raw_quantity != null && raw_quantity.length() > 0 ? Integer.parseInt(raw_quantity) : 0);
            c.setEstimatedeffort(raw_effort != null && raw_effort.length() > 0 ? Integer.parseInt(raw_effort) : 0);
            c.setPlan(plan);
            if (c.getQuantity() != 0 && c.getEstimatedeffort() != 0) {
                plan.getCampains().add(c);
            }
        }

        if (plan.getCampains().size() > 0) {
            PlanDBContext db = new PlanDBContext();
            db.insertPlan(plan);
            req.setAttribute("message", "Kế hoạch đã được tạo thành công!");
            req.getRequestDispatcher("../view/KHSX.jsp").forward(req, resp);
        } else {
            resp.getWriter().println("your plan did not have any campains");
        }
    }

}
