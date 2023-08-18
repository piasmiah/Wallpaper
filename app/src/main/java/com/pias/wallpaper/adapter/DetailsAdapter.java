package com.pias.wallpaper.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.pias.wallpaper.R;
import com.pias.wallpaper.api.Constant;
import com.pias.wallpaper.model.ClickAction;
import com.pias.wallpaper.model.Wallpapers;
import com.pias.wallpaper.room.FavoriteFire;
import com.pias.wallpaper.room.FavoriteQuery;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsHolder> {
    List<Wallpapers> list;
    Activity activity;
    ClickAction clickAction;

    public DetailsAdapter(List<Wallpapers> list, Activity activity, ClickAction clickAction) {
        this.list = list;
        this.activity = activity;
        this.clickAction = clickAction;
    }

    @NonNull
    @Override
    public DetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallpaper_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsHolder holder, int position) {
        Wallpapers wallpapers= list.get(position);

        Glide.with(activity)
                .load(wallpapers.getUrl())
                .error(R.drawable.cover)
                .placeholder(R.drawable.cover)
                .into(holder.cover);
        if (checkFavorite(wallpapers.getUrl(), activity)){
            holder.favorite.setImageResource(R.drawable.favourite_icon);
        }
        else {
            holder.favorite.setImageResource(R.drawable.favorite_border_icon);
        }

        holder.favorite.setOnClickListener(v->{
            if (checkFavorite(wallpapers.getUrl(), activity)){
                removeFavorite(wallpapers.getUrl(), activity, holder.favorite);
            }
            else {
                clickAction.clicked(wallpapers.getUrl(), "favorite");
                holder.favorite.setImageResource(R.drawable.favourite_icon);
            }
        });

        holder.share.setOnClickListener(v->{
            clickAction.clicked(wallpapers.getUrl(), "share");
        });

        holder.set.setOnClickListener(v->{
            clickAction.clicked(wallpapers.getUrl(), "set");
        });

        holder.save.setOnClickListener(v->{
            clickAction.clicked(wallpapers.getUrl(), "save");
        });

    }
    private void removeFavorite(String url, Context context, ImageView imageView){
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Delete...?")
                .setMessage("Are you sure that, you wanna remove this wallpaper from Favorite?")
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FavoriteFire database= Room.databaseBuilder(context, FavoriteFire.class, Constant.databaseName).allowMainThreadQueries().build();
                        FavoriteQuery userDao= database.favoriteQuery();
                        
                        try {
                            userDao.deleteByUrl(url);
                            Toast.makeText(context, "Removed....", Toast.LENGTH_SHORT).show();
                            imageView.setImageResource(R.drawable.favorite_border_icon);
                        }
                        catch (Exception e){
                            Toast.makeText(context, "Failed To Remove....", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                }).show();
    }
    private boolean checkFavorite(String url, Context context){
        FavoriteFire database= Room.databaseBuilder(context, FavoriteFire.class, Constant.databaseName).allowMainThreadQueries().build();
        FavoriteQuery userDao= database.favoriteQuery();
        if (userDao.is_exist(url)) {
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class DetailsHolder extends RecyclerView.ViewHolder{
        ImageView cover, favorite, share, save, set;

        public DetailsHolder(@NonNull View itemView) {
            super(itemView);

            cover= itemView.findViewById(R.id.detailsCover);
            favorite= itemView.findViewById(R.id.favorite);
            share= itemView.findViewById(R.id.share);
            save= itemView.findViewById(R.id.save);
            set= itemView.findViewById(R.id.set);

        }
    }
}
