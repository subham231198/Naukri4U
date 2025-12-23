package com.example.NAOSys.CustomMethods;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator
{
    public static boolean validateEmail(String email, String role)
    {
        if(email.contains("@") && (email.contains(".com") || email.contains(".co.in")))
        {
            if(role.equalsIgnoreCase("Recruiter"))
            {
                if(!email.contains("gmail.com"))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if(role.equalsIgnoreCase("Candidate"))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean validatePhone(String phone)
    {
        if(phone.length()==10)
        {
            String regex = "^\\d{10}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(phone);
            if(matcher.matches())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public static boolean validatePinCode(String pinCode)
    {
        if(pinCode.length()==6)
        {
            String regex = "^\\d{6}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(pinCode);
            if(matcher.matches())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public static String locatDate()
    {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);

        return formattedDate;
    }
}
