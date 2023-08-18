package com.pias.wallpaper.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pias.wallpaper.R;
import com.pias.wallpaper.adapter.FavoriteAdapter;
import com.pias.wallpaper.api.Constant;
import com.pias.wallpaper.model.Wallpapers;
import com.pias.wallpaper.room.FavoriteFire;
import com.pias.wallpaper.room.FavoriteModel;
import com.pias.wallpaper.room.FavoriteQuery;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFrag extends Fragment {

    View layout;
    RecyclerView recyclerView;
    FavoriteAdapter favoriteAdapter;
    List<Wallpapers> wallpapers= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout= inflater.inflate(R.layout.fragment_favourite, container, false);
        
        loadFavorite();

        return layout;
    }

    private void loadFavorite() {
        recyclerView= layout.findViewById(R.id.recycle_favorite_view);
        favoriteAdapter= new FavoriteAdapter(wallpapers, layout.getContext());
        recyclerView.setAdapter(favoriteAdapter);

        wallpapers.clear();
        favoriteAdapter.notifyDataSetChanged();

        FavoriteFire database= Room.databaseBuilder(layout.getContext(), FavoriteFire.class, Constant.databaseName).allowMainThreadQueries().build();
        FavoriteQuery userDao= database.favoriteQuery();

        List<FavoriteModel> list=  userDao.allWallpapers();
        for (FavoriteModel data: list){
            wallpapers.add(new Wallpapers(data.getUrl(), ""));
            favoriteAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            loadFavorite();
        }
        catch (Exception e){
            Log.d("checkkk", "onResume: "+e.toString());
        }
    }
}