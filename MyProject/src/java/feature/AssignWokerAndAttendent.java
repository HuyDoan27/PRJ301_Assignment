/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package feature;

import controller.BaseRBACController;
import dal.AttendentDBContext;
import dal.DBContext;
import dal.EmployeeDBContext;
import dal.WorkerScheduleDBContext;
import data.Attendent;
import data.Employee;
import data.User;
import data.WorkerSchedule;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class AssignWokerAndAttendent extends BaseRBACController {

    private DBContext<Attendent> dbContext;

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String scidStr = req.getParameter("scid");
//        int scid = Integer.parseInt(scidStr);
//
//        WorkerScheduleDBContext wsDB = new WorkerScheduleDBContext();
//        EmployeeDBContext eDB = new EmployeeDBContext();
//
//        ArrayList<Employee> employees = eDB.getEmployeeAtWS();
//        List<WorkerSchedule> workerScheduleList = wsDB.getWorkerScheduleByScid(scid);
//
//        req.getSession().setAttribute("employees", employees);
//        req.getSession().setAttribute("workerScheduleList", workerScheduleList);
//
//        resp.sendRedirect("../view/listWokerAndAttendent.jsp");
//
//    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            if (dbContext == null) {
//                dbContext = new DBContext<Attendent>() {
//                    @Override
//                    public void create(Attendent model) {
//                        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//                    }
//                };
//            }
//            dbContext.connection.setAutoCommit(false);
//
//            AttendentDBContext aDB = new AttendentDBContext();
//
//            String[] wids = req.getParameterValues("wsid");
//            String[] anphals = req.getParameterValues("alpha");
//            String[] actualQuantities = req.getParameterValues("actualQuantity");
//
//            if (wids == null || actualQuantities == null || anphals == null) {
//                throw new IllegalArgumentException("One of the parameters is missing.");
//            }
//
//            for (int i = 0; i < wids.length; i++) {
//                int wsid = Integer.parseInt(wids[i]);
//                float anphal = Float.parseFloat(anphals[i]);
//                int actualQuantity = Integer.parseInt(actualQuantities[i]);
//
//                System.out.println(wsid + "," + anphal + "," + actualQuantity);
//
//                Attendent existingRecord = aDB.getAttendentByWsid(wsid);
//
//                if (existingRecord == null) {
//                    aDB.insert(wsid, actualQuantity, anphal);
//                } else {
//                    aDB.update(wsid, actualQuantity, anphal);
//                }
//            }
//
//            dbContext.connection.commit();
//            resp.getWriter().println("insert , update successfully");
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                dbContext.connection.rollback();
//            } catch (SQLException ex) {
//                Logger.getLogger(AssignWokerAndAttendent.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } finally {
//        }
//    }
    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        try {
            if (dbContext == null) {
                dbContext = new DBContext<Attendent>() {
                    @Override
                    public void create(Attendent model) {
                        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }
                };
            }
            dbContext.connection.setAutoCommit(false);

            AttendentDBContext aDB = new AttendentDBContext();

            String[] wids = req.getParameterValues("wsid");
            String[] anphals = req.getParameterValues("alpha");
            String[] actualQuantities = req.getParameterValues("actualQuantity");

            if (wids == null || actualQuantities == null || anphals == null) {
                throw new IllegalArgumentException("One of the parameters is missing.");
            }

            for (int i = 0; i < wids.length; i++) {
                int wsid = Integer.parseInt(wids[i]);
                float anphal = Float.parseFloat(anphals[i]);
                int actualQuantity = Integer.parseInt(actualQuantities[i]);

                System.out.println(wsid + "," + anphal + "," + actualQuantity);

                Attendent existingRecord = aDB.getAttendentByWsid(wsid);

                if (existingRecord == null) {
                    aDB.insert(wsid, actualQuantity, anphal);
                } else {
                    aDB.update(wsid, actualQuantity, anphal);
                }
            }

            dbContext.connection.commit();
            resp.getWriter().println("insert , update successfully");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                dbContext.connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(AssignWokerAndAttendent.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
        }
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User loggeduser) throws ServletException, IOException {
        String scidStr = req.getParameter("scid");
        int scid = Integer.parseInt(scidStr);

        WorkerScheduleDBContext wsDB = new WorkerScheduleDBContext();
        EmployeeDBContext eDB = new EmployeeDBContext();

        ArrayList<Employee> employees = eDB.getEmployeeAtWS();
        List<WorkerSchedule> workerScheduleList = wsDB.getWorkerScheduleByScid(scid);

        req.getSession().setAttribute("employees", employees);
        req.getSession().setAttribute("workerScheduleList", workerScheduleList);

        resp.sendRedirect("../view/listWokerAndAttendent.jsp");
    }

}
