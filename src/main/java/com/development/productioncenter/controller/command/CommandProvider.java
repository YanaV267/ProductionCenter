package com.development.productioncenter.controller.command;

import jakarta.servlet.http.HttpServletRequest;

public class CommandProvider {
    private static final CommandProvider INSTANCE = new CommandProvider();
    private static final String COMMAND_REQUEST_PARAMETER = "command";

    private CommandProvider() {
    }

    public static CommandProvider getInstance() {
        return INSTANCE;
    }

    public Command defineCommand(HttpServletRequest request) {
        String command = request.getParameter(COMMAND_REQUEST_PARAMETER);
        if (command == null || command.isEmpty()) {
            return CommandType.DEFAULT.getCommand();
        }
        try {
            return CommandType.valueOf(command.toUpperCase()).getCommand();
        } catch (IllegalArgumentException exception) {
            return CommandType.DEFAULT.getCommand();
        }
    }
}
