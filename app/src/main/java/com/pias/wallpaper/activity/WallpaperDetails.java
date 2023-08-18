package com.pias.wallpaper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.pias.wallpaper.R;
import com.pias.wallpaper.adapter.DetailsAdapter;
import com.pias.wallpaper.api.Constant;
import com.pias.wallpaper.model.ClickAction;
import com.pias.wallpaper.model.Wallpapers;
import com.pias.wallpaper.room.FavoriteFire;
import com.pias.wallpaper.room.FavoriteModel;
import com.pias.wallpaper.room.FavoriteQuery;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class WallpaperDetails extends AppCompatActivity {
    int position;
    List<Wallpapers> list = new ArrayList<>();
    DetailsAdapter adapter;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    boolean isShare= false;
    boolean isSet= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_details);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("pos");

        list.addAll(Constant.tempList);
        adapter = new DetailsAdapter(list, WallpaperDetails.this, this::clickAction);

        recyclerView = findViewById(R.id.recycle_wallpaperDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(position);
    }


    private void clickAction(String url, String type) {
        isShare= false;
        isSet= false;

        if (type.contains("favorite")) {
            new AddFavorite(url).start();

        } else if (type.contains("share")) {
            isShare= true;
            new DownloadTask().execute(url);
            
        } else if (type.contains("set")) {
            isSet= true;
            new DownloadTask().execute(url);
            
        } else {
            new DownloadTask().execute(url);
        }
    }


    public class AddFavorite extends Thread {
        FavoriteFire database = Room.databaseBuilder(WallpaperDetails.this, FavoriteFire.class, Constant.databaseName).allowMainThreadQueries().build();
        FavoriteQuery query = database.favoriteQuery();

        String url;

        public AddFavorite(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            super.run();

            try {
                query.insertData(new FavoriteModel(url));
                runOnUiThread(() -> Toast.makeText(WallpaperDetails.this, "Added to Favorite...", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(WallpaperDetails.this, "Failed to Add!", Toast.LENGTH_SHORT).show());
            }
        }
    }
    class DownloadTask extends AsyncTask<String, Integer, String>{
        String image_path;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog= new ProgressDialog(WallpaperDetails.this);
            progressDialog.setTitle("Downloading....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String path= strings[0];
            int file_length= 0;

            try {
                URL url= new URL(path);
                URLConnection urlConnection= url.openConnection();
                urlConnection.connect();
                file_length= urlConnection.getContentLength();

                File file= new File(Environment.getExternalStorageDirectory()+"/Pictures/"+Constant.saveDirectory);
                if (!file.exists()){
                    file.mkdirs();
                }
                String time= String.valueOf(System.currentTimeMillis());
                image_path= file+ getString(R.string.app_name)+"_"+time+".jpg";

                File input_file= new File(image_path);
                InputStream inputStream= new BufferedInputStream(url.openStream(), 8192);
                byte[] data= new byte[1024];
                int total= 0;
                int count= 0;
                OutputStream outputStream= new FileOutputStream(input_file);
                while ((count= inputStream.read(data))!= -1){
                    total+= count;
                    outputStream.write(data, 0, count);
                    int progress= (int) total*100/file_length;
                    publishProgress(progress);
                }
                inputStream.close();
                outputStream.close();

            }
            catch (Exception e){

            }

            return "Download Completed!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            if (isShare){
                shareImage(image_path);

            } else if (isSet) {
                setImage(image_path);

            }
            else {
                runOnUiThread(() -> Toast.makeText(WallpaperDetails.this, "Downloaded....", Toast.LENGTH_SHORT).show());
            }
        }
    }

    private void setImage(String image_path) {
        File file= new File(image_path);
        Uri uri= FileProvider.getUriForFile(this, getPackageName()+".provider", file);

        Intent intent= new Intent(Intent.ACTION_ATTACH_DATA);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("mimeType", "image/*");

        try {
            startActivity(Intent.createChooser(intent, "Set As"));

        }
        catch (Exception e){
            Toast.makeText(this, "Error Can't Set Image : "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void shareImage(String image_path) {
        File file= new File(image_path);
        Uri uri= FileProvider.getUriForFile(this, getPackageName()+".provider", file);

        Intent intent= new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Share Via"+getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/jpg");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(Intent.createChooser(intent, "Set As"));

        }
        catch (Exception e){
            Toast.makeText(this, "Error Can't Set Image : "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}

