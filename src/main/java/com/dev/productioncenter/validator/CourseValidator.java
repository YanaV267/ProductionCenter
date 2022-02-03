package com.dev.productioncenter.validator;

import java.util.Map;

/**
 * @project Production Center
 * @author YanaV
 * The interface Course validator.
 */
public interface CourseValidator {
    /**
     * Check category boolean.
     *
     * @param category the category
     * @return the boolean
     */
    boolean checkCategory(String category);

    /**
     * Check type boolean.
     *
     * @param type the type
     * @return the boolean
     */
    boolean checkType(String type);

    /**
     * Check activity boolean.
     *
     * @param activityData the activity data
     * @return the boolean
     */
    boolean checkActivity(Map<String, String> activityData);

    /**
     * Check teacher boolean.
     *
     * @param teacher the teacher
     * @return the boolean
     */
    boolean checkTeacher(String teacher);

    /**
     * Check student amount boolean.
     *
     * @param studentAmount the student amount
     * @return the boolean
     */
    boolean checkStudentAmount(String studentAmount);

    /**
     * Check lesson price boolean.
     *
     * @param lessonPrice the lesson price
     * @return the boolean
     */
    boolean checkLessonPrice(String lessonPrice);

    /**
     * Check age boolean.
     *
     * @param minAge the min age
     * @param maxAge the max age
     * @return the boolean
     */
    boolean checkAge(String minAge, String maxAge);

    /**
     * Check weekdays boolean.
     *
     * @param weekdays the weekdays
     * @return the boolean
     */
    boolean checkWeekdays(String weekdays);

    /**
     * Check duration boolean.
     *
     * @param duration the duration
     * @return the boolean
     */
    boolean checkDuration(String duration);

    /**
     * Check time boolean.
     *
     * @param time the time
     * @return the boolean
     */
    boolean checkTime(String time);

    /**
     * Check lessons boolean.
     *
     * @param lessonData the lesson data
     * @return the boolean
     */
    boolean checkLessons(Map<String, String> lessonData);

    /**
     * Check course boolean.
     *
     * @param courseData the course data
     * @return the boolean
     */
    boolean checkCourse(Map<String, String> courseData);
}