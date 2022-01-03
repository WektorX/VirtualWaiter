package com.example.virtualwaiter;

import static com.example.virtualwaiter.Net.StaticData.MENU;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.UI.Components.MenuItem;


public class MenuFragment extends Fragment {

    private Context context;
    public MenuFragment(Context c) {
        // Required empty public constructor
        this.context = c;
    }

    public MenuFragment(){}


    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new getMenuDishes().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    public class getMenuDishes extends AsyncTask {

        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.getFood();
        }

        @Override
        protected void onPostExecute(Object o) {
            if(o.equals("success")){
                LinearLayout lvDishes = getView().findViewById(R.id.MenuDishesList);
                LinearLayout lvDrinks = getView().findViewById(R.id.MenuDrinksList);
                for(int i =0;i<MENU.getDishList().size();i++){
                    MenuItem tx = new MenuItem(context, MENU.getDishList().get(i));
                    lvDishes.addView(tx);
                }
                for(int i =0;i<MENU.getDrinksList().size();i++){
                    MenuItem tx = new MenuItem(context, MENU.getDrinksList().get(i));
                    lvDrinks.addView(tx);
                }
            }
        }
    }
}