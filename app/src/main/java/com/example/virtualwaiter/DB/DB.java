package com.example.virtualwaiter.DB;

import android.util.Log;
import android.widget.Toast;

import com.example.virtualwaiter.MainActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DB {

    private static Connection con;
    private static Statement state;
    private static String ip = "192.168.1.104";
    private static String dbName = "projekt";

    public static String initConnection(){
        String status = null;
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


    public static Map<String, String> login(String username, String password){

        Map<String, String> info = new HashMap<>();
        try{
            ResultSet s = state.executeQuery("SELECT EXISTS(SELECT id FROM worker WHERE login='"+username+"' AND password='"+password+"');");
            if(s.next()){
                info.put("status", "success");
                if(s.getInt(1) == 1){
                    ResultSet worker = state.executeQuery("SELECT type FROM worker WHERE login='"+username+"'");
                    if(worker.next()){

                        info.put("login" ,  "true");
                        info.put("type",worker.getString(1));
                    }
                    else {
                        info.put("login", "true");
                        info.put("type" ,  "unknown ");
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



}
