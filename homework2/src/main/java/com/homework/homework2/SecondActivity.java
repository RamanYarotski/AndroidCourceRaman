package com.homework.homework2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class SecondActivity extends FirstActivity {
    private final static String TAG = "SecondActivity";
    private int sum;
    private double average;
    private double resultNumberThree;
    private int onePart;
    private int twoPart;
    ArrayList<Integer> numberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG, "onCreate");

        final Intent intent = getIntent();
        if (intent != null) {
            numberList = intent.getIntegerArrayListExtra("HASH_SET_RANDOM_NUMBERS");
            for (int h : numberList) {
                sum = sum + h;
            }
            average = (sum / numberList.size());
            for (int i = 0; i <= (int) (numberList.size() / 2); i++) {
                onePart = onePart + numberList.get(i);
            }
            for (int i = (int) (numberList.size() / 2); i < numberList.size(); i++) {
                twoPart = twoPart - numberList.get(i);
            }
            resultNumberThree = onePart / twoPart;
        }

        findViewById(R.id.second_activity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("RESULT0", numberList);
                resultIntent.putExtra("RESULT1", average);
                resultIntent.putExtra("RESULT2", sum);
                resultIntent.putExtra("RESULT3", resultNumberThree);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
    }
}
