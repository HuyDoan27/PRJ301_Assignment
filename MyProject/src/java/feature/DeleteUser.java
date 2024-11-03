/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package feature;

import controller.BaseRBACController;
import dal.DBContext;
import dal.UserDBContext;
import data.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class DeleteUser extends HttpServlet {

    private DBContext<User> dbContext;

    public DeleteUser() {
        dbContext = new DBContext<User>() {
            @Override
            public void create(User model) {
                // Cài đặt phương thức create nếu cần
            }
        };
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userID = Integer.parseInt(req.getParameter("userID"));
        String username = req.getParameter("usernname");

        UserDBContext db = new UserDBContext();
        try {
            dbContext.connection.setAutoCommit(false);

            // 1. Xóa từ UserFeature
            db.deleteUserFeature(userID);

            // 2. Xóa từ Employee_User
            db.deleteEmployeeUser(userID);

            // 3. Xóa từ User
            db.deleteUser(userID);

            dbContext.connection.commit();
            req.setAttribute("message", "Đã xóa thành công user này.");
            req.getRequestDispatcher("../view/deleteUser.jsp").forward(req, resp);
        } catch (SQLException ex) {
            try {
                dbContext.connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DeleteUser.class.getName()).log(Level.SEVERE, null, ex1);
            }
            try {
                throw new SQLException("Lỗi khi xóa user và các liên kết: " + ex.getMessage(), ex);
            } catch (SQLException ex1) {
                Logger.getLogger(DeleteUser.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                dbContext.connection.setAutoCommit(true); // Khôi phục trạng thái tự động commit
            } catch (SQLException ex) {
                Logger.getLogger(DeleteUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userID"));
        String username = req.getParameter("username");

        User user = new UserDBContext().getUser(userId);

        if (user != null) {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("../view/deleteUser.jsp");
        } else {
            req.getSession().setAttribute("errorMessage", "Không tìm thấy user");
            resp.sendRedirect("../view/deleteUser.jsp");
        }
    }

//    @Override
//    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
//        int userId = Integer.parseInt(req.getParameter("userID"));
//        String username = req.getParameter("username");
//
//        User user = new UserDBContext().getUser(userId);
//
//        if (user != null && user.getUsername().equals(username)) {
//            req.setAttribute("user", user);
//        } else {
//            req.setAttribute("message", "Không tìm thấy user");
//        }
//
//        // Chuyển tiếp đến deleteUser.jsp và xóa message sau khi hiển thị
//        req.getRequestDispatcher("../view/deleteUser.jsp").forward(req, resp);
//        req.removeAttribute("message");
//    }
//    @Override
//    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
//        int userID = Integer.parseInt(req.getParameter("userID"));
//        String username = req.getParameter("usernname");
//
//        UserDBContext db = new UserDBContext();
//        try {
//            dbContext.connection.setAutoCommit(false);
//
//            // 1. Xóa từ UserFeature
//            db.deleteUserFeature(userID);
//
//            // 2. Xóa từ Employee_User
//            db.deleteEmployeeUser(userID);
//
//            // 3. Xóa từ User
//            db.deleteUser(userID);
//
//            dbContext.connection.commit();
//            req.getSession().setAttribute("successDelete", "Đã xóa thành công user này.");
//        } catch (SQLException ex) {
//            try {
//                dbContext.connection.rollback();
//            } catch (SQLException ex1) {
//                Logger.getLogger(DeleteUser.class.getName()).log(Level.SEVERE, null, ex1);
//            }
//            try {
//                throw new SQLException("Lỗi khi xóa user và các liên kết: " + ex.getMessage(), ex);
//            } catch (SQLException ex1) {
//                Logger.getLogger(DeleteUser.class.getName()).log(Level.SEVERE, null, ex1);
//            }
//        } finally {
//            try {
//                dbContext.connection.setAutoCommit(true); // Khôi phục trạng thái tự động commit
//            } catch (SQLException ex) {
//                Logger.getLogger(DeleteUser.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
}
