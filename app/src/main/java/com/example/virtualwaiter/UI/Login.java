package com.example.virtualwaiter.UI;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.*;


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

public void login(String userName, String password){
    this.password = password;
    this.userName = userName;

    new asynTaskLoggedIn().execute();
    //return "true";
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
                        "jdbc:mysql://192.168.1.106:3306/projekt","root","");
                state = con.createStatement();
                Log.d("DB","Connected");
                ResultSet s = state.executeQuery("SELECT EXISTS(SELECT id FROM worker WHERE login='"+userName+"' AND password='"+password+"');");

                if(s.next()){
                    info.put("status", "success");
                    if(s.getInt(1) == 1){
                        info.put("login" ,  "true");
                        ResultSet worker = state.executeQuery("SELECT type FROM worker WHERE login='"+userName+"'");
                        if(worker.next()){
                            info.put("type",worker.getString(1));
                        }
                    }
                    else{
                        info.put("login" ,  "false");
                    }
                }
                else{
                    info.put("status" ,"unknown");
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
            Log.d("Status",result.get("status"));
            if(result.get("status").equals("success")){
                if(result.get("login").equals("true")){
                    // TODO : RUN NEW ACTIVITY DEPENDS ON RESULT TYPE OF WORKER
                    switch (result.get("type")){
                        case "waiter":
                            Intent i = new Intent(context, WaiterMainActivity.class);
                            context.startActivity(i);
                            break;
                    }

                }
                else{
                    // TODO : CLEAR PASSWORD AND/OR LOGIN FIELD AND RUN TOAST OR MESSAGE LOGIN FAILED
                }
            }
            else{
                // TODO: ERROR MESSAGE - SOMETHING WENT WRONG TRY AGAIN LATER
            }
        }
    }
}
