/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package feature;

import controller.BaseRBACController;
import dal.DBContext;
import dal.EmployeeDBContext;
import dal.UserDBContext;
import data.Employee;
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
public class CreateUser extends BaseRBACController {

    private DBContext<User> dbContext;

    public CreateUser() {
        dbContext = new DBContext<User>() {
            @Override
            public void create(User model) {
                // Cài đặt phương thức create nếu cần
            }
        };
    }

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        String stepParam = req.getParameter("step");
        int step = (stepParam != null) ? Integer.parseInt(stepParam) : 1;

        if (step == 1) {
            // Bước 1: Xử lý employee
            handleStep1(req, resp);
        } else if (step == 2) {
            try {
                // Bước 2: Xử lý việc tạo user và các liên kết
                handleStep2(req, resp);
            } catch (SQLException ex) {
                Logger.getLogger(CreateUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        String stepParam = req.getParameter("step");
        int step = (stepParam != null) ? Integer.parseInt(stepParam) : 1;

        if (step == 1) {
            // Bước 1: Xử lý employee
            handleStep1(req, resp);
        } else if (step == 2) {
            try {
                // Bước 2: Xử lý việc tạo user và các liên kết
                handleStep2(req, resp);
            } catch (SQLException ex) {
                Logger.getLogger(CreateUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void handleStep1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Entering handleStep1 method..."); // Dòng in đầu tiên

        String eidParam = req.getParameter("eid");
        String ename = req.getParameter("ename");

        // Kiểm tra nếu eid hoặc ename là null hoặc rỗng
        if (eidParam == null || eidParam.isEmpty() || ename == null || ename.isEmpty()) {
            req.getSession().setAttribute("error", "Employee ID and Name are required.");
            req.getSession().setAttribute("employee", null);
            req.getRequestDispatcher("../view/listalluser.jsp").forward(req, resp);
            return;
        }

        try {
            int eid = Integer.parseInt(eidParam);
            EmployeeDBContext employeeDB = new EmployeeDBContext(); // Sử dụng kết nối từ dbContext
            Employee e = employeeDB.getAEmployee(eid, ename);

            if (e != null) {
                req.getSession().setAttribute("employee", e);  // Đặt employee vào session
                req.getRequestDispatcher("../view/listalluser.jsp").forward(req, resp);  // Chuyển tới form tiếp theo
            } else {
                req.getSession().setAttribute("error", "Employee không tồn tại.");
                req.getRequestDispatcher("../view/listalluser.jsp").forward(req, resp);
            }
        } catch (NumberFormatException ex) {
            req.getSession().setAttribute("error", "Invalid Employee ID format");
            req.getRequestDispatcher("../view/listalluser.jsp").forward(req, resp);
        }
    }

    private void handleStep2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        System.out.println("Entering handleStep2 method...");

        Employee employee = (Employee) req.getSession().getAttribute("employee");

        if (employee == null) {
            req.getSession().setAttribute("error", "No employee found in session. Please check employee first.");
            resp.sendRedirect("../view/listalluser.jsp");
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            req.getSession().setAttribute("errorMessage", "Invalid input data.");
            resp.sendRedirect("../view/listalluser.jsp");
            return;
        }

        try {
            dbContext.connection.setAutoCommit(false); // Bắt đầu giao dịch

            UserDBContext userDB = new UserDBContext(); // Sử dụng kết nối từ dbContext

            // Chèn User và lấy ra uid vừa được chèn
            User user = userDB.insertUser(username, password);
            int uid = user.getUid(); // Lấy uid từ User vừa được tạo

            // Sau đó chèn vào Employee_User với uid đã lấy từ insertUser
            userDB.insertEmployee_User(employee.getEid(), uid);

            // Chèn UserFeature với uid đã có
            if (employee.getFeatures() != null && !employee.getFeatures().isEmpty()) {
                // Chèn UserFeature với uid đã có
                userDB.insertUserFeature(uid, employee.getEid(), employee.getEname());
            } else {
                System.out.println("Không có Feature nào để chèn vào UserFeature.");
            }
            dbContext.connection.commit(); // Commit transaction

            req.getSession().removeAttribute("employee");
            req.getSession().setAttribute("successMessage", "User và các tính năng liên quan đã được tạo thành công.");
            resp.sendRedirect("../view/listalluser.jsp");
        } catch (SQLException ex) {
            try {
                dbContext.connection.rollback(); // Rollback nếu có lỗi
            } catch (SQLException rollbackEx) {
                throw new ServletException("Lỗi rollback: " + rollbackEx.getMessage(), rollbackEx);
            }
            throw new ServletException("Lỗi khi thực hiện handleStep2: " + ex.getMessage(), ex);
        }
    }
}
