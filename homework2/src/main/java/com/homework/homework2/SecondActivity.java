package com.homework.homework2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class SecondActivity extends FirstActivity {
    private static final String TAG2 = "SecondActivity";

    private double sum;
    private double average;
    private double resultNumberThree;
    private ArrayList<Integer> numberList;

    public static Intent newInstance(Context context, ArrayList<Integer> numberList, String name) {
        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra(name, numberList);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG2, "onCreate");

        final Intent intent = getIntent();
        if (intent != null) {
            numberList = intent.getIntegerArrayListExtra("HASH_SET_RANDOM_NUMBERS");
        }
        findViewById(R.id.second_activity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculator calculator = new Calculator();
                sum = calculator.sumOfArrayList(numberList);
                average = calculator.averageOfArrayList(numberList);
                resultNumberThree = calculator.myOperation(numberList);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("RESULT0", numberList);
                resultIntent.putExtra("RESULT1", sum);
                resultIntent.putExtra("RESULT2", average);
                resultIntent.putExtra("RESULT3", resultNumberThree);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
