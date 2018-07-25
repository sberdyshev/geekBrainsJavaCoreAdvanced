package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson2.homework.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordArray {
    private final List<String> wordArray = new ArrayList<String>();

    public WordArray() {
        wordArray.add("1");
        wordArray.add("2");
        wordArray.add("3");
        wordArray.add("4");
        wordArray.add("5");
        wordArray.add("6");
        wordArray.add("7");
        wordArray.add("8");
        wordArray.add("9");
        wordArray.add("0");
        wordArray.add("3");
        wordArray.add("7");
        wordArray.add("9");
        wordArray.add("11");
    }

    public WordArray(List<String> array) {
        wordArray.retainAll(array);
    }

    public void showUniqueStrings() {
        List<String> uniqueList = new ArrayList<String>(new HashSet<String>(wordArray));
        System.out.println("List of all elements: ");
        System.out.println(wordArray);
        System.out.println("List of unique elements: ");
        System.out.println(uniqueList);
    }
}
