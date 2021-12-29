package com.example.virtualwaiter.CommonClasses;

public class Dish extends Food{

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getVegan() {
        return isVegan;
    }

    public void setVegan(Boolean vegan) {
        isVegan = vegan;
    }

    public Boolean getGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(Boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    private String description;
    private Boolean isVegan;
    private Boolean glutenFree;

    public Dish(String name, String photoName, double price, String description, Integer vegan, Integer gluten, Integer id) {
        super(name, photoName, price, id);
        this.description = description;
        this.isVegan = (vegan == 1);
        this.glutenFree = (gluten == 1);
    }
}
