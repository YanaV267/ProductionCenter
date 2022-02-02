package test.dev.productioncenter.validator;

import com.dev.productioncenter.validator.UserValidator;
import com.dev.productioncenter.validator.impl.UserValidatorImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserValidatorTest {
    private UserValidator validator;

    @BeforeClass
    public void init() {
        validator = UserValidatorImpl.getInstance();
    }

    @Test
    public void checkLogin() {
        String login = "sofa279";
        boolean actual = validator.checkLogin(login);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkPassword() {
        String password = "sofa27";
        boolean actual = validator.checkPassword(password);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkSurname() {
        String surname = "самуйлова";
        boolean actual = validator.checkSurname(surname);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkName() {
        String name = "Sofia";
        boolean actual = validator.checkName(name);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkEmail() {
        String email = "sofa279@gmail.com";
        boolean actual = validator.checkEmail(email);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkNumber() {
        String number = "+375(29)2389104";
        boolean actual = validator.checkNumber(number);
        Assert.assertTrue(actual);
    }
}
