package ru.kpfu.itis.renett.service.security;

import ru.kpfu.itis.renett.exceptions.InvalidSignInDataException;
import ru.kpfu.itis.renett.models.AuthModel;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.repository.AuthRepository;
import ru.kpfu.itis.renett.repository.UserRepository;
import ru.kpfu.itis.renett.service.Constants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

public class SecurityServiceImpl implements SecurityService {
    private final UserRepository userRepository;
    private final RegistrationDataValidator registrationDataValidator;
    private final AuthRepository authRepository;
    private final EncoderInterface encoder;

    public SecurityServiceImpl(UserRepository userRepository, AuthRepository authRepository, EncoderInterface encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.registrationDataValidator = new RegistrationDataValidator();
        this.authRepository = authRepository;
    }

    @Override
    public void signUp(User user, HttpServletRequest request, HttpServletResponse response) {
        UUID uuid = null;
        HttpSession session = request.getSession(true);
        String rawPass = (String) request.getAttribute("password");
        String repeatedPass = (String) request.getAttribute("repeatedPassword");
        if (registrationDataValidator.isUserParametersCorrect(user.getFirstName(), user.getSecondName(), user.getEmail(), user.getLogin(), rawPass, repeatedPass)) {
            user.setPasswordHash(encoder.encodeString(rawPass));
            uuid = UUID.randomUUID();
            userRepository.save(user);
            AuthModel authModel = AuthModel.builder().login(user.getLogin()).uuid(uuid).build();
            authRepository.save(authModel);
            session.setAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME, user);
            setAuthorizedCookieToResponse(uuid, response);
        }
    }

    @Override
    public void signIn(String login, String password, HttpServletRequest request, HttpServletResponse response) {
        UUID uuid = null;
        HttpSession session = request.getSession(true);

        User sessionUser = (User) session.getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
        if (sessionUser != null) {
            if (!sessionUser.getLogin().equals(login)) {
                session.removeAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
            } else {
                uuid = UUID.randomUUID();
                authRepository.update(AuthModel.builder().login(login).uuid(uuid).build());
                setAuthorizedCookieToResponse(uuid, response);
                return;
            }
        }

        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (!optionalUser.isPresent()) {
            throw new InvalidSignInDataException("Пользователь с логином " + login + " не был найден. Повторите попытку.");
        }
        String passHash = encoder.encodeString(password);
        if (passHash.equals(optionalUser.get().getPasswordHash())) {
            uuid = UUID.randomUUID();
            Optional<AuthModel> authModelFromRepo = authRepository.findAuthModelByLogin(login);
            if (authModelFromRepo.isPresent()) {
                authRepository.update(AuthModel.builder().login(login).uuid(uuid).build());
            } else {
                authRepository.save(AuthModel.builder().login(login).uuid(uuid).build());
            }
            session.setAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME, optionalUser.get());
            setAuthorizedCookieToResponse(uuid, response);
        } else {
            throw new InvalidSignInDataException("Неверный пароль.");
        }
    }

    @Override
    public boolean isAuthenticated(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        User userFromSession;
        if (httpSession == null) {
            return false;
        }
        userFromSession = (User) httpSession.getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
        if (userFromSession != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession(false);
        User userToLogOut = (User) httpSession.getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
        AuthModel authModel = AuthModel.builder().login(userToLogOut.getLogin()).build();

        if (userToLogOut != null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.COOKIE_AUTHORIZED_NAME)) {
                    try {
                        authModel.setUuid(UUID.fromString(cookie.getValue()));
                    } catch (IllegalArgumentException ignored) {   // если в куках неправильные данные, без разницы, её всё равно нужно удалить
                    }
                    authRepository.delete(authModel);
                    cookie.setMaxAge(1);
                }
            }
        }

        httpSession.removeAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
    }

    private void setAuthorizedCookieToResponse(UUID uuid, HttpServletResponse response) {
        Cookie authorizedCookie = new Cookie(Constants.COOKIE_AUTHORIZED_NAME, uuid.toString());
        authorizedCookie.setMaxAge(60*60*24);
        response.addCookie(authorizedCookie);
    }
}