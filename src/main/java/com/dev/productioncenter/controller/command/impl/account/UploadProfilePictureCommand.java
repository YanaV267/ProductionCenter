package com.dev.productioncenter.controller.command.impl.account;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.model.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestAttribute.MESSAGE;
import static com.dev.productioncenter.controller.command.RequestAttribute.PICTURE;
import static com.dev.productioncenter.controller.command.RequestParameter.UPLOADED_PICTURE;

public class UploadProfilePictureCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String UPLOAD_PROFILE_PICTURE_CONFIRM_KEY = "confirm.upload_profile_picture";
    private final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        String login = user.getLogin();
        try {
            Part part = request.getPart(UPLOADED_PICTURE);
            InputStream pictureStream = part.getInputStream();
            if (userService.updatePicture(login, pictureStream)) {
                request.setAttribute(MESSAGE, UPLOAD_PROFILE_PICTURE_CONFIRM_KEY);
                Optional<String> picture = userService.loadPicture(login);
                if (picture.isPresent()) {
                    request.setAttribute(PICTURE, picture.get());
                    return new Router(PagePath.ACCOUNT, Router.RouterType.FORWARD);
                }
            }
        } catch (ServiceException | IOException | ServletException exception) {
            LOGGER.error("Error has occurred while updating user account: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}