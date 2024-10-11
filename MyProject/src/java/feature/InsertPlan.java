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
import java.sql.*;

/**
 *
 * @author Admin
 */
public class InsertPlan extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        String raw_start = req.getParameter("start");
        String raw_end = req.getParameter("end");
        String raw_did = req.getParameter("did");

        // Kiểm tra các tham số nhận được
        if (raw_start == null || raw_end == null || raw_did == null) {
            resp.getWriter().println("Missing parameters");
            return; 
        }

        try {
            Plan p = new Plan();
            p.setStart_day(Date.valueOf(raw_start));
            p.setEnd_day(Date.valueOf(raw_end));

            Department d = new Department();
            d.setDid(Integer.parseInt(raw_did));
            p.setDid(d);

            // Kiểm tra xem did có tồn tại trong bảng Department không
            PlanDBContext db = new PlanDBContext();
            if (!db.isDidExists(d.getDid())) {
                resp.getWriter().println("Department ID does not exist");
                return; // Dừng hàm nếu did không tồn tại
            }
            // Tạo đối tượng PlanDBContext và insert kế hoạch
            db.insertPlan(p);
            req.getSession().setAttribute("insertedPlan", p);
            // Gửi phản hồi thành công
            resp.sendRedirect("../view/KHSX.jsp");
        } catch (IllegalArgumentException e) {
            resp.getWriter().println("Invalid date format");
        } catch (Exception e) {
            resp.getWriter().println("An error occurred: " + e.getMessage());
        }
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        String raw_start = req.getParameter("start");
        String raw_end = req.getParameter("end");
        String raw_did = req.getParameter("did");

        // Kiểm tra các tham số nhận được
        if (raw_start == null || raw_end == null || raw_did == null) {
            resp.getWriter().println("Missing parameters");
            return; // Dừng hàm nếu có tham số thiếu
        }

        try {
            // Chuyển đổi kiểu dữ liệu
            Plan p = new Plan();
            p.setStart_day(Date.valueOf(raw_start));
            p.setEnd_day(Date.valueOf(raw_end));

            Department d = new Department();
            d.setDid(Integer.parseInt(raw_did));
            p.setDid(d);

            // Kiểm tra xem did có tồn tại trong bảng Department không
            PlanDBContext db = new PlanDBContext();
            if (!db.isDidExists(d.getDid())) {
                resp.getWriter().println("Department ID does not exist");
                return; // Dừng hàm nếu did không tồn tại
            }

            // Tạo đối tượng PlanDBContext và insert kế hoạch
            db.insertPlan(p);

            // Gửi phản hồi thành công
            resp.getWriter().println("Plan inserted successfully");
        } catch (IllegalArgumentException e) {
            resp.getWriter().println("Invalid date format");
        } catch (Exception e) {
            resp.getWriter().println("An error occurred: " + e.getMessage());
        }
    }

}
