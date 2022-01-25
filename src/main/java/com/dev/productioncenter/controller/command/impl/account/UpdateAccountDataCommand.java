package com.dev.productioncenter.controller.command.impl.account;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestAttribute.MESSAGE;
import static com.dev.productioncenter.controller.command.RequestAttribute.USER;
import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class UpdateAccountDataCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String UPDATE_ACCOUNT_DATA_CONFIRM_MESSAGE_KEY = "confirm.update_account_data";
    private static final String UPDATE_ACCOUNT_DATA_ERROR_MESSAGE_KEY = "error.update_account_data";
    private static final String PASSWORD_REQUIREMENT_ERROR_MESSAGE_KEY = "error.update_account_data.password_requirement";
    private static final String LOGIN_AVAILABILITY_ERROR_MESSAGE_KEY = "error.login_availability";
    private static final String EMAIL_AVAILABILITY_ERROR_MESSAGE_KEY = "error.email_availability";
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(SessionAttribute.USER);
        String login = currentUser.getLogin();
        Map<String, String> userData = new HashMap<>();
        userData.put(LOGIN, request.getParameter(LOGIN));
        userData.put(PASSWORD, request.getParameter(PASSWORD));
        userData.put(NEW_PASSWORD, request.getParameter(NEW_PASSWORD));
        userData.put(REPEATED_PASSWORD, request.getParameter(REPEATED_PASSWORD));
        userData.put(SURNAME, request.getParameter(SURNAME));
        userData.put(NAME, request.getParameter(NAME));
        userData.put(EMAIL, request.getParameter(EMAIL));
        userData.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
        if (!login.equals(userData.get(LOGIN)) && userData.get(PASSWORD).isEmpty()) {
            request.setAttribute(USER, userData);
            request.setAttribute(MESSAGE, PASSWORD_REQUIREMENT_ERROR_MESSAGE_KEY);
            return new Router(PagePath.UPDATE_ACCOUNT_DATA, Router.RouterType.FORWARD);
        }
        try {
            if (!login.equals(userData.get(LOGIN)) && !userService.isLoginAvailable(userData.get(LOGIN))) {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, LOGIN_AVAILABILITY_ERROR_MESSAGE_KEY);
                return new Router(PagePath.UPDATE_ACCOUNT_DATA, Router.RouterType.FORWARD);
            }
            Optional<User> user = userService.findUser(login);
            if (user.isPresent()) {
                userData.put(PASSWORD, user.get().getPassword());
                if (!user.get().getEmail().equals(userData.get(EMAIL))
                        && !userService.isEmailAvailable(userData.get(EMAIL))) {
                    request.setAttribute(USER, userData);
                    request.setAttribute(MESSAGE, EMAIL_AVAILABILITY_ERROR_MESSAGE_KEY);
                    return new Router(PagePath.UPDATE_ACCOUNT_DATA, Router.RouterType.FORWARD);
                }
            }
            if (userService.updateUserAccountData(userData)) {
                user = userService.findUser(login);
                session.setAttribute(SessionAttribute.USER, user);
                session.setAttribute(SessionAttribute.NUMBER, userData.get(PHONE_NUMBER));
                session.setAttribute(SessionAttribute.MESSAGE, UPDATE_ACCOUNT_DATA_CONFIRM_MESSAGE_KEY);
                return new Router(PagePath.ACCOUNT, Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, UPDATE_ACCOUNT_DATA_ERROR_MESSAGE_KEY);
                return new Router(PagePath.UPDATE_ACCOUNT_DATA, Router.RouterType.FORWARD);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while updating user account: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
