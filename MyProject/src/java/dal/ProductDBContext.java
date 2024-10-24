/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import data.Department;
import data.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ProductDBContext extends DBContext<Product> {

    public ArrayList<Product> list() {
        ArrayList<Product> products = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            if (connection == null) {
                System.out.println("Database connection is null.");
                return products;
            }

            String sql = "SELECT [pid], [pname] FROM [Product]";
            stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("pid"));
                p.setName(rs.getString("pname"));
                products.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("An SQL exception occurred: " + ex.getMessage());
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("An exception occurred while closing resources: " + ex.getMessage());
            }
        }

        if (products.isEmpty()) {
            System.out.println("No products found.");
        }
        return products;
    }

    public List<Product> getProductsByPlanId(int planId) {
        List<Product> productList = new ArrayList<>();
        try {
            String sql = "SELECT p.pid, p.pname, pc.quantity\n"
                    + "FROM [dbo].[Product] p \n"
                    + "JOIN [dbo].[PlanCampainn] pc ON p.pid = pc.pid \n"
                    + "WHERE pc.plid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, planId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setId(Integer.parseInt(rs.getString("pid")));
                p.setName(rs.getString("pname"));
                p.setQuantity(rs.getInt("quantity"));
                productList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    
    @Override
    public void create(Product model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
