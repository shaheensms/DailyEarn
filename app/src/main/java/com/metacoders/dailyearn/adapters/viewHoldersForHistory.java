package com.metacoders.dailyearn.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.dailyearn.R;

public class viewHoldersForHistory extends RecyclerView.ViewHolder {

    View mView ;



    public viewHoldersForHistory(@NonNull View itemView) {
        super(itemView);
        mView = itemView ;
    }
    public  void  setData(Context ctx   , String postID  , String tranID  , String date, String reason , String amount ,String status ){

        TextView tranIdTv = mView.findViewById(R.id.postid) ;
        TextView datTv  =  mView.findViewById(R.id.dateTv) ;
        TextView reasonTv  = mView.findViewById(R.id.reasonTv) ;
        TextView amountTv  =  mView.findViewById(R.id.amountTv) ;
        TextView statusTv = mView.findViewById(R.id.statusTv) ;



        tranIdTv.setText(tranID);
        datTv.setText(date);
        reasonTv.setText(reason);
        amountTv.setText(amount);
        statusTv.setText(status);





    }

}
