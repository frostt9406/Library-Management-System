package com.abes.lms.dto;

public class BookDTO {
    private String title;
    private String author;
    private int id;
    private double rating;
    private int quantity;
//constructor
    public BookDTO(String title, String author, int id, double rating, int quantity) {
        this.title = title;
        this.author = author;
        this.id = id;
        this.rating = rating;
        this.quantity = quantity;
    }

//  getter methods
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getId() { return id; }
    public double getRating() { return rating; }
    public int getQuantity() { return quantity; }

// setter methods
    public void setId(int id) { this.id = id; }
    public void setQuantity(int quantity) {this.quantity=quantity; }

//  handling quantity of the book
    public void increaseQuantity() { quantity++; }
    public void decreaseQuantity() { if (quantity > 0) quantity--; }

//  to string overriden
    @Override
    public String toString() {
        return String.format("BookDTO[ID=%d, Title='%s', Author='%s', Rating=%.1f, Quantity=%d]",
                id, title, author, rating, quantity);
    }
}
