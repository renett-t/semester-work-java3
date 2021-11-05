package ru.kpfu.itis.renett.service;

import ru.kpfu.itis.renett.exceptions.InvalidRegistrationDataException;

import java.util.regex.Pattern;

public class RegistrationDataValidator implements UserDataValidatorInterface {
    private static final String EMAIL_PATTERN = "((?:[\\w\\d\\.\\-+\\/%!]*)|(?:[\\w\\d\\.\\-+\\/%!]*\\\"[\\w\\d\\.\\-+\\/%!\\s]+\\\"[\\w\\d\\.\\-+\\/%!]*))@((?:\\w|\\d)[\\w\\d-]{0,61}(?:\\w|\\d)).((?:\\w|\\d)[\\w\\d-]{0,61}(?:\\w|\\d))";

    public boolean isUserParametersCorrect(String firstName, String secondName, String email, String login) throws InvalidRegistrationDataException {
        return isNameCorrect(firstName)
                && isNameCorrect(secondName)
                && isEmailCorrect(email)
                && isLoginCorrect(login);
    }

    public boolean isNameCorrect(String name) throws InvalidRegistrationDataException {
        if (name != null && name.length() > 0) {
            return true;
        } else {
            throw new InvalidRegistrationDataException("Invalid value for name: \'" + name + "\'");
        }
    }

    public boolean isEmailCorrect(String email) throws InvalidRegistrationDataException {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (email != null && email.length() > 5 && pattern.matcher(email).matches()) {
            return true;
        } else {
            throw new InvalidRegistrationDataException("Invalid value for email: \'" + email + "\'");
        }
    }

    public boolean isLoginCorrect(String login) throws InvalidRegistrationDataException {
        if (login != null && login.length() > 5) {
            return true;
        } else {
            throw new InvalidRegistrationDataException("Invalid value for login. It should contain more than 5 characters");
        }
    }
}
