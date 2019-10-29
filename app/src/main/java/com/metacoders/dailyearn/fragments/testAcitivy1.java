package com.metacoders.dailyearn.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.metacoders.dailyearn.R;

public class testAcitivy1 extends Fragment {
    View view;

    public testAcitivy1() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        LinearLayout car ;


        view = inflater.inflate(R.layout.dashboard_fragment, container, false);
        TextView tv=view.findViewById(R.id.newsTicker);
        tv.setSelected(true);


        return view ;
    }

}
