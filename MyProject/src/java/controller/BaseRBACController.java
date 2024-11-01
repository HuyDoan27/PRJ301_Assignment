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

    private boolean isAuthorized(HttpServletRequest req, User loggeduser) {
        UserDBContext db = new UserDBContext();

        ArrayList<Department> depts = db.getDepts(loggeduser.getUsername());  
        loggeduser.setDepts(depts.get(0)); 

        String c_url = req.getServletPath();

        for (Department dept : depts) {
            ArrayList<Feature> features = dept.getFeatures();  
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
    
    
    
    @Override
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
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        if (isAuthorized(req, loggeduser)) {
            doAuthorizedPost(req, resp, loggeduser);
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

}
