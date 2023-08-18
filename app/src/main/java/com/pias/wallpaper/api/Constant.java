package com.pias.wallpaper.api;

import com.pias.wallpaper.model.Wallpapers;

import java.util.ArrayList;
import java.util.List;

public class Constant {

    public static String base= "http://192.168.43.108/wallpaper/api/";
    public static String home= base+ "home.php";
    public static String latest=base+ "latest.php";
    public static String category=base+ "category.php";
    public static String category_view=base+ "category_view.php";
    public static String databaseName= "favoriteDatabase";
    public static String saveDirectory= "wallpapers/";

    public static List<Wallpapers> tempList= new ArrayList<>();

}
