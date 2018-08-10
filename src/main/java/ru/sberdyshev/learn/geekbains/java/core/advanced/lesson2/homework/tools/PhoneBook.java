package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson2.homework.tools;

import java.util.*;

public class PhoneBook {
    private final List<Phone> phoneBook = new ArrayList<Phone>();

    public PhoneBook() {
        phoneBook.add(new Phone("Ivan","+7 903 555 66 77"));
        phoneBook.add(new Phone("Anton","+7 903 111 66 77"));
        phoneBook.add(new Phone("Sergey","+7 903 222 66 77"));
        phoneBook.add(new Phone("Peter","+7 903 333 66 77"));
        phoneBook.add(new Phone("Magomet","+7 903 444 66 77"));
        phoneBook.add(new Phone("Magomet","+7 903 444 66 88"));
        phoneBook.add(new Phone("Magomet","+7 903 444 66 99"));
    }

    public List<Phone> getPhones (String name) {
        List<Phone> resultPhones = new ArrayList<Phone>();
        for (Iterator<Phone> phoneIterator = phoneBook.iterator(); phoneIterator.hasNext();) {
            Phone phone = phoneIterator.next();
            if (phone.getPhone().equals(name)) {
                resultPhones.add(phone);
            }
        }
        return resultPhones;
    }

    public void showPhones(String name) {
        System.out.println("All phone with name " + name);
        for (Iterator<Phone> phoneIterator = phoneBook.iterator(); phoneIterator.hasNext();) {
            Phone phone = phoneIterator.next();
            if (phone.getName().equals(name)) {
                System.out.println(phone.getPhone());
            }
        }

    }
}
