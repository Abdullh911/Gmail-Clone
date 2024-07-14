package com.example.demo;

import java.util.ArrayList;

public class Contact {
    private ArrayList<String>emails;
    private String name;

    @Override
    public String toString() {
        return "Contact{" +
                "emails=" + emails +
                ", name='" + name + '\'' +
                '}';
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
