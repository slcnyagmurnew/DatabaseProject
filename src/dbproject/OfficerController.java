/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject;

import classes.AppointmentOfficer;
import classes.Officer;
import controldb.Database;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * FXML Controller class
 *
 * @author aslis
 */
public class OfficerController implements Initializable {
    
    Database db;
    
    ObservableList<AppointmentOfficer> appointmentsList;
    
    Officer currentOfficer;
    
    @FXML
    private TableView<AppointmentOfficer> appTable = new TableView<>();
    
    @FXML
    private Button listDates; 
    
    void setOfficer(Officer officer){
        this.currentOfficer = officer;
    }
    void setOfficersList(ArrayList<AppointmentOfficer> officersList){
        this.appointmentsList = FXCollections.observableArrayList(officersList);
    }
 
    @FXML
    private void listDateAction(ActionEvent event) {
        db =  new Database();
        db.baglan();
        setOfficersList(db.currentAppointments(currentOfficer.getId()));
        appTable.setItems(appointmentsList);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        db =  new Database();
        db.baglan();
        
        appTable.setEditable(true);
        
        TableColumn<AppointmentOfficer, String> nameCol = new TableColumn<AppointmentOfficer, String>("Ad");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("isim"));
        nameCol.setPrefWidth(90);
        
        TableColumn<AppointmentOfficer, String> surnameCol = new TableColumn<AppointmentOfficer, String>("Soyad");
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("soyisim"));
        surnameCol.setPrefWidth(90);
        
        TableColumn<AppointmentOfficer, String> roleCol = new TableColumn<AppointmentOfficer, String>("Adres");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("adres"));
        roleCol.setPrefWidth(90);
        
        TableColumn<AppointmentOfficer, String> startCol= new TableColumn<AppointmentOfficer, String>("Başlangıç");
        startCol.setCellValueFactory(new PropertyValueFactory<>("start_hour"));
        startCol.setPrefWidth(90);
        
        TableColumn<AppointmentOfficer, String> endCol = new TableColumn<AppointmentOfficer, String>("Bitiş");
        endCol.setCellValueFactory(new PropertyValueFactory<>("finish_hour"));
        endCol.setPrefWidth(90);
        
        appTable.getColumns().addAll(nameCol, surnameCol, roleCol, startCol, endCol);      
        }    
}

