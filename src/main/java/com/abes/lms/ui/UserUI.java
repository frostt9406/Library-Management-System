package com.abes.lms.ui;

import com.abes.lms.exception.BookNotFoundException;
import com.abes.lms.exception.InvalidEmailException;
import com.abes.lms.exception.InvalidInputException;
import com.abes.lms.exception.InvalidPasswordFormatException;
import com.abes.lms.service.BookServices;
import com.abes.lms.service.UserServices;
import com.abes.lms.util.InputValidatorUtil;

import java.util.Scanner;

public class UserUI {

    /**
     * Handles user registration by prompting for username, password, and email.
     * Validates input and checks for duplicate usernames before registration.
     * userService: Service layer handling user-related logic
     */
    public static void registerUser(UserServices userService, Scanner sc) {

        //User login menu
        try {
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            InputValidatorUtil.validate(username);
            String password;
            while (true){
            System.out.print("Enter password: ");
            password = sc.nextLine();
            try{
                InputValidatorUtil.validatePassword(password);
                break;
            }
            catch(InvalidPasswordFormatException e) {
                System.out.println(e.getMessage());
                }
            }
            String email;
            while(true){
            System.out.print("Enter email: ");
            email = sc.nextLine();
            try{
                InputValidatorUtil.validateEmail(email);
                break;
                }
            catch(InvalidEmailException e){
                System.out.println(e.getMessage());
                }
            }

            //Check if user already exists
            if (userService.getUser(username) != null) {
                System.out.println("Username already exists. Please login instead.");
                return;
            }

            //register new user if user does not already exist
            if (userService.userRegister(username, password, email)) {
                System.out.println("User registered successfully!");
            }

        } catch (InvalidInputException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    /**
     * Handles user login and provides access to user functionalities like:
     * viewing, borrowing, returning, and sorting books.
     * userService:  Service layer for user operations
     * bookServices: Service layer for book operations
     */
    public static void handleUserLogin(UserServices userService,BookServices bookServices,Scanner sc) {

        //prompt for username and password
        try {
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            InputValidatorUtil.validate(username);

            System.out.print("Enter password: ");
            String password = sc.nextLine();
            InputValidatorUtil.validate(password);

            //Authenticate the user
            boolean isUserValid = userService.userLogin(username, password);
            if(!isUserValid){
                System.out.println("Invalid Credentials");
                return;
            }

            String choice;
            do {
                //Display the user menu
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
                    //Display all available books
                    case "1":
                        bookServices.getAllBooks().forEach(System.out::println);
                        break;
                    //Borrow a book
                    case "2":
                        System.out.print("Enter book title to borrow: ");
                        String borrowTitle = sc.nextLine();
                        userService.borrowBook(username, borrowTitle);
                        break;
                    //Return a borrowed book
                    case "3":
                        System.out.print("Enter book title to return: ");
                        String returnTitle = sc.nextLine();
                        try{
                        userService.returnBook(username, returnTitle);}
                        catch(InvalidInputException | BookNotFoundException e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    //Sort and display books by id
                    case "4":
                        userService.sortBooksById().forEach(System.out::println);
                        break;
                    //sort and display books by rating
                    case "5":
                        userService.sortBooksByRating().forEach(System.out::println);
                        break;
                    //sort and display books by title
                    case "6":
                        userService.sortBooksByTitle().forEach(System.out::println);
                        break;
                    //log out the user from session
                    case "0":
                        System.out.println("Logged out.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } while (!choice.equals("0"));

        } catch (InvalidInputException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
