package com.innowise.gym.command.impl;

import com.innowise.gym.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/index.jsp";
    }
}

