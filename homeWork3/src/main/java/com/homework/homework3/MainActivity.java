package com.homework.homework3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_constraint);
    }

//      Landscape and portrait:
//  activity_photo_constraint
//  activity_photo_linear
//  activity_poll_linear
//  activity_poll_table
//  activity_sign_in_linear
//  activity_sign_in_constraint

//      Only portrait:
//  activity_photo_relative
}