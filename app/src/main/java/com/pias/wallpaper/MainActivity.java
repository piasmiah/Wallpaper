package com.pias.wallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
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

//        Dexter.withContext(this)
//                .withPermissions(
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.READ_CONTACTS,
//                        Manifest.permission.RECORD_AUDIO
//                ).withListener(new MultiplePermissionsListener() {
//                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
//
//                    }
//                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//
//                    }
//                }).check();
    }

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