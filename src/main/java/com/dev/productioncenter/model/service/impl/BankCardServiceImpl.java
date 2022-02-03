package com.dev.productioncenter.model.service.impl;

import com.dev.productioncenter.entity.BankCard;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.BankCardDao;
import com.dev.productioncenter.model.dao.impl.BankCardDaoImpl;
import com.dev.productioncenter.model.service.BankCardService;
import com.dev.productioncenter.model.service.EnrollmentService;
import com.dev.productioncenter.validator.BankCardValidator;
import com.dev.productioncenter.validator.impl.BankCardValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

/**
 * @project Production Center
 * @author YanaV
 * The type Bank card service.
 */
public class BankCardServiceImpl implements BankCardService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DATE_DELIMITER_REGEX = "/";
    private static final String CARD_NUMBER_REMOVING_SYMBOL_REGEX = " ";
    private static final String CARD_NUMBER_REPLACEMENT_SYMBOL_REGEX = "";
    private static final int EXPIRATION_DATE_MILLENNIUM_VALUE = 2000;
    private static final BankCardService instance = new BankCardServiceImpl();
    private final Map<Integer, Integer> lastDayOfMonth = new HashMap<>();

    private BankCardServiceImpl() {
        lastDayOfMonth.put(1, 31);
        lastDayOfMonth.put(2, 28);
        lastDayOfMonth.put(3, 31);
        lastDayOfMonth.put(4, 30);
        lastDayOfMonth.put(5, 31);
        lastDayOfMonth.put(6, 30);
        lastDayOfMonth.put(7, 31);
        lastDayOfMonth.put(8, 31);
        lastDayOfMonth.put(9, 30);
        lastDayOfMonth.put(10, 31);
        lastDayOfMonth.put(11, 30);
        lastDayOfMonth.put(12, 31);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static BankCardService getInstance() {
        return instance;
    }

    @Override
    public Optional<BankCard> findCard(Map<String, String> bankCardData) throws ServiceException {
        BankCardDao bankCardDao = BankCardDaoImpl.getInstance();
        BankCardValidator validator = BankCardValidatorImpl.getInstance();
        try {
            if (validator.checkCardData(bankCardData)) {
                BankCard bankCard = new BankCard();
                bankCard.setCardNumber(Long.parseLong(bankCardData.get(CARD_NUMBER)
                        .replaceAll(CARD_NUMBER_REMOVING_SYMBOL_REGEX, CARD_NUMBER_REPLACEMENT_SYMBOL_REGEX)));
                bankCard.setOwnerName(bankCardData.get(OWNER_NAME));
                String[] expirationDateParameters = bankCardData.get(EXPIRATION_DATE).split(DATE_DELIMITER_REGEX);
                LocalDate localDate = LocalDate.of(EXPIRATION_DATE_MILLENNIUM_VALUE
                                + Integer.parseInt(expirationDateParameters[1]),
                        Integer.parseInt(expirationDateParameters[0]),
                        lastDayOfMonth.get(Integer.parseInt(expirationDateParameters[0])));
                bankCard.setExpirationDate(localDate);
                bankCard.setCvvNumber(Integer.parseInt(bankCardData.get(CVV_NUMBER)));
                return bankCardDao.findBankCard(bankCard);
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding bank card: " + exception);
            throw new ServiceException("Error has occurred while finding bank card: ", exception);
        }
        return Optional.empty();
    }

    @Override
    public boolean replenishBalance(BankCard bankCard, String replenishmentValue) throws ServiceException {
        BankCardDao bankCardDao = BankCardDaoImpl.getInstance();
        BankCardValidator validator = BankCardValidatorImpl.getInstance();
        try {
            if (validator.checkBalance(replenishmentValue)) {
                BigDecimal currentBalance = bankCardDao.findBalance(bankCard);
                BigDecimal newBalance = currentBalance.add(BigDecimal.valueOf(Double.parseDouble(replenishmentValue)));
                bankCard.setBalance(newBalance);
                return bankCardDao.update(bankCard);
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while replenishing bank card balance: " + exception);
            throw new ServiceException("Error has occurred while replenishing bank card balance: ", exception);
        }
        return false;
    }

    @Override
    public boolean withdrawMoneyForEnrollment(BankCard bankCard, long enrollmentId) throws ServiceException {
        BankCardDao bankCardDao = BankCardDaoImpl.getInstance();
        EnrollmentService enrollmentService = EnrollmentServiceImpl.getInstance();
        Optional<Enrollment> enrollment = enrollmentService.findEnrollment(enrollmentId);
        try {
            if (enrollment.isPresent()) {
                BigDecimal totalCost = enrollment.get().getCourse().getLessonPrice()
                        .multiply(BigDecimal.valueOf(enrollment.get().getLessonAmount()));
                if (bankCard.getBalance().compareTo(totalCost) >= 0) {
                    BigDecimal currentBalance = bankCard.getBalance();
                    BigDecimal newBalance = currentBalance.subtract(totalCost);
                    bankCard.setBalance(newBalance);
                    return bankCardDao.update(bankCard);
                }
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while withdrawing money from bank card: " + exception);
            throw new ServiceException("Error has occurred while withdrawing money from bank card: ", exception);
        }
        return false;
    }
}
