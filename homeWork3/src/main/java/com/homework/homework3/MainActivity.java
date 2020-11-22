package com.homework.homework3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_task5);
    }
}
//      Landscape and portrait:
//  activity_photo_constraint
//  activity_photo_linear
//  activity_poll_linear
//  activity_poll_table
//  activity_sign_in_linear
//  activity_sign_in_constraint
//  activity_android_linear

//      Only portrait:
//  activity_photo_relative
//  activity_sign_in_relative
//  activity_android_constraint
//  На задании 4,2 на activity_android_constraint до меня дошло, что
//  не один и тот же layout должен одонаково хорошо смотреться в гориз и верт располож, а
//  нужно делать отдельно layout-land со своей разметкой для горизонт расположения
//  о Боже, я три недели промучался, но я не сдавался =)