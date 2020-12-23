package dbproject;


import javafx.scene.control.Alert;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author selca
 */
public class RegisterAlert {
    
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
    
    public static Alert IdError(){
        setAlert(AlertEnums.IdError.getAlertMessage(), AlertEnums.IdError.getAlertType());
        return alert;
    }
    
    public static Alert PhoneError(){
        setAlert(AlertEnums.PhoneError.getAlertMessage(), AlertEnums.PhoneError.getAlertType());
        return alert;
    }
    
    public static Alert EmailError(){
        setAlert(AlertEnums.EmailError.getAlertMessage(), AlertEnums.EmailError.getAlertType());
        return alert;
    }
    
    public static Alert DateError(){
        setAlert(AlertEnums.DateError.getAlertMessage(), AlertEnums.DateError.getAlertType());
        return alert;
    }
    
    public static Alert RegisterError(){
        setAlert(AlertEnums.RegisterError.getAlertMessage(), AlertEnums.RegisterError.getAlertType());
        return alert;
    }
    
    public static Alert IdExistsError(){
        setAlert(AlertEnums.IdExistsError.getAlertMessage(), AlertEnums.IdExistsError.getAlertType());
        return alert;
    }
    
    public static Alert PhoneExistsError(){
        setAlert(AlertEnums.PhoneExistsError.getAlertMessage(), AlertEnums.PhoneExistsError.getAlertType());
        return alert;
    }
    
    public static Alert EmailExistsError(){
        setAlert(AlertEnums.EmailExistsError.getAlertMessage(), AlertEnums.EmailExistsError.getAlertType());
        return alert;
    }
    
    public static Alert UserAdded(){
        setAlert(AlertEnums.UserAdded.getAlertMessage(), AlertEnums.UserAdded.getAlertType());
        return alert;
    }
    
    public static Alert NoSelectedDiseaseError(){
        setAlert(AlertEnums.NoSelectedDiseaseError.getAlertMessage(), AlertEnums.NoSelectedDiseaseError.getAlertType());
        return alert;
    }
    
    public static Alert UserInfoAdded(){
        setAlert(AlertEnums.UserInfoAdded.getAlertMessage(), AlertEnums.UserInfoAdded.getAlertType());
        return alert;
    }
}
