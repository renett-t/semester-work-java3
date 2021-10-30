package ru.kpfu.itis.renett.service;

import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.repository.ArticleRepository;
import ru.kpfu.itis.renett.repository.AuthRepository;
import ru.kpfu.itis.renett.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private ArticleRepository articleRepository;
    private AuthRepository authRepository;

    public UserServiceImpl(UserRepository userRepository, ArticleRepository articleRepository, AuthRepository authRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.authRepository = authRepository;
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    // TODO: IMPLEMENTATION OF USER_SERVICE'S METHODS
    @Override
    public void editUserData(User user) {

    }

    @Override
    public void deleteUser(User user, HttpServletRequest servletRequest) {

    }

    @Override
    public void getLikedArticles(User user) {

    }

    @Override
    public void getDislikedArticles(User user) {

    }

    @Override
    public void getCreatedArticles(User user) {

    }
}
