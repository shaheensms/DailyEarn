package com.metacoders.dailyearn.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.dailyearn.R;
import com.metacoders.dailyearn.models.modelForProfile;

public class profileFragment  extends Fragment {

    public profileFragment() {
    }
    String uid  ;
    View view  ;
    TextView nameHeader , nameTv , ownaffId , affliationIdTv , affliationNameTv ,
            mailTv, address1 , adress2 , dobTv , joinTv , activTv , rankTv ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.profile_fragment, container, false);
        uid = FirebaseAuth.getInstance().getUid() ;
        // init views
        nameHeader = view.findViewById(R.id.titleNameTv) ;
        nameTv  = view.findViewById(R.id.titleNameTv2) ;
        ownaffId = view.findViewById(R.id.affIDTv) ;
        affliationIdTv = view.findViewById(R.id.affliationID) ;
        affliationNameTv = view.findViewById(R.id.affnameTv) ;
        mailTv  = view.findViewById(R.id.mailTv) ;
        address1 = view.findViewById(R.id.adressTv) ;
        adress2 = view.findViewById(R.id.adressTv2) ;
        dobTv  = view.findViewById(R.id.dobTV) ;
        joinTv = view.findViewById(R.id.joinedDate) ;
        activTv = view.findViewById(R.id.activatedDate) ;
        rankTv = view.findViewById(R.id.rankTv) ;

downloadProfileData();
        return  view ;

    }
    public  void downloadProfileData() {
        DatabaseReference pref = FirebaseDatabase.getInstance().getReference("profile").child(uid);


        pref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                modelForProfile model = dataSnapshot.getValue(modelForProfile.class) ;


                ownaffId.setText(model.getMy_AffiliationId());
                nameHeader.setText(model.getName());
                nameTv.setText(model.getName());
                address1.setText(model.getAdress1());
                adress2.setText(model.getAdress2());
                dobTv.setText(model.getDob());
                activTv.setText(model.getActivatingDate());
                joinTv.setText(model.getJoining_Date());
                mailTv.setText(model.getMail());
                downloadAffData(uid) ;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void downloadAffData(String headLead) {

        DatabaseReference pref = FirebaseDatabase.getInstance().getReference("profile").child(headLead);
        pref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelForProfile model = dataSnapshot.getValue(modelForProfile.class) ;
                affliationNameTv.setText(model.getName());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}


