package com.example.alphadog.mytestapplication.mvp.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alphadog.mytestapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alpha Dog on 2016/12/14.
 */

public class DataBaseContortFragment extends Fragment {
    View mView;
    @BindView(R.id.input_id)
    EditText mInputId;
    @BindView(R.id.input_str)
    EditText mInputStr;
    @BindView(R.id.input_content)
    EditText mInputContent;
    @BindView(R.id.insert)
    Button mInsert;
    @BindView(R.id.delete)
    Button mDelete;
    @BindView(R.id.updata)
    Button mUpdata;
    @BindView(R.id.search)
    Button mSearch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_databasecontort, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.insert, R.id.delete, R.id.updata, R.id.search})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.insert) {
            Toast.makeText(getActivity(), "增加", Toast.LENGTH_SHORT).show();
            Log.d("DataBaseContortFragment", "增加");
        } else if (i == R.id.delete) {
        } else if (i == R.id.updata) {
        } else if (i == R.id.search) {
        }
    }
}