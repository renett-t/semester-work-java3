package ru.kpfu.itis.renett.service.security;

import ru.kpfu.itis.renett.exceptions.InvalidRegistrationDataException;
import ru.kpfu.itis.renett.exceptions.InvalidSignInDataException;
import ru.kpfu.itis.renett.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecurityService {
    void signUp(User user, HttpServletRequest request, HttpServletResponse response) throws InvalidRegistrationDataException;
    void signIn(String login, String password, HttpServletRequest request, HttpServletResponse response) throws InvalidSignInDataException;
    boolean isAuthenticated(HttpServletRequest request);
    void logout(HttpServletRequest request, HttpServletResponse response);
}
