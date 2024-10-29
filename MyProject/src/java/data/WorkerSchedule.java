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
public class WorkerSchedule {
    private int wsid;
    private int scid;
    private int eid ;
    private String ename;
    private int quantity; 
    private int attendentQuantity; 
    private float anphal;

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getAttendentQuantity() {
        return attendentQuantity;
    }

    public void setAttendentQuantity(int attendentQuantity) {
        this.attendentQuantity = attendentQuantity;
    }

    public float getAnphal() {
        return anphal;
    }

    public void setAnphal(float anphal) {
        this.anphal = anphal;
    }

    
    
    public int getWsid() {
        return wsid;
    }

    public void setWsid(int wsid) {
        this.wsid = wsid;
    }

    public int getScid() {
        return scid;
    }

    public void setScid(int scid) {
        this.scid = scid;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
