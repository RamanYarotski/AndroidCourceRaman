package com.homework.homework2;

import java.util.ArrayList;

public class Calculator {

    public double sumOfArrayList (ArrayList<Integer> numberList) {
        double sum = 0;
        for (int h : numberList) {
            sum = sum + h;
        }
        return sum;
    }

    public double averageOfArrayList (ArrayList<Integer> numberList) {
        double sum = 0;
        for (int h : numberList) {
            sum = sum + h;
        }
        return sum / numberList.size();
    }

    public double myOperation(ArrayList<Integer> numberList) {
        double onePart = 0;
        double twoPart = 0;
        if (numberList.size() % 2 == 0) {   //For EVEN size of set
            for (int i = 0; i < (int) (numberList.size() / 2); i++) {
                onePart = onePart + numberList.get(i);
            }
            for (int i = (int) (numberList.size() / 2); i < numberList.size(); i++) {
                twoPart = twoPart - numberList.get(i);
            }
        } else {    // For UNEVEN size of set
            for (int i = 0; i < (int) ((numberList.size() - 1) / 2); i++) {
                onePart = onePart + numberList.get(i);
            }
            for (int i = (int) ((numberList.size() + 1) / 2); i < numberList.size(); i++) {
                twoPart = twoPart - numberList.get(i);
            }
        }
        return onePart / twoPart;
    }
}
