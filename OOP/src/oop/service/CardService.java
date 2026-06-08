package oop.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import oop.model.Card;
import oop.model.Transaction;

public class CardService {
	
	private String maskCardNumber(String cardNumber) {
	    String lastThree = cardNumber.substring(cardNumber.length() - 3);
	    return "xxxxxxx" + lastThree;
	}
	
	public void registerCard(String cardNumber, String bankName, String username) {
	    try {
	        // Step 1: Check if card already exists
	        BufferedReader reader = new BufferedReader(new FileReader("cards.txt"));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split("\\|");
	            if (parts[0].equals(cardNumber)) {
	                System.out.println("Card already exists!");
	                reader.close();
	                return;
	            }
	        }
	        reader.close();

	        // Step 2: Card doesn't exist, save it with balance 0
	        Card card = new Card(cardNumber, bankName, 0.0, username);
	        BufferedWriter writer = new BufferedWriter(new FileWriter("cards.txt", true));
	        writer.write(card.toFileString());
	        writer.newLine();
	        writer.close();
	        System.out.println("Card registered successfully!");

	    } catch (IOException e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}
	
	public void deposit(String cardNumber, double amount) {
	    try {
	        // Step 1: Read all cards, find the matching one
	        BufferedReader reader = new BufferedReader(new FileReader("cards.txt"));
	        String line;
	        List<String> allLines = new ArrayList<>();
	        boolean found = false;

	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split("\\|");
	            if (parts[0].equals(cardNumber)) {
	                found = true;
	                String bankName = parts[1];
	                double balance = Double.parseDouble(parts[2]);

	                // Step 2: Check deposit rules
	                if (bankName.equals("CMW") && amount < 5) {
	                    System.out.println("CMW minimum deposit is 5 AUD");
	                    reader.close();
	                    return;
	                } else if (!bankName.equals("CMW") && amount < 10) {
	                    System.out.println("ANZ/NAB minimum deposit is 10 AUD");
	                    reader.close();
	                    return;
	                }

	                // Step 3: Update balance
	                balance += amount;
	                parts[2] = String.valueOf(balance);
	                allLines.add(String.join("|", parts));
	                System.out.println("Deposited " + amount + " AUD. New balance: " + balance);
	            } else {
	                allLines.add(line);
	            }
	        }
	        reader.close();

	        if (!found) {
	            System.out.println("Card not found!");
	            return;
	        }

	        // Step 4: Rewrite file with updated balance
	        BufferedWriter writer = new BufferedWriter(new FileWriter("cards.txt"));
	        for (String l : allLines) {
	            writer.write(l);
	            writer.newLine();
	        }
	        writer.close();

	        // Step 5: Save transaction
	        Transaction t = new Transaction(cardNumber, "DEPOSIT", amount, java.time.LocalDate.now().toString());
	        BufferedWriter tWriter = new BufferedWriter(new FileWriter("transactions.txt", true));
	        tWriter.write(t.toFileString());
	        tWriter.newLine();
	        tWriter.close();

	    } catch (IOException e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}
	
