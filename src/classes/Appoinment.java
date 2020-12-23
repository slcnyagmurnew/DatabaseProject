/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Date;

/**
 *
 * @author selca
 */
public class Appoinment {
  //  String start_hour, String finish_hour, String officer_no, String date
    private String start_hour;
    private String finish_hour;
    private String officer_no;
    private String date;
    private String user_no;

    public Appoinment() {
    }

    public Appoinment(String start_hour, String finish_hour, String officer_no, String date, String user_no) {
        this.start_hour = start_hour;
        this.finish_hour = finish_hour;
        this.officer_no = officer_no;
        this.date = date;
        this.user_no = user_no;
    }
    
    public Appoinment(String date, String start_hour, String finish_hour){
        this.date = date;
        this.start_hour = start_hour;
        this.finish_hour = finish_hour;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @return the start_hour
     */
    public String getStart_hour() {
        return start_hour;
    }

    /**
     * @param start_hour the start_hour to set
     */
    public void setStart_hour(String start_hour) {
        this.start_hour = start_hour;
    }

    /**
     * @return the finish_hour
     */
    public String getFinish_hour() {
        return finish_hour;
    }

    /**
     * @param finish_hour the finish_hour to set
     */
    public void setFinish_hour(String finish_hour) {
        this.finish_hour = finish_hour;
    }

    /**
     * @return the officer_no
     */
    public String getOfficer_no() {
        return officer_no;
    }

    /**
     * @param officer_no the officer_no to set
     */
    public void setOfficer_no(String officer_no) {
        this.officer_no = officer_no;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the user_no
     */
    public String getUser_no() {
        return user_no;
    }

    /**
     * @param user_no the user_no to set
     */
    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }
    
    
}
