package com.dev.productioncenter.controller.command.impl.go.account;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class GoToUpdateAccountDataCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.UPDATE_ACCOUNT_DATA, Router.RouterType.FORWARD);
    }
}
