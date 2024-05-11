package javasrc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {

    public boolean isEmailValid(String email) {
        // Regular expression pattern for email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Create matcher object
        Matcher matcher = pattern.matcher(email);

        // Return true if email matches the pattern, else false
        return matcher.matches();
    }

    public boolean isPhoneValid(String phone) {

        if( phone.length()!=10 )
            return false;
        // Regular expression pattern for phone number validation
        String phoneRegex = "^(?:[0-9] ?){6,14}[0-9]$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(phoneRegex);

        // Create matcher object
        Matcher matcher = pattern.matcher(phone);

        // Return true if phone number matches the pattern, else false
        return matcher.matches();
    }

}