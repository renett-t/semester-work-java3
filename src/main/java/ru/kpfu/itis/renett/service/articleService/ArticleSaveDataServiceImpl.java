package ru.kpfu.itis.renett.service.articleService;

import ru.kpfu.itis.renett.exceptions.FileUploadException;
import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Comment;
import ru.kpfu.itis.renett.models.Tag;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.repository.ArticleRepository;
import ru.kpfu.itis.renett.repository.CommentRepository;
import ru.kpfu.itis.renett.repository.TagRepository;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.fileService.*;
import ru.kpfu.itis.renett.service.security.RequestValidatorInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleSaveDataServiceImpl implements ArticleSaveDataService {
    private final String DEFAULT_THUMBNAIL = Constants.DEFAULT_THUMBNAIL;

    private ArticleRepository articleRepository;
    private CommentRepository commentRepository;
    private TagRepository tagRepository;
    private HtmlTagsValidator htmlTagsValidator;
    private FileManager fileManager;
    private RequestValidatorInterface requestValidator;

    public ArticleSaveDataServiceImpl(ArticleRepository articleRepository, CommentRepository commentRepository, TagRepository tagRepository, RequestValidatorInterface requestValidator) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.tagRepository = tagRepository;
        this.htmlTagsValidator = new HtmlTagsValidatorImpl();
        this.requestValidator = requestValidator;
        this.fileManager = new FileManagerImpl(Constants.STORAGE_URL);
    }

    @Override
    public int createArticle(HttpServletRequest request) {
        String title = request.getParameter("title");
        String body = request.getParameter("articleBody");
        String[] tags = request.getParameterValues("tag");

        User author = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);

        Article newArticle = Article.builder()
                .title(title)
                .body(htmlTagsValidator.checkStringInputTags(body))
                .author(author)
                .tagList(new ArrayList<>())
                .build();

        Part imagePart = null;
        String imageFileName = null;
        try {
            imagePart = request.getPart("thumbnailImage");
            if (imagePart != null && imagePart.getSize() != 0) {
                imageFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                try {
                    imageFileName = fileManager.saveFile(imageFileName, imagePart.getInputStream());
                } catch (FileUploadException e) {
                    throw new FileUploadException(e);
                }
            } else {
                imageFileName = DEFAULT_THUMBNAIL;
            }
        } catch (IOException | ServletException e) {
            throw new FileUploadException("Проблемы с загрузкой изображения", e);
        }

        newArticle.setThumbnailPath(imageFileName);

        if (tags != null) {
            for (String tag : tags) {
                if (!tag.equals("-1")) {
                    newArticle.getTagList().add(new Tag(Integer.parseInt(tag)));
                }
            }
        }

        articleRepository.save(newArticle);
        return newArticle.getId();
    }

    @Override
    public void deleteArticle(Article articleToDelete) {
        if (articleToDelete != null) {
            articleRepository.delete(articleToDelete);
        }
    }

    @Override
    public void editArticle(HttpServletRequest request) {
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            System.out.println(entry.getKey() + " ---- >");
            for (String val :
                    entry.getValue()) {
                System.out.println("             " + val);
            }
        }

        String title = request.getParameter("title");
        String body = request.getParameter("articleBody");
        System.out.println("BODY: " + body);
        String[] tags = request.getParameterValues("tag");

        User author = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);

        Article editedArticle = Article.builder()
                .title(title)
                .body(htmlTagsValidator.checkStringInputTags(body))
                .author(author)
                .build();

        Part imagePart = null;
        String imageFileName = null;
        try {
            imagePart = request.getPart("thumbnailImage");
            if (imagePart != null) {
                imageFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                try {
                    imageFileName = fileManager.saveFile(imageFileName, imagePart.getInputStream());
                } catch (FileUploadException e) {
                    throw new FileUploadException(e);
                }
                editedArticle.setThumbnailPath(imageFileName);
                articleRepository.update(editedArticle);
            } else {
                articleRepository.updateWithoutThumbnail(editedArticle);
            }
        } catch (IOException | ServletException e) {
            throw new FileUploadException("Проблемы с загрузкой изображения", e);
        }
        int articleId = Integer.parseInt(request.getParameter("id"));
        List<Tag> newTags = new ArrayList<>();
        List<Tag> leftTags = new ArrayList<>();
        List<Tag> oldTags = tagRepository.findAllArticleTags(articleId);
        if (tags != null) {
            for (String tag : tags) {
                if (!tag.equals("-1")) {
                    newTags.add(new Tag(Integer.parseInt(tag)));
                    leftTags.add(new Tag(Integer.parseInt(tag)));
                }
            }
        }

        newTags.removeAll(oldTags);
        oldTags.removeAll(leftTags);
        tagRepository.saveNewTags(newTags, articleId);
        tagRepository.deleteOldTags(oldTags, articleId);
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
    public void updateViewCount(Article article) {
        articleRepository.updateViewCount(article.getId(), article.getViewAmount());
    }

    @Override
    public void createComment(HttpServletRequest request) {
        String body =  request.getParameter("commentBody");
        String idOfArticle = request.getParameter("articleId");
        String parenCommentId = request.getParameter("parent");

        int artId = requestValidator.checkRequestedIdCorrect(idOfArticle);

        Comment parentComment = null;
        if (parenCommentId != null) {
            int parentId = requestValidator.checkRequestedIdCorrect(parenCommentId);
            parentComment = new Comment(parentId);
        }

        Comment newComment = Comment.builder()
                .body(body)
                .article(new Article(artId))
                .author((User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME))
                .parentComment(parentComment)
                .build();

        if (newComment != null) {
            commentRepository.save(newComment);
        }
    }

    @Override
    public void deleteComment(Comment commentToDelete) {
        throw new UnsupportedOperationException("Method in development");
    }

    @Override
    public void editComment(Comment editedComment) {
        throw new UnsupportedOperationException("Method in development");
    }
}
