package com.example.virtualwaiter.CommonClasses;

import com.example.virtualwaiter.Net.StaticData;

public class FoodToAdd{
    private Integer id;
    private String name;
    private String namePL;
    private String description;
    private String descriptionPL;
    private String type;
    private boolean isVegan;
    private boolean isGlutenFree;
    private boolean isAlcoholic;
    private double price;
    private String photoName;

    public FoodToAdd(String name, String namePL, String description, String descriptionPL, String type, double price, boolean isAlcoholic, boolean isGlutenFree, boolean isVegan, String photoName) {
        this.name = name;
        this.namePL = namePL;
        this.description = description;
        this.descriptionPL = descriptionPL;
        this.type = type;
        this.isAlcoholic = isAlcoholic;
        this.isVegan = isVegan;
        this.isGlutenFree = isGlutenFree;
        this.price = price;
        this.photoName = photoName;
    }

    public FoodToAdd(int id, String name, String namePL, String description, String descriptionPL, String type, double price, boolean isAlcoholic, boolean isGlutenFree, boolean isVegan, String photoName) {
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
        this.photoName = photoName;
    }

    public Integer getId() {
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

    public String getPhotoName() {
        return photoName;
    }

    public static FoodToAdd findFood(Integer id) {
        for (FoodToAdd f: StaticData.ALL_FOOD
        ) {
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }
}
