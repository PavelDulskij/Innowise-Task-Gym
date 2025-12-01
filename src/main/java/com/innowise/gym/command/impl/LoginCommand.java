package com.innowise.gym.command.impl;

import com.innowise.gym.command.Command;
import com.innowise.gym.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setPassword(password);
            user.setLogin(login);
            session.setAttribute("user", user);
        }

        return "redirect:/homepage.jsp";
    }
}
