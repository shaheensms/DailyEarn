
package com.metacoders.dailyearn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.dailyearn.R;
import com.metacoders.dailyearn.models.modelForAdminPaymentDevice;
import com.metacoders.dailyearn.models.modelForHIstory;
import com.metacoders.dailyearn.models.modelForTransactionDb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class paymentSelectActivity extends AppCompatActivity {
    String balance, method, fromAcc;
    ImageView pmMoney, netTeller, skrill, localBank, bitcoin, coinBase, bkash, rocket, nagad, paytm;
    String msg = "" , uid;
    Intent y;
    Button proceedBtn;
    String Flag = null;
    String perfectMoney  , packageName, netTellers , skrills , localBanks , bitCoins , coinBases , bkashs, rockets , nagads, paytms ;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_system_select);
        uid = FirebaseAuth.getInstance().getUid() ;


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



        y = getIntent();
        method = y.getStringExtra("method");
        balance = y.getStringExtra("amount");
        fromAcc = y.getStringExtra("fromAccount");
        // setting the  button  .
        bitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerDialogue("Bitcoin", "no" , bitCoins);
            }
        });

        localBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                triggerDialogue("Local Bank", "yes" , localBanks);

            }
        });

        skrill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerDialogue("skrill", "no" , skrills);
            }
        });

        netTeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerDialogue("Net-teller", "no" , netTellers);
            }
        });

        pmMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                triggerDialogue("Perfect Money", "no", perfectMoney);

            }
        });

        coinBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                triggerDialogue("Coin Base"  , "no" , coinBases);

            }
        });

        bkash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                triggerDialogue("Bkash","yes", bkashs);

            }
        });

        rocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                triggerDialogue("Rocket","yes" , rockets);

            }
        });

        nagad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                triggerDialogue("Nagad","yes" , nagads);

            }
        });

        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                triggerDialogue("Paytm" , "yes" , paytms);

            }
        });




        switch (method) {
            case "withdraw":
                Flag = "withdrawDB";

                break;
            case "mutual":
                packageName = y.getStringExtra("name");
                Flag = "mutualDB";
                showToast(Flag);
                break;
            case "deposit":

                Flag = "depositDB";

                break;
        }


      //  showToast(Flag);

    }

    private void triggerDialogue(final String text , String mobile , final String dep) {
        dialog = new Dialog(paymentSelectActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.deposit_dialogue);
        Button submitBtn = dialog.findViewById(R.id.submitBtn);
        Button nextBtn = dialog.findViewById(R.id.nextBtn);
        Button cancelBtn = dialog.findViewById(R.id.cancelBtn);
        TextView adminMail = dialog.findViewById(R.id.admin_email) ;
        LinearLayout depLayout = dialog.findViewById(R.id.depositLayout) ;
        LinearLayout withdrawLayout = dialog.findViewById(R.id.withdrawLayout) ;
        TextInputEditText bal = dialog.findViewById(R.id.amountIn);
        final TextInputEditText mail = dialog.findViewById(R.id.mailIn);
        TextView header = dialog.findViewById(R.id.headerText);
        adminMail.setText(dep) ;

        if (method.equals("withdraw")) {

                withdrawLayout.setVisibility(View.VISIBLE);
                depLayout.setVisibility(View.GONE);


        }

        else {

            withdrawLayout.setVisibility(View.GONE);
            depLayout.setVisibility(View.VISIBLE);

        }

        if(mobile.equals("yes"))
        {

            mail.setInputType(InputType.TYPE_CLASS_PHONE);
        }
        else {
                mail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        }

        header.setText("Please Enter Your " + text + " Email Adress/Phone Number .  ");
        bal.setText(balance);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent o = new Intent(getApplicationContext() , depositActivity.class);
                o.putExtra("method" , dep) ;
                o.putExtra("type" , method) ;
                o.putExtra("amount" , balance) ;
                o.putExtra("name" , packageName) ;
                startActivity(o);


            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Mail = mail.getText().toString();
                if (!TextUtils.isEmpty(Mail)) {





                        final String DATE = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        final DatabaseReference mref = FirebaseDatabase.getInstance().getReference();
                        // upload the  transfer req to the preferred db
                        final String key = mref.push().getKey();


                        //TODO NEED UID HERE
                        modelForTransactionDb modelForTransactionDb = new modelForTransactionDb(key, balance, uid, fromAcc, DATE, text ,"Pending" );
                     //withdraw
                        mref.child(Flag).child(key).setValue(modelForTransactionDb).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                showToast(Flag);

                                //
                                // TODO uid here
                                DatabaseReference st = FirebaseDatabase.getInstance().getReference("profile").child(uid).child("transHistory");

                                //  String reason, String status, String date, String amount

                                modelForHIstory modelForHIstoryf = new modelForHIstory(key, key, "You Requested  " + balance + "  Your Account", "Pending", DATE, balance);

                                st.child(key).setValue(modelForHIstoryf).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        dialog.dismiss();



                                        showToast("DONE!!");
                                        finish();
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
    private  void readFromDb() {


        DatabaseReference mreg  = FirebaseDatabase.getInstance().getReference("adminPaymentDetails").child("adress");
        mreg.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelForAdminPaymentDevice model = dataSnapshot.getValue(modelForAdminPaymentDevice.class) ;
                    perfectMoney = model.getPerfectMoney() ;
                    netTellers = model.getNetTeller();
                    skrills= model.getSkrill();
                    localBanks = model.getLocalBank() ;
                    bitCoins  = model.getBitCoin() ;
                    coinBases = model.getCoinBase() ;
                    bkashs = model.getBkash();
                    rockets = model.getRocket() ;
                    nagads = model.getNagad();
                    paytms = model.getPaytm() ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        readFromDb();
    }
}
