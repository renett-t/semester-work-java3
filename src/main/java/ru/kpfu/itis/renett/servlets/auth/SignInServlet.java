package ru.kpfu.itis.renett.servlets.auth;

import ru.kpfu.itis.renett.exceptions.InvalidSignInDataException;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.SecurityService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {
    private SecurityService securityService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        securityService = (SecurityService) servletContext.getAttribute(Constants.CNTX_SECURITY_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/signin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            UUID uuid = securityService.signIn(login, password, request.getSession());
            Cookie authorizedCookie = new Cookie(Constants.COOKIE_AUTHORIZED_NAME, uuid.toString());
            authorizedCookie.setMaxAge(60*60);
            response.addCookie(authorizedCookie);

            response.sendRedirect(getServletContext().getContextPath()  + "/profile");
            return;
        } catch (InvalidSignInDataException e) {
            request.setAttribute("message", e.getMessage());
        }

        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/signin.jsp").forward(request, response);
    }
}
