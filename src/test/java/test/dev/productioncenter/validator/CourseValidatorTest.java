package test.dev.productioncenter.validator;

import com.dev.productioncenter.validator.CourseValidator;
import com.dev.productioncenter.validator.impl.CourseValidatorImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class CourseValidatorTest {
    private CourseValidator validator;

    @BeforeClass
    public void init() {
        validator = CourseValidatorImpl.getInstance();
    }

    @Test
    public void checkCategory() {
        String category = "Инструментальное";
        boolean actual = validator.checkCategory(category);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkType() {
        String type = "Гитара";
        boolean actual = validator.checkType(type);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkTeacher() {
        String teacher = "Лесюкова Марина";
        boolean actual = validator.checkTeacher(teacher);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkStudentAmount() {
        String studentAmount = "20";
        boolean actual = validator.checkStudentAmount(studentAmount);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkLessonPrice() {
        String lessonPrice = "3.05";
        boolean actual = validator.checkLessonPrice(lessonPrice);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkAge() {
        String minAge = "25";
        String maxAge = "20";
        boolean actual = validator.checkAge(minAge, maxAge);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkWeekdays() {
        String weekdays = List.of("вт", "пт").toString();
        boolean actual = validator.checkWeekdays(weekdays);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkDuration() {
        String duration = "50";
        boolean actual = validator.checkDuration(duration);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkTime() {
        String time = "17:60";
        boolean actual = validator.checkTime(time);
        Assert.assertFalse(actual);
    }
}