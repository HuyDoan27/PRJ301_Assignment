/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package feature;

import dal.ScheduleCampainDBContext;
import data.ScheduleCampain;
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
public class ListScheduleCampain extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String camidStr = req.getParameter("camid");
        int camid = Integer.parseInt(camidStr);

        ScheduleCampainDBContext scDB = new ScheduleCampainDBContext();
        List<ScheduleCampain> scheduleCampainList = scDB.getScheduleCampainByCamid(camid);

        req.setAttribute("scheduleCampainList", scheduleCampainList);
        req.setAttribute("camid", camid); // Gửi camid để hiển thị nếu cần
        req.getRequestDispatcher("../view/listPlanDetail.jsp").forward(req, resp); // Chuyển hướng về JSP

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}