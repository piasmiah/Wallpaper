package com.pias.wallpaper.model;

public class Wallpapers {

    String url, category;

    public Wallpapers(String url, String category) {
        this.url = url;
        this.category = category;
    }

    public static String getUrl() {
        return url;
    }

    public String getCategory() {
        return category;
    }
}
