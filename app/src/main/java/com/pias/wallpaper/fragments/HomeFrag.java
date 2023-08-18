package com.pias.wallpaper.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pias.wallpaper.R;
import com.pias.wallpaper.adapter.WallpaperAdapter;
import com.pias.wallpaper.api.Constant;
import com.pias.wallpaper.model.Wallpapers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFrag extends Fragment {

    int page;
    WallpaperAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar loader;
    List<Wallpapers> list= new ArrayList<>();
    View layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout= inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView= layout.findViewById(R.id.recycle_view);
        loader= layout.findViewById(R.id.loader_home);

        adapter= new WallpaperAdapter(list, layout.getContext());
        recyclerView.setAdapter(adapter);
        
        loadWallpaper(String.valueOf(page));

        return layout;
    }

    private void loadWallpaper(String page) {

        StringRequest request= new StringRequest(Request.Method.POST, Constant.home, response -> {

            Log.d("nothing", "loadWallpaper: "+response);
            loader.setVisibility(View.GONE);

            try {
                JSONObject jsonObject= new JSONObject(response);
                JSONArray jsonArray= jsonObject.getJSONArray("wallpaper");
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject data= jsonArray.getJSONObject(i);

                    String url= data.getString("url");
                    String category= data.getString("category");

                    list.add(new Wallpapers(url, category));
                    adapter.notifyDataSetChanged();
                }


            }
            catch (Exception e){
                Toast.makeText(layout.getContext(), "Error: "+e.toString(), Toast.LENGTH_SHORT).show();

            }


        }, error -> {
            //Error Handling
            Toast.makeText(layout.getContext(), "Error: "+error.toString(), Toast.LENGTH_SHORT).show();

        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> dt= new HashMap<>();
                dt.put("page", page);

                return dt;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(layout.getContext());
        queue.add(request);
    }
}