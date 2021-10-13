package ru.kpfu.itis.renett.servlets;

import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.repository.UsersRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/all")
public class UsersTableServlet extends HttpServlet {
    private UsersRepository usersRepository;

    @Override
    public void init() throws ServletException {
        super.init();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> userList = usersRepository.findAll();
        req.setAttribute("usersFromDB", userList);

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/all_users.jsp").forward(req, resp);
    }
}
