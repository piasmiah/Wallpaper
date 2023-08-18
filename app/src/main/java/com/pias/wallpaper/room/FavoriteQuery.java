package com.pias.wallpaper.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteQuery {
    @Insert
    void insertData(FavoriteModel favoriteModel);

    @Query("SELECT * FROM FavoriteModel ORDER BY id")
    List<FavoriteModel> allWallpapers();

    @Query("SELECT EXISTS(SELECT * FROM FavoriteModel WHERE url=:url)")
    Boolean is_exist(String url);

    @Query("DELETE FROM FavoriteModel WHERE url=:url")
    void deleteByUrl(String url);
}
