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

/*
* This is entry point for library management system console app(LMS.)
* It provides a basic menu-driven interface to interact as user or librarian.
* */

public class Main {
    public static void main(String[] args) {

        //Instantiate Data access object implementation
        BookDaoImpl bookDAO = new BookDaoImpl();
        UserDAOImpl userDAO = new UserDAOImpl();
        LibrarianDAOImpl librarianDAO = new LibrarianDAOImpl();

        //Instantiate service layer with required DAOs
        BookServices bookServices = new BookServiceImpl(bookDAO);
        UserServices userService = new UserServiceImpl(bookServices, userDAO);
        LibrarianServices librarianServices = new LibrarianServiceImpl(bookServices, librarianDAO);

        //Take input and a true flag to keep the menu running.
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        //main menu loop starts here
        while (running) {
            System.out.println("\n=== Welcome to the Library Management System ===");
            System.out.println("1. Register as User"); //new user registration
            System.out.println("2. Login as User"); //existing user login
            System.out.println("3. Login as Librarian"); //login as librarian
            System.out.println("0. Exit"); //exit the program
            System.out.print("Enter your choice: ");

            int choice;

            //handle invalid input
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    UserUI.registerUser(userService, sc); //registration of new user
                    break;
                case 2:
                    //Handle user login and show user menu
                    UserUI.handleUserLogin(userService,bookServices, sc);
                    break;
                case 3:
                    //Handle librarian login and show librarian menu
                    LibrarianUI.handleLibrarianLogin(librarianServices, bookServices,userService,bookDAO, userDAO, sc);
                    break;
                case 0:
                    //exit the application
                    running = false;
                    System.out.println("Thank you for using the Library Management System. Goodbye!");
                    break;
                default:
                    //invalid input
                    System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }
}
