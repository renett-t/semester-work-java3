package ru.kpfu.itis.renett.filters;

import ru.kpfu.itis.renett.exceptions.InvalidCookieException;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.SecurityService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebFilter("/*")
public class SecurityRedirectsFilter implements Filter {
    private SecurityService securityService;
    private Map<String, String> paths;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        securityService = (SecurityService) filterConfig.getServletContext().getAttribute(Constants.CNTX_SECURITY_SERVICE);
        paths = initializePathsMap();
    }

    private Map<String, String> initializePathsMap() {
        Map<String, String> protectedPaths = new HashMap<>();
        protectedPaths.put("/profile", "/signin");
        protectedPaths.put("", "");
        return null;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        boolean isAccessible = false;
        String pathToRedirect = "";

        if (securityService.isAuthenticated(request)) {
            isAccessible = true;
        } else { // попытка проверить, авторизировался ли пользователь, имеется ли нужная кука
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(Constants.COOKIE_AUTHORIZED_NAME)) {
                        // TODO
                        isAccessible = true;
                        filterChain.doFilter(request, response);
                        return;
                    }
                }
            }
            // TODO - check paths
        }

        if (!isAccessible) {
            response.sendRedirect(request.getServletContext().getContextPath() + pathToRedirect);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
