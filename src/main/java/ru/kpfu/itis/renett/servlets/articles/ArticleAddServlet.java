package ru.kpfu.itis.renett.servlets.articles;

import ru.kpfu.itis.renett.exceptions.FileUploadException;
import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Tag;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.service.ArticleService;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.FileService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/newArticle")
@MultipartConfig
public class ArticleAddServlet extends HttpServlet {
    private ArticleService articleService;
    private FileService fileService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articleService = (ArticleService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_SERVICE);
        fileService = (FileService) config.getServletContext().getAttribute(Constants.CNTX_FILE_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tag> tags = articleService.getAllTags();
        request.setAttribute("tagList", tags);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/article_edit.jsp").forward(request, response);
    }
    // https://javaee.github.io/javaee-spec/javadocs/javax/servlet/annotation/MultipartConfig.html
    // https://stackoverflow.com/questions/2422468/how-can-i-upload-files-to-a-server-using-jsp-servlet

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String body = request.getParameter("article-body");
        String[] tags = request.getParameterValues("tag");
        Part imagePart = request.getPart("thumbnailImage");
        String imageFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
        try {
            fileService.saveFile(imageFileName, imagePart.getInputStream());
        } catch (FileUploadException e) {
            request.setAttribute("message", e.getMessage());
            System.out.println("UNABLE TO SAVE");
            // TODO: REDIRECT
        }
        User author = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
        Article newArticle = Article.builder()
                .title(title)
                .body(body)
                .author(author)
                .thumbnailPath(imageFileName)
                .tagList(new ArrayList<>())
                .build();

        for (String tag : tags) {
            newArticle.getTagList().add(articleService.getTagById(Integer.parseInt(tag)));
        }

        String submit = request.getParameter("submit");
        switch (submit) {
            case "create":
                articleService.createArticle(newArticle);
                break;
            case "edit":
                articleService.editArticle(newArticle);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/article?id=" + newArticle.getId());
    }
}
