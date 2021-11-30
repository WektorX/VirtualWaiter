package com.example.virtualwaiter.UI;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.*;
import android.widget.Toast;


import com.example.virtualwaiter.ChefMainActivity;
import com.example.virtualwaiter.ManagerMainActivity;
import com.example.virtualwaiter.WaiterMainActivity;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Login {

private String userName;
private String password;
private Context context;



public Login(Context c){
    this.context = c;
}

    public Connection con;
    public Statement state;

public void login(String userName, String password){
    this.password = password;
    this.userName = userName;

    new asynTaskLoggedIn().execute();

}



private void loginResult(String status, Boolean succesfulLogin, String workerType){
    Log.d("Result Func", status +" "+ succesfulLogin+ " "+ workerType);
    if(status.equals("success")){
        if(succesfulLogin){
            Intent i =null;
            switch (workerType){
                case "waiter":
                    i = new Intent(context, WaiterMainActivity.class);
                    Log.d("Worker type", workerType);
                    break;
                case "chef":
                    i = new Intent(context, ChefMainActivity.class);
                    Log.d("Worker type", workerType);
                    break;

                case "manager":
                    i = new Intent(context, ManagerMainActivity.class);
                    Log.d("Worker type", workerType);
                    break;
                default:
                    Log.d("Worker type", "Error");
                    break;
            }
            context.startActivity(i);
        }
        else{
            Toast toast = Toast.makeText(context, "Login failed! Try again!", Toast.LENGTH_LONG);
            toast.show();
        }
    }
    else{
        Toast toast = Toast.makeText(context, "Connection error! Try again later!", Toast.LENGTH_LONG);
        toast.show();
    }
}


    public class asynTaskLoggedIn extends AsyncTask<Void, Void, Map<String, String>> {

        public Connection con;
        public Statement state;
        Map<String, String> info = new HashMap<>();

        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con= DriverManager.getConnection(
                        "jdbc:mysql://192.168.1.104:3306/projekt","root","");
                Log.d("DB", "tutaj");
                state = con.createStatement();
                Log.d("DB","Connected");
                ResultSet s = state.executeQuery("SELECT EXISTS(SELECT id FROM worker WHERE login='"+userName+"' AND password='"+password+"');");

                if(s.next()){
                    info.put("status", "success");
                    if(s.getInt(1) == 1){
                        ResultSet worker = state.executeQuery("SELECT type FROM worker WHERE login='"+userName+"'");
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


            }catch(Exception e){
                Log.d("DB","Connection Failed " + e.toString());
                info.put("status", "Error");
                info.put("error", e.toString());
            }
            return info;
        }


        @Override
        protected void onPostExecute(Map<String, String> result) {
            Log.d("Result" ,"ok");
            String status = result.get("status");
            Boolean login = Boolean.valueOf(result.get("login"));
            Log.d(result.get("login") , login.toString());
            String type = result.get("type");
            Login.this.loginResult(status,login,type);

        }
    }
}
