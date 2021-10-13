package ru.kpfu.itis.renett.service;

import ru.kpfu.itis.renett.exceptions.InvalidRegistrationDataException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataValidator {
    private static final String EMAIL_PATTERN = "((?:[\\w\\d\\.\\-+\\/%!]*)|(?:[\\w\\d\\.\\-+\\/%!]*\\\"[\\w\\d\\.\\-+\\/%!\\s]+\\\"[\\w\\d\\.\\-+\\/%!]*))@((?:\\w|\\d)[\\w\\d-]{0,61}(?:\\w|\\d)).((?:\\w|\\d)[\\w\\d-]{0,61}(?:\\w|\\d))";

    public static boolean isUserParametersCorrect(String firstName, String secondName, String email, String login, String password) throws InvalidRegistrationDataException {
        return isNameCorrect(firstName)
                && isNameCorrect(secondName)
                && isEmailCorrect(email)
                && isLoginCorrect(login)
                && isPasswordCorrect(password);
    }

    private static boolean isNameCorrect(String name) throws InvalidRegistrationDataException {
        if ((name.length() > 0) & (name.length() < 100)) {
            return true;
        } else {
            throw new InvalidRegistrationDataException("Invalid value for name: \'" + name + "\'");
        }
    }

    private static boolean isEmailCorrect(String email) throws InvalidRegistrationDataException {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (email.length() > 5 && matcher.matches()) {
            return true;
        } else {
            throw new InvalidRegistrationDataException("Invalid value for email: \'" + email + "\'");
        }
    }

    private static boolean isLoginCorrect(String login) throws InvalidRegistrationDataException {
        if (login.length() > 5) {
            if (!existInDB(login)) {
                return true;
            } else {
                throw new InvalidRegistrationDataException("User under such login \'" + login + "\' already registered in the system, try again");
            }
        } else {
            throw new InvalidRegistrationDataException("Invalid value for login. It should contain more than 5 characters");
        }
    }

    // TODO как-то вынести эту логику подальше от валидатора, этот класс вообще не должен знать о существовании бд((
    private static boolean existInDB(String login) {
//        UsersRepository usersRepository = UsersRepositoryJDBCmpl.getInstance("/home/renett/IdeaProjects/WebAppTest/src/main/java/ru/kpfu/itis/renett/repository/users-data.csv");
//
//        // странная реализация, лучше сразу при нахождения одного совпадения вернуть true и выйти из метода
//        List<User> listOfUsersWithSameLogin = usersRepository.findAll().stream().filter((User user) -> {
//            return user.getLogin().equals(login);
//        }).collect(Collectors.toList());
//
//        if (listOfUsersWithSameLogin.size() > 0) {
//            return true;
//        }
        return false;
    }

    private static boolean isPasswordCorrect(String password) throws InvalidRegistrationDataException {
        if (password.length() > 5) {
            return true;
        } else {
            throw new InvalidRegistrationDataException("Invalid value for password. It should contain more than 5 characters");
        }
    }

}
