package com.pias.wallpaper.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pias.wallpaper.R;
import com.pias.wallpaper.activity.CategoryDetails;
import com.pias.wallpaper.model.Category;
import com.pias.wallpaper.model.Wallpapers;

import org.w3c.dom.Text;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    List<Category> list;
    Context context;

    public CategoryAdapter(List<Category> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

        Category category= list.get(position);
        holder.title.setText(category.getTitle());

        Glide.with(context)
                .load(category.getCover())
                .error(R.drawable.cover)
                .placeholder(R.drawable.cover)
                .skipMemoryCache(true)
                .into(holder.cover);

        holder.itemView.setOnClickListener(v->{
            Intent intent= new Intent(context, CategoryDetails.class);
            intent.putExtra("id", category.getId());
            intent.putExtra("title", category.getTitle());
            context.startActivity(intent);


        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder{

        ImageView cover;
        TextView title;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);

            cover= itemView.findViewById(R.id.item_category_img);
            title= itemView.findViewById(R.id.item_category_title);
        }
    }
}
