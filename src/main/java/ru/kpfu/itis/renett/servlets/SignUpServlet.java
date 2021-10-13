package ru.kpfu.itis.renett.servlets;

import ru.kpfu.itis.renett.exceptions.DataBaseException;
import ru.kpfu.itis.renett.exceptions.InvalidRegistrationDataException;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.repository.UsersRepository;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.UserDataValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private int countOfRegisteredUsers = 0;
    private UsersRepository usersRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = this.getServletContext();
        usersRepository = (UsersRepository) servletContext.getAttribute(Constants.CNTX_USERS_REPOSITRY);

        countOfRegisteredUsers = usersRepository.findAll().size();
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
            request.setAttribute("message", "Passwords doesn't match. Try again");
        } else {
            try {
                if (UserDataValidator.isUserParametersCorrect(firstName, secondName, email, login, password)) {
                    try {
                        User newUser = new User(firstName, secondName, email, login, password);

                        // проверка зарегистрирован ли пользователем с тем же логином
                        Optional<User> optionalUser = usersRepository.findByLogin(newUser.getLogin());
                        if (optionalUser.isPresent()) {
                            throw new DataBaseException("User with the same login already exists");
                        }

                        // если всё ок - достаём айдишник, сохраняем в бд, хорошо бы создать куку о регистрации, перенаправляем на табличку
                        newUser.setId(++countOfRegisteredUsers);
                        usersRepository.save(newUser);
                        request.setAttribute("message", "You've been registered");

                        response.sendRedirect(getServletContext().getContextPath() + "/all");
                        return;
                    } catch (DataBaseException ex) {
                        request.setAttribute("message", ex.getMessage());
                    }
                } else {
                    request.setAttribute("message", "Incorrect data entered or some error happened. Try again");
                }
            } catch (InvalidRegistrationDataException ex) {
                request.setAttribute("message", "You weren't registered. " + ex.getMessage());
            }
        }

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
