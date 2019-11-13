package com.metacoders.dailyearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.metacoders.dailyearn.Database.Database;
import com.metacoders.dailyearn.models.Order;
import com.metacoders.dailyearn.models.modelForBalance;
import com.metacoders.dailyearn.models.modelForHIstory;
import com.metacoders.dailyearn.models.modelForProductPurchageDb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class cart extends AppCompatActivity {
public TextView totalPriceTv ;
public Button  checckoutBtn ;
    RecyclerView.LayoutManager layoutManager;
    CartAdapter adapter;
    RecyclerView recyclerView;
    List<Order> cart = new ArrayList<>();
    int totlal ;
    String json ;
    ProgressBar pbar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        checckoutBtn = findViewById(R.id.chekOutBtn);
        recyclerView = findViewById(R.id.listOfFood) ;
        totalPriceTv = findViewById(R.id.totalPriceOnCart) ;
        pbar = findViewById(R.id.pbar);

        pbar.setVisibility(View.GONE);
        cart = new Database(this).getcarts();
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        checckoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pbar.setVisibility(View.VISIBLE);

                checkEnoughFundAvailable();






            }
        });



        loadListFood();

    }

    void   checkEnoughFundAvailable(){
        String uid = FirebaseAuth.getInstance().getUid();
        // 1st add the fund to the gifted
        final DatabaseReference mref =  FirebaseDatabase.getInstance().getReference("profile").child("MUIdCk609CZBr4ZZTd8Mc9kpzDJ2").child("balanceDb");

        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                modelForBalance model = dataSnapshot.getValue(modelForBalance.class) ;

                // increse balance ;

                double newBal =  Double.parseDouble(model.getPurchase_balance()) ;

                if( newBal > Double.parseDouble(totalPriceTv.getText().toString()))
                {
                    deductTheFund();

                }
                else
                {

                    new AwesomeErrorDialog(cart.this)
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
                                   new AwesomeErrorDialog(cart.this).hide() ;
                                   pbar.setVisibility(View.GONE);
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

    private void loadListFood() {
        cart = new Database(this).getcarts();
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);


/// calculate total price

        totlal = 0;
        for (Order order : cart)
            totlal += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        //  Locale locale = new Locale("en", "BD");
        //  NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        totalPriceTv.setText(totlal+"");
       // FinalTextPriceFloatBar.setText(totlal + "");


    }
    private void deductTheFund() {


        Gson gson = new GsonBuilder().setPrettyPrinting().create();

         json = gson.toJson(cart); // all the order is in  This  String
        //TODO change uid
        String uid = FirebaseAuth.getInstance().getUid();
        final DatabaseReference mref =  FirebaseDatabase.getInstance().getReference("profile").child("MUIdCk609CZBr4ZZTd8Mc9kpzDJ2").child("balanceDb");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                modelForBalance model = dataSnapshot.getValue(modelForBalance.class) ;

                // increse balance ;

               double newBal =  Double.parseDouble(model.getPurchase_balance()) ;

                newBal =  newBal -  Double.parseDouble(totalPriceTv.getText().toString());

                mref.child("purchase_balance").setValue(String.valueOf(newBal)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        DatabaseReference  st = FirebaseDatabase.getInstance().getReference("profile").child("MUIdCk609CZBr4ZZTd8Mc9kpzDJ2").child("transHistory");

                        final String key  = st.push().getKey() ;
                        //  String reason, String status, String date, String amount
                        final String   DATE = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        final modelForHIstory modelForHIstory = new modelForHIstory(key  , key , "You Bought  "+ totalPriceTv.getText().toString() + "Usd Of Product",   "Completed",DATE ,totalPriceTv.getText().toString()) ;

                        st.child(key).setValue(modelForHIstory).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                              // String id , amount , uid  , productList, date   ;
                                DatabaseReference  st = FirebaseDatabase.getInstance().getReference("productPurchasedList").child(key);
                                modelForProductPurchageDb  model = new modelForProductPurchageDb(key ,  totalPriceTv.getText().toString(),"MUIdCk609CZBr4ZZTd8Mc9kpzDJ2" ,json , DATE ) ;

                                st.setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        new AwesomeSuccessDialog(cart.this)
                                                .setTitle("Purchase Was Successful!!")
                                                .setMessage("You Will Soon Receive your Product ")
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
