package ru.kpfu.itis.renett.service;

import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Comment;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.repository.ArticleRepository;
import ru.kpfu.itis.renett.repository.CommentRepository;
import ru.kpfu.itis.renett.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Article getArticleById(int id) {
        Optional<Article> foundArticle = articleRepository.findById(id);

        return foundArticle.orElse(null);
    }

    // TODO: IMPLEMENTATION OF ARTICLE SERVICE METHODS
    @Override
    public List<Article> getUsersArticles(User user) {
        return null;
    }

    @Override
    public List<Article> getLikedArticles(User user) {
        return null;
    }

    @Override
    public List<Article> getAllArticles() {
        return null;
    }

    @Override
    public List<Article> getPortionOfArticles() {
        return null;
    }

    @Override
    public void createArticle(Article newArticle) {

    }

    @Override
    public void deleteArticle(Article articleToDelete) {

    }

    @Override
    public void editArticle(Article editedArticle) {

    }

    @Override
    public void likeArticle(Article likedArticle, User user) {

    }

    @Override
    public void dislikeArticle(Article dislikedArticle, User user) {

    }

    @Override
    public boolean isArticleLikedByUser(Article article, User user) {
        return false;
    }

    @Override
    public List<Article> getArticleComments(Article article) {
        return null;
    }

    @Override
    public void createComment(Comment newComment) {

    }

    @Override
    public void deleteComment(Comment commentToDelete) {

    }

    @Override
    public void editComment(Comment editedComment) {

    }
}
