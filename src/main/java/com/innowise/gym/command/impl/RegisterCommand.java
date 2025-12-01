package com.innowise.gym.command.impl;

import com.innowise.gym.command.Command;
import com.innowise.gym.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class RegisterCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPass");

        User user = (User) session.getAttribute("user");
        if (user == null) {
            if (confirmPassword.equals(password)) {
                user = new User();
                user.setLogin(login);
                user.setPassword(password);
                session.setAttribute("user", user);
            } else {
                return "/registration.jsp";
            }
        }
        return "redirect:/homepage.jsp";
    }
}
