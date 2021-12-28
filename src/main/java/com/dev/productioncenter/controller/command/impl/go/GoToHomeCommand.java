package com.dev.productioncenter.controller.command.impl.go;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class GoToHomeCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.HOME, Router.RouterType.FORWARD);
    }
}
