package com.example.mm.bank.helper.utils;

import android.support.design.widget.TextInputLayout;
import android.util.Patterns;

public class UserInputValidation {

    /**
     *
     * @InputValidation Class Constants
     *
     *  (?=.*\d)      #   must contains one digit from 0-9
     *  (?=.*[a-z])   #   must contains one lowercase characters
     *  (?=.*[A-Z])   #   must contains one uppercase characters
     *  (?=.*[@#$%])  #   must contains one special symbols in the list "@#$%"
     *          .     #   match anything with previous condition checking
     *  {6,20}        #   length at least 6 characters and maximum of 20
     * */

    public static boolean isValidMail(String emailInput) {
        if (emailInput.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(String passwordInput) {
        if (passwordInput.isEmpty() || passwordInput.length() < 3){
            return false;
        }
        return true;
    }

    public static boolean isValidRePassword (String rePassword, String password){
        if (rePassword.isEmpty() || !rePassword.contentEquals(password)){
            return false;
        }
        return true;
    }

    public static boolean isValidMobile (String number){
        if (number.isEmpty() || !Patterns.PHONE.matcher(number).matches() || number.length() < 10 || number.length() > 11){
            return false;
        }
        return true;
    }

    public static boolean isValidName (String name){
        if (name.isEmpty() || name.length() < 3 || name.length() > 20){
            return false;
        }
        return true;
    }
}
