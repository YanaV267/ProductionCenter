package com.dev.productioncenter.validator.impl;

import com.dev.productioncenter.validator.EnrollmentValidator;

import java.util.Map;

public class EnrollmentValidatorImpl implements EnrollmentValidator {
    private static final String LESSON_AMOUNT_REGEX = "([1-9]|1[0-9]|20)";
    private static final EnrollmentValidator instance = new EnrollmentValidatorImpl();

    private EnrollmentValidatorImpl() {

    }

    public static EnrollmentValidator getInstance() {
        return instance;
    }

    @Override
    public boolean checkLessonAmount(String lessonAmount) {
        return lessonAmount != null && lessonAmount.matches(LESSON_AMOUNT_REGEX);
    }

    @Override
    public boolean checkLessonAmount(Map<String, String> enrollmentsLessonAmount) {
        for (Map.Entry<String, String> enrollmentLessonAmount : enrollmentsLessonAmount.entrySet()) {
            if (!checkLessonAmount(enrollmentLessonAmount.getValue())) {
                return false;
            }
        }
        return true;
    }
}
