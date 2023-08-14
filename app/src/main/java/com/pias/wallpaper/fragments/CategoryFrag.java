package com.pias.wallpaper.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.pias.wallpaper.R;
import com.pias.wallpaper.adapter.CategoryAdapter;
import com.pias.wallpaper.adapter.WallpaperAdapter;
import com.pias.wallpaper.model.Category;
import com.pias.wallpaper.model.Wallpapers;

import java.util.ArrayList;
import java.util.List;


public class CategoryFrag extends Fragment {

    CategoryAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar loader;
    List<Category> list= new ArrayList<>();

    View layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout= inflater.inflate(R.layout.fragment_category, container, false);

        recyclerView= layout.findViewById(R.id.recycle_view_category);
        loader= layout.findViewById(R.id.loader_category);

        adapter= new CategoryAdapter(list, layout.getContext());
        recyclerView.setAdapter(adapter);

        loadWallpaper();

        return layout;
    }

    private void loadWallpaper() {
        list.add(new Category("", "", ""));
        list.add(new Category("", "", ""));
        list.add(new Category("", "", ""));
        list.add(new Category("", "", ""));
        list.add(new Category("", "", ""));

        adapter.notifyDataSetChanged();
        loader.setVisibility(View.GONE);

    }
}