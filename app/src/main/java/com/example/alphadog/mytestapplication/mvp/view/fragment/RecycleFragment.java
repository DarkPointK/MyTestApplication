package com.example.alphadog.mytestapplication.mvp.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.alphadog.mytestapplication.R;
import com.example.alphadog.mytestapplication.adapter.RecycleAdapter;
import com.example.alphadog.mytestapplication.evaluator.TitleDecoration;
import com.example.alphadog.mytestapplication.mvp.module.RecyclerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alpha Dog on 2016/12/5.
 */

public class RecycleFragment extends Fragment {
    private RecyclerView mRecycle;
    private ScrollView mScrollView;
    private View mView;
    private RecycleAdapter mRecycleAdapter;

    private List<RecyclerItem> mItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_recycle, container, false);
        initData();
        initView();
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    void initData() {
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 10; y++) {
                RecyclerItem item = new RecyclerItem();
                item.setTitle(x + "");
                item.setContent(y + "");
                item.setAbb(x + "");
                mItems.add(item);
            }
    }

    void initView() {
        mRecycle = (RecyclerView) mView.findViewById(R.id.recycle);
        mRecycleAdapter = new RecycleAdapter(getActivity(), mItems);
        mRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycle.setAdapter(mRecycleAdapter);
        mRecycle.addItemDecoration(new TitleDecoration(mItems, getActivity()));
    }

}
