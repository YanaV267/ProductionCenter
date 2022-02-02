package test.dev.productioncenter.validator;

import com.dev.productioncenter.validator.BankCardValidator;
import com.dev.productioncenter.validator.impl.BankCardValidatorImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BankCardValidatorTest {
    private BankCardValidator validator;

    @BeforeClass
    public void init() {
        validator = BankCardValidatorImpl.getInstance();
    }

    @Test
    public void checkNumber() {
        String cardNumber = "1234 5678 3456 5678";
        boolean actual = validator.checkNumber(cardNumber);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkOwnerName() {
        String ownerName = "Yana Volkova";
        boolean actual = validator.checkOwnerName(ownerName);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkExpirationDate() {
        String expirationDate = "08/23";
        boolean actual = validator.checkExpirationDate(expirationDate);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkCVVNumber() {
        String CVVNumber = "2378";
        boolean actual = validator.checkCVVNumber(CVVNumber);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkBalance() {
        String balance = "143.90";
        boolean actual = validator.checkBalance(balance);
        Assert.assertTrue(actual);
    }
}
