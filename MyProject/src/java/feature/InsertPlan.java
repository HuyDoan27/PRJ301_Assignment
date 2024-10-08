/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package feature;

import controller.BaseRBACController;
import dal.PlanDBContext;
import data.Department;
import data.Plan;
import data.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;

/**
 *
 * @author Admin
 */
public class InsertPlan extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        req.getRequestDispatcher("../view/KHSX.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        String raw_start = req.getParameter("start");
        String raw_end = req.getParameter("end");
        String raw_did = req.getParameter("did");

        Plan p = new Plan();
        p.setStart_day(Date.valueOf(raw_start));
        p.setEnd_day(Date.valueOf(raw_end));

        Department d = new Department();
        d.setDid(Integer.parseInt(raw_did));
        p.setDid(d);

        PlanDBContext db = new PlanDBContext();
        db.insertPlan(p);

        req.setAttribute("message", "Kế hoạch đã được tạo thành công!");
        req.getRequestDispatcher("../view/KHSX.jsp").forward(req, resp);

    }

}
