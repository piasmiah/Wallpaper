package com.pias.wallpaper.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pias.wallpaper.R;
import com.pias.wallpaper.adapter.CategoryAdapter;
import com.pias.wallpaper.adapter.WallpaperAdapter;
import com.pias.wallpaper.api.Constant;
import com.pias.wallpaper.model.Category;
import com.pias.wallpaper.model.Wallpapers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        StringRequest request= new StringRequest(Constant.category, response -> {
            loader.setVisibility(View.GONE);

            try {
                JSONObject jsonObject= new JSONObject(response);
                JSONArray jsonArray= jsonObject.getJSONArray("category");
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject data= jsonArray.getJSONObject(i);

                    String id= data.getString("id");
                    String title= data.getString("title");
                    String cover= data.getString("cover");

                    list.add(new Category(id, title, cover));
                    adapter.notifyDataSetChanged();
                }


            }
            catch (Exception e){
                Toast.makeText(layout.getContext(), "Error: "+e.toString(), Toast.LENGTH_SHORT).show();

            }


        }, error -> {
            //Error Handling
            Toast.makeText(layout.getContext(), "Error: "+error.toString(), Toast.LENGTH_SHORT).show();

        });
        RequestQueue queue= Volley.newRequestQueue(layout.getContext());
        queue.add(request);
    }
}