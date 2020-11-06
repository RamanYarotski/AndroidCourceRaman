package com.homework.homework2;

import android.util.Log;

import java.util.ArrayList;

public class LogD {

    public void getLogs(ArrayList<Integer> numberList, double sum, double average, double resultNumberThree) {
        Log.d("...", "...");
        Log.d("FirstActivity", "A set of random numbers = " + numberList);
        Log.d("FirstActivity", "Result sum = " + sum);
        Log.d("FirstActivity", "Result average = " + average);
        Log.d("FirstActivity", "Result number three = " + resultNumberThree);
    }
}
