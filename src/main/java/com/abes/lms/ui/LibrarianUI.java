package com.abes.lms.ui;

import com.abes.lms.dao.BookDaoImpl;
import com.abes.lms.dao.UserDAOImpl;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.exception.InvalidInputException;
import com.abes.lms.service.BookServices;
import com.abes.lms.service.LibrarianServices;
import com.abes.lms.service.UserServices;
import com.abes.lms.util.InputValidatorUtil;

import java.util.Scanner;

public class LibrarianUI {

    public static void handleLibrarianLogin(LibrarianServices librarianServices, BookServices bookServices, UserServices userServices, BookDaoImpl bookDAO, UserDAOImpl userDAO, Scanner sc) {
        try {
            System.out.print("Enter librarian username: ");
            String username = sc.nextLine();
            InputValidatorUtil.validate(username);

            System.out.print("Enter password: ");
            String password = sc.nextLine();
            InputValidatorUtil.validate(password);

            boolean isValidLibrarian = librarianServices.LibrarianLogin(username, password);
            if(!isValidLibrarian){
                System.out.println("Invalid Credentials");
                return;
            }
            String choice;
            do {
                System.out.println("\n=== Librarian Menu ===");
                System.out.println("1. Add Book");
                System.out.println("2. Remove Book");
                System.out.println("3. View All Books");
                System.out.println("4. View All Users");
                System.out.println("0. Logout");
                System.out.print("Enter your choice: ");
                choice = sc.nextLine();

                switch (choice) {
                    case "1":
                        System.out.print("Enter book ID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        if (bookServices.isBookPresentById(id)) {
                            System.out.println("Book already exists with ID " + id + ". Please update the quantity instead.");
                            System.out.print("Enter quantity to update: ");
                            int quantity = Integer.parseInt(sc.nextLine());
                            bookServices.addQuantity(bookServices.getBookById(id), quantity);
                            System.out.println("Book Updated successfully.");
                            break;
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
                    case "2":
                        System.out.print("Enter book title to remove: ");
                        String removeTitle = sc.nextLine();
                        boolean isBookRemoved = bookServices.removeBook(removeTitle);
                        System.out.println(isBookRemoved?"Removed Successfully":"Failed To Remove");
                        break;
                    case "3":
                        bookServices.getAllBooks().forEach(System.out::println);
                        break;
                    case "4":
//                        userServices.getAllUsers().forEach(System.out::println);
                        userServices.borrowedBookByEachUser();
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

}
