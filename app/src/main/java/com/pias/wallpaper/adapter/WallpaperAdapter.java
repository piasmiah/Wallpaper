package com.pias.wallpaper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pias.wallpaper.R;
import com.pias.wallpaper.model.Wallpapers;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.WallpaperHolder> {

    List<Wallpapers> list;
    Context context;

    public WallpaperAdapter(List<Wallpapers> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public WallpaperHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WallpaperHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallpapers, parent, false)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperHolder holder, int position) {

        Wallpapers wallpapers= list.get(position);

        Glide.with(context)
                .load(Wallpapers.getUrl())
                .error(R.drawable.cover)
                .placeholder(R.drawable.cover)
                .skipMemoryCache(true)
                .into(holder.cover);

        holder.itemView.setOnClickListener(v->{

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class WallpaperHolder extends RecyclerView.ViewHolder {

        ImageView cover;

        public WallpaperHolder(@NonNull View itemView) {
            super(itemView);

            cover= itemView.findViewById(R.id.item_wallpaper_img);
        }
    }
}
