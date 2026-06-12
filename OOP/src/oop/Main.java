package oop;

import java.math.BigDecimal;
import java.util.Scanner;

import oop.model.User;
import oop.service.CardService;
import oop.service.UserService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        CardService cardService = new CardService();
        int choice;

        do {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Register");
            System.out.println("2. View Registered Users");
            System.out.println("3. Login");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter last name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Enter DOB: ");
                    String dob = scanner.nextLine();
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    User user = new User(firstName, lastName, dob, username, password);
                    userService.register(user);
                    System.out.println("Registered successfully!");
                    break;

                case 2:
                    System.out.println("1. Show all");
                    System.out.println("2. Find by name");
                    System.out.print("Choose: ");
                    int subChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (subChoice) {
                        case 1:
                            userService.showAll();
                            break;
                        case 2:
                            System.out.print("Enter name to search: ");
                            String search = scanner.nextLine();
                            userService.findByName(search);
                            break;
                        default:
                            System.out.println("Invalid option");
                    }
                    break;

                case 3:
                    int attempts = 0;
                    String loggedInUser = null;

                    while (attempts < 3) {
                        System.out.print("Enter username: ");
                        String loginUser = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String loginPass = scanner.nextLine();

                        if (userService.login(loginUser, loginPass)) {
                            System.out.println("Login successful!");
                            loggedInUser = loginUser;
                            break;
                        } else {
                            attempts++;
                            if (attempts < 3) {
                                System.out.println("Wrong credentials. " + (3 - attempts) + " attempts left.");
                            } else {
                                System.out.println("Account locked! Too many failed attempts.");
                            }
                        }
                    }

                    // Banking menu — only if login was successful
                    if (loggedInUser != null) {
                        String currentCard = null;
                        int bankChoice;

                        do {
                            System.out.println("\n=== BANKING MENU ===");
                            System.out.println("1. Register New Card");
                            System.out.println("2. Deposit");
                            System.out.println("3. Withdraw");
                            System.out.println("4. View Balance");
                            System.out.println("5. View Transaction History");
                            System.out.println("6. Logout");
                            System.out.print("Choose: ");
                            bankChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (bankChoice) {
                                case 1:
                                    System.out.print("Enter 10-digit card number: ");
                                    String cardNum = scanner.nextLine();
                                    while (cardNum.length() != 10) {
                                        System.out.println("Card number must be 10 digits!");
                                        System.out.print("Enter 10-digit card number: ");
                                        cardNum = scanner.nextLine();
                                    }

                                    System.out.println("Choose bank:");
                                    System.out.println("1. ANZ");
                                    System.out.println("2. NAB");
                                    System.out.println("3. CMW");
                                    System.out.print("Choose: ");
                                    int bankSelect = scanner.nextInt();
                                    scanner.nextLine();

                                    String bankName;
                                    switch (bankSelect) {
                                        case 1: bankName = "ANZ"; break;
                                        case 2: bankName = "NAB"; break;
                                        case 3: bankName = "CMW"; break;
                                        default: bankName = null;
                                    }

                                    if (bankName != null) {
                                        cardService.registerCard(cardNum, bankName, loggedInUser);
                                    } else {
                                        System.out.println("Invalid bank");
                                    }
                                    break;

                                case 2:
                                    currentCard = askForCard(scanner, currentCard);
                                    if (currentCard != null) {
                                        System.out.print("Enter amount to deposit: ");
                                        BigDecimal depositAmount = scanner.nextBigDecimal();
                                        scanner.nextLine();
                                        cardService.deposit(currentCard, depositAmount);
                                    }
                                    break;

                                case 3:
                                    currentCard = askForCard(scanner, currentCard);
                                    if (currentCard != null) {
                                        System.out.print("Enter amount to withdraw: ");
                                        BigDecimal withdrawAmount = scanner.nextBigDecimal();
                                        scanner.nextLine();
                                        cardService.withdraw(currentCard, withdrawAmount);
                                    }
                                    break;

                                case 4:
                                    currentCard = askForCard(scanner, currentCard);
                                    if (currentCard != null) {
                                        cardService.viewBalance(currentCard, loggedInUser);
                                    }
                                    break;

                                case 5:
                                    currentCard = askForCard(scanner, currentCard);
                                    if (currentCard != null) {
                                        cardService.viewHistory(currentCard);
                                    }
                                    break;

                                case 6:
                                    System.out.println("Logged out!");
                                    break;

                                default:
                                    System.out.println("Invalid option");
                            }

                        } while (bankChoice != 6);
                    }
                    break;

                case 4:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option, try again.");
            }

        } while (choice != 4);

        scanner.close();
    }

    // Helper method — ask for card or reuse current
    private static String askForCard(Scanner scanner, String currentCard) {
        if (currentCard != null) {
            System.out.print("Use current card xxxxxxx" + currentCard.substring(currentCard.length() - 3) + "? (yes/no): ");
            String answer = scanner.nextLine();
            if (answer.equals("yes")) {
                return currentCard;
            }
        }
        System.out.print("Enter card number: ");
        return scanner.nextLine();
    }
}
