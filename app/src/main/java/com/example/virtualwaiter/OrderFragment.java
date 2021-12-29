package com.example.virtualwaiter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.virtualwaiter.CommonClasses.Order;
import com.example.virtualwaiter.CommonClasses.OrderItem;
import com.example.virtualwaiter.Net.StaticData;

import java.math.BigDecimal;
import java.util.ArrayList;



public class OrderFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    public OrderFragment() {}

    public OrderFragment(Context c) {
        this.context =c;
        // Required empty public constructor
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

            //Open status activity and close all previous to not allow user to back
            Intent i = new Intent(context, LoginActivity.class);        // Specify any activity here e.g. home or splash or login etc
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("EXIT", true);
            startActivity(i);
            getActivity().finish();
        });
    }
}