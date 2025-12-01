package com.innowise.gym.controller;

import com.innowise.gym.command.Command;
import com.innowise.gym.command.CommandFactory;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "dispatcher", value = "/dispatcher-servlet")
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String commandStr = request.getParameter("command");

        Command command = CommandFactory.defineCommand(commandStr);
        String page = command.execute(request);

        try {
            if (page.startsWith("redirect:")) {
                String redirectPath = page.replace("redirect:", "");
                response.sendRedirect(request.getContextPath() + redirectPath);
            } else {
                request.getRequestDispatcher(page).forward(request, response);
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/errorpage.jsp");
        }
    }
}
