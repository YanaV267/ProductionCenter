package com.development.productioncenter.controller.command;

import com.development.productioncenter.controller.command.impl.*;
import com.development.productioncenter.controller.command.impl.go.*;
import com.development.productioncenter.controller.command.impl.signing.SignInCommand;
import com.development.productioncenter.controller.command.impl.signing.SignOutCommand;
import com.development.productioncenter.controller.command.impl.signing.SignUpCommand;

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

    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
