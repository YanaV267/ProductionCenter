package com.dev.productioncenter.validator.impl;

import com.dev.productioncenter.validator.CourseValidator;

import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class CourseValidatorImpl implements CourseValidator {
    private static final String INCORRECT_VALUE_PARAMETER = "incorrect";
    private static final String CATEGORY_REGEX = "[а-яА-Я]{3,20}";
    private static final String TYPE_REGEX = "[\\p{Alpha}а-яА-Я -&]{3,30}";
    private static final String TEACHER_REGEX = "[\\p{Alpha}а-яА-Я ]{5,30}";
    private static final String AGE_REGEX = "\\b([3-9]|[1-5]\\d|60)\\b";
    private static final String STUDENT_AMOUNT_REGEX = "\\b([1-9]|[1-5]\\d|30)\\b";
    private static final String LESSON_PRICE_REGEX = "^((\\d\\d\\.)?\\d{1,2})$";
    private static final String DURATION_REGEX = "(30|45|60|75|90|105|120)";
    private static final String TIME_REGEX = "(1[0-9]|20):[0-5][05]";
    private static final String CATEGORY_REMOVING_SYMBOLS_REGEX = "[\\[\\]]";
    private static final String CATEGORY_REPLACEMENT_REGEX = "";
    private static final CourseValidatorImpl instance = new CourseValidatorImpl();

    private CourseValidatorImpl() {
    }

    public static CourseValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean checkCategory(String category) {
        return category != null &&
                category.replaceAll(CATEGORY_REMOVING_SYMBOLS_REGEX, CATEGORY_REPLACEMENT_REGEX)
                        .matches(CATEGORY_REGEX);
    }

    @Override
    public boolean checkType(String type) {
        return type != null && type.matches(TYPE_REGEX);
    }

    @Override
    public boolean checkActivity(Map<String, String> activityData) {
        boolean isValid = true;
        if (!checkCategory(activityData.get(CATEGORY))) {
            activityData.put(CATEGORY, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkType(activityData.get(TYPE))) {
            activityData.put(TYPE, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean checkTeacher(String teacher) {
        return teacher != null && teacher.matches(TEACHER_REGEX);
    }

    @Override
    public boolean checkStudentAmount(String studentAmount) {
        return studentAmount != null && studentAmount.matches(STUDENT_AMOUNT_REGEX);
    }

    @Override
    public boolean checkLessonPrice(String lessonPrice) {
        return lessonPrice != null && lessonPrice.matches(LESSON_PRICE_REGEX) && Double.parseDouble(lessonPrice) >= 1
                && Double.parseDouble(lessonPrice) <= 30;
    }

    @Override
    public boolean checkAge(String minAge, String maxAge) {
        return minAge != null && maxAge != null && minAge.matches(AGE_REGEX) && maxAge.matches(AGE_REGEX)
                && Integer.parseInt(minAge) < Integer.parseInt(maxAge);
    }

    @Override
    public boolean checkWeekdays(String weekdays) {
        return weekdays != null;
    }

    @Override
    public boolean checkDuration(String duration) {
        return duration != null && duration.matches(DURATION_REGEX);
    }

    @Override
    public boolean checkTime(String time) {
        return time != null && time.matches(TIME_REGEX);
    }

    @Override
    public boolean checkLessons(Map<String, String> lessonData) {
        boolean isValid = true;
        if (!checkWeekdays(lessonData.get(WEEKDAYS))) {
            lessonData.put(WEEKDAYS, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkDuration(lessonData.get(DURATION))) {
            lessonData.put(DURATION, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkTime(lessonData.get(TIME))) {
            lessonData.put(TIME, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean checkCourse(Map<String, String> courseData) {
        boolean isValid = checkActivity(courseData);
        if (!checkTeacher(courseData.get(TEACHER))) {
            courseData.put(TEACHER, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkAge(courseData.get(MIN_AGE), courseData.get(MAX_AGE))) {
            courseData.put(MIN_AGE, INCORRECT_VALUE_PARAMETER);
            courseData.put(MAX_AGE, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkStudentAmount(courseData.get(STUDENT_AMOUNT))) {
            courseData.put(STUDENT_AMOUNT, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkLessonPrice(courseData.get(LESSON_PRICE))) {
            courseData.put(LESSON_PRICE, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkLessons(courseData)) {
            isValid = false;
        }
        return isValid;
    }
}
