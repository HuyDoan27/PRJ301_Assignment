/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package feature;

import controller.BaseRBACController;
import controller.BaseRBACController;
import dal.UserDBContext;
import data.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class listAllUser extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        UserDBContext db = new UserDBContext();
        ArrayList<User> users = db.getUsers();

        req.getSession().setAttribute("users", users);
        req.getRequestDispatcher("/view/listalluser.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        UserDBContext db = new UserDBContext();
        ArrayList<User> users = db.getUsers();

        req.getSession().setAttribute("users", users);
        req.getRequestDispatcher("/view/listalluser.jsp").forward(req, resp);
    }

}
