package com.example.virtualwaiter.CommonClasses;

public class Worker {
    private int id;
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

    public Worker(int id, String name, String login, String password, String type) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.type = type;
    }

    public int getId() {
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
}
