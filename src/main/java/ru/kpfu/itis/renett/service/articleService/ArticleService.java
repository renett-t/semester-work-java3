package ru.kpfu.itis.renett.service.articleService;

import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Comment;
import ru.kpfu.itis.renett.models.Tag;
import ru.kpfu.itis.renett.models.User;

import java.util.List;

public interface ArticleService {
    Article getArticleById(int id);
    List<Article> getUsersArticles(User user);
    List<Article> getLikedArticles(User user);
    List<Article> getAllArticles();
    List<Article> getAllArticlesExceptUsers(User user);
    List<Article> getAllArticlesByTag(Tag tag);

    List<Tag> getAllTags();
    List<Tag> getArticleTags(Article article);

    void createArticle(Article newArticle);
    void deleteArticle(Article articleToDelete);
    void editArticle(Article editedArticle);
    void likeArticle(User user, Article likedArticle);
    void dislikeArticle(User user, Article dislikedArticle);
    boolean isArticleLikedByUser(User user, Article article);
    int getArticleLikesAmount(Article article);

    List<Comment> getArticleComments(Article article);
    List<Comment> rearrangeArticleCommentsList(List<Comment> commentList);
    void createComment(Comment newComment);
    void deleteComment(Comment commentToDelete);
    void editComment(Comment editedComment);

    Tag getTagById(int tagId);
}
