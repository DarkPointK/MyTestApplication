package com.example.alphadog.mytestapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.alphadog.mytestapplication.R;

/**
 * Created by Alpha Dog on 2016/11/8.
 */
public class MyTextView extends View {
    private int textColor, textSize, backgroudColor;
    private String text;
    private Paint mPaint;
    private Rect rect;
    private RectF arcRect;
    private int viewHeight, viewWidth;
    private int progress;
    private Float radiuo;
    private Context mContext;

    public MyTextView(Context context) {
        super(context);
        mContext = context;
        if (isInEditMode()) {
            return;
        }
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (isInEditMode()) {
            return;
        }

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        textColor = array.getColor(R.styleable.MyTextView_textColor, Color.BLACK);
        backgroudColor = array.getColor(R.styleable.MyTextView_backgroudColor, Color.WHITE);
        textSize = array.getDimensionPixelSize(R.styleable.MyTextView_textSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
        text = array.getString(R.styleable.MyTextView_text);
        radiuo = 1f;

        array.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        rect = new Rect();
        mPaint.setTextSize(textSize);
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.getTextBounds(text, 0, text.length(), rect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(measuredDimension(widthMeasureSpec, 0), measuredDimension(heightMeasureSpec, 1));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(10f);
        arcRect = new RectF(0, viewHeight / 2 - viewWidth / 2, viewWidth, viewHeight / 2 + viewWidth / 2);
        mPaint.setColor(Color.BLACK);
        canvas.drawArc(arcRect, -90, progress, true, mPaint);
        mPaint.setColor(backgroudColor);
        canvas.drawCircle(viewWidth / 2, viewHeight / 2, radiuo * 15 + (viewWidth / 2) - 20, mPaint);
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        canvas.drawText(text, viewWidth / 2, viewHeight / 2 - mPaint.getFontMetrics().descent + (mPaint.getFontMetrics().descent - mPaint.getFontMetrics().ascent) / 2, mPaint);

        Paint nk_Paint = new Paint();
        nk_Paint.setAntiAlias(true);
        nk_Paint.setColor(Color.BLACK);
        nk_Paint.setStrokeWidth(3f);

        canvas.save();
        int startDst = (int) (getPaddingLeft() + viewHeight / 2 - viewWidth / 2 + (15 - radiuo * 15) + 10);
        int endDst = startDst + 40;
        for (int i = 0; i < 12; i++) {
            canvas.drawLine(viewWidth / 2, startDst, viewWidth / 2, endDst, nk_Paint);
            canvas.rotate(30, viewWidth / 2, viewHeight / 2);
        }
        canvas.restore();
    }

    private int measuredDimension(int measureSpec, int type) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (type == 0) {
                result = getPaddingLeft() + getPaddingRight() + (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, mContext.getResources().getDisplayMetrics());
            } else {
                result = getPaddingTop() + getPaddingBottom() + (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, mContext.getResources().getDisplayMetrics());
            }
        }

        if (type == 0) {
            viewWidth = result;
        } else {
            viewHeight = result;
        }
        return result;
    }

    public void setBackgroudColor(int backgroudColor) {
        this.backgroudColor = backgroudColor;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }

    public void setRadiuo(Float radiuo) {
        this.radiuo = radiuo;
        postInvalidate();
    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

}
