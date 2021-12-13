package com.example.virtualwaiter.CommonClasses;

public class Food extends Object{
    private String name;
    private String photoName;
    private double price;
    private String type;
    private String description;
    private Boolean vegan;
    private Boolean glutenFree;
    private Boolean isAlcoholic;

    public Food(String name, String photoName, double price, String type, String description, Integer vegan, Integer gluten, Integer isAlcoholic) {
        this.name = name;
        this.photoName = photoName;
        this.price = price;
        this.type = type;
        this.description = description;
        this.vegan = (vegan == 1);
        this.glutenFree = (gluten == 1);
        this.isAlcoholic = (isAlcoholic == 1);
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

    public Boolean getGlutenFree() {
        return glutenFree;
    }

    public Boolean getVegan() {
        return vegan;
    }

    public Boolean getAlcoholic() {
        return isAlcoholic;
    }
}
