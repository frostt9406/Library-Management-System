package com.abes.lms.dto;

public class BookDTO {
    private String title;
    private String author;
    private int id;
    private double rating;
    private int quantity;

    public BookDTO(String title, String author, int id, double rating, int quantity) {
        this.title = title;
        this.author = author;
        this.id = id;
        this.rating = rating;
        this.quantity = quantity;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getId() { return id; }
    public double getRating() { return rating; }
    public int getQuantity() { return quantity; }

    public void setId(int id) { this.id = id; }

    public void increaseQuantity() { quantity++; }
    public void decreaseQuantity() { if (quantity > 0) quantity--; }

    @Override
    public String toString() {
        return String.format("BookDTO[ID=%d, Title='%s', Author='%s', Rating=%.1f, Quantity=%d]",
                id, title, author, rating, quantity);
    }
}
