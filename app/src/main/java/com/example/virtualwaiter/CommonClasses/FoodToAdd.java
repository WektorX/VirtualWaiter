package com.example.virtualwaiter.CommonClasses;

public class FoodToAdd{
    private int id;
    private String name;
    private String namePL;
    private String description;
    private String descriptionPL;
    private String type;
    private boolean isVegan;
    private boolean isGlutenFree;
    private boolean isAlcoholic;
    private double price;

    public FoodToAdd(String name, String namePL, String description, String descriptionPL, String type, double price, boolean isAlcoholic, boolean isGlutenFree, boolean isVegan) {
        this.name = name;
        this.namePL = namePL;
        this.description = description;
        this.descriptionPL = descriptionPL;
        this.type = type;
        this.isAlcoholic = isAlcoholic;
        this.isVegan = isVegan;
        this.isGlutenFree = isGlutenFree;
        this.price = price;
    }

    public FoodToAdd(int id, String name, String namePL, String description, String descriptionPL, String type, double price, boolean isAlcoholic, boolean isGlutenFree, boolean isVegan) {
        this.id = id;
        this.name = name;
        this.namePL = namePL;
        this.description = description;
        this.descriptionPL = descriptionPL;
        this.type = type;
        this.isAlcoholic = isAlcoholic;
        this.isVegan = isVegan;
        this.isGlutenFree = isGlutenFree;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getDescriptionPL() {
        return descriptionPL;
    }

    public String getNamePL() {
        return namePL;
    }

    public boolean getIsAlcoholic() {
        return isAlcoholic;
    }

    public boolean getIsVegan() {
        return isVegan;
    }

    public boolean getIsGlutenFree() {
        return isGlutenFree;
    }
}
