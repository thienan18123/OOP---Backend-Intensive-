package oop.model.base;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class baseCard {
	protected String cardNumber;
	protected String username;
	protected BigDecimal balance;
	
	public abstract BigDecimal getMaxWithdraw();
	public abstract BigDecimal getMinDeposit();
	public abstract String getBankName();
	public abstract void showBalance(String name);
	
	public void deposit(BigDecimal amount) {
        if (amount.compareTo(getMinDeposit()) < 0) {
            System.out.println(getBankName() + " Minumum deposit is " + getMinDeposit() + " AUD");
            return;
        }
        balance = balance.add(amount);
        System.out.println("deposited " + amount + " AUD. new balance: " + balance);
    }
	
	public void withdraw(BigDecimal amount) {
        if (amount.compareTo(getMaxWithdraw()) > 0) {
            System.out.println(getBankName() + " maximum withdraw is " + getMaxWithdraw() + " AUD");
            return;
        }
        BigDecimal minKeep = new BigDecimal("20");
        if (balance.subtract(amount).compareTo(minKeep) < 0) {
            System.out.println("must kept $20 AUD");
            return;
        }
        balance = balance.subtract(amount);
        System.out.println("Withdrew " + amount + " AUD. new balance: " + balance);
    }
	
	public String getMaskedNumber() {
		return "XXXXXXX" + cardNumber.substring(cardNumber.length() - 3);
	}

    public String toFileString() {
        return cardNumber + "|" + getBankName() + "|" + balance + "|" + username;
    }

}
