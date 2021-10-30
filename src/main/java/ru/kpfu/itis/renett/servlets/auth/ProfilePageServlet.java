package ru.kpfu.itis.renett.servlets.auth;

import ru.kpfu.itis.renett.service.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile")
public class ProfilePageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME) != null) {
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
        } else {
//            response.setStatus(403); // TODO: status codes??
            response.sendRedirect(getServletContext().getContextPath() + "/signin");
        }
    }
}
