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
public class DeleteUser extends BaseRBACController {

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
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        int userID = Integer.parseInt(req.getParameter("userID"));
        String username = req.getParameter("usernname");

        UserDBContext db = new UserDBContext();
        try {
            dbContext.connection.setAutoCommit(false); // Bắt đầu transaction

            // 1. Xóa từ UserFeature
            db.deleteUserFeature(userID);

            // 2. Xóa từ Employee_User
            db.deleteEmployeeUser(userID);

            // 3. Xóa từ User
            db.deleteUser(userID);

            dbContext.connection.commit(); // Commit transaction sau khi xóa thành công
        } catch (SQLException ex) {
            try {
                dbContext.connection.rollback(); // Rollback nếu có lỗi xảy ra
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
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        int userID = Integer.parseInt(req.getParameter("userID"));
        String username = req.getParameter("usernname");

        UserDBContext db = new UserDBContext();
        try {
            dbContext.connection.setAutoCommit(false); // Bắt đầu transaction

            // 1. Xóa từ UserFeature
            db.deleteUserFeature(userID);

            // 2. Xóa từ Employee_User
            db.deleteEmployeeUser(userID);

            // 3. Xóa từ User
            db.deleteUser(userID);

            dbContext.connection.commit(); // Commit transaction sau khi xóa thành công
            req.getSession().setAttribute("successDelete", "Đã xóa thành công user này.");
        } catch (SQLException ex) {
            try {
                dbContext.connection.rollback(); // Rollback nếu có lỗi xảy ra
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

}
