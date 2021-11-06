package ru.kpfu.itis.renett.servlets.articles;

import ru.kpfu.itis.renett.exceptions.InvalidRequestDataException;
import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Comment;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.service.articleService.ArticleService;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.security.RequestValidatorInterface;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/newComment")
public class CommentAddServlet extends HttpServlet {
    private ArticleService articleService;
    private RequestValidatorInterface requestValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articleService = (ArticleService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_SERVICE);
        requestValidator = (RequestValidatorInterface) config.getServletContext().getAttribute(Constants.CNTX_REQUEST_VALIDATOR);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = (String) request.getAttribute("commentBody");
        String idOfArticle = (String) request.getAttribute("articleId");
        String parenCommentId = (String) request.getAttribute("parenCommentId");

        try {
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
            articleService.createComment(newComment);
            response.sendRedirect(getServletContext().getContextPath() + "/article?id=" + idOfArticle);
        } catch (InvalidRequestDataException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
