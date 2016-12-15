package com.example.alphadog.mytestapplication.mvp.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alphadog.mytestapplication.R;

/**
 * Created by Alpha Dog on 2016/12/14.
 */

public class DataBaseContortFragment extends Fragment{
    View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         mView=inflater.inflate(R.layout.fragment_databasecontort,container,false);
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}