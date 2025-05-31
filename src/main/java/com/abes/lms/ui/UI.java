package com.abes.lms.ui;

import com.abes.lms.dao.implementation.BookDaoImpl;
import com.abes.lms.dao.implementation.LibrarianDAOImpl;
import com.abes.lms.dao.implementation.UserDAOImpl;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDTO;
import com.abes.lms.exception.InvalidInputException;
import com.abes.lms.service.BookServices;
import com.abes.lms.service.LibrarianServices;
import com.abes.lms.service.UserServices;
import com.abes.lms.service.implementation.BookServiceImpl;
import com.abes.lms.service.implementation.LibrarianServiceImpl;
import com.abes.lms.service.implementation.UserServiceImpl;
import com.abes.lms.util.InputValidatorUtil;

import java.util.List;
import java.util.Scanner;

public class UI {
    BookDaoImpl bookDAO = new BookDaoImpl();
    UserDAOImpl userDAO = new UserDAOImpl();
    LibrarianDAOImpl librarianDAO = new LibrarianDAOImpl();
    BookServices bookServices = new BookServiceImpl();
    UserServices userService = new UserServiceImpl(bookServices,userDAO);
    LibrarianServices librarianServices = new LibrarianServiceImpl(bookServices,librarianDAO);
    public  void runner() {

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Welcome to the Library Management System ===");
            System.out.println("1. Register as User");
            System.out.println("2. Login as User");
            System.out.println("3. Login as Librarian");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int roleChoice;
            try {
                roleChoice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (roleChoice) {
                case 1:
                    registerUser(userDAO, sc);
                    break;
                case 2:
                    handleUserLogin(userDAO, userService, sc);
                    break;
                case 3:
                    handleLibrarianLogin(librarianDAO, bookDAO, userDAO, sc);
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using the Library Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }

    private  void registerUser(UserDAOImpl userDAO, Scanner sc) {
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
            if(userService.userRegister(username, password, email)) {
                System.out.println("User registered successfully!");
            }

        } catch (InvalidInputException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private  void handleUserLogin(UserDAOImpl userDAO, UserServices userService, Scanner sc) {
        try {
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            InputValidatorUtil.validate(username);

            System.out.print("Enter password: ");
            String password = sc.nextLine();
            InputValidatorUtil.validate(password);
            userService.userLogin(username, password);
            int choice;
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
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        displayBooks(userService.viewBooks());
                        break;
                    case 2:
                        System.out.print("Enter book title to borrow: ");
                        String borrowTitle = sc.nextLine();
                        userService.borrowBook(username, borrowTitle);
                        break;
                    case 3:
                        System.out.print("Enter book title to return: ");
                        String returnTitle = sc.nextLine();
                        userService.returnBook(username, returnTitle);
                        break;
                    case 4:
                        displayBooks(userService.sortBooksById());
                        break;
                    case 5:
                        displayBooks(userService.sortBooksByRating());
                        break;
                    case 6:
                        displayBooks(userService.sortBooksByTitle());
                        break;
                    case 0:
                        System.out.println("Logged out.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } while (choice != 0);

        } catch (InvalidInputException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private  void handleLibrarianLogin(LibrarianDAOImpl librarianDAO, BookDaoImpl bookDAO, UserDAOImpl userDAO, Scanner sc) {
        try {
            System.out.print("Enter librarian username: ");
            String username = sc.nextLine();
            InputValidatorUtil.validate(username);

            System.out.print("Enter password: ");
            String password = sc.nextLine();
            InputValidatorUtil.validate(password);

            librarianServices.LibrarianLogin(username, password);


            int choice;
            do {
                System.out.println("\n=== Librarian Menu ===");
                System.out.println("1. Add Book");
                System.out.println("2. Remove Book");
                System.out.println("3. View All Books");
                System.out.println("4. View All Users");
                System.out.println("0. Logout");
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter book ID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        boolean isBookPresent= bookServices.isBookPresentById(id);
                        if(isBookPresent){
                            System.out.println("Book already exists with "+ id+  " Please Update The  instead.");
                            System.out.println("Enter Quantity to Update");
                            int quantity = Integer.parseInt(sc.nextLine());

                            return;

                        }

                        System.out.print("Enter book title: ");
                        String title = sc.nextLine();
                        System.out.print("Enter author: ");
                        String author = sc.nextLine();

                        System.out.print("Enter rating: ");
                        double rating = Double.parseDouble(sc.nextLine());
                        System.out.print("Enter quantity: ");
                        int quantity = Integer.parseInt(sc.nextLine());
                        BookDTO book = new BookDTO(title, author, id, rating, quantity);
                        bookDAO.addBook(book);
                        System.out.println("Book added successfully.");
                        break;
                    case 2:
                        System.out.print("Enter book title to remove: ");
                        String removeTitle = sc.nextLine();
                        bookDAO.removeBook(removeTitle);
                        System.out.println("Book removed if present.");
                        break;
                    case 3:
                        displayBooks(bookDAO.getAllBooks());
                        break;
                    case 4:
                        List<UserDTO> users = userDAO.getAllUsers();
                        for (UserDTO u : users) {
                            System.out.println(u);
                        }
                        break;
                    case 0:
                        System.out.println("Logged out.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } while (choice != 0);

        } catch (InvalidInputException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private  void displayBooks(List<BookDTO> books) {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (BookDTO b : books) {
                System.out.println(b);
            }
        }
    }
}
