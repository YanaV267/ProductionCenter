package com.dev.productioncenter.validator;

import java.util.Map;

public interface CourseValidator {
    boolean checkCategory(String category);

    boolean checkType(String type);

    boolean checkActivity(Map<String, String> activityData);

    boolean checkTeacher(String teacher);

    boolean checkStudentAmount(String studentAmount);

    boolean checkLessonPrice(String lessonPrice);

    boolean checkAge(String minAge, String maxAge);

    boolean checkWeekdays(String weekdays);

    boolean checkDuration(String duration);

    boolean checkTime(String time);

    boolean checkLessons(Map<String, String> lessonData);

    boolean checkCourse(Map<String, String> courseData);
}