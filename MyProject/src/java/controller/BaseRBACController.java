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
            resp.sendError(403, "you do not have right to access this feature!");
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        if (isAuthorized(req, loggeduser)) {
            doAuthorizedGet(req, resp, loggeduser);
        } else {
            resp.sendError(403, "you do not have right to access this feature!");
        }
    }

    private boolean isAuthorized(HttpServletRequest req, User loggeduser) {
        UserDBContext db = new UserDBContext();
        ArrayList<Department> depts = db.getDepts(loggeduser.getUsername());
        loggeduser.setDepts(depts);
        String c_url = req.getServletPath();
        for (Department dept : depts) {
            for (Feature feature : dept.getFeatures()) {
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
