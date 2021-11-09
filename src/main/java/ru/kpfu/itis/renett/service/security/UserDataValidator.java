package ru.kpfu.itis.renett.service.security;

import ru.kpfu.itis.renett.exceptions.InvalidUserDataException;
import ru.kpfu.itis.renett.repository.UserRepository;

import java.util.regex.Pattern;

public class UserDataValidator implements UserDataValidatorInterface {
    private final UserRepository userRepository;
    private static final String EMAIL_PATTERN = "((?:[\\w\\d\\.\\-+\\/%!]*)|(?:[\\w\\d\\.\\-+\\/%!]*\\\"[\\w\\d\\.\\-+\\/%!\\s]+\\\"[\\w\\d\\.\\-+\\/%!]*))@((?:\\w|\\d)[\\w\\d-]{0,61}(?:\\w|\\d)).((?:\\w|\\d)[\\w\\d-]{0,61}(?:\\w|\\d))";

    public UserDataValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUserParametersCorrect(String firstName, String secondName, String email, String login, String password, String repeatedPassword) throws InvalidUserDataException {
        return isNameCorrect(firstName)
                && isNameCorrect(secondName)
                && isEmailCorrect(email)
                && isLoginCorrect(login)
                && arePasswordsCorrect(password, repeatedPassword);
    }

    @Override
    public boolean isUserParametersCorrect(String firstName, String secondName, String email, String login) {
        return isNameCorrect(firstName)
                && isNameCorrect(secondName)
                && isEmailCorrect(email)
                && isLoginCorrect(login);
    }

    @Override
    public boolean arePasswordsCorrect(String password, String repeatedPassword) {
        if (password != null && repeatedPassword != null) {
            if (password.length() < 5) {
                throw new InvalidUserDataException("Пароль не может быть меньше 5 символов");
            } else if (!password.equals(repeatedPassword)) {
                throw new InvalidUserDataException("Пароли не совпадают :0");
            }
        } else {
            throw new InvalidUserDataException("Пароль не должен быть пустым");
        }
        return true;
    }

    public boolean isNameCorrect(String name) throws InvalidUserDataException {
        if (name != null && name.length() > 1) {
            return true;
        } else {
            throw new InvalidUserDataException("Invalid value for name: \'" + name + "\'");
        }
    }

    public boolean isEmailCorrect(String email) throws InvalidUserDataException {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new InvalidUserDataException("Пользователь с почтой " + email + "уже был зарегистрирован.");
        }
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (email != null && email.length() > 5 && pattern.matcher(email).matches()) {
            return true;
        } else {
            throw new InvalidUserDataException("Invalid value for email: \'" + email + "\'");
        }
    }

    public boolean isLoginCorrect(String login) throws InvalidUserDataException {
        if (login != null && login.length() >= 5) {
            if (userRepository.findByLogin(login).isPresent()) {
                throw new InvalidUserDataException("Пользователь с логином " + login + "уже был зарегистрирован.");
            }
            return true;
        } else {
            throw new InvalidUserDataException("Invalid value for login. It should contain more or equal than 5 characters");
        }
    }
}
