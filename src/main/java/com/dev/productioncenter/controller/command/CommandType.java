package com.dev.productioncenter.controller.command;

import com.dev.productioncenter.controller.command.impl.*;
import com.dev.productioncenter.controller.command.impl.account.*;
import com.dev.productioncenter.controller.command.impl.course.*;
import com.dev.productioncenter.controller.command.impl.enrollment.*;
import com.dev.productioncenter.controller.command.impl.go.*;
import com.dev.productioncenter.controller.command.impl.go.account.*;
import com.dev.productioncenter.controller.command.impl.go.activity.*;
import com.dev.productioncenter.controller.command.impl.go.course.*;
import com.dev.productioncenter.controller.command.impl.go.enrollment.*;
import com.dev.productioncenter.controller.command.impl.signing.*;
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
    GO_TO_REPLENISH_BALANCE(new GoToReplenishBalanceCommand()),
    GO_TO_UPDATE_ACCOUNT_DATA(new GoToUpdateAccountDataCommand()),
    GO_TO_ACTIVITIES(new GoToActivitiesCommand()),
    GO_TO_ADD_ACTIVITY(new GoToAddActivityCommand()),
    GO_TO_ENROLL_ON_COURSE(new GoToEnrollOnCourseCommand()),
    GO_TO_ENROLLMENTS(new GoToEnrollmentsCommand()),
    GO_TO_TIMETABLE(new GoToTimetableCommand()),
    GO_TO_CONTACTS(new GoToContactsCommand()),
    GO_TO_USERS(new GoToUsersCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    SIGN_IN(new SignInCommand()),
    SIGN_OUT(new SignOutCommand()),
    SIGN_UP(new SignUpCommand()),
    UPDATE_ACCOUNT_DATA(new UpdateAccountDataCommand()),
    REPLENISH_BALANCE(new ReplenishBalanceCommand()),
    UPLOAD_PROFILE_PICTURE(new UploadProfilePictureCommand()),
    CHANGE_USER_STATUS(new ChangeUserStatusCommand()),
    CHANGE_ENROLLMENT_STATUS(new ChangeEnrollmentStatusCommand()),
    ADD_COURSE(new AddCourseCommand()),
    ADD_ACTIVITY(new AddActivityCommand()),
    UPDATE_COURSE(new UpdateCourseCommand()),
    ENROLL_ON_COURSE(new EnrollOnCourseCommand()),
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
