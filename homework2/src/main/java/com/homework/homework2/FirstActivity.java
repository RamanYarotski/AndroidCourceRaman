package com.homework.homework2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {
    private static final String TAG = "FirstActivity";
    private static final String HASH_SET_RANDOM_NUMBERS = "HASH_SET_RANDOM_NUMBERS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        findViewById(R.id.generate_numbers_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Generator generator = new Generator();
                startActivityForResult(SecondActivity.newInstance(FirstActivity.this,
                        generator.generateSetOfRandomNumbersRandomSize(2, 15, 50),
                        HASH_SET_RANDOM_NUMBERS), 111);
            }
        });
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
            LogD logD = new LogD();
            logD.getLogs(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
