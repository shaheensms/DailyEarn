package com.metacoders.dailyearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.metacoders.dailyearn.Database.Database;
import com.metacoders.dailyearn.models.Order;

import java.util.ArrayList;
import java.util.List;

public class cart extends AppCompatActivity {
public TextView totalPriceTv ;
public Button  checckoutBtn ;
    RecyclerView.LayoutManager layoutManager;
    CartAdapter adapter;
    RecyclerView recyclerView;
    List<Order> cart = new ArrayList<>();
    int totlal ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        checckoutBtn = findViewById(R.id.chekOutBtn);
        recyclerView = findViewById(R.id.listOfFood) ;
        totalPriceTv = findViewById(R.id.totalPriceOnCart) ;

        cart = new Database(this).getcarts();
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);





        loadListFood();

    }
    private void loadListFood() {
        cart = new Database(this).getcarts();
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);


/// calculate total price

        totlal = 0;
        for (Order order : cart)
            totlal += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        //  Locale locale = new Locale("en", "BD");
        //  NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        totalPriceTv.setText(totlal + " BDT");
       // FinalTextPriceFloatBar.setText(totlal + "");


    }
}
