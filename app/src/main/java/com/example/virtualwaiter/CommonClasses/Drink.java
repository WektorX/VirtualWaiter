package com.example.virtualwaiter.CommonClasses;

public class Drink extends Food{
    private Boolean isAlcoholic;
    public Drink(String name, String photoName, double price, Integer alcoholic) {
        super(name, photoName, price);
        this.isAlcoholic= (alcoholic == 1);
    }

    public Boolean getAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(Boolean alcoholic) {
        isAlcoholic = alcoholic;
    }
}
