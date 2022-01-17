package com.example.virtualwaiter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.CommonClasses.Table;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;

public class AddEditTableActivity extends AppCompatActivity {
    private Button btSave;
    private EditText etSeats;
    private Table table;
    private boolean editTable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_table);
        btSave = (Button) findViewById(R.id.btSaveTable);
        etSeats = (EditText) findViewById(R.id.etSeats);

        Bundle b = getIntent().getExtras();
        if(b != null)
            editTable = b.getBoolean("editTable");
        if (editTable) {
            table = StaticData.TABLE;
            etSeats.setText(Integer.toString(table.getNumberOfSeats()));
        }
        addListenerOnSaveButton();
    }

    public void addListenerOnSaveButton() {
        btSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSeats.getText().toString().isEmpty())
                {
                    Toast.makeText(AddEditTableActivity.this,
                            getString(R.string.empty_fields),
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    if (editTable) {
                        Integer tableId = StaticData.TABLE.getTableId();
                        StaticData.TABLE = new Table(tableId, Integer.parseInt(etSeats.getText().toString()));
                        new AddEditTableActivity.updateTable().execute();
                    }
                    else {
                        StaticData.TABLE = new Table(-1, Integer.parseInt(etSeats.getText().toString()));
                        new AddEditTableActivity.addTable().execute();
                    }
                }
            }
        });
    }

    public class addTable extends AsyncTask {
        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.addTable();
        }

        @Override
        protected void onPostExecute(Object o) {
            String status = (String) o;
            Log.d("Status" , status);
            if(status.equals("success")){
                Toast.makeText(AddEditTableActivity.this,
                        getString(R.string.add_table_success),
                        Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(AddEditTableActivity.this,
                        getString(R.string.add_table_fail),
                        Toast.LENGTH_SHORT).show();
            }
            Intent i = new Intent(AddEditTableActivity.this, ManageTablesActivity.class);
            AddEditTableActivity.this.startActivity(i);
        }
    }

    public class updateTable extends AsyncTask {
        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.updateTable();
        }

        @Override
        protected void onPostExecute(Object o) {
            String status = (String) o;
            Log.d("Status" , status);
            if(status.equals("success")){
                Toast.makeText(AddEditTableActivity.this,
                        getString(R.string.update_table_success),
                        Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(AddEditTableActivity.this,
                        getString(R.string.update_table_fail),
                        Toast.LENGTH_SHORT).show();
            }
            Intent i = new Intent(AddEditTableActivity.this, ManageTablesActivity.class);
            AddEditTableActivity.this.startActivity(i);
        }
    }
}