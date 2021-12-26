package com.example.virtualwaiter.CommonClasses;

import java.math.BigDecimal;

public class OrderItem {

    private Food item;
    private int amount;
    private BigDecimal total;


    public OrderItem(){
        this.total = BigDecimal.valueOf(0);
        this.amount = 0;
    }

    public OrderItem(Food f){
        this.item = f;
        this.amount = 1;
        this.total = f.getPrice();
    }

    public void setFood(Food f){
        this.item = f;
        this.amount = 1;
        this.total = f.getPrice();
    }

    public void addToOrder(){
        this.amount += 1;
        calculateTotal();
    }

    public void removeFromOrder(){
        if(this.amount > 0){
            this.amount -=1;
        }
        calculateTotal();
    }

    private void calculateTotal(){
        this.total = (this.item.getPrice().multiply(BigDecimal.valueOf(amount)));
    }

    public String getItemName(){
        return this.item.getName();
    }

    public int getAmount() {
        return amount;
    }

    public String getFoodName(){
        return  item.getName();
    }

    public BigDecimal getTotal() {
        return total;
    }
}
