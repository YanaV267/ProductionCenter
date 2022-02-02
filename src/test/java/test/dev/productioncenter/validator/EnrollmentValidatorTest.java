package test.dev.productioncenter.validator;

import com.dev.productioncenter.validator.EnrollmentValidator;
import com.dev.productioncenter.validator.impl.EnrollmentValidatorImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EnrollmentValidatorTest {
    private EnrollmentValidator validator;

    @BeforeClass
    public void init() {
        validator = EnrollmentValidatorImpl.getInstance();
    }

    @Test
    public void checkLessonAmount() {
        String lessonAmount = "15";
        boolean actual = validator.checkLessonAmount(lessonAmount);
        Assert.assertTrue(actual);
    }
}