package com.innowise.gym.command.impl;

import com.innowise.gym.command.Command;
import com.innowise.gym.entity.User;
import com.innowise.gym.exception.ServiceException;
import com.innowise.gym.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class LoginCommand implements Command {

    private final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            Optional<User> userOpt = userService.login(login, password);

            if (userOpt.isPresent()) {
                request.getSession().setAttribute("user", userOpt.get());
                return "redirect:/homepage.jsp";
            }

            request.setAttribute("error", "Invalid login or password");
            return "/index.jsp";

        } catch (ServiceException e) {
            request.setAttribute("error", "Internal error");
            return "/index.jsp";
        }
    }
}


