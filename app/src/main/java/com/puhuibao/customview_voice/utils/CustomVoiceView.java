package com.puhuibao.customview_voice.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.puhuibao.customview_voice.R;

/**
 * Created by mahao on 16/7/12.
 */
public class CustomVoiceView extends View {


    private Paint mPaint;
    private int mCentre;
    private int mRadius;
    private int mDotCount;
    private int mImgDivide;
    private int mMFirstColor;
    private int mMSecondColor;

    // 定义当前的精度
    private int mcurrentCount = 3;
    private float mCircleWidth;
    private Bitmap mImge;
    private Rect mRect;

    public CustomVoiceView(Context context) {
        this(context, null);
    }

    public CustomVoiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initData(context, attrs, defStyleAttr);
    }

    private void initData(Context context, AttributeSet attrs, int defStyleAttr) {


        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomVolumControbar, defStyleAttr, 0);
        mMFirstColor = array.getColor(R.styleable.CustomVolumControbar_firstColor, Color.GREEN);
        mMSecondColor = array.getColor(R.styleable.CustomVolumControbar_secondColor, Color.RED);
        mCircleWidth = array.getDimension(R.styleable.CustomVolumControbar_circleWidth, 10);
        mDotCount = array.getInt(R.styleable.CustomVolumControbar_dotCount, 20);
        mImgDivide = array.getInt(R.styleable.CustomVolumControbar_splitSize, 20);
        mImge = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.CustomVolumControbar_bg, 0));

        array.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStrokeWidth(mCircleWidth);  //设置圆环的宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND); //定义线段形状为圆头
        mPaint.setStyle(Paint.Style.FILL);  //设置为空心
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获得圆心的坐标
        mCentre = getWidth() / 2;

        Log.d("mahao","中心点坐标"+mCentre);
        //半径
        mRadius = (int) (mCentre - mCircleWidth / 2);
        mRect = new Rect();
        //画滑块
        drawOval(canvas, mCentre, mRadius);
        //获得内圆的半径
        int relRadius = (int) (mRadius - mCircleWidth / 2);

        Log.d("custom","内切圆:"+relRadius + "外圆半径:"+ mRadius);

        //计算内切正方形的位置
        mRect.left = (int) ((int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth);
        mRect.top = (int) ((mRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth);
        mRect.bottom = (int) (mRect.left + Math.sqrt(2) * relRadius);
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);
        Log.d("custom", mRect.left+"..."+ mRect.top+"...."+ mRect.right+"..."+ mRect.bottom+"....");

        //如果图片比较小,将图片放到正中心
        if (mImge.getWidth() < Math.sqrt(2) * relRadius) {

            mRect.left = (int) (mRect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - mImge.getWidth() * 1.0f / 2);
            mRect.top = (int) (mRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - mImge.getHeight() * 1.0f / 2);
            mRect.right = mRect.left + mImge.getWidth();
            mRect.bottom = mRect.top + mImge.getHeight();
        }

        Log.d("custom", mRect.left+"..."+ mRect.top+"...."+ mRect.right+"..."+ mRect.bottom);

        //绘图
        canvas.drawBitmap(mImge, null, mRect, mPaint);
    }


    //根据参数画出每个小块
    public void drawOval(Canvas canvas, int centre, int radius) {

        //每一个滑块的角度
        float itemSize = (360 * 1.0f - mDotCount * mImgDivide) / mDotCount;

        //用于定义的圆弧的形状和大小的界限
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);

        mPaint.setColor(mMFirstColor); // 设置圆环的颜色

        for (int i = 0; i < mDotCount; i++) {

            //
            canvas.drawArc(oval, (itemSize + mImgDivide) * i, itemSize, true, mPaint); //依据进度话圆弧
        }

        mPaint.setColor(mMSecondColor); //设置圆环的颜色

        for (int i = 0; i < mcurrentCount; i++) {

            canvas.drawArc(oval, (itemSize + mImgDivide) * i, itemSize, true, mPaint);
        }
    }

    public void setCurrentTime(int count){

        mcurrentCount = count;
    }
}







