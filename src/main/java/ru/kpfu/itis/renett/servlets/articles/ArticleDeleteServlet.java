package ru.kpfu.itis.renett.servlets.articles;

import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.service.ArticleService;
import ru.kpfu.itis.renett.service.Constants;

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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articleService = (ArticleService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Article articleToDelete = articleService.getArticleById(id);
                if (articleToDelete != null) {
                    User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
                    if (user.getId() != articleToDelete.getAuthor().getId()) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    } else {
                        articleService.deleteArticle(articleToDelete);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }
    }
}
