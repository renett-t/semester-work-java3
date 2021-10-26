package ru.kpfu.itis.renett.servlets.auth;

import ru.kpfu.itis.renett.exceptions.InvalidRegistrationDataException;
import ru.kpfu.itis.renett.models.User;
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

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private SecurityService securityService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        securityService = (SecurityService) servletContext.getAttribute(Constants.CNTX_SECURITY_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String repeatedPassword = request.getParameter("repeatedPassword");

        if (!password.equals(repeatedPassword)) {
            request.setAttribute("message", "Пароли не совпадают попробуйте снова.");
        } else if (password.length() < 5) {
            request.setAttribute("message", "Слишком короткий пароль, попробуйте снова");
        } else {
            try {
                User newUser = new User(firstName, secondName, email, login, password);

                UUID uuid = securityService.signUp(newUser, request.getSession());
                Cookie authorizedCookie = new Cookie(Constants.COOKIE_AUTHORIZED_NAME, uuid.toString());
                authorizedCookie.setMaxAge(60*60);  //TODO Change cookie's max age
                response.addCookie(authorizedCookie);

                response.sendRedirect(getServletContext().getContextPath() + "/profile");
                return;
            } catch (InvalidRegistrationDataException ex) {
                request.setAttribute("message", "Вы не были зарегистрированы. " + ex.getMessage());
            }
        }

        // TODO recheck form data saving feature
        if ((firstName != null) && (firstName.length() > 0))
            request.getSession().setAttribute("firstName", firstName);
        if ((secondName != null) && (secondName.length() > 0))
            request.getSession().setAttribute("secondName", secondName);
        if ((email != null) && (email.length() > 0))
            request.getSession().setAttribute("email", email);
        if ((login != null) && (login.length() > 0))
            request.getSession().setAttribute("login", login);

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);

    }
}
