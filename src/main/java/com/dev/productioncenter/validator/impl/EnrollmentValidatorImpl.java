package com.dev.productioncenter.validator.impl;

import com.dev.productioncenter.validator.EnrollmentValidator;

public class EnrollmentValidatorImpl implements EnrollmentValidator {
    private static final EnrollmentValidatorImpl instance = new EnrollmentValidatorImpl();
    private static final String LESSON_AMOUNT_REGEX = "([1-9]|1[0-9]|20)";

    private EnrollmentValidatorImpl() {

    }

    public static EnrollmentValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean checkLessonAmount(String lessonAmount) {
        return lessonAmount != null && lessonAmount.matches(LESSON_AMOUNT_REGEX);
    }
}
