package com.metacoders.dailyearn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.dailyearn.R;
import com.metacoders.dailyearn.constants;
import com.metacoders.dailyearn.fragments.dashboardFragment;
import com.metacoders.dailyearn.models.modelForAd;
import com.metacoders.dailyearn.models.modelForBalDb;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class dailyActivity extends AppCompatActivity {



    Button dailyEarnBtn ;
    String activeDate , todayDate  ,  uid = "MUIdCk609CZBr4ZZTd8Mc9kpzDJ2";
    int day ;
    Dialog dialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily2);



        todayDate = getTodayDate();

        dashboardFragment dashboardFragment = new dashboardFragment() ;
        activeDate= dashboardFragment.sendActiveDate() ;




        dailyEarnBtn = findViewById(R.id.DailyEarnBtn);



        dailyEarnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // create a  dialog
                dialog = new Dialog(dailyActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.daily_ad_show_dialogue);


                final ImageView adView =dialog.findViewById(R.id.image) ;
                ImageButton likeBtn = dialog.findViewById(R.id.likeBtn) ;


                DatabaseReference mref = FirebaseDatabase.getInstance().getReference("addDb").child("dailyAdd").child("image");
                mref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        Picasso.get().load(String.valueOf(dataSnapshot.getValue())).into(adView) ;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                likeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(activeDate.equals("NotActivate")) {

                            showToast("Your ID is Not Activated");

                        }

                        else {
                            day = calculateDayCount() ;

                            //  showToast(day+ " ");
                            if ( day == 0)
                            {


                            }
                            else {
                                // 1st check he is all ready done it or not

                                checkIsHeDoneAlready() ;


                            }



                        }



                    }
                });




            dialog.show();



            }
        });
    }



    private int calculateDayCount() {
        //Setting dates
        String dayDifference = "0.00" ;
        try {
            //Dates to compare
            String CurrentDate=  activeDate;
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

 private  void showToast(String txt )
 {
    Toast.makeText(getApplicationContext() , txt , Toast.LENGTH_SHORT )
            .show();
 }

    private  void checkIsHeDoneAlready() {
        DatabaseReference userNameRef = FirebaseDatabase.getInstance().getReference("profile").child(uid).child("dailyEarnDateDb").child(getTodayDate());

        // checking id the username exist or not
        userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {


                    showToast("You All ready DOne IT ");
                    dialog.dismiss();

                }
                else {

                    // go with the transaction
                    showToast("GO with it  ");
                    readBalance() ;

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void readBalance() {

        DatabaseReference userNameRef = FirebaseDatabase.getInstance().getReference("profile").child(uid).child(constants.baldb);




        // downlaod the data
        userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelForBalDb model  = dataSnapshot.getValue(modelForBalDb.class) ;
                final double oldVal  ;
                oldVal = Double.valueOf(model.getEarn_Bonus()) ;

                writeBalacne(oldVal);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void writeBalacne(double oldVal) {

        DatabaseReference userNameRef = FirebaseDatabase.getInstance().getReference("profile").child(uid).child(constants.baldb).child("earn_Bonus");
                // calculate  value

                     double   newValue = oldVal +23 ;



        userNameRef.setValue(String.valueOf(newValue)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                writeToday() ;



            }
        }) ;


    }

    private void writeToday() {

        DatabaseReference userNameRef = FirebaseDatabase.getInstance().getReference("profile").child(uid).child("dailyEarnDateDb");

        HashMap map = new HashMap();
        map.put("date" , todayDate) ;


        userNameRef.child(todayDate).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                showToast("Done With The Transaction");
                dialog.dismiss();
            }
        }) ;



    }

}
