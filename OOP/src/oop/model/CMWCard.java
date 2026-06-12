package oop.model;

import java.math.BigDecimal;

import oop.model.base.baseCard;

public class CMWCard extends baseCard {

    public CMWCard(String cardNumber, String username, BigDecimal balance) {
        super(cardNumber, username, balance);  // call from baseCard
    }

    @Override
    public BigDecimal getMaxWithdraw() { return new BigDecimal("3000"); }

    @Override
    public BigDecimal getMinDeposit() { return new BigDecimal("5"); }

    @Override
    public String getBankName() { return "CMW"; }

    @Override
    public void showBalance(String name) {
        System.out.println("CMW bank account with number " + getMaskedNumber() + " has balance is " + balance);
    }
}