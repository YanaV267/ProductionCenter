package com.development.productioncenter.controller.command;

import com.development.productioncenter.controller.command.impl.*;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_OUT(new SignOutCommand()),
    SIGN_UP(new SignUpCommand()),
    DEFAULT(new DefaultCommand());

    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
