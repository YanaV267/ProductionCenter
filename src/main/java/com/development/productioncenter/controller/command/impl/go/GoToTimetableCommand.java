package com.development.productioncenter.controller.command.impl.go;

import com.development.productioncenter.controller.command.Command;
import com.development.productioncenter.controller.command.PagePath;
import com.development.productioncenter.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class GoToTimetableCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.TIMETABLE, Router.RouterType.FORWARD);
    }
}
