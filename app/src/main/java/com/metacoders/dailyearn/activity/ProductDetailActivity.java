package com.metacoders.dailyearn.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.metacoders.dailyearn.Database.Database;
import com.metacoders.dailyearn.R;
import com.metacoders.dailyearn.cart;
import com.metacoders.dailyearn.models.Order;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {
    Button addToCartBtn , buyNowBtn  ;
    TextView title , details , price   ;
    ImageView productImage , cartImage ;

    String productName , productDetails , productPrice , ProductImageLink ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        productImage = findViewById(R.id.productImage);
        title = findViewById(R.id.productName) ;
        price = findViewById(R.id.productPrice) ;
        details = findViewById(R.id.porductDetails) ;
        addToCartBtn = findViewById(R.id.addToCartBtn);
        buyNowBtn = findViewById(R.id.buynowBtn);
        cartImage = findViewById(R.id.cartImage) ;


        Intent  i = getIntent();
        ProductImageLink = i.getStringExtra("imageLink") ;
        productPrice =  i.getStringExtra("price") ;
        productDetails = i.getStringExtra("details") ;
        productName = i.getStringExtra("name") ;


        Picasso.get().load(ProductImageLink).into(productImage) ;
        title.setText(productName);
        details.setText(productDetails);
        price.setText(productPrice);



        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext() , cart.class);
                startActivity(i);


            }
        });



        // buy now click
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Database(getApplicationContext()).addToCart(new Order(

                        productName, "1", productPrice


                ));

                Intent i = new Intent(getApplicationContext() , cart.class);
                startActivity(i);

            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                new Database(getApplicationContext()).addToCart(new Order(

                        productName, "1", productPrice


                ));

                Toast.makeText(getApplicationContext() , "Product Added To the Cart ", Toast.LENGTH_SHORT)
                        .show();

            }
        });

    }
}
