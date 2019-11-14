/*
 * Copyright (c) $today.year.This Code is written by Gamkiller .
 */

package com.metacoders.dailyearn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.dailyearn.LoginActivity;
import com.metacoders.dailyearn.R;
import com.metacoders.dailyearn.constants;
import com.metacoders.dailyearn.models.modelForDwldMutualState;
import com.metacoders.dailyearn.models.modelForlevelDb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class splashScreen extends AppCompatActivity {
    String l1 ,l2 ,l3,l4,l5,l6,l7, l8,l9 , l10 , l11, l12 ,l13 ,l14 ,l15 ;
    String uid ;
    String name , date  , todayDate , royalDate;
    double price ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
      //  createLvl();

        try {
            FirebaseAuth mauth = FirebaseAuth.getInstance();
            uid = mauth.getUid().toString() ;
            isMutualAvaiable();

        }
        catch (Exception e)
        {



        //    uid = "MUIdCk609CZBr4ZZTd8Mc9kpzDJ2" ;

            isMutualAvaiable();
        }

    }

    public  void readLvl() {
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("lvlDb");

        mref.child("percent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                modelForlevelDb model = dataSnapshot.getValue(modelForlevelDb.class) ;

                l1 = model.getL1() ;
                l2 = model.getL2();
                l3 = model.getL3() ;
                l4 = model.getL4();
                l5 = model.getL5() ;
                l6 = model.getL6() ;
                l7 = model.getL7() ;
                l8 = model.getL8() ;
                l9 = model.getL9();
                l10 = model.getL10();
                l11 = model.getL11() ;
                l12 = model.getL12() ;
                l13 = model.getL13() ;
                l14 = model.getL14() ;
                l15 = model.getL15() ;




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }
    public  void  write() {

        final DatabaseReference mref = FirebaseDatabase.getInstance().getReference("profile").child(uid).child("myMutualList");


        modelForDwldMutualState modelForDwldMutualState = new modelForDwldMutualState("normal" , "15/11/2019" , "normal" , "12") ;
        mref.setValue(modelForDwldMutualState) ;


    }
    public  void isMutualAvaiable(){


        final DatabaseReference mref = FirebaseDatabase.getInstance().getReference("profile").child(uid).child("myMutualList");


        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    mref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            modelForDwldMutualState model = dataSnapshot.getValue(modelForDwldMutualState.class) ;
                            name = model.getRunningName();
                            date = model.getPurchangeDate();
                            royalDate = model.getRunningMutualFund()  ;
                            price = Double.parseDouble(model.getPrice());

                            if(name.equals("normal"))
                            {
                                checkAsNormal() ;
                            }
                            else {
                                checkAsRoyal() ;

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
                else {

                  sentToNext();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

    private void checkAsRoyal() {

        // check 360 days is done
        int diifInday = calculateDayCount();

        if(diifInday == 360)
        {

            // cheeck price is null or what
            if (price == 0)
            {
                // price is all-ready added

                sentToNext();

            }
            else {
                // do 60%
                price = price+(price * 0.60);
                final DatabaseReference mref = FirebaseDatabase.getInstance().getReference("profile").child(uid).child(constants.baldb);
                mref.child("mutual_Bonus").setValue(String.valueOf(price)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        final DatabaseReference mreff = FirebaseDatabase.getInstance().getReference("profile").child(uid).child("myMutualList").child("price");


                        mreff.setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                sentToNext();
                            }
                        }) ;

                    }
                });

            }

        }
        else {

           // do 30 days add 10% ;
       // now calculate the 30 days interval ;

            date = royalDate ;
            int diffdays  = calculateDayCount()  ;


            if(diffdays >=30)
            {
                price = price+(price * 0.10);
                final DatabaseReference mref = FirebaseDatabase.getInstance().getReference("profile").child(uid).child(constants.baldb).child("mutual_Bonus");

                mref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        price = price+ Double.parseDouble(dataSnapshot.getValue().toString()) ;
                        mref.setValue(String.valueOf(price)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                final DatabaseReference mreff = FirebaseDatabase.getInstance().getReference("profile").child(uid).child("myMutualList").child("runningMutualFund");


                                mreff.setValue(getTodayDate()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        sentToNext();
                                    }
                                }) ;

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




            }
            else {
                sentToNext();

            }






        }


    }

    private void checkAsNormal() {
        int diifInday = calculateDayCount();

        if(diifInday==180)
        {
            // cheeck price is null or what
            if (price == 0)
            {
                sentToNext();

            }
            else {

                // write the db
                price = price/2 ;
     final DatabaseReference mref = FirebaseDatabase.getInstance().getReference("profile").child(uid).child(constants.baldb).child("mutual_Bonus");

     mref.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

             price = price+ Double.valueOf(dataSnapshot.getValue().toString());

             mref.setValue(String.valueOf(price)).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     final DatabaseReference mreff = FirebaseDatabase.getInstance().getReference("profile").child(uid).child("myMutualList").child("price");


                     mreff.setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             sentToNext();
                         }
                     }) ;

                 }
             });

         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });





            }



        }
        else {

            sentToNext();
        }


    }

    public  void sentToNext()
    { Intent i = new Intent(getApplicationContext() , LoginActivity.class);
        startActivity(i);

    }
    private  void showToast(String s ) {

        Toast.makeText(getApplicationContext() , s , Toast.LENGTH_SHORT)
                .show();
    }
    private int calculateDayCount() {
        //Setting dates
        String dayDifference = "0.00" ;
        try {
            //Dates to compare
            String CurrentDate=  date;
            String FinalDate=  todayDate;

            Date date1;
            Date date2;

            SimpleDateFormat dates = new SimpleDateFormat("dd-mm-yyyy");

            //Setting dates
            date1 = dates.parse(CurrentDate);
            date2 = dates.parse(FinalDate);

            //Comparing dates
            long difference = Math.abs(date1.getTime() - date2.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);

            //Convert long to String
            dayDifference = Long.toString(differenceDates);

            Log.e("HERE","HERE: " + dayDifference);
        }

        catch (Exception exception) {
            Log.e("DIDN'T WORK", "exception " + exception);
            dayDifference = "0.00" ;
        }



        return  Integer.parseInt(dayDifference) ;
    }


    private   String getTodayDate()
    {
        String   DATE = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        return  DATE ;


    }

    @Override
    protected void onStart() {
        super.onStart();
        todayDate = getTodayDate() ;


    }
}
