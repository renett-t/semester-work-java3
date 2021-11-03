package ru.kpfu.itis.renett.service;

import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Comment;
import ru.kpfu.itis.renett.models.Tag;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.repository.ArticleRepository;
import ru.kpfu.itis.renett.repository.CommentRepository;
import ru.kpfu.itis.renett.repository.TagRepository;
import ru.kpfu.itis.renett.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private TagRepository tagRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, CommentRepository commentRepository, TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Article getArticleById(int id) {
        Optional<Article> foundArticle = articleRepository.findById(id);

        Article article = foundArticle.orElse(null);

        if (article != null) {
            User author = userRepository.findById(article.getAuthor().getId()).orElse(article.getAuthor());
            article.setAuthor(author);
            List<Comment> commentList = this.getArticleComments(article);
            article.setCommentAmount(commentList.size());
            article.setCommentList(this.rearrangeArticleCommentsList(commentList));
            article.setTagList(this.getArticleTags(article));
            article.setLikeAmount(this.getArticleLikesAmount(article));
        }

        return article;
    }

    @Override
    public List<Article> getUsersArticles(User user) {
        if (user != null) {
            List<Article> articleList = articleRepository.findAllByOwnerId(user.getId());
            initializeArticlesWithBasicInfo(articleList);
            return articleList;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Article> getLikedArticles(User user) {
        if (user != null) {
            List<Article> articleList = articleRepository.findAllLikedArticles(user.getId());
            initializeArticlesWithBasicInfo(articleList);
            return articleList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Article> getAllArticles() {
        List<Article> articleList = articleRepository.findAll();
        initializeArticlesWithBasicInfo(articleList);
        return articleList;
    }

    @Override
    public List<Article> getAllArticlesExceptUsers(User user) {
        List<Article> all = articleRepository.findAll();
        if (user != null) {
            List<Article> users = articleRepository.findAllByOwnerId(user.getId());
            all.removeAll(users);
            initializeArticlesWithBasicInfo(all);
        }
        return all;
    }

    @Override
    public List<Article> getAllArticlesByTag(Tag tag) {
        if (tag != null) {
            List<Article> articleList = articleRepository.findAllByTagId(tag.getId());
            initializeArticlesWithBasicInfo(articleList);
            return articleList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> getArticleTags(Article article) {
        return tagRepository.findAllArticleTags(article.getId());
    }

    @Override
    public void createArticle(Article newArticle) {
        if (newArticle != null) {
            articleRepository.save(newArticle);
        }
    }

    @Override
    public void deleteArticle(Article articleToDelete) {
        if (articleToDelete != null) {
            articleRepository.delete(articleToDelete);
        }
    }

    @Override
    public void editArticle(Article editedArticle) {
        if (editedArticle != null) {
            articleRepository.update(editedArticle);
        }
    }

    @Override
    public void likeArticle(User user, Article likedArticle) {
        if (likedArticle != null && user != null) {
            articleRepository.updateLikesAmount(user.getId(), likedArticle.getId());
        }
    }

    @Override
    public void dislikeArticle(User user, Article dislikedArticle) {
        if (user != null && dislikedArticle != null) {
            articleRepository.removeLikeFromArticle(user.getId(), dislikedArticle.getId());
        }
    }

    @Override
    public boolean isArticleLikedByUser(User user, Article article) {
        if (user != null && article != null) {
            List<Article> articleList = this.getLikedArticles(user);
            return articleList.contains(article);
        }
        return false;
    }

    @Override
    public int getArticleLikesAmount(Article article) {
        return articleRepository.getLikesAmount(article.getId());
    }

    /**
     * Not rearranged comment list. Call rearrangeArticleCommentsList before displaying article
     * @param article
     * @return raw list of article's comments
     */
    @Override
    public List<Comment> getArticleComments(Article article) {
        return commentRepository.findAllArticleComments(article.getId());
    }

    @Override
    public List<Comment> rearrangeArticleCommentsList(List<Comment> commentList) {
        for (int i = commentList.size() - 1; i >= 0; i--) {                                                                 // нужно идти с конца, так как id вложенных комментариев точно больше родительских
            Comment child = commentList.get(i);
            if (child.getParentComment() != null) {
                commentList.remove(i);
                for (int k = 0; k < commentList.size(); k++) {
                    if (commentList.get(k).getId().equals(child.getParentComment().getId())){
                        commentList.get(k).getChildComments().add(child);
                        break;
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
        return commentList;
    }

    @Override
    public void createComment(Comment newComment) {
        if (newComment != null) {
            commentRepository.save(newComment);
        }
    }

    @Override
    public void deleteComment(Comment commentToDelete) {
        if (commentToDelete != null) {
            commentRepository.delete(commentToDelete);
        }
    }

    @Override
    public void editComment(Comment editedComment) {
        if (editedComment != null) {
            commentRepository.update(editedComment);
        }
    }

    private void initializeArticlesWithBasicInfo(List<Article> articleList) {
        for (Article art : articleList) {
            this.initializeArticleWithBasicInfo(art);
        }
    }

    private void initializeArticleWithBasicInfo(Article article) {
        article.setCommentAmount(this.getArticleComments(article).size());
        article.setLikeAmount(this.getArticleLikesAmount(article));
        article.setTagList(this.getArticleTags(article));
    }
}
