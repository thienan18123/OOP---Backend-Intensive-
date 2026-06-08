package oop.model;

import lombok.Data;
import lombok.AllArgsConstructor;



@Data
@AllArgsConstructor
public class Transaction {
    private String cardNumber;
    private String type;         // "DEPOSIT" or "WITHDRAW"
    private double amount;
    private String date;

    // constructor, getters, setters, toFileString()
    
    public String toFileString() {
        return cardNumber + "|" + type + "|" + amount + "|" + date;
    }
}
