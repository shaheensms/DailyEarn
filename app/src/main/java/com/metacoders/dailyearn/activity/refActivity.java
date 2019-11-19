/*
 * Copyright (c) $today.year.This Code is written by Gamkiller .
 */

package com.metacoders.dailyearn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.dailyearn.R;
import com.metacoders.dailyearn.adapters.viewHolderForRefList;
import com.metacoders.dailyearn.models.modelForProfile;
import com.metacoders.dailyearn.models.modelForUid;


public class refActivity extends AppCompatActivity {

    String refLink ;
    TextView noRef ;

    RecyclerView recyclerView ;
    LinearLayoutManager manager ;
    ProgressBar progressBar ;
DatabaseReference profile  ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ref);

        recyclerView = findViewById(R.id.refLIst) ;
        progressBar = findViewById(R.id.pbar);
        progressBar.setVisibility(View.VISIBLE);
        noRef = findViewById(R.id.nolvl);
        manager = new LinearLayoutManager(getApplicationContext());
        noRef.setVisibility(View.GONE);
        Intent o = getIntent();
        refLink = o.getStringExtra("ref") ;

        DatabaseReference mrf = FirebaseDatabase.getInstance().getReference("affdblist").child(refLink).child("list");
        profile = FirebaseDatabase.getInstance().getReference("profile");

        mrf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    loadPakageList();

                }
                else {

                    // change The UI
                    progressBar.setVisibility(View.INVISIBLE);
                    noRef.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // check the list is empty or not
                if(manager.getItemCount() == 0)
                {
                    progressBar.setVisibility(View.GONE);
                    noRef.setVisibility(View.VISIBLE);
                }
                else
                    { progressBar.setVisibility(View.GONE);
                        noRef.setVisibility(View.INVISIBLE);

                }


            }
        }, 2500) ;


    }
    private void loadPakageList() {



      //  manager.setStackFromEnd(true);
      //  manager.setReverseLayout(true);

        //   mrecyclerview.setLayoutManager(linearLayoutManager) ;
        recyclerView.setHasFixedSize(true);


        FirebaseRecyclerOptions<modelForUid> options ;
        FirebaseRecyclerAdapter<modelForUid , viewHolderForRefList> firebaseRecyclerAdapter ;

        DatabaseReference mreff = FirebaseDatabase.getInstance().getReference("affdblist").child(refLink).child("list"); // db link
        options = new FirebaseRecyclerOptions.Builder<modelForUid>().setQuery(mreff , modelForUid.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<modelForUid, viewHolderForRefList>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final viewHolderForRefList viewholdersForProducts, final int i, @NonNull final modelForUid model) {


                profile.child(model.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        final modelForProfile models = dataSnapshot.getValue(modelForProfile.class) ;

                        viewholdersForProducts.setData(getApplicationContext() ,models.getName() ,models.getMy_AffiliationId());

                        viewholdersForProducts.setOnClickListener(new viewHolderForRefList.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {


                                Intent o  =  new Intent(getApplicationContext() , refActivity.class);

                                o.putExtra("ref" , models.getMy_AffiliationId()) ;

                                startActivity(o);




                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });






            }

            @NonNull
            @Override
            public viewHolderForRefList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_referral_view, parent, false);
                final viewHolderForRefList viewholders = new viewHolderForRefList(iteamVIew);

                return viewholders;
            }
        };
        recyclerView.setLayoutManager(manager) ;
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }
}
