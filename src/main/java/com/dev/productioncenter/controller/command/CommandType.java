package com.dev.productioncenter.controller.command;

import com.dev.productioncenter.controller.command.impl.ChangeLocaleCommand;
import com.dev.productioncenter.controller.command.impl.ChangeUserStatusCommand;
import com.dev.productioncenter.controller.command.impl.DefaultCommand;
import com.dev.productioncenter.controller.command.impl.account.UpdateAccountDataCommand;
import com.dev.productioncenter.controller.command.impl.account.UploadProfilePictureCommand;
import com.dev.productioncenter.controller.command.impl.AddActivityCommand;
import com.dev.productioncenter.controller.command.impl.course.AddCourseCommand;
import com.dev.productioncenter.controller.command.impl.course.UpdateCourseCommand;
import com.dev.productioncenter.controller.command.impl.go.*;
import com.dev.productioncenter.controller.command.impl.go.account.GoToAccountCommand;
import com.dev.productioncenter.controller.command.impl.go.account.GoToUpdateAccountDataCommand;
import com.dev.productioncenter.controller.command.impl.go.activity.GoToActivitiesCommand;
import com.dev.productioncenter.controller.command.impl.go.activity.GoToAddActivityCommand;
import com.dev.productioncenter.controller.command.impl.go.course.GoToAddCourseCommand;
import com.dev.productioncenter.controller.command.impl.go.course.GoToCourseInfoCommand;
import com.dev.productioncenter.controller.command.impl.go.course.GoToCoursesCommand;
import com.dev.productioncenter.controller.command.impl.go.course.GoToUpdateCourseCommand;
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
    GO_TO_COURSE_INFO(new GoToCourseInfoCommand()),
    GO_TO_ADD_COURSE(new GoToAddCourseCommand()),
    GO_TO_UPDATE_COURSE(new GoToUpdateCourseCommand()),
    GO_TO_ACCOUNT(new GoToAccountCommand()),
    GO_TO_UPDATE_ACCOUNT_DATA(new GoToUpdateAccountDataCommand()),
    GO_TO_ACTIVITIES(new GoToActivitiesCommand()),
    GO_TO_ADD_ACTIVITY(new GoToAddActivityCommand()),
    GO_TO_TIMETABLE(new GoToTimetableCommand()),
    GO_TO_CONTACTS(new GoToContactsCommand()),
    GO_TO_USERS(new GoToUsersCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    SIGN_IN(new SignInCommand()),
    SIGN_OUT(new SignOutCommand()),
    SIGN_UP(new SignUpCommand()),
    UPDATE_ACCOUNT_DATA(new UpdateAccountDataCommand()),
    UPLOAD_PROFILE_PICTURE(new UploadProfilePictureCommand()),
    CHANGE_USER_STATUS(new ChangeUserStatusCommand()),
    ADD_COURSE(new AddCourseCommand()),
    ADD_ACTIVITY(new AddActivityCommand()),
    UPDATE_COURSE(new UpdateCourseCommand()),
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
