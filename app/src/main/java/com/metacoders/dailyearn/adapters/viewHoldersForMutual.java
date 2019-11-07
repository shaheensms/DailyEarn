package com.metacoders.dailyearn.adapters;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.dailyearn.R;

public class viewHoldersForMutual  extends RecyclerView.ViewHolder {
    View mView ;


    public viewHoldersForMutual(@NonNull View itemView) {
        super(itemView);

        mView = itemView ;


    }
    public  void  setData(Context ctx   , String name, String id, String price ){

        TextView priceTv = mView.findViewById(R.id.priceTv) ;
        TextView nameTv =  mView.findViewById(R.id.nameTV) ;

        priceTv.setText(price);
        nameTv.setText(name);






    }
}
