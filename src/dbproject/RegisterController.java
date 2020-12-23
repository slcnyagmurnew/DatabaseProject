/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject;

import classes.Patient;
import controldb.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
/**
 * FXML Controller class
 *
 * @author selca
 */
public class RegisterController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    Database db;
    
    @FXML
    private TextField fid;

    @FXML
    private TextField fsurname;

    @FXML
    private TextField fname;

    @FXML
    private TextField faddress;

    @FXML
    private TextField fphone;

    @FXML
    private DatePicker fbirthday;

    @FXML
    private Button registerAction;

    @FXML
    private TextField femail;

    @FXML
    private RadioButton f_male;

    @FXML
    private PasswordField fpassword;

    @FXML
    private RadioButton f_female;
    
    @FXML
    private ComboBox<String> fprovince;
    
    @FXML
    private Button backBtn;
    
    ToggleGroup toggleGroup = new ToggleGroup();
    
    @FXML
    void onRegister(ActionEvent event) throws IOException {
        String name = fname.getText();
        String surname = fsurname.getText();
        String id = fid.getText();
        String province = fprovince.getValue();
        String address = faddress.getText();
        String gender;
        if(f_female.isSelected())
            gender = f_female.getText();
        else
            gender = f_male.getText();
        LocalDate date = fbirthday.getValue();
        String email = femail.getText();
        String password = fpassword.getText();
        String phone = fphone.getText();
        Patient patient = new Patient();
        patient.setId(id);
        patient.setName(name);
        patient.setSurname(surname);
        patient.setProvince(province);
        if(!isNullOrEmpty(email))
            patient.setEmail(email);
        patient.setGender(gender);
        patient.setPhone(phone);
        patient.setPassword(password);
        patient.setAddress(address);
        if(date != null)
            patient.setBirthDate(Date.valueOf(date));
        if(isValidUser(patient)){
            Parent root;
            try {
                if(db.insertUser(patient)){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PatientDiseases.fxml"));     
                    root = (Parent)fxmlLoader.load();          
                    PatientDiseasesController controller = fxmlLoader.<PatientDiseasesController>getController();
                    controller.setPatient(patient);
                    RegisterAlert.UserAdded().showAndWait();
                    registerAction.getScene().setRoot(root);
                }
                else{
                    root = FXMLLoader.load(getClass().getResource("Register.fxml"));
                    registerAction.getScene().setRoot(root);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    ObservableList<String> provinceOptions = FXCollections.observableArrayList(
            "Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir", "Bilecik", 
            "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Edirne", 
            "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Isparta", 
            "İçel", "İstanbul", "İzmir", "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya",
                    "Kütahya", "Malatya", "Manisa", "K.maraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize",
                    "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa",
                    "Uşak", "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale", "Batman","Şırnak", 
                    "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce");
    
    private boolean isValidUser(Patient patient){
        if(isNullOrEmpty(patient.getId()) 
                || isNullOrEmpty(patient.getName()) 
                || isNullOrEmpty(patient.getSurname()) 
                || isNullOrEmpty(patient.getProvince())
                || isNullOrEmpty(patient.getAddress())
                || isNullOrEmpty(patient.getGender())
                || patient.getBirthDate() == null 
                || isNullOrEmpty(patient.getPhone()) 
                || isNullOrEmpty(patient.getPassword()))
        {
            RegisterAlert.RegisterError().showAndWait();
            return false;
        }
        return true;
    }
    
    @FXML
    void backAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        backBtn.getScene().setRoot(root);
    }
    
    public static boolean isNullOrEmpty(String str) {
        return !(str != null && !str.isEmpty());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fprovince.setItems(provinceOptions);
        f_female.setToggleGroup(toggleGroup);
        f_male.setToggleGroup(toggleGroup);
        db = new Database();
        db.baglan();
    }    
    
}
