package com.metacoders.dailyearn.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.metacoders.dailyearn.models.modelForBalance;
import com.metacoders.dailyearn.models.modelForHIstory;
import com.metacoders.dailyearn.models.modelForProfile;
import com.metacoders.dailyearn.models.modelForUid;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FundTransferActivity extends AppCompatActivity {

    TextInputEditText mynamein, myuserIDin, touserIDin, amuntin, passin;
    String myname, myuser, touserId, amount, pass , checkPass;
    Button conbtn;
    String sentID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer);
        mynamein = findViewById(R.id.myusername);
        myuserIDin = findViewById(R.id.myuserID);
        touserIDin = findViewById(R.id.touserID);
        amuntin = findViewById(R.id.amount);
        passin = findViewById(R.id.mypassWord);
        conbtn = findViewById(R.id.confirmBtn);

        downloadProfileData();
        conbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                touserId = touserIDin.getText().toString();
                amount = amuntin.getText().toString();
                pass = passin.getText().toString();


                if(pass.equals(checkPass))
                {
                    // 1st of all check the balance exist or not ;
                    checkEnoughFundAvailable();
                }
                else {

                    showToast("Password Don't Match ");
                }



            }
        });


    }

    private void checkingusername(String username) {
        DatabaseReference userNameRef = FirebaseDatabase.getInstance().getReference("userNameDb").child(username);

        // checking id the username exist or not
        userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    modelForUid  model = dataSnapshot.getValue(modelForUid.class) ;



                    // carry on with the transfer
                    sentID = model.getUid() ;
                     transferFund(  model.getUid() );



                } else {
                    showToast(" Wrong Transfer UserID");


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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

                double newBal =  Double.parseDouble(model.getMutual_Bonus()) ;

                if( newBal > Double.parseDouble(amount))
                {
                   checkingusername(touserId);

                }
                else
                {

                    showToast("NOT Enough Balance !! ");
                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void transferFund(String uid) {

        // 1st add the fund to the gifted

        final DatabaseReference mref =  FirebaseDatabase.getInstance().getReference("profile").child(uid).child("balanceDb");

        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                modelForBalance model = dataSnapshot.getValue(modelForBalance.class) ;

                // increse balance ;

                double newBal =  Double.parseDouble(model.getMutual_Bonus()) ;

                newBal = newBal + Double.parseDouble(amount) ;

                mref.child("mutual_Bonus").setValue(newBal).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        deductTheFund() ;

                    }
                }) ;



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

                Double newBal =  Double.parseDouble(model.getMutual_Bonus()) ;

                newBal =  newBal -  Double.parseDouble(amount);

                mref.child("mutual_Bonus").setValue(String.valueOf(newBal)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        DatabaseReference  st = FirebaseDatabase.getInstance().getReference("profile").child("MUIdCk609CZBr4ZZTd8Mc9kpzDJ2").child("transHistory");

                        final String key  = st.push().getKey() ;
                      //  String reason, String status, String date, String amount
                        String   DATE = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        final modelForHIstory modelForHIstory = new modelForHIstory(key  , key , "You Sent "+ amount + "To " + touserId,   "Completed",DATE ,amount) ;

                        st.child(key).setValue(modelForHIstory).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                DatabaseReference  st = FirebaseDatabase.getInstance().getReference("profile").child(sentID).child("transHistory");

                                //  String reason, String status, String date, String amount
                                String   DATE = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                modelForHIstory modelForHIstoryf = new modelForHIstory(key  , key , "You Received  "+ amount + "From " + myuserIDin.getText() , "Completed",DATE ,amount) ;

                                st.child(key).setValue(modelForHIstoryf).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        showToast("DONE!!");
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

  public   void  showToast(String msg) {
        Toast.makeText(getApplicationContext() , msg, Toast.LENGTH_SHORT)
                .show();


    }
    public  void downloadProfileData() {
        DatabaseReference  pref = FirebaseDatabase.getInstance().getReference("profile").child("MUIdCk609CZBr4ZZTd8Mc9kpzDJ2");


        pref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                modelForProfile  model = dataSnapshot.getValue(modelForProfile.class) ;


                mynamein.setText(model.getUsername());
                myuserIDin.setText(model.getUsername());
                checkPass = model.getPassword() ;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }



}
