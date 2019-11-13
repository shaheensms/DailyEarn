package com.metacoders.dailyearn.adapters;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.dailyearn.R;

public class viewHolderForPackage extends RecyclerView.ViewHolder {

    View mView ;


    public viewHolderForPackage(@NonNull View itemView) {
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
     public  void  setData(Context ctx   , String name, String id, String price ){

        TextView priceTv = mView.findViewById(R.id.priceTv) ;
        TextView nameTv =  mView.findViewById(R.id.nameTV) ;
         LinearLayout backGroundLayout  = mView.findViewById(R.id.pakageBackGround) ;


         switch (name) {
             case "Platinum":

                 backGroundLayout.setBackgroundResource(R.drawable.platinum_gradient);
                 break;
             case "Gold":

                 backGroundLayout.setBackgroundResource(R.drawable.gold_gradient);
                 break;
             case "Silver":
                 backGroundLayout.setBackgroundResource(R.drawable.silver_gradient);
                 break;
             case  "Business"   :
                 backGroundLayout.setBackgroundResource(R.drawable.business_gradient);

                 break;
             default:

                 backGroundLayout.setBackgroundResource(R.drawable.bronze_gradient);
                 break;
         }

         priceTv.setText(price+ "$");
         nameTv.setText(name);

     }

    private viewHolderForPackage.ClickListener mClickListener;
    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(viewHolderForPackage.ClickListener clickListener)
    {
        mClickListener = clickListener;
    }





}
