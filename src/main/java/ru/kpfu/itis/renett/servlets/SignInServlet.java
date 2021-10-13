package ru.kpfu.itis.renett.servlets;

import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.repository.UsersRepository;
import ru.kpfu.itis.renett.service.Constants;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {
    private UsersRepository usersRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = this.getServletContext();
        usersRepository = (UsersRepository) servletContext.getAttribute(Constants.CNTX_USERS_REPOSITRY) ;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
    }

    // здесь лучше как-нить по-другому реализовать проверку не только с куки... но и через бд???
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Optional<User> optionalUser = usersRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            if (optionalUser.get().getPassword().equals(password)) {
                Cookie authorizedCookie = new Cookie(Constants.COOKIE_AUTHORIZED_NAME, "какое-то значение куки");
                authorizedCookie.setMaxAge(60 * 60 * 10);
                request.setAttribute("message", "You've been authorized");
                response.addCookie(authorizedCookie);

                response.sendRedirect(getServletContext().getContextPath() + "/all");
                return;
            }
        } else {
            request.setAttribute("message", "Incorrect login, no user registered under this login '" + login + "';");
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/signin.jsp").forward(request, response);
        }

    }

}
