package com.example.virtualwaiter.CommonClasses;

import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;

public class Order {

    private ArrayList<OrderItem> itemsList = new ArrayList<>();
    private BigDecimal total = new BigDecimal(0);
    private String status;
    private int id = -1;
    private Table table = null;
    private Date date;
    private int splitBillBetween;

    public int getSplitBillBetween() {
        return splitBillBetween;
    }

    public Boolean getPayByCard() {
        return payByCard;
    }

    public void setPayByCard(Boolean payByCard) {
        this.payByCard = payByCard;
    }

    private Boolean payByCard;


    public Order(){
        this.status = "creation";
        this.total = BigDecimal.valueOf(0.0);
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public void setDate(Date d){
        date = d;
    }
    public Date getDate(){
        return date;
    }
    public  void setTable(Table t){
        table = t;
    }

    public void setStatus(String s){status = s;};

    public String getStatus(){
        return status;
    }

    public Table getTable(){
        return table;
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
    public void addToOrder(OrderItem item){
        this.itemsList.add(item);
        total = new BigDecimal(this.total.doubleValue()).add(item.getTotal());

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
