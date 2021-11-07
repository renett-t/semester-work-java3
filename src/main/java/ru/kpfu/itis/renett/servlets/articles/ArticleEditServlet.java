package ru.kpfu.itis.renett.servlets.articles;

import ru.kpfu.itis.renett.exceptions.FileUploadException;
import ru.kpfu.itis.renett.exceptions.InvalidRequestDataException;
import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Tag;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.service.articleService.ArticleGetDataService;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.articleService.ArticleSaveDataService;
import ru.kpfu.itis.renett.service.fileService.FileService;
import ru.kpfu.itis.renett.service.security.RequestValidatorInterface;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

@WebServlet("/editArticle")
public class ArticleEditServlet extends HttpServlet {
    private ArticleGetDataService articleGetDataService;
    private ArticleSaveDataService articleSaveDataService;
    private RequestValidatorInterface requestValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articleGetDataService = (ArticleGetDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_GET_SERVICE);
        articleSaveDataService = (ArticleSaveDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_SAVE_SERVICE);
        requestValidator = (RequestValidatorInterface) config.getServletContext().getAttribute(Constants.CNTX_REQUEST_VALIDATOR);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idOfRequestedArticle = requestValidator.checkRequestedIdCorrect(request.getParameter("id"));
            Article requestedArticle = articleGetDataService.getArticleById(idOfRequestedArticle);
            if (requestedArticle != null) {
                request.setAttribute("articleInstance", requestedArticle);
                List<Tag> tags = articleGetDataService.getAllTags();
                request.setAttribute("tagList", tags);
            } else {
                request.setAttribute("message", "Извините, но данная статья не была найдена, нечего редактировать. Глядите, что у нас есть для вас:");
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
                return;
            }
        } catch (InvalidRequestDataException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/article_edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {   // validation of id has the same reasons as stated at ArticleLikeServlet class. id sends automatically by a's href value
            int artId = requestValidator.checkRequestedIdCorrect(request.getParameter("id"));
            Part imagePart = request.getPart("thumbnailImage");
            if (imagePart != null) {
                articleSaveDataService.editArticle(request);
            } else {
                articleSaveDataService.editArticleWithoutThumbnail(request);
            }
            response.sendRedirect(getServletContext().getContextPath() + "/article?id=" + artId);
        } catch (InvalidRequestDataException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
