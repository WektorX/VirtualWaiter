package com.example.virtualwaiter.UI.Actions;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.virtualwaiter.ChefMainActivity;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.FoodMenuActivity;
import com.example.virtualwaiter.ManagerMainActivity;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.WaiterMainActivity;

import java.sql.SQLException;
import java.util.Map;

public class Login {

private String userName;
private String password;
private boolean table;
private Context context;



public Login(Context c){
    this.context = c;
}



public void login(String userName, String password, boolean table){
    this.password = password;
    this.userName = userName;
    this.table = table;
    new loginDB().execute();
}



private void loginResult(String status, Boolean succesfulLogin, String workerType) throws SQLException {
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
                case "table":
                   // ConnectDB.setWaiterToTable(420);
                    i = new Intent(context, FoodMenuActivity.class);
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
            return ConnectDB.login(userName,password, table);
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            String status = result.get("status");
            Log.d("status", status);
                Boolean login = Boolean.valueOf(result.get("login"));
                String type = result.get("type");
                int id = Integer.valueOf(result.get("id"));
                StaticData.WORKER_ID = id;
                try {
                    assert status != null;
                    Login.this.loginResult(status,login,type);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
        }
    }
}
