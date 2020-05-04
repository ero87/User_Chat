package am.vtc.userchat.util;

import org.apache.commons.validator.routines.EmailValidator;

public class DataValidator {

    private DataValidator() {
        throw new SecurityException("Can't instantiate this class.");
    }

    public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

    public static boolean isValidEmail(String value) {
        return EmailValidator.getInstance().isValid(value);
    }
}
