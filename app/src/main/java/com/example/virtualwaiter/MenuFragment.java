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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    public MenuFragment(Context c) {
        // Required empty public constructor
        this.context = c;
    }

    public MenuFragment(){}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        new getMenuDishes().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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