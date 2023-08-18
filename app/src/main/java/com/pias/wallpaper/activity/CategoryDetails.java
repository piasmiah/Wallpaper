package com.pias.wallpaper.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pias.wallpaper.R;
import com.pias.wallpaper.adapter.WallpaperAdapter;
import com.pias.wallpaper.api.Constant;
import com.pias.wallpaper.model.Wallpapers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDetails extends AppCompatActivity {

    WallpaperAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar loader;
    List<Wallpapers> list= new ArrayList<>();
    String categoryId, categoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        Bundle bundle= getIntent().getExtras();

        categoryId= bundle.getString("id");
        categoryTitle= bundle.getString("title");

        recyclerView= findViewById(R.id.recycle_categoryView);
        loader= findViewById(R.id.loader_categoryView);

        adapter= new WallpaperAdapter(list, CategoryDetails.this);
        recyclerView.setAdapter(adapter);

        loadWallpaper(categoryId);

        TextView title= findViewById(R.id.toolTitle);
        ImageView back= findViewById(R.id.toolBack);
        back.setOnClickListener(v->finish());
        title.setText(categoryTitle);
    }
    private void loadWallpaper(String categoryId) {

        StringRequest request= new StringRequest(Request.Method.POST, Constant.category_view, response -> {

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
                Toast.makeText(CategoryDetails.this, "Error: "+e.toString(), Toast.LENGTH_SHORT).show();

            }


        }, error -> {
            //Error Handling
            Toast.makeText(CategoryDetails.this, "Error: "+error.toString(), Toast.LENGTH_SHORT).show();

        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> dt= new HashMap<>();
                dt.put("id", categoryId);

                return dt;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(CategoryDetails.this);
        queue.add(request);
    }
}