package com.development.productioncenter.controller.command.impl;

import com.development.productioncenter.controller.command.Command;
import com.development.productioncenter.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PagePath.ERROR;
    }
}
