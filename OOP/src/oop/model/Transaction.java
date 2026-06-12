package oop.model;

import lombok.Data;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;



@Data
@AllArgsConstructor
public class Transaction {
    private String cardNumber;
    private String type;         // "DEPOSIT" or "WITHDRAW"
    private BigDecimal amount;
    private String date;

    
    public String toFileString() {
        return cardNumber + "|" + type + "|" + amount + "|" + date;
    }
}
