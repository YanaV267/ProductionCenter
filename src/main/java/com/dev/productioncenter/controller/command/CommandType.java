package com.dev.productioncenter.controller.command;

import com.dev.productioncenter.controller.command.impl.ChangeLocaleCommand;
import com.dev.productioncenter.controller.command.impl.DefaultCommand;
import com.dev.productioncenter.controller.command.impl.go.*;
import com.dev.productioncenter.controller.command.impl.signing.SignInCommand;
import com.dev.productioncenter.controller.command.impl.signing.SignOutCommand;
import com.dev.productioncenter.controller.command.impl.signing.SignUpCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum CommandType {
    GO_TO_HOME(new GoToHomeCommand()),
    GO_TO_SIGN_IN(new GoToSignInCommand()),
    GO_TO_SIGN_UP(new GoToSignUpCommand()),
    GO_TO_COURSES(new GoToCoursesCommand()),
    GO_TO_ACCOUNT(new GoToAccountCommand()),
    GO_TO_ACTIVITIES(new GoToActivitiesCommand()),
    GO_TO_TIMETABLE(new GoToTimetableCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    SIGN_IN(new SignInCommand()),
    SIGN_OUT(new SignOutCommand()),
    SIGN_UP(new SignUpCommand()),
    DEFAULT(new DefaultCommand());

    private final Command command;
    private static final Logger LOGGER = LogManager.getLogger();

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public static Command defineCommand(String commandType) {
        if (commandType == null || commandType.isEmpty()) {
            return CommandType.DEFAULT.getCommand();
        }
        try {
            return CommandType.valueOf(commandType.toUpperCase()).getCommand();
        } catch (IllegalArgumentException exception) {
            LOGGER.error("Error has occurred while defining command: " + exception);
            return CommandType.DEFAULT.getCommand();
        }
    }
}
