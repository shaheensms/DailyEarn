
package com.metacoders.dailyearn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.dailyearn.R;
import com.metacoders.dailyearn.models.modelForHIstory;
import com.metacoders.dailyearn.models.modelForTransactionDb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class paymentSelectActivity extends AppCompatActivity {
    String balance, method, fromAcc;
    ImageView pmMoney, netTeller, skrill, localBank, bitcoin, coinBase, bkash, rocket, nagad, paytm;
    String msg = "";
    Button proceedBtn;
    String Flag = null;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_system_select);

        pmMoney = findViewById(R.id.perFectMoneyImage);
        netTeller = findViewById(R.id.netleerImage);
        skrill = findViewById(R.id.skrill);
        localBank = findViewById(R.id.bank);
        bitcoin = findViewById(R.id.btcoinImage);
        coinBase = findViewById(R.id.coin_base);
        bkash = findViewById(R.id.bkash);
        rocket = findViewById(R.id.rocket);
        nagad = findViewById(R.id.nagad);
        paytm = findViewById(R.id.paytm);


        // setting the  button  .
        bitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerDialogue("Bitcoin");
            }
        });

        localBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                triggerDialogue("Local Bank");

            }
        });

        skrill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerDialogue("Skrill");
            }
        });

        netTeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerDialogue("Net-teller");
            }
        });

        pmMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                triggerDialogue("Perfect Money");

            }
        });

        coinBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                triggerDialogue("Coin Base");

            }
        });

        bkash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                triggerDialogue("Bkash");

            }
        });

        rocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                triggerDialogue("Rocket");

            }
        });

        nagad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                triggerDialogue("Nagad");

            }
        });

        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                triggerDialogue("Paytm");

            }
        });


        Intent y = getIntent();
        method = y.getStringExtra("method");
        balance = y.getStringExtra("amount");
        fromAcc = y.getStringExtra("fromAccount");
        if (method.equals("withdraw")) {
            Flag = "withdraw";

        } else {

            Flag = "deposit";

        }


    }

    private void triggerDialogue(final String text) {
        Context context;
        dialog = new Dialog(paymentSelectActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.deposit_dialogue);
        Button submitBtn = dialog.findViewById(R.id.submitBtn);
        Button cancelBtn = dialog.findViewById(R.id.cancelBtn);
        TextInputEditText bal = dialog.findViewById(R.id.amountIn);
        final TextInputEditText mail = dialog.findViewById(R.id.mailIn);
        TextView header = dialog.findViewById(R.id.headerText);


        header.setText("Please Enter Your " + text + " Email Adress ");
        bal.setText(balance);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Mail = mail.getText().toString();
                if (!TextUtils.isEmpty(Mail)) {
                    final String DATE = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    DatabaseReference mref = FirebaseDatabase.getInstance().getReference();
                    // upload the  transfer req to the preferred db
                    final String key = mref.push().getKey();


                    //TODO NEED UID HERE
                    modelForTransactionDb modelForTransactionDb = new modelForTransactionDb(key, balance, "testId", Mail, DATE, text);
                    mref.child("transacDb").child(key).setValue(modelForTransactionDb).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            //TODO uid here
                            DatabaseReference st = FirebaseDatabase.getInstance().getReference("profile").child("MUIdCk609CZBr4ZZTd8Mc9kpzDJ2").child("transHistory");

                            //  String reason, String status, String date, String amount

                            modelForHIstory modelForHIstoryf = new modelForHIstory(key, key, "You Requested  " + balance + "  Your Account", "Pending", DATE, balance);

                            st.child(key).setValue(modelForHIstoryf).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    dialog.dismiss();
                                    showToast("DONE!!");
                                }
                            });


                        }
                    });

                } else {

                    showToast("Mail Address is empty !!!");
                }


            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


        dialog.show();


    }

    private void showToast(String s) {

        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT)
                .show();
    }
}
