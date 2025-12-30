package com.innowise.gym.command.impl;

import com.innowise.gym.command.Command;
import com.innowise.gym.exception.ServiceException;
import com.innowise.gym.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class RegisterCommand implements Command {

    private final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        try {
            userService.register(login, password, email);
            return "redirect:/index.jsp";

        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            return "/register.jsp";
        }
    }
}

