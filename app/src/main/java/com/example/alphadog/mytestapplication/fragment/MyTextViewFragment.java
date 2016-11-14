package com.example.alphadog.mytestapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.alphadog.mytestapplication.R;
import com.example.alphadog.mytestapplication.view.MyTextView;

/**
 * Created by Alpha Dog on 2016/11/8.
 */
public class MyTextViewFragment extends Fragment {
    private View mView;
    private MyTextView mMyTextView;
    private EditText inputEt;
    private Button okBtn;
    private int progress = 0;
    private int maxProgress;
    private boolean turnRun = true;
    private Thread thread;
    private Float radiuo=1f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mytextview, container, false);
        initView();
        return mView;
    }

    private void initView() {
        mMyTextView = (MyTextView) mView.findViewById(R.id.my_textview);
        okBtn = (Button) mView.findViewById(R.id.ok_btn);
        inputEt = (EditText) mView.findViewById(R.id.input_et);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(inputEt.getText().toString()) != null && Integer.valueOf(inputEt.getText().toString()) > 0 && Integer.valueOf(inputEt.getText().toString()) <= 360) {
                    thread.interrupt();
                    turnRun = true;
                    maxProgress = Integer.valueOf(inputEt.getText().toString());
                    progress=0;
                    initView();
                    thread.start();
                }
            }
        });
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    if (turnRun)
                        while (true) {
                            Thread.sleep(10);
                            if (progress == maxProgress) {
                                thread.interrupt();
                                turnRun = false;
                                initView();
                                thread.start();
                                break;
                            }
                            progress++;
                            radiuo=(float)progress/maxProgress;
                            mMyTextView.setText(progress + "");
                            mMyTextView.setProgress(progress);
                            mMyTextView.setRadiuo(radiuo);
                        }
                    if (!turnRun)
                        while (true) {
                            Thread.sleep(10);
                            if (progress <= 0) {
                                thread.interrupt();
                                turnRun = true;
                                initView();
                                thread.start();
                                break;
                            }
                            progress--;
                            radiuo=(float)progress/maxProgress;
                            mMyTextView.setText(progress + "");
                            mMyTextView.setProgress(progress);
                            mMyTextView.setRadiuo(radiuo);
                        }

                } catch (InterruptedException e) {
//                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.interrupt();
    }
}
