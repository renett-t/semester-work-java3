package ru.kpfu.itis.renett.service;

import ru.kpfu.itis.renett.exceptions.InvalidCookieException;
import ru.kpfu.itis.renett.exceptions.InvalidRegistrationDataException;
import ru.kpfu.itis.renett.exceptions.InvalidSignInDataException;
import ru.kpfu.itis.renett.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public interface SecurityService {
    UUID signUp(User user, HttpServletRequest request, HttpServletResponse response) throws InvalidRegistrationDataException;
    UUID signIn(String login, String password, HttpServletRequest request, HttpServletResponse response) throws InvalidSignInDataException;
    boolean isAuthenticated(HttpServletRequest request);
    void logout(HttpServletRequest request, HttpServletResponse response);
}
