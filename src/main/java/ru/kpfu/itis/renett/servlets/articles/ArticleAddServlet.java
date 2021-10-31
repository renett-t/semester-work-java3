package ru.kpfu.itis.renett.servlets.articles;

import ru.kpfu.itis.renett.models.Tag;
import ru.kpfu.itis.renett.service.ArticleService;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/article/new")
public class ArticleAddServlet extends HttpServlet {
    private ArticleService articleService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute(Constants.CNTX_USER_SERVICE);
        articleService = (ArticleService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tag> tags = articleService.getAllTags();
        request.setAttribute("tagList", tags);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/article_edit.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO
    }
}
