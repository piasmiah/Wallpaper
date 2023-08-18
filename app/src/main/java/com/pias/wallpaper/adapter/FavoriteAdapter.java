package com.pias.wallpaper.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pias.wallpaper.R;
import com.pias.wallpaper.activity.WallpaperDetails;
import com.pias.wallpaper.api.Constant;
import com.pias.wallpaper.model.Wallpapers;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {
    List<Wallpapers> list;
    Context context;

    public FavoriteAdapter(List<Wallpapers> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {
        Wallpapers wallpapers= list.get(position);

        Glide.with(context)
                .load(wallpapers.getUrl())
                .error(R.drawable.cover)
                .placeholder(R.drawable.cover)
                .skipMemoryCache(true)
                .into(holder.cover);

        holder.itemView.setOnClickListener(v->{
            Constant.tempList.clear();
            Constant.tempList.addAll(list);
            Intent intent= new Intent(context, WallpaperDetails.class);
            intent.putExtra("pos", position);
            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class FavoriteHolder extends RecyclerView.ViewHolder{
        ImageView cover;

        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);

            cover= itemView.findViewById(R.id.item_favorite_img);
        }
    }
}
