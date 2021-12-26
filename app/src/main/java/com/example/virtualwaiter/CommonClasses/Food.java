package com.example.virtualwaiter.CommonClasses;

import android.util.Log;

import java.math.BigDecimal;

public class Food extends Object{
    private String name;
    private String photoName;
    private BigDecimal price;


    public Food(String name, String photoName, double price) {
        this.name = name;
        this.photoName = photoName;
        this.price = BigDecimal.valueOf(price);
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


}
