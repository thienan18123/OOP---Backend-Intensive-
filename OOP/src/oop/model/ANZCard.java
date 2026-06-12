package oop.model;

import java.math.BigDecimal;

import oop.model.base.baseCard;

public class ANZCard extends baseCard {

    public ANZCard(String cardNumber, String username, BigDecimal balance) {
        super(cardNumber, username, balance);  // call from baseCard
    }

    @Override
    public BigDecimal getMaxWithdraw() { return new BigDecimal("1000"); }

    @Override
    public BigDecimal getMinDeposit() { return new BigDecimal("10"); }

    @Override
    public String getBankName() { return "ANZ"; }

    @Override
    public void showBalance(String name) {
    	System.out.println("Your ANZ account " + getMaskedNumber() + " balance is " + balance);
    }
}