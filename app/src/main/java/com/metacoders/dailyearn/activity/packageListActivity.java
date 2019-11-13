package com.metacoders.dailyearn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.dailyearn.R;
import com.metacoders.dailyearn.adapters.viewHolderForPackage;
import com.metacoders.dailyearn.adapters.viewholdersForProducts;
import com.metacoders.dailyearn.models.modelForPakage;
import com.metacoders.dailyearn.models.modelForProducts;

public class packageListActivity extends AppCompatActivity {
    RecyclerView mrecyclerview ;
    LinearLayoutManager linearLayoutManager ;
    DatabaseReference mref ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_list);
        loadPakageList();
    }
    private void loadPakageList() {
        RecyclerView    recyclerview = (RecyclerView) findViewById(R.id.packageListl) ;

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());


        manager.setStackFromEnd(true);
        manager.setReverseLayout(true);

        //   mrecyclerview.setLayoutManager(linearLayoutManager) ;
        recyclerview.setHasFixedSize(true);


        FirebaseRecyclerOptions<modelForPakage> options ;
        FirebaseRecyclerAdapter<modelForPakage , viewHolderForPackage> firebaseRecyclerAdapter ;

        DatabaseReference  mreff = FirebaseDatabase.getInstance().getReference("packageList"); // db link
        options = new FirebaseRecyclerOptions.Builder<modelForPakage>().setQuery(mreff , modelForPakage.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<modelForPakage, viewHolderForPackage>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewHolderForPackage viewholdersForProducts,final int i, @NonNull final modelForPakage model) {


                viewholdersForProducts.setData(getApplicationContext() , model.getName() , model.getId() , model.getPrice());

                viewholdersForProducts.setOnClickListener(new viewHolderForPackage.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        Intent o  =  new Intent(getApplicationContext() , PackageDetailsActivity.class);
                        o.putExtra("name" , model.getName()) ;
                        o.putExtra("price" , model.getPrice()) ;
                        o.putExtra("imageLink" , model.getImage()) ;
                        o.putExtra("details" , model.getDetail()) ;
                        o.putExtra("type" , model.getType()) ;
                        o.putExtra("id" , model.getId()) ;

                        startActivity(o);




                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });





            }

            @NonNull
            @Override
            public viewHolderForPackage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_row_platinum, parent, false);
                final viewHolderForPackage viewholders = new viewHolderForPackage(iteamVIew);

                return viewholders;
            }
        };
        recyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext() , 1 )) ;
        firebaseRecyclerAdapter.startListening();
        recyclerview.setAdapter(firebaseRecyclerAdapter);


    }

}
