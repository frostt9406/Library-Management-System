package com.abes.lms.dto;

/**
 * BookDTO is a Data Transfer Object that represents the details of a book in the Library Management System.
 */
public class BookDTO {
    private String title;
    private String author;
    private int id;
    private double rating;
    private int quantity;

    //Constructs a new BookDTO with the specified details.
    public BookDTO(String title, String author, int id, double rating, int quantity) {
        this.title = title;
        this.author = author;
        this.id = id;
        this.rating = rating;
        this.quantity = quantity;
    }

    //Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getId() { return id; }
    public double getRating() { return rating; }
    public int getQuantity() { return quantity; }

    //Setters
    public void setId(int id) { this.id = id; }
    public void setQuantity(int quantity) {this.quantity=quantity; }

    // Quantity operations
    public void increaseQuantity() { quantity++; }
    public void decreaseQuantity() { if (quantity > 0) quantity--; }

    //Returns a string representation of the book object.
    @Override
    public String toString() {
        return "BookDTO{" +
                "title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}
