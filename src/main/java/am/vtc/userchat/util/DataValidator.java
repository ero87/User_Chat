package am.vtc.userchat.util;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.IntegerValidator;

import java.util.Collection;

public class DataValidator {

    private DataValidator() {
        throw new SecurityException("Can't instantiate this class.");
    }

    public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

    public static boolean isNullOrBlank(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isValidEmail(String value) {
        return EmailValidator.getInstance().isValid(value);
    }

    public static boolean isNumber(String value) {
        return IntegerValidator.getInstance().isValid(value);
    }
}
