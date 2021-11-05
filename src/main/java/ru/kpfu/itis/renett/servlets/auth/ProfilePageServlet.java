package ru.kpfu.itis.renett.servlets.auth;

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

@WebServlet("/profile")
public class ProfilePageServlet extends HttpServlet {
    private ArticleService articleService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articleService = (ArticleService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
        if (user != null) {
            List<Article> likedArticlesList = articleService.getLikedArticles(user);
            request.setAttribute("user", user);
            request.setAttribute("likedArticlesList", likedArticlesList);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
        } else {
            response.sendRedirect(getServletContext().getContextPath() + "/signin");
        }
    }
}
