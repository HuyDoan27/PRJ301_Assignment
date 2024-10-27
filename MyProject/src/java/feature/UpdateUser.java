/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package feature;

import dal.UserDBContext;
import data.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
public class UpdateUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eidStr = req.getParameter("employeeID");
        String uidStr = req.getParameter("userID");

        int eid = Integer.parseInt(eidStr);
        int uid = Integer.parseInt(uidStr);

        UserDBContext userDB = new UserDBContext();
        User u = userDB.getUserByEidUid(eid, uid);

        if (u == null) {
            req.getSession().setAttribute("searchStatus", "notFound");
        } else {
            req.getSession().setAttribute("user", u);  // Đặt user vào session
        }

        resp.sendRedirect("../view/updateUser.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdStr = req.getParameter("userID");
        String newPassword = req.getParameter("newPassword");

        int userId = Integer.parseInt(userIdStr);

        UserDBContext userDB = new UserDBContext();
        boolean isUpdated = userDB.updatePassword(userId, newPassword);

        if (isUpdated) {
            req.getSession().setAttribute("updateStatus", "Password updated successfully.");
        } else {
            req.getSession().setAttribute("updateStatus", "Failed to update password.");
        }

        resp.sendRedirect("../view/updateUser.jsp");
    }

}
