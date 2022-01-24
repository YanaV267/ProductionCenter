package com.dev.productioncenter.controller.command.impl.account;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.entity.BankCard;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.BankCardService;
import com.dev.productioncenter.model.service.impl.BankCardServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestAttribute.CARD;
import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class ReplenishBalanceCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String REPLENISH_BALANCE_CONFIRM_MESSAGE_KEY = "confirm.balance.replenish_balance";
    private static final String INCORRECT_CARD_DATA_ERROR_MESSAGE_KEY = "error.balance.incorrect_data";
    private final BankCardService bankCardService = new BankCardServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, String> cardData = new HashMap<>();
        cardData.put(CARD_NUMBER, request.getParameter(CARD_NUMBER));
        cardData.put(OWNER_NAME, request.getParameter(OWNER_NAME));
        cardData.put(EXPIRATION_DATE, request.getParameter(EXPIRATION_DATE));
        cardData.put(CVV_NUMBER, request.getParameter(CVV_NUMBER));
        cardData.put(BALANCE, request.getParameter(BALANCE));
        try {
            Optional<BankCard> bankCard = bankCardService.findCard(cardData);
            if (bankCard.isPresent() && bankCardService.replenishBalance(bankCard.get(), cardData.get(BALANCE))) {
                session.setAttribute(SessionAttribute.MESSAGE, REPLENISH_BALANCE_CONFIRM_MESSAGE_KEY);
                return new Router(PagePath.ACCOUNT, Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(CARD, cardData);
                session.setAttribute(SessionAttribute.MESSAGE, INCORRECT_CARD_DATA_ERROR_MESSAGE_KEY);
                return new Router(PagePath.REPLENISH_BALANCE, Router.RouterType.FORWARD);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while replenishing balance: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
