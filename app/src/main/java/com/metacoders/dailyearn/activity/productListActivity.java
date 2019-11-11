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
import com.metacoders.dailyearn.adapters.viewholdersForProducts;
import com.metacoders.dailyearn.models.modelForProducts;

public class productListActivity extends AppCompatActivity {

    RecyclerView mrecyclerview ;
    LinearLayoutManager linearLayoutManager ;
    DatabaseReference mref ;
    FirebaseRecyclerOptions<modelForProducts> options ;
    FirebaseRecyclerAdapter<modelForProducts , viewholdersForProducts> firebaseRecyclerAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        loadProduts();
    }
    public  void loadProduts() {
        mrecyclerview = (RecyclerView) findViewById(R.id.productlist) ;

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());


        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        //   mrecyclerview.setLayoutManager(linearLayoutManager) ;
        mrecyclerview.setHasFixedSize(true);



        mref = FirebaseDatabase.getInstance().getReference("productsList"); // db link
        options = new FirebaseRecyclerOptions.Builder<modelForProducts>().setQuery(mref , modelForProducts.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<modelForProducts, viewholdersForProducts>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewholdersForProducts viewholdersForProducts, final int i, @NonNull  final modelForProducts model) {


                viewholdersForProducts.setdataToview(getApplicationContext() ,  model.getId()  , model.getName() , model.getPrice()  , model.getDiscount()
                        , model.getDisc()  , model.getLink());


                viewholdersForProducts.setOnClickListener(new viewholdersForProducts.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        Intent o  =  new Intent(getApplicationContext() , ProductDetailActivity.class);
                        o.putExtra("name" , model.getName()) ;
                        o.putExtra("price" , model.getPrice()) ;
                        o.putExtra("imageLink" , model.getLink()) ;
                        o.putExtra("details" , model.getDisc()) ;
                        startActivity(o);




                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });


            }

            @NonNull
            @Override
            public viewholdersForProducts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_for_products, parent, false);
                final viewholdersForProducts viewholders = new viewholdersForProducts(iteamVIew);

                return viewholders;
            }
        };
        mrecyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3) );
        firebaseRecyclerAdapter.startListening();
        mrecyclerview.setAdapter(firebaseRecyclerAdapter);




    }
}
