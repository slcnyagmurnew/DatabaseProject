/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject;

/**
 *
 * @author selca
 */
public enum AlertEnums {
    
    IdError("Lütfen 11 haneli, geçerli bir TC Kimlik Numarası giriniz.", "ERROR"),
    DateError("Lütfen geçerli bir doğum tarihi giriniz.","ERROR"),
    LoginError("Lütfen geçerli bir giriş tipi seçiniz.","ERROR"),
    PhoneError("Lütfen geçerli bir telefon numarası giriniz.","ERROR"),
    EmailError("Lütfen geçerli bir e-mail adresi giriniz.", "ERROR"),
    RegisterError("Lütfen gerekli alanları doldurunuz.", "ERROR"),
    IdExistsError("Bu TC Kimlik numarası zaten sistemde var!","ERROR"),
    PhoneExistsError("Bu telefon numarası zaten sistemde var!", "ERROR"),
    EmailExistsError("Bu email adresi zaten sistemde var!", "ERROR"),
    UserAdded("Kayıt işlemi başarılı!", "INFO"),
    NoSelectedDiseaseError("En az 1 tane rahatsızlık işaretlenmelidir!", "ERROR"),
    UserInfoAdded("Kayıt işlemi başarıyla tamamlandı!", "INFO"),
    EmptyUserNameorPassword("Lütfen TC Kimlik numaranızı ve şifrenizi giriniz.", "ERROR"),
    LoginSuccessful("Giriş başarılı", "CONFIRM"),
    LoginFailed("TC Kimlik Numarası veya Şifre hatalı!", "WARN"),
    NoSelectedOfficerError("Lütfen bir görevli seçiniz!", "ERROR"),
    NoSelectedDateError("Lütfen randevu bir tarihi seçiniz!","ERROR"),
    SelectedPastDateError("Geçmiş bir güne randevu verilemez!", "ERROR"),
    NoSelectedHourError("Lütfen bir randevu saati seçiniz!", "ERROR"),
    FilledSelectedHourError("Randevu saati doludur, lütfen başka bir saat seçiniz.", "WARN"),
    TakeAppointmentSuccess("Randevunuz başarıyla kaydedildi!", "CONFIRM"),
    TakeAppointmentDuplicateError("Aynı güne tekrar randevu alamazsınız.","WARN"),
    DeleteAppointmentSuccess("Randevu başarıyla silindi!", "INFO"),
    EmptyUpdateFieldsError("Lütfen değişiklik yapacağınız alanları doldurunuz.", "ERROR"),
    UpdateEmailSuccess("Email adresi başarıyla güncellendi.","CONFIRM"),
    UpdatePhoneSuccess("Telefon numarası başarıyla güncellendi.","CONFIRM"),
    DeleteUserSuccess("Kaydınız başarıyla silindi!", "INFO");
    
    private String alertMessage;
    
    private String alertType;

    private AlertEnums(String alertMessage, String alertType) {
        this.alertMessage = alertMessage;
        this.alertType = alertType;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }
}
