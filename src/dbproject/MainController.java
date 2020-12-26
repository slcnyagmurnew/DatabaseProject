/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author selca
 */
package dbproject;

import classes.Officer;
import classes.Patient;
import controldb.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable {

    Database db;
    
    @FXML
    private Label label;

    @FXML
    private Button login;

    @FXML
    private ComboBox<String> dropdown;

    @FXML
    private Button register;
    
    @FXML
    private AnchorPane MainAnchor;
    
    ObservableList<String> loginOptions = FXCollections.observableArrayList(
            "Hasta",
            "Personel");
    
    @FXML
    private PasswordField fpassword;
    
    @FXML
    private TextField fid;

    @FXML
    void LoginAction(ActionEvent event) {
        String userType = dropdown.getValue();
        String id = fid.getText();
        String password = fpassword.getText();
        
        try {
            /*
            database de user type kolonu da olacak
            burada user type ile birlikte şifre ve kullanıcı ad kontrolu
            eger hepsi okeyse giris yapılacak
            */
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            if(isNullOrEmpty(id) || isNullOrEmpty(password)){
                LoginAlert.EmptyUserNameorPassword().showAndWait();
            }
            else{
                if(isNullOrEmpty(userType)){
                    LoginAlert.LoginError().showAndWait();
                }
                else if(userType.equals(UserTypeEnums.PATIENT.getType())){
                    Patient patient = new Patient(id, password);
                    if(db.logInPatient(patient)){
                        patient = db.getPatient(patient);
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Patient.fxml"));     
                        root = (Parent)fxmlLoader.load();          
                        PatientController controller = fxmlLoader.<PatientController>getController();
                        controller.setPatient(patient);
                        LoginAlert.LoginSuccessful().showAndWait();
                    }
                    else{
                        LoginAlert.LoginFailed().showAndWait();
                    }
                }
                else{
                    Officer officer = new Officer(id, password);
                    if(db.logInOfficer(officer)){
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Officer.fxml"));     
                        root = (Parent)fxmlLoader.load();          
                        OfficerController controller = fxmlLoader.<OfficerController>getController();
                        controller.setOfficer(officer);
                        LoginAlert.LoginSuccessful().showAndWait();
                    }
                    else{
                        LoginAlert.LoginFailed().showAndWait();
                    }
                }
            }
            login.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void RegisterAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
            register.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static boolean isNullOrEmpty(String str) {
        return !(str != null && !str.isEmpty());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dropdown.setItems(loginOptions);
        db =  new Database();
        db.baglan();
    }    

}

