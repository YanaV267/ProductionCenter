package com.dev.productioncenter.controller;

import com.dev.productioncenter.controller.command.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/uploadController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 16, maxFileSize = 1024 * 1024 * 16, maxRequestSize = 1024 * 1024 * 10 * 10)
public class UploadController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Command command = CommandType.UPLOAD_PROFILE_PICTURE.getCommand();
        Router router = command.execute(request);
        request.getRequestDispatcher(router.getPage()).forward(request, response);
    }
}
