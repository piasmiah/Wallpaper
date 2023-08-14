package com.pias.wallpaper.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class FavoriteHolder extends RecyclerView.ViewHolder{

        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
