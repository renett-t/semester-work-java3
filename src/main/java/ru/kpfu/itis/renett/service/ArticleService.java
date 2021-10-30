package ru.kpfu.itis.renett.service;

import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Comment;
import ru.kpfu.itis.renett.models.User;

import java.util.List;

public interface ArticleService {
    Article getArticleById(int id);
    List<Article> getUsersArticles(User user);
    List<Article> getLikedArticles(User user);
    List<Article> getAllArticles();
    // TODO
    List<Article> getPortionOfArticles();

    void createArticle(Article newArticle);
    void deleteArticle(Article articleToDelete);
    void editArticle(Article editedArticle);
    void likeArticle(Article likedArticle, User user);
    void dislikeArticle(Article dislikedArticle, User user);
    boolean isArticleLikedByUser(Article article, User user);

    // what if i create another service class for comments...
    List<Article> getArticleComments(Article article);
    void createComment(Comment newComment);
    void deleteComment(Comment commentToDelete);
    void editComment(Comment editedComment);
}
