package com.innowise.gym.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final Set<String> PUBLIC_PAGES = Set.of(
            "/index.jsp",
            "/register.jsp",
            "/dispatcher-servlet"
    );

    private static final Set<String> PUBLIC_COMMANDS = Set.of(
            "login",
            "register",
            "default"
    );

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();
        String command = request.getParameter("command");

        HttpSession session = request.getSession(false);
        boolean loggedIn =
                session != null && session.getAttribute("user") != null;

        if (isPublicPage(uri)) {
            chain.doFilter(req, res);
            return;
        }

        if (command != null && PUBLIC_COMMANDS.contains(command)) {
            chain.doFilter(req, res);
            return;
        }

        if (loggedIn) {
            chain.doFilter(req, res);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    private boolean isPublicPage(String uri) {
        return PUBLIC_PAGES.stream().anyMatch(uri::endsWith);
    }
}

