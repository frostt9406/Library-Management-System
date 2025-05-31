package com.abes.lms.ui;

import com.abes.lms.dao.BookDaoImpl;
import com.abes.lms.dao.UserDAOImpl;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDTO;
import com.abes.lms.exception.InvalidInputException;
import com.abes.lms.service.LibrarianServices;
import com.abes.lms.service.BookServices;
import com.abes.lms.util.InputValidatorUtil;

import java.util.List;
import java.util.Scanner;

public class LibrarianUI {

    public static void handleLibrarianLogin(LibrarianServices librarianServices,BookServices bookServices, BookDaoImpl bookDAO, UserDAOImpl userDAO, Scanner sc) {
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
                        if (bookServices.isBookPresentById(id)) {
                            System.out.println("Book already exists with ID " + id + ". Please update the quantity instead.");
                            System.out.print("Enter quantity to update: ");
                            int quantity = Integer.parseInt(sc.nextLine());
                            // Optionally add logic here to update quantity
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
