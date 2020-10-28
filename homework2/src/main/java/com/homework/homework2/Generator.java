package com.homework.homework2;

import java.util.ArrayList;
import java.util.HashSet;

public class Generator {

    public ArrayList<Integer> generateSetOfRandomNumbersRandomSize(int minAmountOfNumbers, int maxAmountOfNumbers, int numbersMaxSize) {
        HashSet<Integer> hashSet = new HashSet<>();
        int hashSetSize = minAmountOfNumbers + (int) (Math.random() * ((maxAmountOfNumbers - minAmountOfNumbers) + 1));
        for (int i = 0; i < hashSetSize; i++) {
            hashSet.add(((int) (Math.random() * numbersMaxSize)) + 1);
        }
        return new ArrayList<>(hashSet);
    }
}
