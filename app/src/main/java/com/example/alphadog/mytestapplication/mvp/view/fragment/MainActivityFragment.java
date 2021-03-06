package com.example.alphadog.mytestapplication.mvp.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alphadog.mytestapplication.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        Bundle b=getArguments();
        if(b!=null&&b.getString("type")!=null)
        if (b.getString("type").equals("ball")) {
            setBall();
        }
                return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setBall() {
        view.findViewById(R.id.MyAnimView).setVisibility(View.VISIBLE);
        view.findViewById(R.id.heart).setVisibility(View.GONE);
    }
}
