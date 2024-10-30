/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.Attendent;
import data.WorkerSchedule;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class WorkerScheduleDBContext extends DBContext<WorkerSchedule> {

    public List<WorkerSchedule> getWorkerScheduleByScid(int scid) {
        List<WorkerSchedule> workerScheduleList = new ArrayList<>();
        String sql = "SELECT sc.scid_new, ws.wsid_new , ws.eid, e.ename, ws.quantity, a.quantity as attendentQuantity, a.anphal \n"
                + "		   From [dbo].[ScheduleCampain] sc\n"
                + "                left join [dbo].[WorkerSchedule] ws on ws.scid = sc.scid_new\n"
                + "                LEFT JOIN [dbo].[Attendent] a ON a.wsid = ws.wsid_new \n"
                + "                LEFT JOIN [dbo].[Employee] e ON e.eid = ws.eid \n"
                + "                WHERE sc.scid_new = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, scid);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                WorkerSchedule workerSchedule = new WorkerSchedule();
                workerSchedule.setWsid(resultSet.getInt("wsid_new"));
                workerSchedule.setScid(resultSet.getInt("scid_new"));
                workerSchedule.setEid(resultSet.getInt("eid"));
                workerSchedule.setEname(resultSet.getString("ename"));

                int quantity = resultSet.getObject("quantity") != null ? resultSet.getInt("quantity") : 0;
                workerSchedule.setQuantity(quantity);

                int attendentQuantity = resultSet.getObject("attendentQuantity") != null ? resultSet.getInt("attendentQuantity") : 0;
                workerSchedule.setAttendentQuantity(attendentQuantity);

                float anphal = resultSet.getObject("anphal") != null ? resultSet.getFloat("anphal") : 0.0f;
                workerSchedule.setAnphal(anphal);

                if (quantity > 0) {
                    workerSchedule.setAnphal((float) attendentQuantity / quantity);
                } else {
                    workerSchedule.setAnphal(0.0f); 
                }

                workerScheduleList.add(workerSchedule);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workerScheduleList;
    }

    @Override
    public void create(WorkerSchedule model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
