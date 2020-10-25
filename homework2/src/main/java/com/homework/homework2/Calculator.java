package com.homework.homework2;

import java.util.ArrayList;

public class Calculator {
    private double sum;
    private double average;
    private double resultNumberThree;
    private double onePart;
    private double twoPart;

    public double sum ( ArrayList<Integer> numberList){
        for (int h : numberList) {
            sum = sum + h;
        }
        return sum;
    }

    public double average (ArrayList<Integer> numberList){
        average = (sum / numberList.size());
        return average;
    }

    public double myOperation (ArrayList<Integer> numberList){
        if (numberList.size() % 2 == 0) {   //For EVEN size of set
            for (int i = 0; i < (int) (numberList.size() / 2); i++) {
                onePart = onePart + numberList.get(i);
            }
            for (int i = (int) (numberList.size() / 2); i < numberList.size(); i++) {
                twoPart = twoPart - numberList.get(i);
            }
        } else {    // For UNEVEN sizeof set
            for (int i = 0; i < (int) ((numberList.size() - 1) / 2); i++) {
                onePart = onePart + numberList.get(i);
            }
            for (int i = (int) ((numberList.size() + 1) / 2); i < numberList.size(); i++) {
                twoPart = twoPart - numberList.get(i);
            }
        }
        resultNumberThree = onePart / twoPart;
        return resultNumberThree;
    }
}
