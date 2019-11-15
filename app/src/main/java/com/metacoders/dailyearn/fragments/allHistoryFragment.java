package com.metacoders.dailyearn.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.dailyearn.R;
import com.metacoders.dailyearn.adapters.viewHoldersForHistory;
import com.metacoders.dailyearn.adapters.viewHoldersForMutual;
import com.metacoders.dailyearn.models.modelForHIstory;
import com.metacoders.dailyearn.models.modelForMutulPatner;

public class allHistoryFragment extends Fragment
{
RecyclerView mrecyclerview;

    View mview ;
    LinearLayoutManager linearLayoutManager ;
    String uid  ;


    public allHistoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mview = inflater.inflate(R.layout.activity_deposit_history , container , false)  ;
        uid = FirebaseAuth.getInstance().getUid() ;


            loadHistory();



        return mview;
    }
    public  void loadHistory() {
        FirebaseRecyclerOptions<modelForHIstory> optionss ;
        FirebaseRecyclerAdapter<modelForHIstory , viewHoldersForHistory> firebaseRecyclerAdapter ;
        //mutualList
        mrecyclerview = (RecyclerView) mview.findViewById(R.id.historyList) ;

        linearLayoutManager = new LinearLayoutManager(getContext());


        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        //   mrecyclerview.setLayoutManager(linearLayoutManager) ;
        mrecyclerview.setHasFixedSize(true);



      DatabaseReference  mref = FirebaseDatabase.getInstance().getReference("profile").child(uid).child("transHistory"); // db link
        optionss = new FirebaseRecyclerOptions.Builder<modelForHIstory>().setQuery(mref , modelForHIstory.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<modelForHIstory, viewHoldersForHistory>(optionss) {
            @Override
            protected void onBindViewHolder(@NonNull viewHoldersForHistory viewHoldersForHistory, final int i, @NonNull modelForHIstory model) {


                viewHoldersForHistory.setData(getContext() ,model.getId() , model.getTransID() ,model.getDate() , model.getReason() , model.getAmount()
                , model.getStatus());


            }

            @NonNull
            @Override
            public viewHoldersForHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.deposit_history_row, parent, false);
                final viewHoldersForHistory viewholders = new viewHoldersForHistory(iteamVIew);

                return viewholders;
            }
        };
        mrecyclerview.setLayoutManager(linearLayoutManager) ;
        firebaseRecyclerAdapter.startListening();
        mrecyclerview.setAdapter(firebaseRecyclerAdapter);



    }
}
