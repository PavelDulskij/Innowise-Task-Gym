package com.innowise.gym.command.impl;

import com.innowise.gym.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/index.jsp";
    }
}
