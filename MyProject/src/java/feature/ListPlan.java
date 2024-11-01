/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package feature;

import dal.DepartmentDBContext;
import dal.PlanDBContext;
import dal.ProductDBContext;
import data.Plan;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class ListPlan extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String inputDate = req.getParameter("inputDate");
        PlanDBContext db = new PlanDBContext();
        ProductDBContext dbProduct = new ProductDBContext();
        DepartmentDBContext dbDept = new DepartmentDBContext();
        
        ArrayList<Plan> plans = new ArrayList<>(); 
        ArrayList<Plan> cumulativeList = new ArrayList<>(); 
        
        plans = db.planList(inputDate);
        
        req.setAttribute("inputDate", inputDate);
        req.setAttribute("plans", plans);
        req.setAttribute("products", dbProduct.list());
        req.setAttribute("depts", dbDept.get("WS"));
        
        
        req.getRequestDispatcher("../view/KHSX.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
