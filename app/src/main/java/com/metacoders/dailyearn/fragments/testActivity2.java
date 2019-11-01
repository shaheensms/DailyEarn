package com.metacoders.dailyearn.fragments;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.metacoders.dailyearn.R;


public class testActivity2 extends Fragment {

    View view;

    public testActivity2() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        LinearLayout car ;


        view = inflater.inflate(R.layout.activity_test_acitivy1, container, false);



        return view ;
    }

}
