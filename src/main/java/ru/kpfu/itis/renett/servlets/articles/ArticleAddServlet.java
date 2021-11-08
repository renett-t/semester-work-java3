package ru.kpfu.itis.renett.servlets.articles;

import ru.kpfu.itis.renett.exceptions.FileUploadException;
import ru.kpfu.itis.renett.exceptions.InvalidRequestDataException;
import ru.kpfu.itis.renett.models.Tag;
import ru.kpfu.itis.renett.service.articleService.ArticleGetDataService;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.articleService.ArticleSaveDataService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/newArticle")
@MultipartConfig
public class ArticleAddServlet extends HttpServlet {
    private ArticleGetDataService articleGetDataService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articleGetDataService = (ArticleGetDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_GET_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tag> tags = articleGetDataService.getAllTags();
        request.setAttribute("tagList", tags);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/article_edit.jsp").forward(request, response);
    }

}
