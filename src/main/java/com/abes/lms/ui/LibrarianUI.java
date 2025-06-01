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

    /**
     * Handles librarian login and provides a menu to manage library operations.
     * librarianServices: Service to authenticate and manage librarians
     * bookServices: Service to manage book-related operations
     * userServices: Service to manage user-related operations
     * bookDAO: DAO implementation to directly modify book data
     * userDAO: DAO implementation for user data
     */

    public static void handleLibrarianLogin(LibrarianServices librarianServices, BookServices bookServices, UserServices userServices, BookDaoImpl bookDAO, UserDAOImpl userDAO, Scanner sc) {
        try {
            // Prompt for librarian credentials
            System.out.print("Enter librarian username: ");
            String username = sc.nextLine();
            InputValidatorUtil.validate(username);

            System.out.print("Enter password: ");
            String password = sc.nextLine();
            InputValidatorUtil.validate(password);

            //Authenticate librarian
            boolean isValidLibrarian = librarianServices.LibrarianLogin(username, password);
            if(!isValidLibrarian){
                System.out.println("Invalid Credentials");
                return;
            }
            String choice;
            do {
                //Display librarian menu options
                System.out.println("\n=== Librarian Menu ===");
                System.out.println("1. Add Book");
                System.out.println("2. Remove Book");
                System.out.println("3. View All Books");
                System.out.println("4. View All Users");
                System.out.println("0. Logout");
                System.out.print("Enter your choice: ");
                choice = sc.nextLine();

                switch (choice) {
                    //Add or update book
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

                        //Input book details
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
                        //Remove book by title
                        System.out.print("Enter book title to remove: ");
                        String removeTitle = sc.nextLine();
                        boolean isBookRemoved = bookServices.removeBook(removeTitle);
                        System.out.println(isBookRemoved?"Removed Successfully":"Failed To Remove");
                        break;
                    case "3":
                        //View all books
                        bookServices.getAllBooks().forEach(System.out::println);
                        break;
                    case "4":
                        //View users and their borrowed books
//                        userServices.getAllUsers().forEach(System.out::println);
                        userServices.borrowedBookByEachUser();
                        break;
                    case "0":
                        //Exit the librarian menu
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
