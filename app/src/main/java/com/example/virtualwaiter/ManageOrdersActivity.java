package com.example.virtualwaiter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virtualwaiter.CommonClasses.Order;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.UI.Components.ManagerOrderCardView;

public class ManageOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_orders);

        new getOrders().execute();
    }

    public void showAllOrders() {
        LinearLayout allOrdersLinearLayout = (LinearLayout) findViewById(R.id.llManageOrdersList);
        allOrdersLinearLayout.removeAllViews();

        for (Order o: StaticData.ALL_ORDERS
        ) {
            allOrdersLinearLayout.addView(new ManagerOrderCardView(ManageOrdersActivity.this, o));
        }
    }

    public class getOrders extends AsyncTask {
        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.getAllOrders();
        }

        @Override
        protected void onPostExecute(Object o) {
            String status = (String) o;
            Log.d("Status" , status);
            if(status.equals("success")){
                Toast.makeText(ManageOrdersActivity.this,
                        getString(R.string.get_orders_success),
                        Toast.LENGTH_SHORT).show();
                showAllOrders();
            }
            else {
                Toast.makeText(ManageOrdersActivity.this,
                        getString(R.string.get_orders_fail),
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ManageOrdersActivity.this, ManagerMainActivity.class);
                ManageOrdersActivity.this.startActivity(i);
            }
        }
    }
}