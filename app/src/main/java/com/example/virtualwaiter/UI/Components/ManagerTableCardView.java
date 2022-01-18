package com.example.virtualwaiter.UI.Components;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.virtualwaiter.AddEditTableActivity;
import com.example.virtualwaiter.CommonClasses.Table;
import com.example.virtualwaiter.ManageTablesActivity;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;
import com.example.virtualwaiter.R;

public class ManagerTableCardView extends CardView {

    private Context context;

    public ManagerTableCardView(@NonNull Context context) {
        super(context);
    }

    @SuppressLint("ResourceAsColor")
    public ManagerTableCardView(@NonNull Context context, Table t) {
        super(context);
        this.context = context;
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams((StaticData.DEVICE_WIDTH*4)/5, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(cardParams);
        cardParams.setMargins(0, 60, 0 , 60);
        this.setCardBackgroundColor(context.getColor(R.color.transparent));

        LinearLayout ly = new LinearLayout(context);
        LinearLayout.LayoutParams params  = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ly.setLayoutParams(params);
        ly.setBackgroundColor(context.getColor(R.color.almost_transparent));
        ly.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams componentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        componentParams.setMargins(20,0,20,0);

        TextView name = new TextView(context);
        name.setText(context.getString(R.string.table) + " " + t.getTableId());
        name.setTextSize(20);
        name.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        name.setLayoutParams(componentParams);
        name.setTextColor(context.getColor(R.color.white));
        ly.addView(name);

        TextView seats = new TextView(context);
        seats.setText(context.getString(R.string.number_of_seats) + ": " + t.getNumberOfSeats());
        seats.setTextSize(20);
        seats.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        seats.setLayoutParams(componentParams);
        seats.setTextColor(context.getColor(R.color.white));
        ly.addView(seats);

        View line = new View(context);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5);
        line.setBackgroundColor(context.getColor(R.color.white));
        line.setLayoutParams(lineParams);
        ly.addView(line);

        Button btEdit = new Button(context);
        btEdit.setText(context.getString(R.string.edit));
        btEdit.setBackgroundColor(context.getColor(R.color.almost_transparent));
        btEdit.setTag(t.getTableId());
        ly.addView(btEdit);
        btEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(v.getTag().toString());
                StaticData.TABLE = Table.findTable(id);
                Intent i = new Intent(context, AddEditTableActivity.class);
                Bundle b = new Bundle();
                b.putBoolean("editTable", true);
                i.putExtras(b);
                context.startActivity(i);
                ((Activity)context).finish();
            }
        });

        Button btDelete = new Button(context);
        btDelete.setText(context.getString(R.string.delete));
        btDelete.setBackgroundColor(context.getColor(R.color.remove_btn_color_dark));
        btDelete.setTag(t.getTableId());
        ly.addView(btDelete);
        btDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.delete))
                        .setMessage(context.getString(R.string.delete_question))
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = Integer.parseInt(v.getTag().toString());
                                StaticData.TABLE = Table.findTable(id);
                                new deleteTable().execute();
                            }
                        })
                        .setNegativeButton(R.string.no, null)
                        .show();
            }
        });


        this.addView(ly);
    }

    public ManagerTableCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public ManagerTableCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public class deleteTable extends AsyncTask {
        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.deleteTable();
        }

        @Override
        protected void onPostExecute(Object o) {
            String status = (String) o;
            Log.d("Status" , status);
            if(status.equals("success")){
                Toast.makeText(context,
                        context.getString(R.string.delete_table_success),
                        Toast.LENGTH_SHORT).show();
                Log.d("delete table success", status);
                Intent i = new Intent(context, ManageTablesActivity.class);
                context.startActivity(i);
                ((Activity)context).overridePendingTransition(0,0);
                context.startActivity(i);
                ((Activity)context).overridePendingTransition(0,0);
            }
            else {
                Log.d("Delete table fail", status);
                Toast.makeText(context,
                        context.getString(R.string.delete_table_fail),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
