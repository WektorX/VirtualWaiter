package com.example.virtualwaiter.CommonClasses;

import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Order {

    private ArrayList<OrderItem> itemsList = new ArrayList<>();
    private BigDecimal total = new BigDecimal(0);
    private String status;
    private int id;
    private Table table;
    private int splitBillBetween;


    public Order(){
        this.status = "creation";
        this.total = BigDecimal.valueOf(0.0);
    }

    public  void setTable(Table t){
        table = t;
    }

    public void addToOrder(Food item){
        int itemIndex =-1;
        for(OrderItem i : itemsList){
            if(i.getFoodName().equals(item.getName())){
                itemIndex = itemsList.indexOf(i);
            }
        }
        if(itemIndex == -1){
           OrderItem newItem = new OrderItem(item);
           itemsList.add(newItem);
        }
        else {
            itemsList.get(itemIndex).addToOrder();
        }
        total = new BigDecimal(this.total.doubleValue()).add(item.getPrice());

    }

    public void removeFromOrder(Food item){
        int itemIndex =-1;
        for(OrderItem i : itemsList){
            if(i.getFoodName().equals(item.getName())){
                itemIndex = itemsList.indexOf(i);
            }
        }
        if(itemIndex == -1){
            Log.d("ORDER" , "Nie znaleziono pozycji zamówienia");
        }
        else {
            itemsList.get(itemIndex).removeFromOrder();
            if(itemsList.get(itemIndex).getAmount() == 0){
                itemsList.remove(itemIndex);
            }
            total = new BigDecimal(total.doubleValue()).subtract(item.getPrice());
        }
    }

    private void sumTotal(){
        this.total = BigDecimal.valueOf(0);
        for(OrderItem item : this.itemsList){
            this.total.add(item.getTotal());
        }
    }

    public void setSplitBillBetween(int n){
        this.splitBillBetween = n;
    }

    public ArrayList<OrderItem> getList(){
        return this.itemsList;
    }

    public BigDecimal getTotal(){
        return  total;
    }
}
