package oop;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService service = new UserService();
        int choice;

        do {
            System.out.println("\n=== MENU ===");
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
                    service.register(user);
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
                            service.showAll();
                            break;
                        case 2:
                            System.out.print("Enter name to search: ");
                            String search = scanner.nextLine();
                            service.findByName(search);
                            break;
                        default:
                            System.out.println("Invalid option");
                    }
                    break;

                case 3:
                    int attempts = 0;
                    while (attempts < 3) {
                        System.out.print("Enter username: ");
                        String loginUser = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String loginPass = scanner.nextLine();

                        if (service.login(loginUser, loginPass)) {
                            System.out.println("Login successful!");
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
}
