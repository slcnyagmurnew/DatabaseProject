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
public class LoginAlert {
    
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
    
    public static Alert LoginError(){
        setAlert(AlertEnums.LoginError.getAlertMessage(), AlertEnums.LoginError.getAlertType());
        return alert;
    }
    
    public static Alert EmptyUserNameorPassword(){
        setAlert(AlertEnums.EmptyUserNameorPassword.getAlertMessage(), AlertEnums.EmptyUserNameorPassword.getAlertType());
        return alert;
    }
    
    public static Alert LoginSuccessful(){
        setAlert(AlertEnums.LoginSuccessful.getAlertMessage(), AlertEnums.LoginSuccessful.getAlertType());
        return alert;
    }
    
    public static Alert LoginFailed(){
        setAlert(AlertEnums.LoginFailed.getAlertMessage(), AlertEnums.LoginFailed.getAlertType());
        return alert;
    }
}
