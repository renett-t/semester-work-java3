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
import java.util.List;

@WebServlet("/articles")
public class ArticleAllServlet extends HttpServlet {
    private ArticleService articleService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articleService = (ArticleService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // тут может null упасть??
        User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
        List<Article> list = null;

        // TAGS!
        if (user != null) {
            List<Article> userArticles = articleService.getUsersArticles(user);
            list = articleService.getAllArticles();
            request.setAttribute("userArticlesList", userArticles);
        } else {
            list = articleService.getAllArticlesExceptUsers(user);
        }

        // TODO: cookies of last viewed article, getting just a portion of articles
        request.setAttribute("articlesList", list);

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/articles_page.jsp").forward(request, response);
    }
}
