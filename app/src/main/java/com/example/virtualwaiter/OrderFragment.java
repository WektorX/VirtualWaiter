package com.example.virtualwaiter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;


public class OrderFragment extends Fragment {

    private Context context;
    public OrderFragment() {}

    public OrderFragment(Context c) {
        this.context =c;
    }


    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_order, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn = getView().findViewById(R.id.btn_place_order);

        btn.setOnClickListener(v ->{

            if(!StaticData.ORDER.getList().isEmpty()){

                new placeOrder().execute();

            }

        });
    }



    public class placeOrder extends AsyncTask {

        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.placeOrder();
        }

        @Override
        protected void onPostExecute(Object o) {
            if(o.equals("success")){
                Log.d("Fragment" , o.toString());
                Intent i = new Intent(context, OrderStatusActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("EXIT", true);
                startActivity(i);
                getActivity().finish();
            }
        }
    }



}