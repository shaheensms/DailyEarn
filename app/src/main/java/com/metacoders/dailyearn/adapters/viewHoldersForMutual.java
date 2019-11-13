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
    public  void  setData(Context ctx   , String name, String id, String price ){

        TextView priceTv = mView.findViewById(R.id.priceTv) ;
        TextView nameTv =  mView.findViewById(R.id.nameTV) ;

        priceTv.setText(price);
        nameTv.setText(name);
    }
    private viewHoldersForMutual.ClickListener mClickListener;
    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(viewHoldersForMutual.ClickListener clickListener)
    {
        mClickListener = clickListener;
    }

}
