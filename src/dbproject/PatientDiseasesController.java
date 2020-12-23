/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject;

import classes.Patient;
import controldb.Database;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author selca
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

public class PatientDiseasesController implements Initializable{

    Database db;
    
    @FXML
    private RadioButton alzheimer;

    @FXML
    private RadioButton bloodPressure;

    @FXML
    private TextArea addedNote;

    @FXML
    private RadioButton disability;

    @FXML
    private Button completeRegister;

    @FXML
    private RadioButton diabetes;

    @FXML
    private RadioButton obesity;
    
    public Patient currentPatient;

    @FXML
    void completeAction(ActionEvent event) {
        ArrayList<String> selectedDiseases = new ArrayList<>();
        if(diabetes.isSelected())
            selectedDiseases.add(String.valueOf('T'));
        else
            selectedDiseases.add(String.valueOf('F'));
        if(alzheimer.isSelected())
            selectedDiseases.add(String.valueOf('T'));
        else
            selectedDiseases.add(String.valueOf('F'));
        if(bloodPressure.isSelected())
            selectedDiseases.add(String.valueOf('T'));
        else
            selectedDiseases.add(String.valueOf('F'));
        if(disability.isSelected())
            selectedDiseases.add(String.valueOf('T'));
        else
            selectedDiseases.add(String.valueOf('F'));
        if(obesity.isSelected())
            selectedDiseases.add(String.valueOf('T'));
        else
            selectedDiseases.add(String.valueOf('F'));
        if(!isNullOrEmpty(addedNote.getText()))
            selectedDiseases.add(addedNote.getText());
        else
            selectedDiseases.add(null);
        
        currentPatient.setDiseases(selectedDiseases);
        Parent root;
        try {
            if(!selectedDiseases.contains(String.valueOf('T'))){
                RegisterAlert.NoSelectedDiseaseError().showAndWait();
            }
            else if(db.insertUserInfo(currentPatient)){
                root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                RegisterAlert.UserInfoAdded().showAndWait();
                completeRegister.getScene().setRoot(root);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setPatient(Patient patient){
        this.currentPatient = patient;
    }
    
    public static boolean isNullOrEmpty(String str) {
        return !(str != null && !str.isEmpty());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        db = new Database();
        db.baglan();
    }    
}