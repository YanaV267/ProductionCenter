package com.dev.productioncenter.controller.command.impl.go.account;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestAttribute.*;

public class GoToAccountCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(SessionAttribute.LOGIN);
        try {
            Optional<User> user = userService.findUser(login);
            if (user.isPresent()) {
                request.setAttribute(USER_DATA, user.get());
                String number = userService.formatPhoneNumber(user.get().getPhoneNumber());//TODO: форматирование номера
                request.setAttribute(NUMBER, number);
                Optional<String> picture = userService.loadPicture(login);
                if (picture.isPresent()) {
                    request.setAttribute(PICTURE, picture.get());
                    return new Router(PagePath.ACCOUNT, Router.RouterType.FORWARD);
                }
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to account page: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
