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
import android.widget.ImageView;
import android.widget.TextView;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.metacoders.dailyearn.R;
import com.metacoders.dailyearn.cart;
import com.metacoders.dailyearn.fragments.dashboardFragment;
import com.metacoders.dailyearn.models.modelForBalance;
import com.metacoders.dailyearn.models.modelForHIstory;
import com.metacoders.dailyearn.models.modelForProductPurchageDb;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PackageDetailsActivity extends AppCompatActivity {
    Button addToCartBtn , buyNowBtn  ;
    TextView title , details , price  , adress  , maill  ;
    ImageView productImage , cartImage ;

    String productName , productDetails , productPrice , ProductImageLink ,Type  , flag , id , balDb , adrees , mail  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);
        productImage = findViewById(R.id.productImage);
        title = findViewById(R.id.productName) ;
        price = findViewById(R.id.productPrice) ;
        details = findViewById(R.id.porductDetails) ;
        addToCartBtn = findViewById(R.id.addToCartBtn);
        buyNowBtn = findViewById(R.id.buynowBtn);
        adress  =findViewById(R.id.adressss) ;
        maill = findViewById(R.id.contactMail)  ;




        dashboardFragment dashboardfragment = new dashboardFragment();
        adrees = dashboardFragment.getAdress();
        mail = dashboardFragment.getMail() ;


        Intent i = getIntent();
        ProductImageLink = i.getStringExtra("imageLink") ;
        productPrice =  i.getStringExtra("price") ;
        productDetails = i.getStringExtra("details") ;
        productName = i.getStringExtra("name") ;
        Type = i.getStringExtra("type") ;
        id = i.getStringExtra("id") ;
        adress.setText(adrees);
        maill.setText(mail);




        Picasso.get().load(ProductImageLink).into(productImage) ;
        title.setText(productName);
        details.setText(productDetails);
        price.setText(productPrice);

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Type.equals("withProduct")){

                    // balance katbe Puchage  bal
                    flag="withProduct" ;
                    balDb = "purchase_balance" ;

                }
                else  {
                    //with Bonus
                    // katbe  equity balance :
                    flag = "withBonus" ;
                    balDb = "equity_balance" ;

                }
                //check available balance in account
                checkEnoughFundAvailable();

            }
        });


    }
    void   checkEnoughFundAvailable(){
        //TODO UID NEED

        String uid = FirebaseAuth.getInstance().getUid();
        // 1st add the fund to the gifted
        final DatabaseReference mref =  FirebaseDatabase.getInstance().getReference("profile").child("MUIdCk609CZBr4ZZTd8Mc9kpzDJ2").child("balanceDb");

        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                modelForBalance model = dataSnapshot.getValue(modelForBalance.class) ;
                double newBal  ;
                // increse balance ;

                        balDb = "equity_balance" ;
                     newBal =  Double.parseDouble(model.getEquity_balance()) ;





                if( newBal > Double.parseDouble(price.getText().toString()))
                {
                    deductTheFund();

                }
                else
                {

                    new AwesomeErrorDialog(PackageDetailsActivity.this)
                            .setTitle("ERROR!!")
                            .setMessage("Not Enough Fund In your Purchase Balance. Please Deposit money")
                            .setColoredCircle(R.color.dialogErrorBackgroundColor)
                            .setDialogIconAndColor(R.drawable.ic_dialog_error, R.color.white)
                            .setCancelable(true).setButtonText(getString(R.string.dialog_ok_button))
                            .setButtonBackgroundColor(R.color.dialogErrorBackgroundColor)
                            .setButtonText(getString(R.string.dialog_ok_button))
                            .setErrorButtonClick(new Closure() {
                                @Override
                                public void exec() {
                                    // click
                                    new AwesomeErrorDialog(PackageDetailsActivity.this).hide() ;
                                   // pbar.setVisibility(View.GONE);
                                }
                            })
                            .show();
                }
            }







            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void deductTheFund() {

        //TODO change uid
        String uid = FirebaseAuth.getInstance().getUid();
        final DatabaseReference mref =  FirebaseDatabase.getInstance().getReference("profile").child("MUIdCk609CZBr4ZZTd8Mc9kpzDJ2").child("balanceDb");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                modelForBalance model = dataSnapshot.getValue(modelForBalance.class) ;

                // increse balance ;
                double newBal;
                if(  flag.contains( "withBonus" ))
                {
                    balDb = "equity_balance" ;
                     newBal =  Double.parseDouble(model.getEquity_balance()) ;
                }
                else
                {
                    // withProduct
                    // // balance katbe Puchage  bal
                    balDb = "purchase_balance" ;
                    newBal =  Double.parseDouble(model.getPurchase_balance()) ;
                }




                newBal =  newBal -  Double.parseDouble(price.getText().toString());

                mref.child(balDb).setValue(String.valueOf(newBal)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        DatabaseReference  st = FirebaseDatabase.getInstance().getReference("profile").child("MUIdCk609CZBr4ZZTd8Mc9kpzDJ2").child("transHistory");

                        final String key  = st.push().getKey() ;
                        //  String reason, String status, String date, String amount
                        final String   DATE = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        final modelForHIstory modelForHIstory = new modelForHIstory(key  , key , "You Bought  "+ price.getText().toString() + "Usd Of Package id :"+ id,   "Completed",DATE ,price.getText().toString()) ;

                        st.child(key).setValue(modelForHIstory).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // String id , amount , uid  , productList, date   ;
                                DatabaseReference  st = FirebaseDatabase.getInstance().getReference("packagePurchasedList").child(key);
                                final modelForProductPurchageDb model = new modelForProductPurchageDb(key ,  price.getText().toString(),"MUIdCk609CZBr4ZZTd8Mc9kpzDJ2" ,id , DATE ) ;

                                st.setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        DatabaseReference my = FirebaseDatabase.getInstance().getReference("profile").child("MUIdCk609CZBr4ZZTd8Mc9kpzDJ2").child("mypackageList");
                                        my.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                final DatabaseReference my = FirebaseDatabase.getInstance().getReference("profile").child("MUIdCk609CZBr4ZZTd8Mc9kpzDJ2").child("mypackageList");

                                                my.child("Packgetype").setValue(flag).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                         my.child("packageDate").setValue(DATE) ;
                                                        new AwesomeSuccessDialog(PackageDetailsActivity.this)
                                                                .setTitle("Purchase Was Successful!!")
                                                                .setMessage("You Received your Package ")
                                                                .setColoredCircle(R.color.dialogSuccessBackgroundColor)
                                                                .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
                                                                .setCancelable(false)
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
                                                }) ;



                                            }
                                        }) ;



                                    }
                                }) ;




                            }
                        }) ;



                    }
                }) ;



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
