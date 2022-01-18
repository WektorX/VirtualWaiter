package com.example.virtualwaiter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualwaiter.CommonClasses.FoodToAdd;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;

public class AddEditFoodActivity extends AppCompatActivity {

    private Spinner foodTypeDropdown;
    private Button btSave;
    private EditText etName, etDescription, etNamePL, etDescriptionPL, etPrice, etPhoto;
    private CheckBox cbVegan, cbGlutenFree, cbAlcoholic;
    private FoodToAdd food;
    private boolean editFood = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_food);
        foodTypeDropdown = (Spinner) findViewById(R.id.spinnerFoodType);
        btSave = (Button) findViewById(R.id.btSaveFood);
        etName = (EditText) findViewById(R.id.etFoodName);
        etNamePL = (EditText) findViewById(R.id.etFoodNamePL);
        etDescription = (EditText) findViewById(R.id.etFoodDescription);
        etDescriptionPL = (EditText) findViewById(R.id.etFoodDescriptionPL);
        etPrice = (EditText) findViewById(R.id.etFoodPrice);
        etPhoto = (EditText) findViewById(R.id.etFoodPhotoName);
        cbVegan = (CheckBox) findViewById(R.id.checkBoxVeganFood);
        cbGlutenFree = (CheckBox) findViewById(R.id.checkBoxGlutenFreeFood);
        cbAlcoholic = (CheckBox) findViewById(R.id.checkBoxAlcoholicFood);

        Bundle b = getIntent().getExtras();
        if(b != null)
            editFood = b.getBoolean("editFood");
        if (editFood) {
            food = StaticData.FOOD;
            etName.setText(food.getName());
            etNamePL.setText(food.getNamePL());
            etDescription.setText(food.getDescription());
            etDescriptionPL.setText(food.getDescriptionPL());
            etPrice.setText(Double.toString(food.getPrice()));
            etPhoto.setText(food.getPhotoName());

            cbAlcoholic.setChecked(food.getIsAlcoholic());
            cbVegan.setChecked(food.getIsVegan());
            cbGlutenFree.setChecked(food.getIsGlutenFree());

            switch (food.getType()) {
                case "dish":
                    foodTypeDropdown.setSelection(0);
                    break;
                default:
                    foodTypeDropdown.setSelection(1);
                    break;
            }
        }
        addListenerOnSaveButton();
    }

    public void addListenerOnSaveButton() {
        btSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNamePL.getText().toString().isEmpty()
                        || etName.getText().toString().isEmpty()
                        || etPrice.getText().toString().isEmpty()
                )
                {
                    Toast.makeText(AddEditFoodActivity.this,
                            getString(R.string.empty_fields),
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    String foodType = "";
                    switch (foodTypeDropdown.getSelectedItem().toString()) {
                        case "Dish":
                        case "Danie":
                            foodType = "dish";
                            break;
                        case "Drink":
                        case "Napój":
                            foodType = "drink";
                            break;
                    }

                    if (editFood) {
                        Integer foodId = StaticData.FOOD.getId();
                        StaticData.FOOD = new FoodToAdd(foodId,
                                etName.getText().toString(),
                                etNamePL.getText().toString(),
                                etDescription.getText().toString(),
                                etDescriptionPL.getText().toString(),
                                foodType,
                                Double.parseDouble(etPrice.getText().toString()),
                                cbAlcoholic.isChecked(),
                                cbGlutenFree.isChecked(),
                                cbVegan.isChecked(),
                                etPhoto.getText().toString()
                        );
                        new AddEditFoodActivity.updateFood().execute();
                    }
                    else {
                        StaticData.FOOD = new FoodToAdd(etName.getText().toString(),
                                etNamePL.getText().toString(),
                                etDescription.getText().toString(),
                                etDescriptionPL.getText().toString(),
                                foodType,
                                Double.parseDouble(etPrice.getText().toString()),
                                cbAlcoholic.isChecked(),
                                cbGlutenFree.isChecked(),
                                cbVegan.isChecked(),
                                etPhoto.getText().toString()
                                );
                        new AddEditFoodActivity.addFood().execute();
                    }
                }
            }
        });
    }

    public class addFood extends AsyncTask {
        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.addFood();
        }

        @Override
        protected void onPostExecute(Object o) {
            String status = (String) o;
            Log.d("Status" , status);
            if(status.equals("success")){
                Toast.makeText(AddEditFoodActivity.this,
                        getString(R.string.add_food_success),
                        Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(AddEditFoodActivity.this,
                        getString(R.string.add_food_fail),
                        Toast.LENGTH_SHORT).show();
            }
            Intent i = new Intent(AddEditFoodActivity.this, ManageMenuActivity.class);
            AddEditFoodActivity.this.startActivity(i);
            finish();
        }
    }

    public class updateFood extends AsyncTask {
        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.updateFood();
        }

        @Override
        protected void onPostExecute(Object o) {
            String status = (String) o;
            Log.d("Status" , status);
            if(status.equals("success")){
                Toast.makeText(AddEditFoodActivity.this,
                        getString(R.string.update_food_success),
                        Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(AddEditFoodActivity.this,
                        getString(R.string.update_food_fail),
                        Toast.LENGTH_SHORT).show();
            }
            Intent i = new Intent(AddEditFoodActivity.this, ManageMenuActivity.class);
            AddEditFoodActivity.this.startActivity(i);
            finish();
        }
    }
}