package com.metacoders.dailyearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.metacoders.dailyearn.models.modelForBalDb;

public class withdrawActivity extends AppCompatActivity {

    Spinner accspinner;
    EditText passIn, balin, balTv;
    Button sumbitbtn;
    String pass , enteredPass;
    String uid = "MUIdCk609CZBr4ZZTd8Mc9kpzDJ2";
    String affBal, earnBal, equityBal, globalProfitBal, joiningBal, mutualBal, rewardBal;
    String fromDB;
    final String[] fromac = new String[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdraw_dialogue);

        accspinner = findViewById(R.id.accountSPinner);
        passIn = findViewById(R.id.passin);
        balin = findViewById(R.id.balin);
        balTv = findViewById(R.id.balTv);
        sumbitbtn = findViewById(R.id.confirmBTn);



        dashboardFragment dashboardfragment = new dashboardFragment();
        pass = dashboardFragment.getPassWord();
        String[] data = {"Reward Bonus", "Global Profit Share", "Earn Bonus", "Mutual Bonus", "Afflicted Bonus", "Joining Bonus", "Equity Balance"};
        final ArrayAdapter<String> fromadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, data);
        accspinner.setAdapter(fromadapter);
        fromadapter.notifyDataSetChanged();

        accspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                fromac[0] = adapterView.getSelectedItem().toString()     ;
                if(fromac[0].contains("Reward Bonus"))
                {
                    fromDB = "reward_Bonus" ;
                    balTv.setText(rewardBal);

                }
                else if (fromac[0].contains("Equity Balance"))
                {
                    fromDB ="equity_balance";
                    balTv.setText(equityBal);
                }
                else  if(fromac[0].contains("Global Profit Share"))
                {
                    fromDB = "global_profit_share" ;
                    balTv.setText(globalProfitBal);

                }
                else  if(fromac[0].contains("Earn Bonus")) {

                    fromDB = "earn_Bonus" ;
                    balTv.setText(earnBal);
                }
                else     if(fromac[0].contains("Mutual Bonus"))
                {

                    fromDB = "mutual_Bonus" ;
                    balTv.setText(mutualBal);
                }
                else  if(fromac[0].contains("Afflicted Bonus"))
                {

                    fromDB = "afflicted_Bonus" ;
                    balTv.setText(affBal);
                }
                else  if(fromac[0].contains("Joining Bonus"))
                {
                    fromDB = "joining_Bonus" ;
                    balTv.setText(joiningBal);

                }

                //showToast("selected : "+ fromac[0] + " ToDB"+ fromDB);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        sumbitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredPass  = passIn.getText().toString() ;
                if(pass.equals(enteredPass))
                {
                    if( Double.parseDouble(balTv.getText().toString() ) >=Double.parseDouble(balin.getText().toString()) && Double.parseDouble(balin.getText().toString()) >=5)
                    {
                        // showToast("all ok ");

                        double bal = Double.parseDouble(balin.getText().toString()) ;

                        convertDeductedBal(fromDB , "null" ,bal  ) ;

                    }
                    else showToast("not Enough Balance ");

                    //

                }

                else showToast("wrong password !!!"+ enteredPass) ;

            }


        });



    }

    private void get_Bal (){

        DatabaseReference pref = FirebaseDatabase.getInstance().getReference("profile").child(uid).child(constants.baldb);


        pref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                modelForBalDb model = dataSnapshot.getValue(modelForBalDb.class);


                affBal = model.getAfflicted_Bonus();
                earnBal = model.getEarn_Bonus();
                equityBal = model.getEquity_balance();
                globalProfitBal = model.getGlobal_profit_share();
                joiningBal = model.getJoining_Bonus();
                mutualBal = model.getMutual_Bonus();
                rewardBal = model.getReward_Bonus();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void showToast (String s ){

        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT)
                .show();
    }

    private void convertDeductedBal(final String fromDB, final String toDB, final double deducbal) {

        //  fromDB = "mutual_Bonus" ;
        final DatabaseReference mref =  FirebaseDatabase.getInstance().getReference("profile").child(uid).child("balanceDb").child(fromDB);

        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //   modelForBalDb model = dataSnapshot.getValue(modelForBalDb.class) ;
                double oldValue = Double.parseDouble(dataSnapshot.getValue().toString());

                if (oldValue < deducbal)
                {
                    showToast("Not Enough Fund");
                }
                else {

                    double newValue = oldValue- deducbal ;
                    Intent i = new Intent(getApplicationContext() , paymentSelectActivity.class);
                    i.putExtra("amount", String.valueOf(deducbal));
                    i.putExtra("method" , "withdraw") ;
                    i.putExtra("fromAccount" , fromDB) ;
                    startActivity(i);

                    /*
                    mref.setValue(String.valueOf(newValue)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            // now deduct the value fom the dame account

                        //   showToast("deducted MOney ");


                            Intent i = new Intent(getApplicationContext() , paymentSelectActivity.class);
                            i.putExtra("amount", String.valueOf(deducbal));
                            i.putExtra("method" , "withdraw") ;
                            i.putExtra("fromAccount" , fromDB) ;
                            startActivity(i);

                            // send the money



                        }
                    }) ;
                    */


                }








            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();
        get_Bal();

    }
}

