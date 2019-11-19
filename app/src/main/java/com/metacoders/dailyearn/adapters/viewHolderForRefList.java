/*
 * Copyright (c) $today.year.This Code is written by Gamkiller .
 */

package com.metacoders.dailyearn.adapters;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.dailyearn.R;

public class viewHolderForRefList extends RecyclerView.ViewHolder {

    TextView  name ,ref ;
    View mView ;

    public viewHolderForRefList(@NonNull View itemView) {

        super(itemView);
        mView = itemView ;

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



    public  void  setData(Context ctx   , String name , String ref  ){

        TextView refTv = mView.findViewById(R.id.refID) ;
        TextView nameTv =  mView.findViewById(R.id.name) ;


        nameTv.setText(name);
        refTv.setText(ref);

    }

    private viewHolderForRefList.ClickListener mClickListener;
    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(viewHolderForRefList.ClickListener clickListener)
    {
        mClickListener = clickListener;
    }

}
