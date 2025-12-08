package com.innowise.gym.command.impl;

import com.innowise.gym.command.Command;
import com.innowise.gym.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String USER = "user";
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        User user = (User) session.getAttribute(USER);
        if (user == null) {
            user = new User();
            user.setPassword(password);
            user.setLogin(login);
            session.setAttribute(USER, user);
        }
        request.setAttribute(LOGIN, login);
        request.setAttribute(PASSWORD, password);

        return "redirect:/homepage.jsp";
    }
}
