package com.example.virtualwaiter.Net;

import com.example.virtualwaiter.CommonClasses.Menu;
import com.example.virtualwaiter.CommonClasses.Order;
import com.example.virtualwaiter.CommonClasses.Table;

import java.util.ArrayList;

public class StaticData {
    public static Boolean LOGGED_IN;
    public static String IP = "192.168.1.108";
    public static String DB_NAME = "projekt";
    public static String LANGUAGE;
    public static Menu MENU = new Menu();
    public static Table TABLE = new Table();
    public static Order ORDER = new Order();
    public static int DEVICE_WIDTH;
    public static int DEVICE_HEIGHT;
    public static ArrayList<Order> CURRENT_WAITER_ORDERS = new ArrayList<>();
    public static ArrayList<Order> PAST_WAITER_ORDERS = new ArrayList<>();
    public static int WORKER_ID = -1;
}
