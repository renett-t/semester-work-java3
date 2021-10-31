package ru.kpfu.itis.renett.service;

import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Comment;
import ru.kpfu.itis.renett.models.Tag;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.repository.ArticleRepository;
import ru.kpfu.itis.renett.repository.CommentRepository;
import ru.kpfu.itis.renett.repository.UserRepository;

import java.util.ArrayList;
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

        Article article = foundArticle.orElse(null);
        if (article != null) {
            System.out.println(article.getCommentList());
        }

        return article;
    }

    // TODO: IMPLEMENTATION OF ARTICLE SERVICE METHODS
    @Override
    public List<Article> getUsersArticles(User user) {
        if (user != null) {
            return articleRepository.findAllByOwnerId(user.getId());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Article> getLikedArticles(User user) {
        return null;
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> getAllArticlesExceptUsers(User user) {
        List<Article> all = articleRepository.findAll();
        if (user != null) {
            List<Article> users = articleRepository.findAllByOwnerId(user.getId());
            all.removeAll(users);
        }
        return all;
    }

    @Override
    public List<Article> getPortionOfArticles() {
        return null;
    }

    @Override
    public List<Tag> getAllTags() {
        return null;
    }

    @Override
    public List<Tag> getArticleTags(Article article) {
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

    private void setCommentAmount(Article article) {
        int count = 0;
        for(Comment comment : article.getCommentList()) {
            count++;
            for (Comment nested : comment.getChildComments()) {
                count++;
            }
        }
        article.setCommentAmount(count);
    }

    // so funny.................
    private void rearrangeCommentsList(List<Comment> commentList) {
        for (int i = 0; i < commentList.size(); i++) {
            if (commentList.get(i).getChildComments().size() > 0) {
                for (int n_i = 0; n_i < commentList.get(i).getChildComments().size(); n_i++) {
                    for(int j = i; j < commentList.size(); j++) {
                        if (commentList.get(j).getId().equals(commentList.get(i).getChildComments().get(n_i).getId())) {
                            System.out.println("removing " + commentList.get(j).getId());
                            commentList.remove(j);
                        }
                    }
                }
            }
        }

        for (int j = 0; j < commentList.size(); j++) {
            List<Comment> listToReverse = commentList.get(j).getChildComments();
            for (int i = 0; i < listToReverse.size() / 2; i++) {
                Comment temp = listToReverse.get(i);
                listToReverse.set(i, listToReverse.get(listToReverse.size() - i - 1));
                listToReverse.set(listToReverse.size() - i - 1, temp);
            }

        }

    }
}
