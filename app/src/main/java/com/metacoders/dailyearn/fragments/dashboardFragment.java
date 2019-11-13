package com.metacoders.dailyearn.fragments;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.dailyearn.R;
import com.metacoders.dailyearn.SignUpActivity;
import com.metacoders.dailyearn.activity.ProductDetailActivity;
import com.metacoders.dailyearn.adapters.viewHolderForPackage;
import com.metacoders.dailyearn.adapters.viewHoldersForMutual;
import com.metacoders.dailyearn.adapters.viewholdersForProducts;
import com.metacoders.dailyearn.models.modelForMutulPatner;
import com.metacoders.dailyearn.models.modelForPakage;
import com.metacoders.dailyearn.models.modelForProducts;
import com.metacoders.dailyearn.models.modelForProfile;

public class dashboardFragment extends Fragment {
    private static String password;
    RecyclerView mrecyclerview  ;
    LinearLayoutManager linearLayoutManager ;
    DatabaseReference mref;

    FirebaseRecyclerOptions<modelForProducts> options ;
    FirebaseRecyclerAdapter<modelForProducts , viewholdersForProducts> firebaseRecyclerAdapter ;
    View view;
    TextView affTv  ;
    static String activeDate ;



    public dashboardFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.dashboard_fragment, container, false);
     TextView tv=view.findViewById(R.id.newsTicker);
        affTv = view.findViewById(R.id.affLinkTv) ;


        affTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth mauth = FirebaseAuth.getInstance();
                mauth.signOut();
                Intent i = new Intent(getContext(), SignUpActivity.class);
                startActivity(i);




            }
        });


       tv.setSelected(true);


        loadProduts();
      //  createSomeData();
        Handler   handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                loadPakageList() ;


            }
        } , 1000) ;

        loadMutualPackage();

        return view ;
    }


    private void loadPakageList() {
    RecyclerView    recyclerview = (RecyclerView) view.findViewById(R.id.pakageList) ;

        LinearLayoutManager manager = new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL  ,false);


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
            protected void onBindViewHolder(@NonNull viewHolderForPackage viewholdersForProducts,final int i, @NonNull modelForPakage model) {


                viewholdersForProducts.setData(getContext() , model.getName() , model.getId() , model.getPrice());


            }

            @NonNull
            @Override
            public viewHolderForPackage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_row_platinum, parent, false);
                final viewHolderForPackage viewholders = new viewHolderForPackage(iteamVIew);

                return viewholders;
            }
        };
        recyclerview.setLayoutManager(manager) ;
        firebaseRecyclerAdapter.startListening();
        recyclerview.setAdapter(firebaseRecyclerAdapter);


    }

    public  void loadProduts() {



        mrecyclerview = (RecyclerView) view.findViewById(R.id.productlist) ;

        linearLayoutManager = new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL  ,false);


        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        //   mrecyclerview.setLayoutManager(linearLayoutManager) ;
        mrecyclerview.setHasFixedSize(true);



        mref = FirebaseDatabase.getInstance().getReference("productsList"); // db link
        options = new FirebaseRecyclerOptions.Builder<modelForProducts>().setQuery(mref , modelForProducts.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<modelForProducts, viewholdersForProducts>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewholdersForProducts viewholdersForProducts,final int i, @NonNull final modelForProducts model) {


                viewholdersForProducts.setdataToview(getContext() ,  model.getId()  , model.getName() , model.getPrice()  , model.getDiscount()
                 , model.getDisc()  , model.getLink());


                viewholdersForProducts.setOnClickListener(new viewholdersForProducts.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        Intent o  =  new Intent(getContext() , ProductDetailActivity.class);
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
        mrecyclerview.setLayoutManager(linearLayoutManager) ;
        firebaseRecyclerAdapter.startListening();
        mrecyclerview.setAdapter(firebaseRecyclerAdapter);




    }
    public  void loadMutualPackage()
    {
        FirebaseRecyclerOptions<modelForMutulPatner> optionss ;
        FirebaseRecyclerAdapter<modelForMutulPatner , viewHoldersForMutual> firebaseRecyclerAdapter ;
        //mutualList
        mrecyclerview = (RecyclerView) view.findViewById(R.id.mutualList) ;

        linearLayoutManager = new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL  ,false);


        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        //   mrecyclerview.setLayoutManager(linearLayoutManager) ;
        mrecyclerview.setHasFixedSize(true);



        mref = FirebaseDatabase.getInstance().getReference("mutualFundPackageList"); // db link
        optionss = new FirebaseRecyclerOptions.Builder<modelForMutulPatner>().setQuery(mref , modelForMutulPatner.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<modelForMutulPatner, viewHoldersForMutual>(optionss) {
            @Override
            protected void onBindViewHolder(@NonNull viewHoldersForMutual viewholdersforMutual,final int i, @NonNull modelForMutulPatner model) {


                viewholdersforMutual.setData(getContext() , model.getName()   , model.getName() , model.getPrice());


            }

            @NonNull
            @Override
            public viewHoldersForMutual onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_partnership, parent, false);
                final viewHoldersForMutual viewholders = new viewHoldersForMutual(iteamVIew);

                return viewholders;
            }
        };
        mrecyclerview.setLayoutManager(linearLayoutManager) ;
        firebaseRecyclerAdapter.startListening();
        mrecyclerview.setAdapter(firebaseRecyclerAdapter);



    }

 public  void createSomeData()
 {


     DatabaseReference mdef  = FirebaseDatabase.getInstance().getReference("packageList"); // db link
     String id = "Business" ;

      modelForPakage model =  new modelForPakage(id ,id , "1250" , "dfs" ) ;


      mdef.child(id).setValue(model) ;
 }

 public  void downloadProfileData() {
     DatabaseReference  pref = FirebaseDatabase.getInstance().getReference("profile").child("MUIdCk609CZBr4ZZTd8Mc9kpzDJ2");


     pref.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


             modelForProfile  model = dataSnapshot.getValue(modelForProfile.class) ;


            affTv.setText(model.getMy_AffiliationId());
            activeDate = model.getActivatingDate() ;
            password = model.getPassword();




         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });



 }
 public static String  sendActiveDate()
 {

     return activeDate ;

 }
 public static    String getPassWord(){



        return password ;
    }


    @Override
    public void onStart() {
        super.onStart();

        downloadProfileData();
    }
}



