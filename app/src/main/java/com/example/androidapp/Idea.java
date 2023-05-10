package com.example.androidapp;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Idea {
    private int id;
    private String title; //строка название/заголовок
    private String description;//строка с длинным описанием
    private int likes; // целое число количество лайков
    private Timestamp timestamp;//дата создания идеи
    private boolean favorite;
    private User author;


    public Idea(int id, String title, String description, int likes, Timestamp timestamp, boolean favorite, User author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.timestamp = timestamp;
        this.favorite = favorite;
        this.author = author;

    }

    public Idea() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public User getAuthor() {
        return author;
    }


    public void setAuthor(User author) {
        this.author = author;
    }

    public String toString() {//"\n" это отступ на новую строку
            return "\n" + "\n" + "\n" + "\n" + "\n" +" " + "Id- " + getId() + "\n"+ " " + "Title-" + getTitle()
                    + "\n" + " " + "Description-" + getDescription() + "\n" + " " + "Id_Author-" + getAuthor() + "\n" + " " + "Likes-" + getLikes()
                    + "\n" + " " + "Time-" + convert(getTimestamp());
    }

    public static String convert(Timestamp timestamp) {
        String time = timestamp.toString();
        return time.substring(0,11);//вырезаем 12 первых цифр
    }


}
