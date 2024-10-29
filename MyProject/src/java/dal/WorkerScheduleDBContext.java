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
public class WorkerScheduleDBContext extends DBContext<WorkerSchedule>{

    public List<WorkerSchedule> getWorkerScheduleByScid(int scid) {
        List<WorkerSchedule> workerScheduleList = new ArrayList<>();
        String sql = "SELECT ws.wsid, ws.scid, ws.eid, e.ename, ws.quantity, a.quantity as luyke, a.anphal "
                + "FROM [dbo].[WorkerSchedule] ws "
                + "LEFT JOIN [dbo].[Attendent] a ON a.wsid = ws.wsid "
                + "LEFT JOIN [dbo].[Employee] e ON e.eid = ws.eid "
                + "WHERE ws.scid = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, scid);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                WorkerSchedule workerSchedule = new WorkerSchedule();
                workerSchedule.setWsid(resultSet.getInt("wsid"));
                workerSchedule.setScid(resultSet.getInt("scid"));
                workerSchedule.setEid(resultSet.getInt("eid"));
                workerSchedule.setEname(resultSet.getString("ename")); // Tên nhân viên
                workerSchedule.setQuantity(resultSet.getInt("quantity")); // Số lượng từ WorkerSchedule
                workerSchedule.setAttendentQuantity(resultSet.getInt("attendentQuantity")); // Số lượng từ Attendent
                workerSchedule.setAnphal(resultSet.getFloat("anphal")); // anphal từ Attendent

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
