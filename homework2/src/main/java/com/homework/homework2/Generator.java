package com.homework.homework2;

import java.util.ArrayList;
import java.util.HashSet;

public class Generator {
    private int minAmountOfNumbers;
    private int maxAmountOfNumbers;
    private int numbersMaxSize;
    private int hashSetSize;
    private HashSet<Integer> hashSet;
    ArrayList<Integer> numberList;

    public Generator(int minAmountOfNumbers, int maxAmountOfNumbers, int numbersMaxSize) {
        this.minAmountOfNumbers = minAmountOfNumbers;
        this.maxAmountOfNumbers = maxAmountOfNumbers;
        this.numbersMaxSize = numbersMaxSize;
    }

    public ArrayList<Integer> generateSetOfRandomNumbersRandomSize() {
        hashSet = new HashSet<>();
        hashSetSize = minAmountOfNumbers + (int) (Math.random() * ((maxAmountOfNumbers - minAmountOfNumbers) + 1));
        for (int i = 0; i < hashSetSize; i++) {
            hashSet.add(((int) (Math.random() * numbersMaxSize)) + 1);
        }
        numberList = new ArrayList<>(hashSet);
        return numberList;
    }
}
