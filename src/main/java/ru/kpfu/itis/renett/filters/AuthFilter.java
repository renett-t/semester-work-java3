package ru.kpfu.itis.renett.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/articles/new")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals(Constants.COOKIE_AUTHORIZED_NAME)) {
//                    System.out.println("USER IS NOT AUTHORIZED");
//                } else {
//                    System.out.println("USER IS NOT AUTHORIZED");
//                    response.sendRedirect(request.getContextPath() + "/signin");
//                    return;
//                }
//            }
//        } else {
//            System.out.println("USER IS NOT AUTHORIZED");
//            response.sendRedirect(request.getContextPath() + "/signin");
//            return;
//        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
