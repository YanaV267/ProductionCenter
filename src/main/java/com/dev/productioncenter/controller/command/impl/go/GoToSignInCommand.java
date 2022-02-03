package com.dev.productioncenter.controller.command.impl.go;

import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @project Production Center
 * @author YanaV
 * The type Go to sign in command.
 */
public class GoToSignInCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.SIGN_IN, Router.RouterType.FORWARD);
    }
}
