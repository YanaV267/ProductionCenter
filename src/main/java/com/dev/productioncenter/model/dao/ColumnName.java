package com.dev.productioncenter.model.dao;

public final class ColumnName {
    public static final String USER_ID = "id_user";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_SURNAME = "surname";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONE_NUMBER = "phone_number";
    public static final String USER_STATUS = "status";
    public static final String USER_ROLE = "role";
    public static final String USER_PROFILE_PICTURE = "profile_picture";

    public static final String AGE_GROUP_MIN_AGE = "min_age";
    public static final String AGE_GROUP_MAX_AGE = "max_age";

    public static final String ACTIVITY_ID = "id_activity";
    public static final String ACTIVITY_CATEGORY = "category";
    public static final String ACTIVITY_TYPE = "type";

    public static final String COURSE_ID = "courses.id_course";
    public static final String COURSE_DESCRIPTION = "description";
    public static final String COURSE_LESSON_PRICE = "lesson_price";
    public static final String COURSE_STUDENT_AMOUNT = "student_amount";
    public static final String COURSE_STATUS = "status";

    public static final String LESSON_ID = "id_lesson";
    public static final String LESSON_WEEK_DAY = "week_day";
    public static final String LESSON_START_TIME = "start_time";
    public static final String LESSON_DURATION = "duration";

    public static final String ENROLLMENT_ID = "id_enrollment";
    public static final String ENROLLMENT_USER_SURNAME = "users.surname";
    public static final String ENROLLMENT_USER_NAME = "users.name";
    public static final String ENROLLMENT_COURSE_ID = "enrollments.id_course";
    public static final String ENROLLMENT_LESSON_AMOUNT = "lesson_amount";
    public static final String ENROLLMENT_TEACHER_SURNAME = "teacher.surname";
    public static final String ENROLLMENT_TEACHER_NAME = "teacher.name";
    public static final String ENROLLMENT_RESERVATION_DATETIME = "reservation_datetime";
    public static final String ENROLLMENT_STATUS = "status";

    public static final String BANK_CARD_BALANCE = "balance";

    private ColumnName() {
    }
}
