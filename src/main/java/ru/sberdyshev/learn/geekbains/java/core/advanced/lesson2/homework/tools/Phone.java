package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson2.homework.tools;

public class Phone {
    private String phone;
    private String name;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null) {
            this.phone = "no phone";
        } else {
            this.phone = phone;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            this.name = "no name";
        } else {
            this.name = name;
        }

    }

    public Phone(String name, String phone) {
        if (phone == null) {
            this.phone = "no phone";
        } else {
            this.phone = phone;
        }
        if (name == null) {
            this.name = "no name";
        } else {
            this.name = name;
        }
    }
}
