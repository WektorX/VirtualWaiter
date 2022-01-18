package com.example.virtualwaiter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.CommonClasses.Worker;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;

public class AddEditWorkerActivity extends AppCompatActivity {

    private Spinner workerTypeDropdown;
    private Button btSave;
    private EditText etName, etLogin, etPassword, etConfirmPassword;
    private Worker worker;
    private boolean editWorker = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_worker);
        workerTypeDropdown = (Spinner) findViewById(R.id.spinnerWorkerType);
        btSave = (Button) findViewById(R.id.btSaveWorker);
        etName = (EditText) findViewById(R.id.etWorkerName);
        etLogin = (EditText) findViewById(R.id.etWorkerLogin);
        etPassword = (EditText) findViewById(R.id.etWorkerPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etWorkerConfirmPassword);

        Bundle b = getIntent().getExtras();
        if(b != null)
            editWorker = b.getBoolean("editWorker");
        if (editWorker) {
            worker = StaticData.WORKER;
            etName.setText(worker.getName());
            etLogin.setText(worker.getLogin());
            etPassword.setText(worker.getPassword());
            etConfirmPassword.setText(worker.getPassword());
            switch (worker.getType()) {
                case "waiter":
                    workerTypeDropdown.setSelection(1);
                    break;
                case "manager":
                    workerTypeDropdown.setSelection(2);
                    break;
                default:
                    workerTypeDropdown.setSelection(0);
                    break;
            }
        }
        addListenerOnSaveButton();
    }

    public void addListenerOnSaveButton() {
        btSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getText().toString().isEmpty()
                        || etConfirmPassword.getText().toString().isEmpty()
                        || etName.getText().toString().isEmpty()
                        || etLogin.getText().toString().isEmpty()
                )
                {
                    Toast.makeText(AddEditWorkerActivity.this,
                            getString(R.string.empty_fields),
                            Toast.LENGTH_SHORT).show();
                }
                else if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    Toast.makeText(AddEditWorkerActivity.this,
                            getString(R.string.passwords_dont_match),
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    String workerType = "";
                    switch (workerTypeDropdown.getSelectedItem().toString()) {
                        case "Chef":
                        case "Kucharz":
                            workerType = "chef";
                            break;
                        case "Waiter":
                        case "Kelner":
                            workerType = "waiter";
                            break;
                        case "Manager":
                        case "Menadżer":
                            workerType = "manager";
                            break;
                    }

                    if (editWorker) {
                        Integer workerId = StaticData.WORKER.getId();
                        StaticData.WORKER = new Worker(workerId, etName.getText().toString(), etLogin.getText().toString(), etPassword.getText().toString(), workerType);
                        new updateWorker().execute();
                    }
                    else {
                        StaticData.WORKER = new Worker(etName.getText().toString(), etLogin.getText().toString(), etPassword.getText().toString(), workerType);
                        new addWorker().execute();
                    }
                }
            }
        });
    }

    public class addWorker extends AsyncTask {
        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.addWorker();
        }

        @Override
        protected void onPostExecute(Object o) {
            String status = (String) o;
            Log.d("Status" , status);
            if(status.equals("success")){
                Toast.makeText(AddEditWorkerActivity.this,
                        getString(R.string.add_worker_success),
                        Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(AddEditWorkerActivity.this,
                        getString(R.string.add_worker_fail),
                        Toast.LENGTH_SHORT).show();
            }
            Intent i = new Intent(AddEditWorkerActivity.this, ManageWorkersActivity.class);
            AddEditWorkerActivity.this.startActivity(i);
            finish();
        }
    }

    public class updateWorker extends AsyncTask {
        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.updateWorker();
        }

        @Override
        protected void onPostExecute(Object o) {
            String status = (String) o;
            Log.d("Status" , status);
            if(status.equals("success")){
                Toast.makeText(AddEditWorkerActivity.this,
                        getString(R.string.update_worker_success),
                        Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(AddEditWorkerActivity.this,
                        getString(R.string.update_worker_fail),
                        Toast.LENGTH_SHORT).show();
            }
            Intent i = new Intent(AddEditWorkerActivity.this, ManageWorkersActivity.class);
            AddEditWorkerActivity.this.startActivity(i);
            finish();
        }
    }
}