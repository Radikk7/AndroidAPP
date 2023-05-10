package com.example.androidapp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<Idea> ideas() {
        Idea idea = new Idea();
        User user77 = new User();
        user77.setId(777);
        user77.setName("Cuckold");
        user77.setPhone("+4972212332");
        idea.setId(777);
        idea.setAuthor(user77);
        idea.setDescription("Pridurki");
        idea.setTitle("Kto takie?");
        Timestamp today = new Timestamp(System.currentTimeMillis());
        List<Idea>ideaList22 = new ArrayList<>();
        ideaList22.add(idea);
        return ideaList22;
    }
}
