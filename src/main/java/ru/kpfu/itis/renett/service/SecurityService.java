package ru.kpfu.itis.renett.service;

import ru.kpfu.itis.renett.exceptions.InvalidRegistrationDataException;
import ru.kpfu.itis.renett.exceptions.InvalidSignInDataException;
import ru.kpfu.itis.renett.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public interface SecurityService {
    UUID signUp(User user, HttpSession session) throws InvalidRegistrationDataException;
    UUID signIn(String login, String password, HttpSession session) throws InvalidSignInDataException;
    boolean isAuthenticated(HttpServletRequest request);
    void logout(HttpServletRequest request);
}
