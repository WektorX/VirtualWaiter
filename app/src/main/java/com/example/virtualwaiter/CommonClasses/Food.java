package com.example.virtualwaiter.CommonClasses;

import java.math.BigDecimal;

public class Food extends Object{
    private String name;
    private String photoName;
    private BigDecimal price;
    private int id;

    public Food(String name, String photoName, double price, int id) {
        this.name = name;
        this.photoName = photoName;
        this.price = BigDecimal.valueOf(price);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPhotoName() {
        return photoName;
    }

    public int getId() {
        return id;
    }
}
