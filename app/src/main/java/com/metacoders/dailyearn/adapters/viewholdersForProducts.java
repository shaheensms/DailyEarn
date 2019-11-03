package com.metacoders.dailyearn.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.dailyearn.R;

public class viewholdersForProducts  extends RecyclerView.ViewHolder {

    View mview ;



    public viewholdersForProducts(@NonNull View itemView) {


        super(itemView);

        mview = itemView;
    }



     public  void  setdataToview (Context ctx , String id, String name, String price, String discount, String disc , String link )
     {

         TextView nameTv  = mview.findViewById(R.id.rProductNameTv) ;


         nameTv.setText(name);






     }
}
