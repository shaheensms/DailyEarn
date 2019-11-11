package com.metacoders.dailyearn.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.dailyearn.R;
import com.squareup.picasso.Picasso;

public class viewholdersForProducts  extends RecyclerView.ViewHolder {

    View mview ;



    public viewholdersForProducts(@NonNull View itemView) {


        super(itemView);

        mview = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());

            }
        });
        //item long click
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });

    }



     public  void  setdataToview (Context ctx , String id, String name, String price, String discount, String disc , String link )
     {

         TextView nameTv  = mview.findViewById(R.id.rProductNameTv) ;
         TextView priceTv  = mview.findViewById(R.id.pricesONROW) ;
         ImageView imageView = mview.findViewById(R.id.rimageTv) ;


         Picasso.get().load(link).into(imageView);
         priceTv.setText(price);
         nameTv.setText(name);
     }

    private viewholdersForProducts.ClickListener mClickListener;
    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(viewholdersForProducts.ClickListener clickListener)
    {
        mClickListener = clickListener;
    }


}
