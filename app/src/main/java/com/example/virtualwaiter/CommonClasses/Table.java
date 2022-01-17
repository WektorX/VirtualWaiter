package com.example.virtualwaiter.CommonClasses;

import com.example.virtualwaiter.Net.StaticData;

public class Table {

    private Integer tableId = -1;
    private int numberOfSeats;
    private int waiterId = -1;

    public Table(){};

    public Table(int id){
        tableId = id;
    }

    public Table(int id, int number){
        this.tableId = id;
        this.numberOfSeats = number;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(int id){
        this.tableId = id;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(int waiterId) {
        this.waiterId = waiterId;
    }

    public static Table findTable(Integer id) {
        for (Table t: StaticData.TABLES
        ) {
            if (t.getTableId().equals(id)) {
                return t;
            }
        }
        return null;
    }
}
