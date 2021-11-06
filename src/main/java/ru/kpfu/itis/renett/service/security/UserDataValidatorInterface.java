package ru.kpfu.itis.renett.service.security;

import ru.kpfu.itis.renett.exceptions.InvalidRegistrationDataException;

public interface UserDataValidatorInterface {
    boolean isNameCorrect(String name) throws InvalidRegistrationDataException;
    boolean isEmailCorrect(String email) throws InvalidRegistrationDataException;
    boolean isLoginCorrect(String login) throws InvalidRegistrationDataException;
    boolean isUserParametersCorrect(String firstName, String secondName,
                                    String email, String login, String password, String repeatedPassword) throws InvalidRegistrationDataException;
}
