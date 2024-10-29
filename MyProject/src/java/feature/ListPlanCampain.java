/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package feature;

import dal.PlanCampainDBContext;
import dal.PlanDBContext;
import data.PlanCampain;
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
public class ListPlanCampain extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String plidStr = req.getParameter("plid");
        int plid = Integer.parseInt(plidStr);

        PlanCampainDBContext pcDB = new PlanCampainDBContext();
        PlanDBContext pDB = new PlanDBContext();
        
        List<String> startEndDates = pDB.getStartAndEndByPlanId(plid);
        List<PlanCampain> planCampainList = pcDB.getPlanCampainByPid(plid);
        
        req.getSession().setAttribute("startEndDates", startEndDates);
        req.getSession().setAttribute("planCampainList", planCampainList);
        resp.sendRedirect("../view/listPlanDetail.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
