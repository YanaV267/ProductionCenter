package com.dev.productioncenter.controller;

import com.dev.productioncenter.controller.command.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @project Production Center
 * @author YanaV
 * The type Controller.
 */
@WebServlet("/controller")
@MultipartConfig(maxRequestSize = 1024 * 1024 * 10)
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandType = request.getParameter(RequestParameter.COMMAND);
        Command command = CommandType.defineCommand(commandType);
        Router router = command.execute(request);
        switch (router.getType()) {
            case FORWARD -> request.getRequestDispatcher(router.getPage()).forward(request, response);
            case REDIRECT -> response.sendRedirect(request.getContextPath() + router.getPage());
            default -> request.getRequestDispatcher(PagePath.ERROR_404).forward(request, response);
        }
    }
}
