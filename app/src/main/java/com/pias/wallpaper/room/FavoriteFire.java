package com.pias.wallpaper.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteModel.class}, version = 1)

public abstract class FavoriteFire extends RoomDatabase {
    public abstract FavoriteQuery favoriteQuery();
}
