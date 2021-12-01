package com.example.virtualwaiter.UI;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.*;
import android.widget.Toast;


import com.example.virtualwaiter.ChefMainActivity;
import com.example.virtualwaiter.DB.DB;
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



public void login(String userName, String password){
    this.password = password;
    this.userName = userName;
    new loginDB().execute();

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


    public class loginDB extends AsyncTask<Void, Void, Map<String, String>>{


        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            return DB.login(userName,password);
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            String status = result.get("status");
            Boolean login = Boolean.valueOf(result.get("login"));
            String type = result.get("type");
            Login.this.loginResult(status,login,type);
        }
    }
}
