package com.example.virtualwaiter.CommonClasses;

import java.util.ArrayList;

public class Menu {

    private ArrayList<Food> dishList = new ArrayList<>();
    private ArrayList<Food> drinksList = new ArrayList<>();

    public Menu(){}


    public ArrayList<Food> getDishList() {
        return dishList;
    }

    public void setDishList(ArrayList<Food> dishList) {
        this.dishList = dishList;
    }

    public ArrayList<Food> getDrinksList() {
        return drinksList;
    }

    public void setDrinksList(ArrayList<Food> drinksList) {
        this.drinksList = drinksList;
    }

    public void addDish(Food f){
        this.dishList.add(f);
    }

    public void addDrink(Food f){
        this.drinksList.add(f);
    }

}
