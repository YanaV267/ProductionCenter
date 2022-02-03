package com.dev.productioncenter.validator;

import java.util.Map;

/**
 * @project Production Center
 * @author YanaV
 * The interface Enrollment validator.
 */
public interface EnrollmentValidator {
    /**
     * Check lesson amount boolean.
     *
     * @param lessonAmount the lesson amount
     * @return the boolean
     */
    boolean checkLessonAmount(String lessonAmount);

    /**
     * Check lesson amount boolean.
     *
     * @param enrollmentsLessonAmount the enrollments lesson amount
     * @return the boolean
     */
    boolean checkLessonAmount(Map<String, String> enrollmentsLessonAmount);
}
