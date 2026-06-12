package oop.model;

import java.math.BigDecimal;

import oop.model.base.baseCard;

public class NABCard extends baseCard {

    public NABCard(String cardNumber, String username, BigDecimal balance) {
        super(cardNumber, username, balance);  // call from baseCard
    }

    @Override
    public BigDecimal getMaxWithdraw() { return new BigDecimal("2000"); }

    @Override
    public BigDecimal getMinDeposit() { return new BigDecimal("10"); }

    @Override
    public String getBankName() { return "NAB"; }

    @Override
    public void showBalance(String name) {
    	System.out.println("Hi " + name + "your card balance on NAB bank is " + balance);
    }
}