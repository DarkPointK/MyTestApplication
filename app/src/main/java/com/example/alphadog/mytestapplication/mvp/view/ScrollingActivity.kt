package com.example.alphadog.mytestapplication.mvp.view

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.alphadog.mytestapplication.R
import kotlinx.android.synthetic.main.activity_coordinator.*

class ScrollingActivity : AppCompatActivity() {
    val TAG = "ScrollingActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)
        ctbl.title = "666"
        appbar.addOnOffsetChangedListener() { appBarLayout: AppBarLayout?, verticalOffset: Int ->
            if (verticalOffset == 0)
                tvTitle.visibility = View.GONE
            else
                tvTitle.visibility = View.VISIBLE
        }
    }
}
