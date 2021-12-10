package com.example.virtualwaiter.CommonClasses;

public class Food {
    private String name;
    private String photoName;
    private double price;
    private String type;
    private String description;

    public Food(String name, String photoName, double price, String type, String description) {
        this.name = name;
        this.photoName = photoName;
        this.price = price;
        this.type = type;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getPhotoName() {
        return photoName;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}
