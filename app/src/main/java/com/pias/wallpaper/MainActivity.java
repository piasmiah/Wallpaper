package com.pias.wallpaper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.pias.wallpaper.fragments.CategoryFrag;
import com.pias.wallpaper.fragments.FavouriteFrag;
import com.pias.wallpaper.fragments.HomeFrag;
import com.pias.wallpaper.fragments.LatestFrag;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);
        bottomNavigationView= findViewById(R.id.bottom_nav);
        toolbar= findViewById(R.id.toolbar);

        setSidebar();
        setBottom();
        //dataLoad();
    }

//    private void dataLoad() {
//                Dexter.withContext(this)
//                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                        Intent intent = new Intent();
//                        intent.setType("image/*");
//                        //  intent.setType("pdf/docs/ppt/*");
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
//                        startActivityForResult(intent, 101);
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//                        Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                        permissionToken.continuePermissionRequest();
//
//                    }
//                }).check();
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 101 && resultCode == RESULT_OK) {
//            imageUri = data.getData();
//            imageIv.setImageURI(imageUri);
//        }
//
//    }

    private void setBottom(){

        getSupportFragmentManager().beginTransaction().replace(R.id.frage_container, new HomeFrag()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFrag= new HomeFrag();

                switch (item.getItemId()){
                    case R.id.bottom_home:
                        selectedFrag= new HomeFrag();
                        break;
                    case R.id.bottom_cat:
                        selectedFrag= new CategoryFrag();
                        break;
                    case R.id.bottom_lat:
                        selectedFrag= new LatestFrag();
                        break;
                    case R.id.bottom_favo:
                        selectedFrag= new FavouriteFrag();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frage_container, selectedFrag).commit();

                return true;
            }
        });
    }

    private void setSidebar(){

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_developer:

                        break;
                    case R.id.nav_share:
                        break;
                    case R.id.nav_rate:
                        break;
                    case R.id.nav_policy:
                        startActivity(new Intent(MainActivity.this, PrivacyPolicy.class));
                        break;
                    case R.id.nav_apps:
                        break;
                }
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                return true;
            }
        });
    }

    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}