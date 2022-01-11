package com.example.virtualwaiter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virtualwaiter.CommonClasses.Order;
import com.example.virtualwaiter.CommonClasses.Table;
import com.example.virtualwaiter.Net.ConnectDB;
import com.example.virtualwaiter.Net.StaticData;

import java.util.Arrays;

public class PayActivity extends AppCompatActivity {

    private Boolean split = false;
    private Boolean payByCard = null;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        context = this;

        Button btnYes = findViewById(R.id.btnSplitYes);
        Button btnNo = findViewById(R.id.btnSplitNo);

        btnNo.setOnClickListener(v->{
            split = false;
            showPaymentMethodView();
        });

        btnYes.setOnClickListener(v->{
            split = true;
            showPaymentMethodView();
        });


    }

    private void showPaymentMethodView(){
        LinearLayout splitQuestion = findViewById(R.id.payLayoutSplitQuestion);
        splitQuestion.setVisibility(View.GONE);

        LinearLayout paymentQuestion = findViewById(R.id.payLayoutSplit);
        paymentQuestion.setVisibility(View.VISIBLE);

        TextView splitAmountQuestion = findViewById(R.id.splitBillQuestion);
        EditText inputSplitTo = findViewById(R.id.splitBillAmount);

        if(split){
            splitAmountQuestion.setVisibility(View.VISIBLE);
            inputSplitTo.setVisibility(View.VISIBLE);
        }

        ImageView card = findViewById(R.id.paymentCard);
        ImageView cash = findViewById(R.id.paymentCash);

        card.setOnClickListener(v->{
            this.payByCard = true;
            card.setBackgroundColor(getColor(R.color.white));
            cash.setBackgroundColor(getColor(R.color.transparent));
        });
        cash.setOnClickListener(v->{
            this.payByCard = false;
            card.setBackgroundColor(getColor(R.color.transparent));
            cash.setBackgroundColor(getColor(R.color.white));
        });

        Button btnAskForBill = findViewById(R.id.btnPay);

        btnAskForBill.setOnClickListener(v->{
            String errorMessage = "";
            if(payByCard != null){
                if(split){
                    if(!inputSplitTo.getText().toString().equals("")){
                        int amount = Integer.valueOf(inputSplitTo.getText().toString());
                        Log.d("Amount" , amount + "");
                        if(amount >=1 ){
                            StaticData.ORDER.setSplitBillBetween(amount);
                        }
                        else{
                            errorMessage = PayActivity.this.getString(R.string.toast_invalid_split_amount);
                        }
                    }
                    else{
                        errorMessage = PayActivity.this.getString(R.string.toast_fill_split_amount);
                    }
                }
                else{
                    StaticData.ORDER.setSplitBillBetween(1);
                }
            }
            else{
                errorMessage = PayActivity.this.getString(R.string.toast_choose_payment_method);
            }


            if(!errorMessage.isEmpty()){
                Toast toast = Toast.makeText(PayActivity.this, errorMessage, Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                StaticData.ORDER.setPayByCard(payByCard);
                new askForBill().execute();
            }
        });



    }



    public class askForBill extends AsyncTask {

        @Override
        protected String doInBackground(Object... objects) {
            return ConnectDB.askForBill();
        }

        @Override
        protected void onPostExecute(Object o) {
            String status = (String) o;
            Log.d("Status" , status);
            if(status.equals("success")){
                CardView paymentCard = findViewById(R.id.cardViewPayment);
                paymentCard.setBackgroundColor(getColor(R.color.transparent));

                LinearLayout payLayoutSplitQuestion = findViewById(R.id.payLayoutSplitQuestion);
                payLayoutSplitQuestion.setVisibility(View.GONE);

                LinearLayout payLayoutSplit = findViewById(R.id.payLayoutSplit);
                payLayoutSplit.setVisibility(View.GONE);

                LinearLayout paymentSummary = findViewById(R.id.paymentSummary);
                paymentSummary.setVisibility(View.VISIBLE);

                ImageView backToHomeMenu = findViewById(R.id.backToHomeMenu);
                backToHomeMenu.setOnClickListener(v->{
                    StaticData.ORDER = new Order();
                    StaticData.TABLE = new Table();


                    Intent i = new Intent(PayActivity.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("EXIT", true);
                    startActivity(i);
                    finish();
                });

            }
        }
    }
}