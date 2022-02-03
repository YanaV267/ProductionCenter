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

/**
 * @project Production Center
 * @author YanaV
 * The enum Command type.
 */
public enum CommandType {
    /**
     * The Go to home.
     */
    GO_TO_HOME(new GoToHomeCommand()),
    /**
     * The Go to sign in.
     */
    GO_TO_SIGN_IN(new GoToSignInCommand()),
    /**
     * The Go to sign up.
     */
    GO_TO_SIGN_UP(new GoToSignUpCommand()),
    /**
     * The Go to courses.
     */
    GO_TO_COURSES(new GoToCoursesCommand()),
    /**
     * The Go to course info.
     */
    GO_TO_COURSE_INFO(new GoToCourseInfoCommand()),
    /**
     * The Go to add course.
     */
    GO_TO_ADD_COURSE(new GoToAddCourseCommand()),
    /**
     * The Go to update course.
     */
    GO_TO_UPDATE_COURSE(new GoToUpdateCourseCommand()),
    /**
     * The Go to account.
     */
    GO_TO_ACCOUNT(new GoToAccountCommand()),
    /**
     * The Go to replenish balance.
     */
    GO_TO_REPLENISH_BALANCE(new GoToReplenishBalanceCommand()),
    /**
     * The Go to update account data.
     */
    GO_TO_UPDATE_ACCOUNT_DATA(new GoToUpdateAccountDataCommand()),
    /**
     * The Go to activities.
     */
    GO_TO_ACTIVITIES(new GoToActivitiesCommand()),
    /**
     * The Go to add activity.
     */
    GO_TO_ADD_ACTIVITY(new GoToAddActivityCommand()),
    /**
     * The Go to enroll on course.
     */
    GO_TO_ENROLL_ON_COURSE(new GoToEnrollOnCourseCommand()),
    /**
     * The Go to enrollments.
     */
    GO_TO_ENROLLMENTS(new GoToEnrollmentsCommand()),
    /**
     * The Go to user enrollments.
     */
    GO_TO_USER_ENROLLMENTS(new GoToUserEnrollmentsCommand()),
    /**
     * The Go to enrolled on course.
     */
    GO_TO_ENROLLED_ON_COURSE(new GoToEnrolledOnCourseCommand()),
    /**
     * The Go to pay for enrollment.
     */
    GO_TO_PAY_FOR_ENROLLMENT(new GoToPayForEnrollmentCommand()),
    /**
     * The Go to timetable.
     */
    GO_TO_TIMETABLE(new GoToTimetableCommand()),
    /**
     * The Go to contacts.
     */
    GO_TO_CONTACTS(new GoToContactsCommand()),
    /**
     * The Go to users.
     */
    GO_TO_USERS(new GoToUsersCommand()),
    /**
     * The Go to teachers.
     */
    GO_TO_TEACHERS(new GoToTeachersCommand()),
    /**
     * The Change locale.
     */
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    /**
     * The Sign in.
     */
    SIGN_IN(new SignInCommand()),
    /**
     * The Sign out.
     */
    SIGN_OUT(new SignOutCommand()),
    /**
     * The Sign up.
     */
    SIGN_UP(new SignUpCommand()),
    /**
     * The Update account data.
     */
    UPDATE_ACCOUNT_DATA(new UpdateAccountDataCommand()),
    /**
     * The Replenish balance.
     */
    REPLENISH_BALANCE(new ReplenishBalanceCommand()),
    /**
     * The Upload profile picture.
     */
    UPLOAD_PROFILE_PICTURE(new UploadProfilePictureCommand()),
    /**
     * The Change user status.
     */
    CHANGE_USER_STATUS(new ChangeUserStatusCommand()),
    /**
     * The Change user role.
     */
    CHANGE_USER_ROLE(new ChangeUserRoleCommand()),
    /**
     * The Change enrollment status.
     */
    CHANGE_ENROLLMENT_STATUS(new ChangeEnrollmentStatusCommand()),
    /**
     * The Enroll on course.
     */
    ENROLL_ON_COURSE(new EnrollOnCourseCommand()),
    /**
     * The Update enrollment.
     */
    UPDATE_ENROLLMENT(new UpdateEnrollmentCommand()),
    /**
     * The Renew enrollment.
     */
    RENEW_ENROLLMENT(new RenewEnrollmentCommand()),
    /**
     * The Cancel enrollment.
     */
    CANCEL_ENROLLMENT(new CancelEnrollmentCommand()),
    /**
     * The Pay for enrollment.
     */
    PAY_FOR_ENROLLMENT(new PayForEnrollmentCommand()),
    /**
     * The Add activity.
     */
    ADD_ACTIVITY(new AddActivityCommand()),
    /**
     * The Add course.
     */
    ADD_COURSE(new AddCourseCommand()),
    /**
     * The Update course.
     */
    UPDATE_COURSE(new UpdateCourseCommand()),
    /**
     * The Search courses.
     */
    SEARCH_COURSES(new SearchCoursesCommand()),
    /**
     * The Search users.
     */
    SEARCH_USERS(new SearchUsersCommand()),
    /**
     * The Search teachers.
     */
    SEARCH_TEACHERS(new SearchTeachersCommand()),
    /**
     * The Default.
     */
    DEFAULT(new DefaultCommand());

    private final Command command;
    private static final Logger LOGGER = LogManager.getLogger();

    CommandType(Command command) {
        this.command = command;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Define command command.
     *
     * @param commandType the command type
     * @return the command
     */
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
