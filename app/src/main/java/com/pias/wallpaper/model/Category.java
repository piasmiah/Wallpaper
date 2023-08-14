package com.pias.wallpaper.model;

public class Category  {

    String id, title, cover;

    public Category(String id, String title, String cover) {
        this.id = id;
        this.title = title;
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static String getCover() {
        return cover;
    }
}
