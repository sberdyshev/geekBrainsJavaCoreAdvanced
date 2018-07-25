package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson2.homework;

import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson2.homework.tools.PhoneBook;
import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson2.homework.tools.WordArray;

public class App {

    public static void main(String[] args) {
        WordArray words = new WordArray();
        words.showUniqueStrings();

        PhoneBook pb = new PhoneBook();
        pb.showPhones("Magomet");
        pb.showPhones("Sergey");
    }
}