	public void withdraw(String cardNumber, double amount) {
	    try {
	        // Step 1: Read all cards, find the matching one
	        BufferedReader reader = new BufferedReader(new FileReader("cards.txt"));
	        String line;
	        List<String> allLines = new ArrayList<>();
	        boolean found = false;

	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split("\\|");
	            if (parts[0].equals(cardNumber)) {
	                found = true;
	                String bankName = parts[1];
	                double balance = Double.parseDouble(parts[2]);
	                
	                //set bank withdraw limits
	                
	                int maxWithdraw;
	                switch(bankName) {
	                case "ANZ": maxWithdraw = 1000; break;
	                case "NAB": maxWithdraw = 2000; break;
	                case "CMW": maxWithdraw = 3000; break;
	                default: maxWithdraw = 0;
	                }

	                // Step 2: Check withdraw rules
	                 if (amount > maxWithdraw) {
	                	 System.out.println(bankName + " limit is " + maxWithdraw + " AUD per withdrawal");
	                	 reader.close();
	                	 return;
	                 }
	                 
	                 //minimum balance
	                 if (balance - amount < 20) {
	                	 System.out.println("Cannot withdraw. Must keep minumum 20 AUD");
	                	 System.out.println("Available to withdraw: " + (balance - 20) + " AUD");
	                	 reader.close();
	                	 return;
	                 }

	                // Step 3: Update balance
	                balance -= amount;
	                parts[2] = String.valueOf(balance);
	                allLines.add(String.join("|", parts));
	                System.out.println("Withdrew " + amount + " AUD. New balance: " + balance);
	            } else {
	                allLines.add(line);
	            }
	        }
	        reader.close();

	        if (!found) {
	            System.out.println("Card not found!");
	            return;
	        }

	        // Step 4: Rewrite file with updated balance
	        BufferedWriter writer = new BufferedWriter(new FileWriter("cards.txt"));
	        for (String l : allLines) {
	            writer.write(l);
	            writer.newLine();
	        }
	        writer.close();

	        // Step 5: Save transaction
	        Transaction t = new Transaction(cardNumber, "WITHDRAW", amount, java.time.LocalDate.now().toString());
	        BufferedWriter tWriter = new BufferedWriter(new FileWriter("transactions.txt", true));
	        tWriter.write(t.toFileString());
	        tWriter.newLine();
	        tWriter.close();

	    } catch (IOException e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}
	
	public void viewBalance(String cardNumber, String loggedInUser) {
	    try {
	        BufferedReader reader = new BufferedReader(new FileReader("cards.txt"));
	        String line;
	        boolean found = false;

	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split("\\|");
	            if (parts[0].equals(cardNumber)) {
	                found = true;
	                String bankName = parts[1];
	                double balance = Double.parseDouble(parts[2]);

	                // Different message per bank
	                switch (bankName) {
	                    case "NAB":
	                        System.out.println("Hi " + loggedInUser + ", your card balance on NAB bank is " + balance + " AUD");
	                        break;
	                    case "ANZ":
	                        System.out.println("Your ANZ account " + maskCardNumber(cardNumber) + " balance is " + balance + " AUD");
	                        break;
	                    case "CMW":
	                        System.out.println("CMW bank account with number " + maskCardNumber(cardNumber) + " has balance is " + balance + " AUD");
	                        break;
	                }
	                break;
	            }
	        }
	        reader.close();

	        if (!found) {
	            System.out.println("Card not found!");
	        }

	    } catch (IOException e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}
	
	public void viewHistory(String cardNumber) {
	    try {
	        // Step 1: Find the card's bank
	        BufferedReader cardReader = new BufferedReader(new FileReader("cards.txt"));
	        String line;
	        String bankName = null;

	        while ((line = cardReader.readLine()) != null) {
	            String[] parts = line.split("\\|");
	            if (parts[0].equals(cardNumber)) {
	                bankName = parts[1];
	                break;
	            }
	        }
	        cardReader.close();

	        if (bankName == null) {
	            System.out.println("Card not found!");
	            return;
	        }

	        // Step 2: NAB only
	        if (!bankName.equals("NAB")) {
	            System.out.println("Transaction history not supported for " + bankName + " bank");
	            return;
	        }

	        // Step 3: Read all transactions for this card
	        BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"));
	        List<String> transactions = new ArrayList<>();

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

	        // Step 4: Show last 10 only
	        System.out.println("\n=== Transaction History (NAB) ===");
	        int start = Math.max(0, transactions.size() - 10);
	        for (int i = start; i < transactions.size(); i++) {
	            String[] parts = transactions.get(i).split("\\|");
	            System.out.println(parts[3] + " | " + parts[1] + " | " + parts[2] + " AUD");
	        }
	        System.out.println("=================================");

	    } catch (IOException e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}

}
