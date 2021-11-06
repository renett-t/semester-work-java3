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

@WebServlet("/article")
public class ArticleDisplayServlet extends HttpServlet {
    private ArticleService articleService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articleService = (ArticleService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idOfRequestedArticle = 0;

        if (request.getParameter("id") != null) {
            try {
                idOfRequestedArticle = Integer.parseInt(request.getParameter("id"));
                Article requestedArticle = articleService.getArticleById(idOfRequestedArticle);
                if (requestedArticle != null) {
                    User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
                    if (user != null) {
                        if (user.getId() == requestedArticle.getAuthor().getId()) {
                            request.setAttribute("author", user);
                        }
                        if (articleService.isArticleLikedByUser(user, requestedArticle)) {
                            request.setAttribute("liked", true);
                        }
                    }
                    request.setAttribute("articleInstance", requestedArticle);
                } else {
                    request.setAttribute("message", "Извините, но данная статья не была найдена. ");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Извините, но данная статья не была найдена. ");
            }
        } else {
            request.setAttribute("message", "Извините, но данная статья не была найдена. ");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/article_display.jsp").forward(request, response);
    }
}
