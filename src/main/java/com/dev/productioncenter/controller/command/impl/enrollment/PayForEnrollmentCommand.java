package com.dev.productioncenter.controller.command.impl.enrollment;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.entity.BankCard;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.EnrollmentStatus;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.BankCardService;
import com.dev.productioncenter.model.service.EnrollmentService;
import com.dev.productioncenter.model.service.impl.BankCardServiceImpl;
import com.dev.productioncenter.model.service.impl.EnrollmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestAttribute.CARD;
import static com.dev.productioncenter.controller.command.RequestAttribute.MESSAGE;
import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class PayForEnrollmentCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PAYING_FOR_ENROLLMENT_CONFIRM_MESSAGE_KEY = "confirm.paying_for_enrollment";
    private static final String NO_FUNDS_ERROR_MESSAGE_KEY = "error.balance.no_funds";
    private static final String INCORRECT_CARD_DATA_ERROR_MESSAGE_KEY = "error.balance.incorrect_data";
    private final BankCardService bankCardService = new BankCardServiceImpl();
    private final EnrollmentService enrollmentService = new EnrollmentServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        long chosenEnrollmentId = Long.parseLong(request.getParameter(CHOSEN_ENROLLMENT_ID));
        Map<String, String> cardData = new HashMap<>();
        cardData.put(CARD_NUMBER, request.getParameter(CARD_NUMBER));
        cardData.put(OWNER_NAME, request.getParameter(OWNER_NAME));
        cardData.put(EXPIRATION_DATE, request.getParameter(EXPIRATION_DATE));
        cardData.put(CVV_NUMBER, request.getParameter(CVV_NUMBER));
        cardData.put(BALANCE, request.getParameter(BALANCE));
        try {
            Optional<BankCard> bankCard = bankCardService.findCard(cardData);
            if (bankCard.isPresent()) {
                if (bankCardService.withdrawMoneyForEnrollment(bankCard.get(), chosenEnrollmentId)) {
                    if (enrollmentService.updateStatus(chosenEnrollmentId, EnrollmentStatus.PAID)) {
                        Map<Enrollment, LocalDate> enrollments = enrollmentService.findEnrollments(user);
                        session.setAttribute(SessionAttribute.ENROLLMENTS, enrollments);
                        session.setAttribute(SessionAttribute.MESSAGE, PAYING_FOR_ENROLLMENT_CONFIRM_MESSAGE_KEY);
                        return new Router(PagePath.SHOW_USER_ENROLLMENTS, Router.RouterType.REDIRECT);
                    }
                } else {
                    request.setAttribute(CARD, cardData);
                    request.setAttribute(MESSAGE, NO_FUNDS_ERROR_MESSAGE_KEY);
                    return new Router(PagePath.PAY_FOR_ENROLLMENT, Router.RouterType.FORWARD);
                }
            } else {
                request.setAttribute(CARD, cardData);
                request.setAttribute(MESSAGE, INCORRECT_CARD_DATA_ERROR_MESSAGE_KEY);
                return new Router(PagePath.PAY_FOR_ENROLLMENT, Router.RouterType.FORWARD);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while paying for enrollment: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
