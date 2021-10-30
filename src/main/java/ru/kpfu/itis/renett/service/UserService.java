package ru.kpfu.itis.renett.service;

import ru.kpfu.itis.renett.models.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    User getUserById(int id);
    void editUserData(User user);
    void deleteUser(User user, HttpServletRequest servletRequest);
    void getLikedArticles(User user);
    void getDislikedArticles(User user);
    void getCreatedArticles(User user);
}
