/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Plan {
    private int plid;
    private Date start_day;
    private Date end_day;
    private Department dept;
    
    private ArrayList<PlanCampain> campains = new ArrayList<>();

    public int getPlid() {
        return plid;
    }

    public void setPlid(int plid) {
        this.plid = plid;
    }

    public Date getStart_day() {
        return start_day;
    }

    public void setStart_day(Date start_day) {
        this.start_day = start_day;
    }

    public Date getEnd_day() {
        return end_day;
    }

    public void setEnd_day(Date end_day) {
        this.end_day = end_day;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    

    public ArrayList<PlanCampain> getCampains() {
        return campains;
    }

    public void setCampains(ArrayList<PlanCampain> campains) {
        this.campains = campains;
    }
    
    

}
