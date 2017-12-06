package com.example.alphadog.mytestapplication.mvp.view

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.view.View
import com.example.alphadog.mytestapplication.R
import kotlinx.android.synthetic.main.activity_coordinator.*

class ScrollingActivity : BaseActivity() {
    val TAG = "ScrollingActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)
        ctbl.title = "这么6的吗"
        appbar.addOnOffsetChangedListener() { appBarLayout: AppBarLayout?, verticalOffset: Int ->
            if (verticalOffset == 0)
                tvTitle.visibility = View.GONE
            else {
                tvTitle.visibility = View.VISIBLE
                tvTitle.alpha= Math.abs(verticalOffset)/appbar.totalScrollRange.toFloat()
            }
        }
    }
}
