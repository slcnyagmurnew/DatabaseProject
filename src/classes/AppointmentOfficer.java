/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
/**
 *
 * @author aslis
 */
public class AppointmentOfficer {
    
    private String isim;
    private String soyisim;
    private String adres;
    private String start_hour;
    private String finish_hour;
    
     public AppointmentOfficer(String isim, String soyisim, String adres, String start_hour, String finish_hour) {
        this.start_hour = start_hour;
        this.finish_hour = finish_hour;
        this.isim= isim;
        this.soyisim = soyisim;
        this.adres = adres;
    }

    public AppointmentOfficer() {
    }

    /**
     * @return the isim
     */
    public String getIsim() {
        return isim;
    }

    /**
     * @param isim the isim to set
     */
    public void setIsim(String isim) {
        this.isim = isim;
    }

    /**
     * @return the soyisim
     */
    public String getSoyisim() {
        return soyisim;
    }

    /**
     * @param soyisim the soyisim to set
     */
    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    /**
     * @return the adres
     */
    public String getAdres() {
        return adres;
    }

    /**
     * @param adres the adres to set
     */
    public void setAdres(String adres) {
        this.adres = adres;
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
    
}
