package com.homework.homework2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;

public class FirstActivity extends AppCompatActivity {
    private final static String TAG = "FirstActivity";
    private static final String HASH_SET_RANDOM_NUMBERS = "HASH_SET_RANDOM_NUMBERS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        findViewById(R.id.generate_numbers_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashSet<Integer> hashSet = new HashSet<Integer>();
                int amountOfNumbers = 15;
                int numbersMaxSize = 100;
                int hashSetSize = (int) (Math.random() * amountOfNumbers);
                for (int i = 0; i < hashSetSize; i++) {
                    hashSet.add((int) (Math.random() * numbersMaxSize));
                }
                ArrayList<Integer> numberList = new ArrayList<>(hashSet);
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                intent.putExtra(HASH_SET_RANDOM_NUMBERS, numberList);
                startActivityForResult(intent, 111);
            }
        });
        Log.d("FirstActivity", "onCreate");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("...", "...");
            Log.d("FirstActivity", "A set of random numbers = " + data.getIntegerArrayListExtra("RESULT0"));
            Log.d("FirstActivity", "Result average = " + data.getDoubleExtra("RESULT1", 0));
            Log.d("FirstActivity", "Result sum = " + data.getIntExtra("RESULT2", 0));
            Log.d("FirstActivity", "Result number three = " + data.getDoubleExtra("RESULT3", 0));
        }
        super.onActivityResult(requestCode, resultCode, data);
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
