/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import dbproject.RegisterAlert;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author selca
 */
public class Patient {
    private String name;
    private String surname;
    private String id; // tckno, PK 
    private String province;
    private String address;
    private ArrayList<String>diseases; // birden çok max 3 yapılabilir mi? dropdown
    private String gender; // dropdown
    private String email;
    private String phone;
    private Date birthDate;
    private String password;
    
    private final static int IDLENGTH = 11;
    private final static int MAXAGE = 120;
    private final static int MINAGE = 10;
    private final static int PHONELENGTH = 11;

    public Patient() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        if(id.length() != IDLENGTH || !id.matches("[0-9]+"))
        {
            RegisterAlert.IdError().showAndWait();
            return;
        }
        this.id = id;
    }

    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(String province) {
        this.province = province;
    }

    public void setDiseases(ArrayList<String> diseases) {
        this.diseases = diseases;
    }

    public ArrayList<String> getDiseases() {
        return diseases;
    }

    
    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$")){
            RegisterAlert.EmailError().showAndWait();
            return;
        }
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        if(phone.length() != PHONELENGTH || !phone.matches("[0-9]+"))
        {
            RegisterAlert.PhoneError().showAndWait();
            return;
        }
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setBirthDate(Date birthDate) {
        if(!isWithinRange(birthDate))
        {
            RegisterAlert.DateError().showAndWait();
            return;
        }
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    private boolean isWithinRange(Date testDate) {
        String start = "1940-01-01";
        String end = "2020-01-01";
        Date startDate = Date.valueOf(start);
        Date endDate = Date.valueOf(end);
        return !(testDate.before(startDate) || testDate.after(endDate));
    }

    public Patient(String name, String surname, String id, String province, String address, ArrayList<String> diseases, String gender, String email, String phone, Date birthDate, String password) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.province = province;
        this.address = address;
        this.diseases = diseases;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.password = password;
    }

    public Patient(String id, String password){
        this.id = id;
        this.password = password;
    }
    
}
