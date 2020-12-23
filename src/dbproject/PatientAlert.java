/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject;

import javafx.scene.control.Alert;

/**
 *
 * @author selca
 */
public class PatientAlert {
    
    private static Alert alert;
    
    public static void setAlert(String alertMessage, String type) {
        switch (type) {
            case "ERROR":
                alert = new Alert(Alert.AlertType.ERROR, alertMessage);
                alert.setTitle("Hata");
                alert.setHeaderText("Hata");
                break;
            case "CONFIRM":
                alert = new Alert(Alert.AlertType.CONFIRMATION, alertMessage);
                alert.setTitle("Onay");
                alert.setHeaderText("Onay");
                break;
            case "INFO":
                alert = new Alert(Alert.AlertType.INFORMATION, alertMessage);
                alert.setTitle("Bilgi");
                alert.setHeaderText("Bilgi");
                break;
            default:
                alert = new Alert(Alert.AlertType.WARNING, alertMessage);
                alert.setTitle("Uyarı");
                alert.setHeaderText("Uyarı");
                break;
        }
    }

    public Alert getAlert() {
        return alert;
    }
    
    public static Alert NoSelectedOfficerError(){
        setAlert(AlertEnums.NoSelectedOfficerError.getAlertMessage(), AlertEnums.NoSelectedOfficerError.getAlertType());
        return alert;
    }
    
    public static Alert NoSelectedDateError(){
        setAlert(AlertEnums.NoSelectedDateError.getAlertMessage(), AlertEnums.NoSelectedDateError.getAlertType());
        return alert;
    }
    
    public static Alert SelectedPastDateError(){
        setAlert(AlertEnums.SelectedPastDateError.getAlertMessage(), AlertEnums.SelectedPastDateError.getAlertType());
        return alert;
    }
    
    public static Alert NoSelectedHourError(){
        setAlert(AlertEnums.NoSelectedHourError.getAlertMessage(), AlertEnums.NoSelectedHourError.getAlertType());
        return alert;
    }
    
    public static Alert FilledSelectedHourError(){
        setAlert(AlertEnums.FilledSelectedHourError.getAlertMessage(), AlertEnums.FilledSelectedHourError.getAlertType());
        return alert;
    }
    
    public static Alert TakeAppointmentSuccess(){
        setAlert(AlertEnums.TakeAppointmentSuccess.getAlertMessage(), AlertEnums.TakeAppointmentSuccess.getAlertType());
        return alert;
    }
    
    public static Alert TakeAppointmentDuplicateError(){
        setAlert(AlertEnums.TakeAppointmentDuplicateError.getAlertMessage(), AlertEnums.TakeAppointmentDuplicateError.getAlertType());
        return alert;
    }
    
    public static Alert DeleteAppointmentSuccess(){
        setAlert(AlertEnums.DeleteAppointmentSuccess.getAlertMessage(), AlertEnums.DeleteAppointmentSuccess.getAlertType());
        return alert;
    }
    
    public static Alert EmptyUpdateFieldsError(){
        setAlert(AlertEnums.EmptyUpdateFieldsError.getAlertMessage(), AlertEnums.EmptyUpdateFieldsError.getAlertType());
        return alert;
    }
    
    public static Alert UpdateEmailSuccess(){
        setAlert(AlertEnums.UpdateEmailSuccess.getAlertMessage(), AlertEnums.UpdateEmailSuccess.getAlertType());
        return alert;
    }
    
    public static Alert UpdatePhoneSuccess(){
        setAlert(AlertEnums.UpdatePhoneSuccess.getAlertMessage(), AlertEnums.UpdatePhoneSuccess.getAlertType());
        return alert;
    }
    
    public static Alert DeleteUserSuccess(){
        setAlert(AlertEnums.DeleteUserSuccess.getAlertMessage(), AlertEnums.DeleteAppointmentSuccess.getAlertType());
        return alert;
    }
}

