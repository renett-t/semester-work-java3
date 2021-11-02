package ru.kpfu.itis.renett.servlets.articles;

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
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

@WebServlet("/article/new")
public class ArticleNewServlet extends HttpServlet {
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
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/article_edit.jsp").forward(request, response);
    }

    // https://www.baeldung.com/upload-file-servlet

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart("thumbnailImage");
        fileService.upload("random.jpg", part.getInputStream());
    }
}
