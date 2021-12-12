package com.example.virtualwaiter.DB;

import android.util.Log;

import com.example.virtualwaiter.CommonClasses.Food;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DB {

    private static Connection con;
    private static Statement state;
    private static String ip = "192.168.1.104";
    private static String dbName = "projekt";

    public static String initConnection(){
        String status;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://"+ip+":3306/"+dbName,"root","");
            state = con.createStatement();
            status = "ok";
        }
        catch (Exception e){
            Log.d("DB connection error" , e.toString());
            status = "error";
        }
        return status;
    }


    public static Map<String, String> login(String username, String password, Boolean table){

        Map<String, String> info = new HashMap<>();
        try{
            ResultSet s = state.executeQuery("SELECT EXISTS(SELECT id FROM worker WHERE login='"+username+"' AND password='"+password+"');");
            if(s.next()){
                info.put("status", "success");
                if(s.getInt(1) == 1){
                    ResultSet worker = state.executeQuery("SELECT type FROM worker WHERE login='"+username+"'");
                    if(worker.next()){
                        info.put("login" ,  "true");
                        if (table) {
                            info.put("type", "table");
                        }
                        else {
                            info.put("type",worker.getString(1));
                        }
                    }
                    else {
                        info.put("login", "true");
                        if (table) {
                            info.put("type", "table");
                        }
                        else {
                            info.put("type", "unknown ");
                        }
                    }
                }
                else{
                    info.put("login" ,  "false");
                    info.put("type" ,  "unknown ");
                }
            }
            else{
                info.put("status" ,"unknown user");
                info.put("login" ,  "false");
                info.put("type" ,  "unknown ");
            }


        }
        catch (Exception e){
            Log.d("DB","Connection Failed " + e.toString());
            info.put("status", "Error");
            info.put("error", e.toString());
        }
        return info;
    }

    public static ArrayList<Food> getMenu(int menuId) throws SQLException {
        ArrayList<Food> menu_items = new ArrayList<>();
        ResultSet rs = state.executeQuery("SELECT name, price, type, description, photoName FROM Food f INNER JOIN Menu_Food mf ON f.id = mf.FoodId WHERE mf.MenuId=" + menuId);
        while (rs.next()) {
            menu_items.add(new Food(rs.getString("name"), rs.getString("photoName"), rs.getDouble("price"), rs.getString("type"), rs.getString("description")));
        }
        return menu_items;
    }

    public static ArrayList<Integer> getFreeTables() {
        ArrayList<Integer> freeTables = new ArrayList<>();
        try{
            ResultSet rs = state.executeQuery("SELECT id FROM `table`");
            while (rs.next()) {
                freeTables.add(rs.getInt("id"));
            }
        }
        catch(Exception e){
            Log.d("Error stoliki", e.toString());
        }

        return freeTables;
    }

    public static void setWaiterToTable(Integer waiterId) throws SQLException {
        state.executeQuery("UPDATE Table ON WorkerId = " + waiterId);
    }

    public static Map<String, ArrayList<Object>> getWaitersList() {

        ArrayList<Object> waitersID = new ArrayList<>();
        ArrayList<Object> waitersName = new ArrayList<>();
        ArrayList<Object> status = new ArrayList<>();
        HashMap<String, ArrayList<Object>> waiters = new HashMap<>();

        try{
            ResultSet rs = state.executeQuery("SELECT id, name FROM worker WHERE type = 'waiter'");
            while (rs.next()) {
                Log.d("waiters", "jest cos" + rs.getInt("id"));
                waitersID.add(rs.getInt("id"));
                waitersName.add(rs.getString("name"));
            }
            waiters.put("id", waitersID);
            waiters.put("name", waitersName);
            status.add("success");
            waiters.put("status", status);
            return  waiters;
        }
        catch(Exception e){
            Log.d("Error kelnerzy", e.toString());
            status.add("error");
            waiters.put("status", status);
            return waiters;
        }

    }

    public static Map<String, ArrayList<Object>> getFood() {

        ArrayList<Object> foodList = new ArrayList<>();
        ArrayList<Object> status = new ArrayList<>();
        HashMap<String, ArrayList<Object>> food = new HashMap<>();

        try{
            ResultSet rs = state.executeQuery("SELECT * FROM food");
            while (rs.next()) {
                Log.d("waiters", "jest cos" + rs.getInt("id"));
                Food temp = new Food(rs.getString("name_pl"),
                        rs.getString("photoName"),
                        rs.getDouble("price"),
                        rs.getString("type"),
                        rs.getString("description_pl"));
                foodList.add(temp);
            }
            status.add("success");
            food.put("status", status);
            food.put("food", foodList);

            return  food;
        }
        catch(Exception e){
            Log.d("Error food", e.toString());
            status.add("error");
            food.put("status", status);
            return food;
        }



    }
}
