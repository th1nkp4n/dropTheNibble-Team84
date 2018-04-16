package edu.gatech.shelterme.model;

import org.apache.commons.validator.routines.EmailValidator;
/**
 * Created by KKhosla on 4/16/18.
 */

public class EmailCheck {
    public static boolean testEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }
}
