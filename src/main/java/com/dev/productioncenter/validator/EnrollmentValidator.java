package com.dev.productioncenter.validator;

import java.util.Map;

public interface EnrollmentValidator {
    boolean checkLessonAmount(String lessonAmount);

    boolean checkLessonAmount(Map<String, String> enrollmentsLessonAmount);
}
