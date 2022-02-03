package com.dev.productioncenter.model.dao;

/**
 * @project Production Center
 * @author YanaV
 * The type Column name.
 */
public final class ColumnName {
    /**
     * The constant USER_ID.
     */
    public static final String USER_ID = "id_user";
    /**
     * The constant USER_LOGIN.
     */
    public static final String USER_LOGIN = "login";
    /**
     * The constant USER_PASSWORD.
     */
    public static final String USER_PASSWORD = "password";
    /**
     * The constant USER_SURNAME.
     */
    public static final String USER_SURNAME = "surname";
    /**
     * The constant USER_NAME.
     */
    public static final String USER_NAME = "name";
    /**
     * The constant USER_EMAIL.
     */
    public static final String USER_EMAIL = "email";
    /**
     * The constant USER_PHONE_NUMBER.
     */
    public static final String USER_PHONE_NUMBER = "phone_number";
    /**
     * The constant USER_STATUS.
     */
    public static final String USER_STATUS = "status";
    /**
     * The constant USER_ROLE.
     */
    public static final String USER_ROLE = "role";
    /**
     * The constant USER_PROFILE_PICTURE.
     */
    public static final String USER_PROFILE_PICTURE = "profile_picture";

    /**
     * The constant AGE_GROUP_ID.
     */
    public static final String AGE_GROUP_ID = "id_age_group";
    /**
     * The constant AGE_GROUP_MIN_AGE.
     */
    public static final String AGE_GROUP_MIN_AGE = "min_age";
    /**
     * The constant AGE_GROUP_MAX_AGE.
     */
    public static final String AGE_GROUP_MAX_AGE = "max_age";

    /**
     * The constant ACTIVITY_ID.
     */
    public static final String ACTIVITY_ID = "id_activity";
    /**
     * The constant ACTIVITY_CATEGORY.
     */
    public static final String ACTIVITY_CATEGORY = "category";
    /**
     * The constant ACTIVITY_TYPE.
     */
    public static final String ACTIVITY_TYPE = "type";

    /**
     * The constant COURSE_ID.
     */
    public static final String COURSE_ID = "courses.id_course";
    /**
     * The constant COURSE_DESCRIPTION.
     */
    public static final String COURSE_DESCRIPTION = "description";
    /**
     * The constant COURSE_LESSON_PRICE.
     */
    public static final String COURSE_LESSON_PRICE = "lesson_price";
    /**
     * The constant COURSE_STUDENT_AMOUNT.
     */
    public static final String COURSE_STUDENT_AMOUNT = "student_amount";
    /**
     * The constant COURSE_STATUS.
     */
    public static final String COURSE_STATUS = "status";

    /**
     * The constant LESSON_ID.
     */
    public static final String LESSON_ID = "id_lesson";
    /**
     * The constant LESSON_WEEK_DAY.
     */
    public static final String LESSON_WEEK_DAY = "week_day";
    /**
     * The constant LESSON_START_TIME.
     */
    public static final String LESSON_START_TIME = "start_time";
    /**
     * The constant LESSON_DURATION.
     */
    public static final String LESSON_DURATION = "duration";

    /**
     * The constant ENROLLMENT_ID.
     */
    public static final String ENROLLMENT_ID = "id_enrollment";
    /**
     * The constant ENROLLMENT_USER_SURNAME.
     */
    public static final String ENROLLMENT_USER_SURNAME = "users.surname";
    /**
     * The constant ENROLLMENT_USER_NAME.
     */
    public static final String ENROLLMENT_USER_NAME = "users.name";
    /**
     * The constant ENROLLMENT_COURSE_ID.
     */
    public static final String ENROLLMENT_COURSE_ID = "enrollments.id_course";
    /**
     * The constant ENROLLMENT_LESSON_AMOUNT.
     */
    public static final String ENROLLMENT_LESSON_AMOUNT = "lesson_amount";
    /**
     * The constant ENROLLMENT_TEACHER_SURNAME.
     */
    public static final String ENROLLMENT_TEACHER_SURNAME = "teacher.surname";
    /**
     * The constant ENROLLMENT_TEACHER_NAME.
     */
    public static final String ENROLLMENT_TEACHER_NAME = "teacher.name";
    /**
     * The constant ENROLLMENT_RESERVATION_DATETIME.
     */
    public static final String ENROLLMENT_RESERVATION_DATETIME = "reservation_datetime";
    /**
     * The constant ENROLLMENT_STATUS.
     */
    public static final String ENROLLMENT_STATUS = "status";

    /**
     * The constant BANK_CARD_BALANCE.
     */
    public static final String BANK_CARD_BALANCE = "balance";

    private ColumnName() {
    }
}
