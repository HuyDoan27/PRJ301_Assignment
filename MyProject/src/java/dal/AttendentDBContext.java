/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.Attendent;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class AttendentDBContext extends DBContext<Attendent> {

    @Override
    public void create(Attendent model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void insert(int wsid, int quantity, double anphal) {
        String sql = "INSERT INTO [dbo].[Attendent] ([wsid], [quantity], [anphal]) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, wsid); // Gán giá trị cho tham số đầu tiên
            pstmt.setInt(2, quantity); // Gán giá trị cho tham số thứ hai
            pstmt.setDouble(3, anphal); // Gán giá trị cho tham số thứ ba

            pstmt.executeUpdate(); // Thực thi câu truy vấn
            System.out.println("Insert successful for wsid: " + wsid);
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ
            System.out.println("Insert failed for wsid: " + wsid);
        }
    }

    public void update(int wsid, int quantity, double anphal) {
        String sql = "UPDATE [dbo].[Attendent] SET [quantity] = ?, [anphal] = ? WHERE [wsid] = ?"; // Câu truy vấn SQL

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, quantity); // Gán giá trị cho tham số quantity
            pstmt.setDouble(2, anphal); // Gán giá trị cho tham số anphal
            pstmt.setInt(3, wsid); // Gán giá trị cho tham số wsid

            int rowsUpdated = pstmt.executeUpdate(); // Thực thi câu truy vấn và lấy số dòng được cập nhật
            if (rowsUpdated > 0) {
                System.out.println("Update successful for wsid: " + wsid);
            } else {
                System.out.println("No record found for wsid: " + wsid);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ
            System.out.println("Update failed for wsid: " + wsid);
        }
    }
    
    public Attendent getAttendentByWsid(int wsid) {
    String sql = "SELECT * FROM [dbo].[Attendent] WHERE [wsid] = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, wsid);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            // Tạo đối tượng Attendent từ kết quả truy vấn
            Attendent attendent = new Attendent();
            attendent.setWsid(rs.getInt("wsid"));
            attendent.setQuantity(rs.getInt("quantity"));
            attendent.setAnphal(rs.getFloat("anphal"));
            return attendent;
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Xử lý ngoại lệ
    }
    return null; // Trả về null nếu không tìm thấy bản ghi
}

}
