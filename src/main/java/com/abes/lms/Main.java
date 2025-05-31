package com.abes.lms;

import com.abes.lms.dao.BookDaoImpl;
import com.abes.lms.dao.LibrarianDAOImpl;
import com.abes.lms.dao.UserDAOImpl;
import com.abes.lms.service.BookServices;
import com.abes.lms.service.LibrarianServices;
import com.abes.lms.service.UserServices;
import com.abes.lms.service.BookServiceImpl;
import com.abes.lms.service.LibrarianServiceImpl;
import com.abes.lms.service.UserServiceImpl;
import com.abes.lms.ui.LibrarianUI;
import com.abes.lms.ui.UserUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookDaoImpl bookDAO = new BookDaoImpl();
        UserDAOImpl userDAO = new UserDAOImpl();
        LibrarianDAOImpl librarianDAO = new LibrarianDAOImpl();
        BookServices bookServices = new BookServiceImpl(bookDAO);
        UserServices userService = new UserServiceImpl(bookServices, userDAO);
        LibrarianServices librarianServices = new LibrarianServiceImpl(bookServices, librarianDAO);

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Welcome to the Library Management System ===");
            System.out.println("1. Register as User");
            System.out.println("2. Login as User");
            System.out.println("3. Login as Librarian");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    UserUI.registerUser(userService, sc);
                    break;
                case 2:
                    UserUI.handleUserLogin(userService,bookServices, sc);
                    break;
                case 3:
                    LibrarianUI.handleLibrarianLogin(librarianServices, bookServices,userService,bookDAO, userDAO, sc);
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
}
