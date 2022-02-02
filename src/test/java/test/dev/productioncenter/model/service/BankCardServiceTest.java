package test.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.BankCard;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.BankCardDao;
import com.dev.productioncenter.model.dao.impl.BankCardDaoImpl;
import com.dev.productioncenter.model.service.BankCardService;
import com.dev.productioncenter.model.service.EnrollmentService;
import com.dev.productioncenter.model.service.impl.BankCardServiceImpl;
import com.dev.productioncenter.model.service.impl.EnrollmentServiceImpl;
import com.dev.productioncenter.validator.BankCardValidator;
import com.dev.productioncenter.validator.impl.BankCardValidatorImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestParameter.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BankCardServiceTest {
    private MockedStatic<BankCardDaoImpl> bankCardDaoImpl;
    private MockedStatic<BankCardValidatorImpl> bankCardValidator;
    private MockedStatic<EnrollmentServiceImpl> enrollmentServiceImpl;
    @Mock
    private BankCardDao bankCardDao;
    @Mock
    private BankCardValidator validator;
    @Mock
    private EnrollmentService enrollmentService;
    @InjectMocks
    private BankCardService bankCardService = BankCardServiceImpl.getInstance();

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeTest
    public void initStatic() {
        bankCardDaoImpl = mockStatic(BankCardDaoImpl.class);
        bankCardValidator = mockStatic(BankCardValidatorImpl.class);
        enrollmentServiceImpl = mockStatic(EnrollmentServiceImpl.class);
    }

    @Test(dataProvider = "bankCardData")
    public void findCard(Map<String, String> bankCardData) throws ServiceException, DaoException {
        bankCardDaoImpl.when(BankCardDaoImpl::getInstance).thenReturn(bankCardDao);
        bankCardValidator.when(BankCardValidatorImpl::getInstance).thenReturn(validator);

        when(validator.checkCardData(anyMap())).thenReturn(true);
        when(bankCardDao.findBankCard(any(BankCard.class))).thenReturn(Optional.of(new BankCard()));

        Optional<BankCard> actual = bankCardService.findCard(bankCardData);
        Assert.assertTrue(actual.isPresent());
    }

    @Test
    public void replenishBalance() throws ServiceException, DaoException {
        bankCardDaoImpl.when(BankCardDaoImpl::getInstance).thenReturn(bankCardDao);
        bankCardValidator.when(BankCardValidatorImpl::getInstance).thenReturn(validator);

        when(validator.checkBalance(anyString())).thenReturn(true);
        when(bankCardDao.findBalance(any(BankCard.class))).thenReturn(new BigDecimal("15"));
        when(bankCardDao.update(any(BankCard.class))).thenReturn(false);

        BankCard bankCard = new BankCard();
        String replenishmentValue = "100";
        boolean actual = bankCardService.replenishBalance(bankCard, replenishmentValue);
        Assert.assertFalse(actual);
    }

    @Test
    public void withdrawMoneyForEnrollment() throws ServiceException, DaoException {
        bankCardDaoImpl.when(BankCardDaoImpl::getInstance).thenReturn(bankCardDao);
        enrollmentServiceImpl.when(EnrollmentServiceImpl::getInstance).thenReturn(enrollmentService);

        when(enrollmentService.findEnrollment(anyLong())).thenReturn(Optional.of(
                new Enrollment.EnrollmentBuilder()
                        .setCourse(new Course.CourseBuilder()
                                .setLessonPrice(BigDecimal.valueOf(5))
                                .build())
                        .setLessonAmount(5)
                        .build()));
        when(bankCardDao.update(any(BankCard.class))).thenReturn(true);

        BankCard bankCard = new BankCard();
        bankCard.setBalance(BigDecimal.valueOf(100));
        long enrollmentId = 3;
        boolean actual = bankCardService.withdrawMoneyForEnrollment(bankCard, enrollmentId);
        Assert.assertTrue(actual);
    }

    @DataProvider(name = "bankCardData")
    public Object[][] getBankCardData() {
        return new Object[][]{
                {Map.of(CARD_NUMBER, "1111 2222 3333 4444", OWNER_NAME, "YANA VOLKOVA",
                        EXPIRATION_DATE, "08/23", CVV_NUMBER, "589")},
                {Map.of(CARD_NUMBER, "111122223333 4444", OWNER_NAME, "Yana Volkova",
                        EXPIRATION_DATE, "08/23", CVV_NUMBER, "5859")},
                {Map.of(CARD_NUMBER, "1111 2222 3333 4444", OWNER_NAME, "YANA VOLKOVA",
                        EXPIRATION_DATE, "08/13", CVV_NUMBER, "589")}
        };
    }
}
