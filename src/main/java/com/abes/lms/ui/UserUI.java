package com.abes.lms.ui;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.exception.InvalidInputException;
import com.abes.lms.service.UserServices;
import com.abes.lms.util.InputValidatorUtil;

import java.util.List;
import java.util.Scanner;

public class UserUI {

    public static void registerUser(UserServices userService, Scanner sc) {
        try {
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            InputValidatorUtil.validate(username);

            System.out.print("Enter password: ");
            String password = sc.nextLine();
            InputValidatorUtil.validate(password);

            System.out.print("Enter email: ");
            String email = sc.nextLine();
            InputValidatorUtil.validateEmail(email);

            if (userService.getUser(username) != null) {
                System.out.println("Username already exists. Please login instead.");
                return;
            }

            if (userService.userRegister(username, password, email)) {
                System.out.println("User registered successfully!");
            }

        } catch (InvalidInputException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    public static void handleUserLogin(UserServices userService, Scanner sc) {
        try {
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            InputValidatorUtil.validate(username);

            System.out.print("Enter password: ");
            String password = sc.nextLine();
            InputValidatorUtil.validate(password);

            userService.userLogin(username, password);

            String choice;
            do {
                System.out.println("\n=== User Menu ===");
                System.out.println("1. View All Books");
                System.out.println("2. Borrow a Book");
                System.out.println("3. Return a Book");
                System.out.println("4. Sort Books by ID");
                System.out.println("5. Sort Books by Rating");
                System.out.println("6. Sort Books by Title");
                System.out.println("0. Logout");
                System.out.print("Enter your choice: ");
                choice = sc.nextLine();

                switch (choice) {
                    case "1":
                        displayBooks(userService.viewBooks());
                        break;
                    case "2":
                        System.out.print("Enter book title to borrow: ");
                        String borrowTitle = sc.nextLine();
                        userService.borrowBook(username, borrowTitle);
                        break;
                    case "3":
                        System.out.print("Enter book title to return: ");
                        String returnTitle = sc.nextLine();
                        userService.returnBook(username, returnTitle);
                        break;
                    case "4":
                        displayBooks(userService.sortBooksById());
                        break;
                    case "5":
                        displayBooks(userService.sortBooksByRating());
                        break;
                    case "6":
                        displayBooks(userService.sortBooksByTitle());
                        break;
                    case "0":
                        System.out.println("Logged out.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } while (!choice.equals("0"));

        } catch (InvalidInputException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayBooks(List<BookDTO> books) {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (BookDTO b : books) {
                System.out.println(b);
            }
        }
    }
}
