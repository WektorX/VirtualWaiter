package com.example.virtualwaiter.CommonClasses;

import android.util.Log;

import com.example.virtualwaiter.Net.StaticData;

public class Worker {
    private Integer id;
    private String name;
    private String login;
    private String password;
    private String type;

    public Worker(String name, String login, String password, String type) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.type = type;
    }

    public Worker(Integer id, String name, String login, String password, String type) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Worker findWorker(Integer id) {
        for (Worker w: StaticData.EMPLOYEES
             ) {
            if (w.getId().equals(id)) {
                return w;
            }
        }
        return null;
    }
}
