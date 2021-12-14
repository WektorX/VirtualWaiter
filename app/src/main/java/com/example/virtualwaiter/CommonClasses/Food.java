package com.example.virtualwaiter.CommonClasses;

public class Food extends Object{
    private String name;
    private String photoName;
    private double price;


    public Food(String name, String photoName, double price) {
        this.name = name;
        this.photoName = photoName;
        this.price = price;

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


}
