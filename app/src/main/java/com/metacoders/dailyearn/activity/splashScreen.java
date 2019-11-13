/*
 * Copyright (c) $today.year.This Code is written by Gamkiller .
 */

package com.metacoders.dailyearn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.dailyearn.R;
import com.metacoders.dailyearn.models.modelForlevelDb;

public class splashScreen extends AppCompatActivity {
    String l1 ,l2 ,l3,l4,l5,l6,l7, l8,l9 , l10 , l11, l12 ,l13 ,l14 ,l15 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
      //  createLvl();

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
}
