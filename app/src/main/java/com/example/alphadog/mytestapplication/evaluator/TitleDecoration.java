package com.example.alphadog.mytestapplication.evaluator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.alphadog.mytestapplication.mvp.module.RecyclerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alpha Dog on 2016/12/6.
 */

public class TitleDecoration extends RecyclerView.ItemDecoration {
    private List<RecyclerItem> mItems = new ArrayList<>();
    private Paint mPaint;
    private int mTitleHeight ;
    private int mTitleTextSize;

    public TitleDecoration(List<RecyclerItem> mItems, Context context) {
        this.mItems = mItems;
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        mTitleTextSize=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,14,context.getResources().getDisplayMetrics());
        mPaint.setTextSize(mTitleTextSize);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (mItems == null || mItems.isEmpty() || position > mItems.size() - 1) {//pos为1，size为1，1>0? true
            return;//越界
        }
        //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
        if (position > -1) {
            if (position == 0) {//等于0肯定要有title的
                outRect.set(0, mTitleHeight, 0, 0);
            } else {//其他的通过判断
                if (!mItems.get(position).getAbb().equals(mItems.get(position -1).getAbb())) {
                    outRect.set(0, mTitleHeight, 0, 0);//不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }

    //绘制固定的title
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int left = parent.getPaddingLeft();
        int right = parent.getRight()-parent.getPaddingRight();
        for (int i = 0; i < parent.getChildCount(); i++) {
            //i是正显示在屏幕中的子view的序号，完全版的序号需要通过getChildLayoutPosition获取
            int position = parent.getChildLayoutPosition(parent.getChildAt(i));
            if(mItems!=null&&mItems.size()>0)
            if (position == 0) {
                drawTitleRec(c, left, right, parent.findViewHolderForLayoutPosition(position).itemView, position);
            } else if(position>-1){
                if (!mItems.get(position).getAbb().equals(mItems.get(position -1).getAbb())) {
                    drawTitleRec(c, left, right, parent.findViewHolderForLayoutPosition(position).itemView, position);
                }
            }
        }
    }

    //绘制随滑动而滚动的title
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        super.onDrawOver(c, parent, state);
    }

    private void drawTitleRec(Canvas c, int left, int right, View child, int pos) {
        mPaint.setColor(Color.BLUE);
        c.drawRect(left, child.getTop()- mTitleHeight, right, child.getTop(),mPaint);

        Log.d("TitleDecoration", child.getTop() + " " + (child.getTop()+ mTitleHeight)+" "+pos);

        mPaint.setColor(Color.WHITE);
        mPaint.getTextBounds(mItems.get(pos).getAbb(), 0, mItems.get(pos).getAbb().length(), new Rect());
        c.drawText(mItems.get(pos).getAbb(),mTitleHeight/2, mTitleHeight / 2 - mPaint.getFontMetrics().descent + (mPaint.getFontMetrics().descent - mPaint.getFontMetrics().ascent) / 2+child.getTop()-mTitleHeight, mPaint);
    }
}
