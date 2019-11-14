package com.metacoders.dailyearn.fragments;

import android.app.Dialog;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.dailyearn.R;
import com.metacoders.dailyearn.constants;
import com.metacoders.dailyearn.models.modelForBalDb;
import com.metacoders.dailyearn.models.modelForProfile;


public class balanceFragment extends Fragment {

    View view;
    TextView   affTv , earnTv ,
            equityTv , globalProfitTv ,
            joiningTv , mutualTv , rewardTv
            , affIdTv , nameTv  , withdrawTv  , converTv;
    String uid ;

    String  Password  ;
    double  affBal  , earnBal , equityBal  , globalProfitBal , joiningBal , mutualBal , rewardBal ;
    String fromDB  , toDB   = null ;



    public balanceFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


       // uid = FirebaseAuth.getInstance().getUid() ;
        uid = FirebaseAuth.getInstance().getUid();


        view = inflater.inflate(R.layout.balance_fragment, container, false);
        affTv = view.findViewById(R.id.afftotalBal) ;
        earnTv = view.findViewById(R.id.earntotalBal) ;
        equityTv = view.findViewById(R.id.equityBal) ;
        globalProfitTv = view.findViewById(R.id.globalProfiteShareTv) ;
        joiningTv = view.findViewById(R.id.joinBalTotal) ;
        mutualTv = view.findViewById(R.id.mutualtotalbal) ;
        rewardTv = view.findViewById(R.id.rewardTotalBal) ;
        nameTv = view.findViewById(R.id.nameTv) ;
        withdrawTv = view.findViewById(R.id.withDraw)  ;
        converTv = view.findViewById(R.id.convertBal) ;

        affIdTv  = view.findViewById(R.id.affIDTv) ;


        converTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                triggerDialogue(affBal  , earnBal , equityBal  , globalProfitBal , joiningBal , mutualBal , rewardBal) ;
            }
        });

        downloadProfileData() ;

            get_Bal("");

       // convertDeductedBal(fromDB,  toDB, 200) ;

        return view ;
    }

    private void triggerDialogue(double affBal, double earnBal, double equityBal, double globalProfitBal, double joiningBal, double mutualBal, double rewardBal) {


        final Dialog convertBal = new Dialog(getContext());
        final String[] fromac = new String[1];
        final String[] toac = new String[1];

        convertBal.requestWindowFeature(Window.FEATURE_NO_TITLE);
        convertBal.setContentView(R.layout.convert_balance_dialogue);
        final Spinner fromAccount , toAccount ;
        fromAccount = convertBal.findViewById(R.id.fromacc) ;
        toAccount = convertBal.findViewById(R.id.toacc) ;
        final EditText password , balance ;
        Button cancel , submit ;
        password = convertBal.findViewById(R.id.password) ;
        balance = convertBal.findViewById(R.id.amount) ;
        cancel = convertBal.findViewById(R.id.cancelBtn) ;
        submit =  convertBal.findViewById(R.id.confirmBtn) ;

        // load data into the spinner
        String[] dataTO= {"Earn Bonus" , "Equity Balance" };
        String[] data= {"Reward Bonus" , "Global Profit Share","Earn Bonus" ,"Mutual Bonus" ,"Afflicted Bonus" , "Joining Bonus"   };
        final ArrayAdapter<String> fromadapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,data);
        final ArrayAdapter<String> toadapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,dataTO);

        fromAccount .setAdapter(fromadapter);
        fromadapter.notifyDataSetChanged();


        toAccount .setAdapter(toadapter);
        toadapter.notifyDataSetChanged();


        fromAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                fromac[0] = adapterView.getSelectedItem().toString()     ;
                if(fromac[0].contains("Reward Bonus"))
                {
                    fromDB = "reward_Bonus" ;

                }
                else if (toac[0].contains("Equity Balance"))
                {
                    toDB ="equity_balance";
                }
            else  if(fromac[0].contains("Global Profit Share"))
                {
                    fromDB = "global_profit_share" ;


                }
            else  if(fromac[0].contains("Earn Bonus")) {

                    fromDB = "earn_Bonus" ;
                }
            else     if(fromac[0].contains("Mutual Bonus"))
                {

                    fromDB = "mutual_Bonus" ;
                }
            else  if(fromac[0].contains("Afflicted Bonus"))
                {

                    fromDB = "afflicted_Bonus" ;
                }
            else  if(fromac[0].contains("Joining Bonus"))
                {
                    fromDB = "joining_Bonus" ;

                }

               //showToast("selected : "+ fromac[0] + " ToDB"+ fromDB);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        toAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                toac[0] = toAccount.getSelectedItem().toString()  ;


                if(toac[0].contains("Reward Bonus"))
                {
                    toDB = "reward_Bonus" ;

                }
                else  if(toac[0].contains("Global Profit Share"))
                {
                    toDB = "global_profit_share" ;


                }
                else  if(toac[0].contains("Earn Bonus")) {

                    toDB = "earn_Bonus" ;
                }
                else if (toac[0].contains("Equity Balance"))
                {
                    toDB ="equity_balance";
                }
                else     if(toac[0].contains("Mutual Bonus"))
                {

                    toDB = "mutual_Bonus" ;
                }
                else  if(toac[0].contains("Afflicted Bonus"))
                {

                    toDB = "afflicted_Bonus" ;
                }
                else  if(toac[0].contains("Joining Bonus"))
                {
                    toDB = "joining_Bonus" ;

                }


          //      showToast("selected : "+ toac[0] + " ToDB"+ toDB);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertBal.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String val = balance.getText().toString() ;


                String  newPass = password.getText().toString();


                if (val.isEmpty()){

                    showToast("Enter balance ");
                }
           else  if(newPass.equals(Password))
                {

                    if(!fromac[0].equals(toac[0]))
                    {
                        double  deducbal = Double.valueOf(val);

                        convertDeductedBal(fromDB , toDB , deducbal ) ;
                        convertBal.dismiss();
                    }
                    else {

                        showToast("The account can not be same");

                    }

                }


                else  showToast("Wrong Password !!");

            }
        });


