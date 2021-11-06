package ru.kpfu.itis.renett.servlets.articles;

import ru.kpfu.itis.renett.exceptions.InvalidRequestDataException;
import ru.kpfu.itis.renett.models.Article;
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

@WebServlet("/deleteArticle")
public class ArticleDeleteServlet extends HttpServlet {
    private ArticleService articleService;
    private RequestValidatorInterface requestValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articleService = (ArticleService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_SERVICE);
        requestValidator = (RequestValidatorInterface) config.getServletContext().getAttribute(Constants.CNTX_REQUEST_VALIDATOR);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = requestValidator.checkRequestedIdCorrect(request.getParameter("id"));
            Article articleToDelete = articleService.getArticleById(id);
            if (articleToDelete != null) {
                articleService.deleteArticle(articleToDelete);
                response.sendRedirect(getServletContext().getContextPath() + "/articles");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (InvalidRequestDataException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
