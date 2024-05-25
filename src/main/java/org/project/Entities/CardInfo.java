package org.project.Entities;

public class CardInfo {

    private String cardNumber;
    private String cardOwner;
    private String expirationDate;
    private String cvv;
    private String amount;

    public CardInfo(String cardNumber, String cardOwner, String expirationDate, String cvv, String amount) {
        this.cardNumber = cardNumber;
        this.cardOwner = cardOwner;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean validateCreditCardInfo() {

        String cardNumber = this.getCardNumber();
        String cardOwner = this.getCardOwner();
        String expirationDate = this.getExpirationDate();
        String cvv = this.getCvv();
        String amount = this.getAmount();

        // Validate card number (example: 16 digits)
        if (!cardNumber.matches("\\d{16}")) {
            return false;
        }

        // Validate card owner (non-empty and contains only letters and spaces)
        if (cardOwner.isEmpty() || !cardOwner.matches("[a-zA-Z\\s]+")) {
            return false;
        }

        // Validate expiration date (MM/YY format)
        if (!expirationDate.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            return false;
        }

        // Validate CVV (example: 3 or 4 digits)
        if (!cvv.matches("\\d{3,4}")) {
            return false;
        }

        // Validate amount (positive decimal number)
        if (!amount.matches("\\d+(\\.\\d{1,2})?")) {
            return false;
        }

        return true;
    }
}
