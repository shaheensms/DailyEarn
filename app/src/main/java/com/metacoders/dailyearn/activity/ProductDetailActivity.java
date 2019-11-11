package com.metacoders.dailyearn.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.metacoders.dailyearn.R;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {
    Button addToCartBtn , buyNowBtn  ;
    TextView title , details , price   ;
    ImageView productImage ;

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

        Intent  i = getIntent();
        ProductImageLink = i.getStringExtra("imageLink") ;
        productPrice =  i.getStringExtra("price") ;
        productDetails = i.getStringExtra("details") ;
        productName = i.getStringExtra("name") ;


        Picasso.get().load(ProductImageLink).into(productImage) ;
        title.setText(productName);
        details.setText(productDetails);
        price.setText(productPrice);






        // buy now click
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
}
