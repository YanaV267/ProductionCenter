package test.dev.productioncenter.util;

import com.dev.productioncenter.util.PhoneNumberFormatter;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigInteger;

public class PhoneNumberFormatterTest {
    private final BigInteger initialPhoneNumber = new BigInteger("375298940073");

    @Test
    public void format() {
        String expected = "+375(29)894-00-73";
        String actual = PhoneNumberFormatter.format(initialPhoneNumber);
        Assert.assertEquals(actual, expected);
    }
}
