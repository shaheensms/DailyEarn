/*
 * Copyright (c) $today.year.This Code is written by Gamkiller .
 */

package com.metacoders.dailyearn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.dailyearn.R;
import com.metacoders.dailyearn.fragments.dashboardFragment;
import com.metacoders.dailyearn.models.modelForHIstory;
import com.metacoders.dailyearn.models.modelForTransactionDb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class depositActivity extends AppCompatActivity {

    String method , enterPass  , mypass , bal;
    Button submit;
    TextInputEditText pas , amount   , sentFrom  ;
String Flag , balance , fromAcc , packageName , m  ;

    String uid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        uid = FirebaseAuth.getInstance().getUid() ;
        pas = findViewById(R.id.mypassWord) ;
        amount = findViewById(R.id.amount);
        submit = findViewById(R.id.confirmBtn);
        sentFrom = findViewById(R.id.sentUser) ;

        dashboardFragment dashboardfragment = new dashboardFragment();
        mypass = dashboardFragment.getPassWord();




        final Intent p = getIntent();
        method = p.getStringExtra("method") ;
        balance = p.getStringExtra("amount");
        fromAcc = p.getStringExtra("fromAccount");
        packageName = p.getStringExtra("name") ;
        m = p.getStringExtra("type") ;


        if(m.contains("mutual"))
        {
            Flag = "mutualDB";

        }
        else Flag = "depositDb";





            submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterPass = pas.getText().toString() ;
                bal = amount.getText().toString() ;
                if ( mypass.equals(enterPass))
                {
                    final String DATE = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    final DatabaseReference mref = FirebaseDatabase.getInstance().getReference();
                    // upload the  transfer req to the preferred db
                    final String key = mref.push().getKey();



                    modelForTransactionDb modelForTransactionDb = new modelForTransactionDb(key, bal, uid, sentFrom.getText().toString(), DATE, method ,"Pending" );
                    //withdraw
                    mref.child(Flag).child(key).setValue(modelForTransactionDb).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {



                            DatabaseReference st = FirebaseDatabase.getInstance().getReference("profile").child(uid).child("transHistory");

                            //  String reason, String status, String date, String amount

                            modelForHIstory modelForHIstoryf = new modelForHIstory(key, key, "You deposited  " + bal + " To Your Account", "Pending", DATE, bal);

                            if(Flag.equals("mutualDB"))
                            {
                                mref.child(Flag).child(key).child("pname").setValue(packageName) ;
                            }

                            st.child(key).setValue(modelForHIstoryf).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    new AwesomeSuccessDialog(depositActivity.this)
                                            .setTitle("Your Request was Successful")
                                            .setMessage("Our Admin will soon review your request.")
                                            .setColoredCircle(R.color.dialogSuccessBackgroundColor)
                                            .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
                                            .setCancelable(true)
                                            .setPositiveButtonText(getString(R.string.dialog_yes_button))
                                            .setPositiveButtonbackgroundColor(R.color.dialogSuccessBackgroundColor)
                                            .setPositiveButtonTextColor(R.color.white)
                                            .setPositiveButtonClick(new Closure() {
                                                @Override
                                                public void exec() {
                                                    //click
                                                    finish();
                                                }
                                            })
                                            .show();
                                }
                            });


                        }
                    });


                }
                else {

                    Toast.makeText(getApplicationContext() , "Password is wrong!!" +
                            " ", Toast.LENGTH_SHORT)
                            .show();

                }


            }
        });
    }
}