convertBal.show();


    }

    private void convertDeductedBal(final String fromDB, final String toDB, final double deducbal) {
        //TODO plz update uid from test
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

                    mref.setValue(String.valueOf(newValue)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            // now deduct the value fom the dame account

                            convertAddedBalToAc(fromDB , toDB , deducbal) ;


                        }
                    }) ;

                }








            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    private void convertAddedBalToAc(String fromDB, String toDB, final double deducbal) {

        final DatabaseReference mref =  FirebaseDatabase.getInstance().getReference("profile").child(uid).child("balanceDb").child(toDB);

        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //   modelForBalDb model = dataSnapshot.getValue(modelForBalDb.class) ;
                double oldValue = Double.parseDouble(dataSnapshot.getValue().toString());



                    double newValue = oldValue+ deducbal ;

                    mref.setValue(String.valueOf(newValue)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            // done

                          showToast("Conversion Complete !! ");




                        }
                    }) ;

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void downloadProfileData() {
        DatabaseReference pref = FirebaseDatabase.getInstance().getReference("profile").child(uid);


        pref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                modelForProfile model = dataSnapshot.getValue(modelForProfile.class) ;


                affIdTv.setText(model.getMy_AffiliationId());
                nameTv.setText(model.getName());
                Password = model.getPassword() ;




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }



    private  void get_Bal(String baal) {

        DatabaseReference pref = FirebaseDatabase.getInstance().getReference("profile").child(uid).child(constants.baldb);


            pref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    modelForBalDb  model  = dataSnapshot.getValue(modelForBalDb.class) ;

                    affTv.setText(model.getAfflicted_Bonus());
                    earnTv.setText(model.getEarn_Bonus());
                    equityTv.setText(model.getEquity_balance());
                    globalProfitTv.setText(model.getGlobal_profit_share());
                    joiningTv.setText(model.getJoining_Bonus());
                    mutualTv.setText(model.getMutual_Bonus());
                    rewardTv.setText(model.getReward_Bonus());

                    affBal  = Double.parseDouble(model.getAfflicted_Bonus()) ;
                    earnBal = Double.parseDouble(model.getEarn_Bonus());
                    equityBal  = Double.parseDouble(model.getEquity_balance()) ;
                    globalProfitBal = Double.parseDouble(model.getGlobal_profit_share());
                    joiningBal  = Double.parseDouble(model.getJoining_Bonus());
                    mutualBal  = Double.parseDouble(model.getMutual_Bonus());
                    rewardBal = Double.parseDouble(model.getReward_Bonus());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



    }

    private  void showToast(String s ) {

        Toast.makeText(getContext() , s , Toast.LENGTH_SHORT)
                .show();
    }

}
