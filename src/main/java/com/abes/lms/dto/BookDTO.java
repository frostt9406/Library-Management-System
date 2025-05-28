package com.abes.lms.dto;

public class BookDTO {
    private String title;
    private String author;
    private String id;
    private double price;
    private double rating;
    private boolean isIssued;

    // constructor for the book DTO
    public BookDTO(String title, String author, String id, double price, double rating, boolean isIssued) {
        setTitle(title);
        setAuthor(author);
        setId(id);
        setPrice(price);
        setRating(rating);
        setIssued(isIssued);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }
}
