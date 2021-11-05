package ru.kpfu.itis.renett.servlets.articles;

import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Tag;
import ru.kpfu.itis.renett.service.ArticleService;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.FileService;
import ru.kpfu.itis.renett.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/editArticle")
public class ArticleEditServlet extends HttpServlet {
    private ArticleService articleService;
    private UserService userService;
    private FileService fileService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute(Constants.CNTX_USER_SERVICE);
        articleService = (ArticleService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_SERVICE);
        fileService = (FileService) config.getServletContext().getAttribute(Constants.CNTX_FILE_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tag> tags = articleService.getAllTags();
        request.setAttribute("tagList", tags);

        int idOfRequestedArticle = 0;
        if (request.getParameter("id") != null) {
            try {
                idOfRequestedArticle = Integer.parseInt(request.getParameter("id"));
                Article requestedArticle = articleService.getArticleById(idOfRequestedArticle);
                if (requestedArticle != null) {
                    request.setAttribute("articleInstance", requestedArticle);
                } else {
                    request.setAttribute("message", "Извините, но данная статья не была найдена, нечего редактировать. Глядите, что у нас есть для вас:");
                    getServletContext().getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/article_edit.jsp").forward(request, response);
    }
}
