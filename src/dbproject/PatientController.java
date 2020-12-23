/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject;

import classes.Appoinment;
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

/**
 * FXML Controller class
 *
 * @author selca
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PatientController implements Initializable {

    Database db;

    @FXML
    private TableView<Officer> officersTable = new TableView<>();
    
    @FXML
    private TableView<Appoinment> appoinmentsTable = new TableView<>();
    
    @FXML
    private TableView<Appoinment> currentAppointments = new TableView<>();

    @FXML
    private Button takeAppointment;
    
    @FXML
    private Button selectOfficer;

    @FXML
    private Button listOfficers;
    
    @FXML
    private Button exitBtn;
    
    @FXML
    private TextField fnewPhone;

    @FXML
    private TextField fnewEmail;

    @FXML
    private Button updateUserData;
    
    @FXML
    private Button deleteAppointment;
    
    @FXML
    private Button deleteRecord;
    
    @FXML
    private DatePicker appointmentDate;
    
    Patient currentPatient;
    
    Officer selectedOfficer;
    
    Date selectedDate;
    
    ObservableList<Appoinment> appointmentsList;
    
    ObservableList<Officer> officersArrayList;
    
    ObservableList<Appoinment> currentAppointmentsList;
    
    @FXML
    void listOfficersAction(ActionEvent event) {
        if(appointmentDate.getValue() == null){
            PatientAlert.NoSelectedDateError().showAndWait();
        }
        else{
            selectedDate = Date.valueOf(appointmentDate.getValue());
            if(!isDateValid(selectedDate)){
                PatientAlert.SelectedPastDateError().showAndWait();
            }
            else{
                setOfficersArrayList(db.getSuitableOfficers(selectedDate, currentPatient.getProvince()));
                officersTable.setItems(officersArrayList);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(selectedDate);
                appointmentsList = FXCollections.observableArrayList(
                    new Appoinment(dateString,"7:00", "9:00"), new Appoinment(dateString,"10:00", "12:00"),
                    new Appoinment(dateString,"14:00", "16:00"), new Appoinment(dateString,"17:00", "19:00"));
                selectOfficer.setDisable(false);
            }
        }
    }
    
    @FXML
    void selectOfficerAction(ActionEvent event) {
        int selectedIndex = officersTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex == -1)
            PatientAlert.NoSelectedOfficerError().showAndWait();
        else{
            this.selectedOfficer = officersArrayList.get(selectedIndex);
            appoinmentsTable.setItems(appointmentsList);
            takeAppointment.setDisable(false);
        }
    }

    @FXML
    void takeAppointmentAction(ActionEvent event) {
        int selectedIndex = appoinmentsTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex == -1)
            PatientAlert.NoSelectedHourError().showAndWait();
        else{
            Appoinment selectedAppoinment = appoinmentsTable.getSelectionModel().getSelectedItem();
            Date selectedDate = Date.valueOf(selectedAppoinment.getDate());
            // is suitable hour da kaldın
            String splitStart[] = selectedAppoinment.getStart_hour().split(":");
            Integer startHour = Integer.valueOf(splitStart[0]);
            String splitFinish[] = selectedAppoinment.getFinish_hour().split(":");
            Integer finishHour = Integer.valueOf(splitFinish[0]);
            if(db.isSuitableHour(startHour, finishHour, selectedOfficer.getId(), selectedDate.toString())){
                // take appointment
                if(db.takeAppointment(selectedOfficer.getId(), currentPatient.getId(), 
                        selectedDate.toString(), startHour, finishHour)){
                    PatientAlert.TakeAppointmentSuccess().showAndWait();
                }
                // aynı güne aynı kişiyle randevu alınamaz
                else{
                    PatientAlert.TakeAppointmentDuplicateError().showAndWait();
                }
            }
            else{
                PatientAlert.FilledSelectedHourError().showAndWait();
            }
        }
    }
    
    @FXML
    void listAppointmentsAction(ActionEvent event) {
        currentAppointmentsList = FXCollections.observableArrayList(
                db.currentPatientAppointments(currentPatient.getId()));
        currentAppointments.setItems(currentAppointmentsList);
        deleteAppointment.setDisable(false);
    }
    
    @FXML
    void deleteAppointmentAction(ActionEvent event) throws IOException {
        int selectedIndex = currentAppointments.getSelectionModel().getSelectedIndex();
        if(selectedIndex == -1)
            PatientAlert.NoSelectedDateError().showAndWait();
        else{
            Appoinment deletedAppointment = currentAppointments.getSelectionModel().getSelectedItem();
            String deleteDate = currentAppointmentsList.get(selectedIndex).getDate();
            if(db.cancelAppointment(currentPatient.getId(), deleteDate)){
                PatientAlert.DeleteAppointmentSuccess().showAndWait();
                currentAppointments.getItems().remove(deletedAppointment);
            }
        }
    }
    
    @FXML
    void deleteRecordAction(ActionEvent event) {
        Parent root;
        try {
            db.deleteUser(currentPatient.getId(), false);
            PatientAlert.DeleteUserSuccess().showAndWait();
            root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            deleteRecord.getScene().setRoot(root);
        } catch (IOException ex) {
        }
    }
    
    @FXML
    void exitAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            exitBtn.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void updateUserDataAction(ActionEvent event) {
        String email = fnewEmail.getText();
        String phone = fnewPhone.getText();
        
        if(!isNullOrEmpty(email) && !isNullOrEmpty(phone)){
            if(isPhoneValid(phone) && isEmailValid(email)){
                db.updateUserEmail(currentPatient.getId(), email);
                db.updateUserTelNo(currentPatient.getId(), phone);
                PatientAlert.UpdateEmailSuccess().showAndWait();
                PatientAlert.UpdatePhoneSuccess().showAndWait();
            }
        }
        else if(!isNullOrEmpty(phone)){
            if(isPhoneValid(phone)){
                db.updateUserTelNo(currentPatient.getId(), phone);
                PatientAlert.UpdatePhoneSuccess().showAndWait();
            }
            else{
                RegisterAlert.PhoneError().showAndWait();
            }
        }
        else if(!isNullOrEmpty(email)){
            if(isEmailValid(email)){
                db.updateUserEmail(currentPatient.getId(), email);
                PatientAlert.UpdateEmailSuccess().showAndWait();
            }
            else{
                RegisterAlert.EmailError().showAndWait();
            }
        }
        else{
            PatientAlert.EmptyUpdateFieldsError().showAndWait();
        }
        fnewEmail.clear();
        fnewPhone.clear();
    }
    
    void setPatient(Patient patient){
        this.currentPatient = patient;
    }
    
    void setOfficersArrayList(ArrayList<Officer>officersList){
        this.officersArrayList = FXCollections.observableArrayList(officersList);
    }
    
    public static boolean isNullOrEmpty(String str) {
        return !(str != null && !str.isEmpty());
    }
    
    private boolean isDateValid(Date date){
        long millis=System.currentTimeMillis();  
        Date currentDate= new Date(millis);  
        return !date.before(currentDate);
    }
    
    private boolean isPhoneValid(String phone){
        return phone.matches("[0-9]+");
    }
    
    private boolean isEmailValid(String email){
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$");
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        db =  new Database();
        db.baglan();
        officersTable.setEditable(true);
        selectOfficer.setDisable(true);
        takeAppointment.setDisable(true);
        deleteAppointment.setDisable(true);
        
        TableColumn<Officer, String> roleCol = new TableColumn<>("#");
        roleCol.setCellValueFactory(new PropertyValueFactory<Officer, String>("role"));
        roleCol.setPrefWidth(120);
        
        TableColumn<Officer, String> nameCol = new TableColumn<Officer, String>("Ad");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(120);
        
        TableColumn<Officer, String> surnameCol = new TableColumn<Officer, String>("Soyad");
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameCol.setPrefWidth(120);
        
        officersTable.getColumns().addAll(roleCol, nameCol, surnameCol);
        
        TableColumn<Appoinment, String> dateCol = new TableColumn<Appoinment, String>("Gün");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setPrefWidth(120);
        
        TableColumn<Appoinment, String> startHourCol = new TableColumn<Appoinment, String>("Başlangıç");
        startHourCol.setCellValueFactory(new PropertyValueFactory<>("start_hour"));
        startHourCol.setPrefWidth(120);
        
        TableColumn<Appoinment, String> finishHourCol = new TableColumn<Appoinment, String>("Bitiş");
        finishHourCol.setCellValueFactory(new PropertyValueFactory<>("finish_hour"));
        finishHourCol.setPrefWidth(120);
        
        appoinmentsTable.getColumns().addAll(dateCol, startHourCol, finishHourCol);
        
        TableColumn<Appoinment, String> currentdateCol = new TableColumn<Appoinment, String>("Gün");
        currentdateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        currentdateCol.setPrefWidth(120);
        
        TableColumn<Appoinment, String> currentstartHourCol = new TableColumn<Appoinment, String>("Başlangıç");
        currentstartHourCol.setCellValueFactory(new PropertyValueFactory<>("start_hour"));
        currentstartHourCol.setPrefWidth(120);
        
        TableColumn<Appoinment, String> currentfinishHourCol = new TableColumn<Appoinment, String>("Bitiş");
        currentfinishHourCol.setCellValueFactory(new PropertyValueFactory<>("finish_hour"));
        currentfinishHourCol.setPrefWidth(120);
        
        currentAppointments.getColumns().addAll(currentdateCol, currentstartHourCol, currentfinishHourCol);
    }    
}
