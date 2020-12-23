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
public enum UserTypeEnums {

    PATIENT("Hasta"), STAFF("Personel"),LoginError("Lütfen geçerli bir giriş tipi seçiniz.");
    
    private String type;

    public String getType() {
        return type;
    }

    private UserTypeEnums(String type) {
        this.type = type;
    }
}
