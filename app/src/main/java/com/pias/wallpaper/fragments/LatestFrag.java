package com.pias.wallpaper.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pias.wallpaper.R;

public class LatestFrag extends Fragment {

    View layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        layout= inflater.inflate(R.layout.fragment_latest, container, false);

        return layout;
    }
}