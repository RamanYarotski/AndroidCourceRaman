package com.homework.homework2;

import android.content.Intent;
import android.util.Log;

public class LogD {

    public void getLogs(Intent data) {
        Log.d("...", "...");
        Log.d("FirstActivity", "A set of random numbers = " + data.getIntegerArrayListExtra("RESULT0"));
        Log.d("FirstActivity", "Result sum = " + data.getDoubleExtra("RESULT1", 0));
        Log.d("FirstActivity", "Result average = " + data.getDoubleExtra("RESULT2", 0));
        Log.d("FirstActivity", "Result number three = " + data.getDoubleExtra("RESULT3", 0));
    }
}
