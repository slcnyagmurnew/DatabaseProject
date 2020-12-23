package controldb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author selca
 */

import classes.Appoinment;
import classes.Officer;
import classes.Patient;
import dbproject.RegisterAlert;
import java.sql.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.sql.Date;

public class Database {

    private Connection conn;
    public static final String dburl = "jdbc:postgresql://localhost:5432/projectdb";
    public final String user = "postgres";
    private final String pass = "dbyagmur99";


    public Database () {

    }

    //veritabanına bağlantı kuran method
    public Connection baglan() {
        try {
            System.out.println("Secilen Veritabanina Baglaniliyor!");
            conn = DriverManager.getConnection(dburl, user, pass);
            System.out.println("Baglanti Basarili!");
        }
        catch(Exception e) {
            System.out.println("Baglanti Basarisiz!");
            System.out.println(e);
        }
        return conn;
    }

    //girilen sifrenin MD5 hashini donduren method
    public String MD5(String password) throws NoSuchAlgorithmException {
        String mdPassword;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for(byte b : hashInBytes) {
            sb.append(String.format("%02x",b));
        }
        mdPassword = sb.toString();
        return mdPassword;
    }

    //girilen email gecerli ise true, gecersiz ise false donduren method
    public boolean valid_email(String email) throws SQLException {
        String sql = "SELECT email FROM users WHERE email = '" + email + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()) {
                resultSet.close();
                RegisterAlert.EmailExistsError().showAndWait();
                return false;
            }
            else {
                resultSet.close();
                return true;
            }
    }

    //kullanici sisteme gecersiz tcno girerse false, gecerli tcno girerse true donduren method
    public boolean valid_tcno (String tcno) throws SQLException {
        String sql = "SELECT tcno FROM users WHERE tcno = '" + tcno + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()) {
                resultSet.close();
                RegisterAlert.IdExistsError().showAndWait();
                return false;
            }
            else {
                resultSet.close();
                return true;
            }
    } 

    //girilen telefon numarasi gecerli mi degil mi kontrol eden method
    public boolean valid_telno (String telno) throws SQLException {
        String sql = "SELECT telno FROM users WHERE telno = '" + telno + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()) {
                resultSet.close();
                RegisterAlert.PhoneExistsError().showAndWait();
                return false;
            }
            else {
                resultSet.close();
                return true;
            }
    }

    //veritabanina bilgileri verilen kullaniciyi ekleyen method
    public boolean insertUser(Patient patient) throws SQLException {
        boolean isInserted = false;
        //email, telefon, tc onceden eklenmiş ise
        if( !valid_email(patient.getEmail()) ||
            !valid_tcno(patient.getId()) ||
            !valid_telno(patient.getPhone())){
            return isInserted;
        }
        try {
            String mdPassword = this.MD5(patient.getPassword());
            String sql = "INSERT INTO users VALUES (?,?,?,?,?,?,?,?,?,?) ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, patient.getId());
            pstmt.setString(2, patient.getName());
            pstmt.setString(3, patient.getSurname());
            if(patient.getEmail() != null)
                pstmt.setString(4, patient.getEmail());
            else
                pstmt.setNull(4, Types.NULL);
            pstmt.setString(5, patient.getAddress());
            if("Kadın".equals(patient.getGender()))
                pstmt.setString(6, String.valueOf('K'));
            else
                pstmt.setString(6, String.valueOf('E'));
            pstmt.setDate(7, patient.getBirthDate());
            pstmt.setString(8, mdPassword);
            pstmt.setString(9, patient.getPhone());
            pstmt.setString(10, patient.getProvince());
            pstmt.execute();
            isInserted = true;
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return isInserted;
    }
    
    public boolean insertUserInfo(Patient patient){
        boolean isInsertedInfo = false;
        if(patient.getDiseases().isEmpty())
            return isInsertedInfo;
        
        try{
            String sql = "INSERT INTO user_info VALUES (?,?,?,?,?,?,?) ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, patient.getId());
            pstmt.setString(2, patient.getDiseases().get(0));
            pstmt.setString(3, patient.getDiseases().get(1));
            pstmt.setString(4, patient.getDiseases().get(2));
            pstmt.setString(5, patient.getDiseases().get(3));
            pstmt.setString(6, patient.getDiseases().get(4));
            if(patient.getDiseases().get(5) == null)
                pstmt.setNull(7, Types.NULL);
            else
                pstmt.setString(7, patient.getDiseases().get(5));
            pstmt.execute();
            isInsertedInfo = true;
            conn.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return isInsertedInfo;
    }
    
    //users tablosundan login icin method. Login basarili olursa true aksi taktirde false doner.
    public boolean logInPatient(Patient patient) {
        boolean isLogIn = false;
        try {
            String mdPassword = this.MD5(patient.getPassword());
            String sql = "SELECT tcno, sifre FROM users WHERE tcno = '" + patient.getId() + "' AND sifre = '" + mdPassword + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()) {
                resultSet.close();
                isLogIn = true;
            }
            resultSet.close();
        }
        catch (NoSuchAlgorithmException | SQLException e) {
        }
        return isLogIn;
    }
    
    public boolean logInOfficer(Officer officer) {
        boolean isLogIn = false;
        try {
            String mdPassword = this.MD5(officer.getPassword());
            String sql = "SELECT tcno, sifre FROM officers WHERE tcno = '" + officer.getId() + "' AND sifre = '" + mdPassword + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()) {
                resultSet.close();
                isLogIn = true;
            }
            resultSet.close();
            conn.close();
        }
        catch (NoSuchAlgorithmException | SQLException e) {
        }
        return isLogIn;
    }
    
    // hasta tcsi ve sifresiyle dbden hastanın diğer bilgileriyle eşitlenmesi
    public Patient getPatient(Patient patient){
        try{
            String sql = "SELECT * FROM users WHERE tcno = '" + patient.getId() + "'" ;
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                patient.setName(resultSet.getString(2));
                patient.setSurname(resultSet.getString(3));
                String email;
                if((email = resultSet.getString(4)) != null)
                    patient.setEmail(email);
                patient.setAddress(resultSet.getString(5));
                patient.setGender(resultSet.getString(6));
                patient.setBirthDate((Date)resultSet.getDate(7));
                patient.setPhone(resultSet.getString(9));
                patient.setProvince(resultSet.getString(10));
            }
            resultSet.close();
        }catch(SQLException ex){
        }
        return patient;
    }

    //tcno'su verilen kullanıcı veya görevliyi silen method
    public void deleteUser(String tcno, boolean isOfficer) {
        /*if(conn == null) {
            //System.out.println("Veritabani Bagli Degil!  Baglaniliyor!");
            baglan();
        }*/
        try {
            String sql;
            if(isOfficer) {
                sql = "DELETE FROM officers WHERE tcno = '" + tcno +"'";
            }
            else {
                sql = "DELETE FROM users WHERE tcno = '" + tcno +"'";
            }
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
            //conn.close();
        }
        catch (SQLException e) {
        }
    }
    
    public boolean cancelAppointment(String user_no, String date) {
        /*if(conn == null) {
            System.out.println("Veritabani Bagli Degil!  Baglaniliyor!");
            baglan();
        }*/
        boolean isCanceled = false;
        try {
            String sql = "DELETE FROM appointments WHERE user_no = '" + user_no + "' AND appointment_date = '" + date + "'";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
            isCanceled = true;
            //conn.close();
        }
        catch (SQLException e) {
        }
        return isCanceled;
    }
    
    /*
    hastanın seçtiği saate uygun gorevlilerin listesi
    bu listeyle hastanın sehrinde bulunanlar listesinde ortak olanlar gösterilir.
    */
    public ArrayList<Officer> getSuitableOfficers (Date date, String province) {
        ArrayList<Officer> suitableOfficersList = new ArrayList<>();
        String record;
        try {
            String sql = "SELECT get_suitable_officers('" + date + "', '" + province + "')";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                Officer officer = new Officer();
                record = resultSet.getString(1);
                record = record.replace("(","");    //record veri tipinden dönen parantezler kaldırlır
                record = record.replace(")","");    //record veri tipinden dönen parantezler kaldırlır
                String delims = "[,]";
                String[] tokens = record.split(delims);
                officer.setId(tokens[0]);
                officer.setName(tokens[1]);
                officer.setSurname(tokens[2]);
                officer.setRole(tokens[3]);
                suitableOfficersList.add(officer);
            }
            resultSet.close();
            //conn.close();
        }
        catch (SQLException e) {
            ///System.out.println("Hata!");
            e.printStackTrace();
        }
        return suitableOfficersList;
    }

    //tcno'su verilen kullanicinin telefon numarasini guncelleyen method
    public boolean updateUserTelNo(String tcno, String telno) {
        try {
            String sql = "UPDATE users SET telno = '" + telno + "' WHERE tcno = '" + tcno+ "' ";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
            //conn.close();
            return true;
        }
        catch (SQLException e) {
        }
        return false;
    }

    //tcno'su verilen kullanicinin email'ini guncelleyen method
    public boolean updateUserEmail(String tcno, String email) {
        try {
            String sql = "UPDATE users SET email = '" + email + "' WHERE tcno = '" + tcno+ "' ";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
            //conn.close();
            return true;
        }
        catch (SQLException e) {
        }
        return false;
    }
    
    public ArrayList<Appoinment> currentPatientAppointments(String user_no){
        ArrayList<Appoinment> currentAppointmentsList = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM appointments WHERE user_no = '" + user_no + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Appoinment appoinment = new Appoinment();
                appoinment.setOfficer_no(resultSet.getString(1));
                appoinment.setUser_no(resultSet.getString(2));
                Date date = Date.valueOf(resultSet.getString(3));
                appoinment.setDate(date.toString());
                appoinment.setStart_hour(resultSet.getString(4) + ":00");
                appoinment.setFinish_hour(resultSet.getString(5) + ":00");
                currentAppointmentsList.add(appoinment);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return currentAppointmentsList;
    }

    public boolean isSuitableHour(Integer start_hour, Integer finish_hour, String officer_no, String date) {
        boolean control = true;
        int hour_1;
        try {
            String sql = "SELECT  start_hour, finish_hour, user_no FROM appointments WHERE appointment_date = '" + date + "' AND officer_no = '" + officer_no + "' ";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next() && control) {
                hour_1 = resultSet.getInt("start_hour");
                if (start_hour == hour_1) {
                    control = false;
                }
            }
        }
        catch (SQLException e) {
        }
        return control;
    }
    //############################################################################
    /*
    pk olarak kullanılan tarih ve hasta iki defa kayıt edilemiyor, onun için kontrol eklenmeli
    */ 
    public boolean takeAppointment(String officer_no, String user_no, String date, int start_hour, int finish_hour) {
        String sql;
        Statement statement;
        try {
            sql = "SELECT user_no FROM appointments WHERE user_no = '" + user_no + "' AND appointment_date = '" + date + "' "; 
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                return false;
            }
            else{
                sql = "INSERT INTO appointments VALUES ('" + officer_no + "', '" + user_no + "', '" + date + "', " + start_hour + ", " + finish_hour + ")";
                statement = conn.createStatement();
                statement.executeUpdate(sql);
                return true;
            }
        }
        catch (SQLException e) {
        }
        return false;
    }
}

