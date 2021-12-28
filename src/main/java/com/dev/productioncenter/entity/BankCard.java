package com.dev.productioncenter.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BankCard extends AbstractEntity {
    private long cardNumber;
    private LocalDate expirationDate;
    private String ownerName;
    private int cvvNumber;
    private BigDecimal balance;

    public BankCard() {

    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getCvvNumber() {
        return cvvNumber;
    }

    public void setCvvNumber(int cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BankCard bankCard = (BankCard) o;
        if (cardNumber != bankCard.cardNumber) {
            return false;
        }
        if (cvvNumber != bankCard.cvvNumber) {
            return false;
        }
        if (expirationDate != null ? !expirationDate.equals(bankCard.expirationDate) : bankCard.expirationDate != null) {
            return false;
        }
        if (ownerName != null ? !ownerName.equals(bankCard.ownerName) : bankCard.ownerName != null) {
            return false;
        }
        return balance != null ? balance.equals(bankCard.balance) : bankCard.balance == null;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 31 + Long.hashCode(cardNumber);
        result = result * 31 + expirationDate.hashCode();
        result = result * 31 + (ownerName != null ? ownerName.hashCode() : 0);
        result = result * 31 + Long.hashCode(cvvNumber);
        result = result * 31 + balance.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("cardNumber=").append(cardNumber);
        sb.append(", expirationDate=").append(expirationDate);
        sb.append(", ownerName='").append(ownerName);
        sb.append("', cvvNumber=").append(cvvNumber);
        sb.append(", balance=").append(balance).append("}");
        return sb.toString();
    }
}
