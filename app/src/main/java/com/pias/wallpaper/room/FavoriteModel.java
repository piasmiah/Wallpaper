package com.pias.wallpaper.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "url")
    public String url;


    public FavoriteModel(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
