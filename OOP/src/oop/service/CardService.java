package oop.service;

import oop.model.*;
import oop.model.base.baseCard;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CardService {

    
    private baseCard createCard(String[] parts) {
        String cardNumber = parts[0];
        String bankName = parts[1];
        BigDecimal balance = new BigDecimal(parts[2]);
        String username = parts[3];

        switch (bankName) {
            case "ANZ": return new ANZCard(cardNumber, username, balance);
            case "NAB": return new NABCard(cardNumber, username, balance);
            case "CMW": return new CMWCard(cardNumber, username, balance);
            default: return null;
        }
    }

    
    private baseCard findCard(String cardNumber) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("cards.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals(cardNumber)) {
                    reader.close();
                    return createCard(parts);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

   
    private void updateCard(baseCard card) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("cards.txt"));
            List<String> allLines = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals(card.getCardNumber())) {
                    allLines.add(card.toFileString());  
                } else {
                    allLines.add(line);                  
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("cards.txt"));
            for (String l : allLines) {
                writer.write(l);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    
    private void saveTransaction(String cardNumber, String type, BigDecimal depositAmount) {
        try {
            Transaction t = new Transaction(cardNumber, type, depositAmount,
                    java.time.LocalDate.now().toString());
            BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true));
            writer.write(t.toFileString());
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

   

    public void registerCard(String cardNumber, String bankName, String username) {
        if (findCard(cardNumber) != null) {
            System.out.println("Card already exists!");
            return;
        }
        try {
            String[] parts = {cardNumber, bankName, "0.0", username};
            baseCard card = createCard(parts);
            BufferedWriter writer = new BufferedWriter(new FileWriter("cards.txt", true));
            writer.write(card.toFileString());
            writer.newLine();
            writer.close();
            System.out.println("Card registered successfully!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deposit(String cardNumber, BigDecimal depositAmount) {
        baseCard card = findCard(cardNumber);
        if (card == null) {
            System.out.println("Card not found!");
            return;
        }

        BigDecimal oldBalance = card.getBalance();
        card.deposit(depositAmount);  

        if (card.getBalance().compareTo(oldBalance) > 0) {  
            updateCard(card);
            saveTransaction(cardNumber, "DEPOSIT", depositAmount);
        }
    }

    public void withdraw(String cardNumber, BigDecimal amount) {
        baseCard card = findCard(cardNumber);
        if (card == null) {
            System.out.println("Card not found!");
            return;
        }

        BigDecimal oldBalance = card.getBalance();
        card.withdraw(amount);  

        if (card.getBalance().compareTo(oldBalance) < 0) {  
            updateCard(card);
            saveTransaction(cardNumber, "WITHDRAW", amount);
        }
    }

    public void viewBalance(String cardNumber, String name) {
        baseCard card = findCard(cardNumber);
        if (card == null) {
            System.out.println("Card not found!");
            return;
        }
        card.showBalance(name);  
    }

    public void viewHistory(String cardNumber) {
        baseCard card = findCard(cardNumber);
        if (card == null) {
            System.out.println("Card not found!");
            return;
        }

        // NAB only
        if (!card.getBankName().equals("NAB")) {
            System.out.println("Transaction history not supported for " + card.getBankName());
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"));
            List<String> transactions = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals(cardNumber)) {
                    transactions.add(line);
                }
            }
            reader.close();

            if (transactions.isEmpty()) {
                System.out.println("No transactions found");
                return;
            }

            System.out.println("\n=== Transaction History (NAB) card " + card.getCardNumber());
            int start = Math.max(0, transactions.size() - 10);
            for (int i = start; i < transactions.size(); i++) {
                String[] parts = transactions.get(i).split("\\|");
                System.out.println(parts[3] + " | " + parts[1] + " | " + parts[2] + " AUD");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}