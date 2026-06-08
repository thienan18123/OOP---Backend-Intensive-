package oop.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private String cardNumber;   // 10 digits
    private String bankName;     // ANZ, NAB, CMW
    private double balance;
    private String username;     // who owns this card

    public String toFileString() {
        return cardNumber + "|" + bankName + "|" + balance + "|" + username;
    }
}