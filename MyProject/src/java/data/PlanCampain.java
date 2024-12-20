/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class PlanCampain {
    private int camid;
    private Plan plan;
    private int quantity;
    private float estimatedeffort;
    private Product product;
    private ArrayList<ScheduleCampain> schedules = new ArrayList<>();

    public ArrayList<ScheduleCampain> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<ScheduleCampain> schedules) {
        this.schedules = schedules;
    }


    public int getCamid() {
        return camid;
    }

    public void setCamid(int camid) {
        this.camid = camid;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getEstimatedeffort() {
        return estimatedeffort;
    }

    public void setEstimatedeffort(float estimatedeffort) {
        this.estimatedeffort = estimatedeffort;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    } 

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
    
    
}
