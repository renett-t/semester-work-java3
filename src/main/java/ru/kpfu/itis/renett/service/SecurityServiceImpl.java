package ru.kpfu.itis.renett.service;

import ru.kpfu.itis.renett.exceptions.InvalidSignInDataException;
import ru.kpfu.itis.renett.models.AuthModel;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.repository.AuthRepository;
import ru.kpfu.itis.renett.repository.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

public class SecurityServiceImpl implements SecurityService {
    private final UserRepository userRepository;
    private final RegistrationDataValidator registrationDataValidator;
    private final AuthRepository authRepository;

    public SecurityServiceImpl(UserRepository userRepository, AuthRepository authRepository) {
        this.userRepository = userRepository;
        this.registrationDataValidator = new RegistrationDataValidator();
        this.authRepository = authRepository;
    }

    @Override
    public UUID signUp(User user, HttpSession session) {
        UUID uuid = null;
        if (registrationDataValidator.isUserParametersCorrect(user.getFirstName(), user.getSecondName(), user.getEmail(), user.getLogin())) {
            user.setPasswordHash(user.getPasswordHash()); // TODO - PASSWORD HASHING
            uuid = UUID.randomUUID();
            userRepository.save(user);
            AuthModel authModel = AuthModel.builder().login(user.getLogin()).uuid(uuid).build();
            authRepository.save(authModel);
            session.setAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME, user);
        }
        return uuid;
    }

    @Override
    public UUID signIn(String login, String password, HttpSession session) {
        UUID uuid = null;

        User sessionUser = (User) session.getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
        if (sessionUser != null) {
            if (!sessionUser.getLogin().equals(login)) {
                session.removeAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
            } else {
                uuid = UUID.randomUUID();
                authRepository.update(AuthModel.builder().login(login).uuid(uuid).build());
                return uuid;
            }
        }

        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (!optionalUser.isPresent()) {
            throw new InvalidSignInDataException("Пользователь с логином " + login + " не был найден. Повторите попытку.");
        }
        if (password.equals(optionalUser.get().getPasswordHash())) { // TODO - password hashing
            uuid = UUID.randomUUID();
            Optional<AuthModel> authModelFromRepo = authRepository.findAuthModelByLogin(login);
            if (authModelFromRepo.isPresent()) {
                authRepository.update(AuthModel.builder().login(login).uuid(uuid).build());
            } else {
                authRepository.save(AuthModel.builder().login(login).uuid(uuid).build());
            }
            session.setAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME, optionalUser.get());
            return uuid;

        } else {
            throw new InvalidSignInDataException("Неверный пароль.");
        }
    }

    @Override
    public boolean isAuthenticated(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME) == null) {
            return false;
        } else {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.COOKIE_AUTHORIZED_NAME)) {
                    // TODO - доделать метод проверки акторизации
                    //  получить пользователя по токену в куке
                    //  если в сесии тот же логин, что и у пользователя - всё ок, если нет - кто-то хотел украсть куку, делаем логаут
                    //  а если есть пользователь в сессии, но куку удалили..... (((
                    // FFFFFFF

                }
            }
        }

        return false;
    }

    @Override
    public void logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        User userToLogOut = (User) httpSession.getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
        AuthModel authModel = AuthModel.builder().login(userToLogOut.getLogin()).build();

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Constants.COOKIE_AUTHORIZED_NAME)) {
                authModel.setUuid(UUID.fromString(cookie.getValue()));
                authRepository.delete(authModel);
            }
        }

        httpSession.removeAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
    }
}
