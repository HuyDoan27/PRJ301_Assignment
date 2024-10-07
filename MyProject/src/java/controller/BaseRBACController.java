/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.UserDBContext;
import data.Department;
import data.Feature;
import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public abstract class BaseRBACController extends BaseRequiredAuthenticationController {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        if (isAuthorized(req, loggeduser)) {
            doAuthorizedGet(req, resp, loggeduser);
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<html>");
            out.println("<head><title>Access Denied</title></head>");
            out.println("<body>");
            out.println("<script type='text/javascript'>");
            out.println("alert('Bạn không được phép sử dụng chức năng này!');");
            out.println("window.history.back();"); // Quay lại trang trước
            out.println("</script>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        if (isAuthorized(req, loggeduser)) {
            doAuthorizedGet(req, resp, loggeduser);
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<html>");
            out.println("<head><title>Access Denied</title></head>");
            out.println("<body>");
            out.println("<script type='text/javascript'>");
            out.println("alert('Bạn không được phép sử dụng chức năng này!');");
            out.println("window.history.back();"); // Quay lại trang trước
            out.println("</script>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private boolean isAuthorized(HttpServletRequest req, User loggeduser) {
        UserDBContext db = new UserDBContext();

        // Lấy danh sách Department và gán cho loggeduser
        ArrayList<Department> depts = db.getDepts(loggeduser.getUsername());  // Giả sử hàm getDepts trả về danh sách Department
        loggeduser.setDepts(depts.get(0));  // Nếu chỉ cần gán 1 Department cho loggeduser

        // Lấy URL hiện tại
        String c_url = req.getServletPath();

        // Duyệt qua từng Department trong danh sách
        for (Department dept : depts) {
            ArrayList<Feature> features = dept.getFeatures();  // Lấy danh sách Feature từ Department
            for (Feature feature : features) {
                if (feature.getUrl().equals(c_url)) {
                    return true;
                }
            }
        }

        return false;
    }

    protected abstract void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException;

    protected abstract void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException;

}
