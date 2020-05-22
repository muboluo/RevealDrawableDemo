package com.dongnao.revealviewdemo;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;

public class RevealDrawable extends Drawable {

	public static final String TAG = RevealDrawable.class.getSimpleName();
    private final Rect mTmpRect = new Rect();
    private Drawable mUnselectedDrawable;
    private Drawable mSelectedDrawable;

    public RevealDrawable(Drawable unselected, Drawable selected) {
        mUnselectedDrawable = unselected;
        mSelectedDrawable = selected;
        //本质上来讲他只是由Drawable提供的参数数值，我们可以把它认为是一个状态
        setLevel(100);
    }

    @Override
    public void draw(Canvas canvas) {

		Log.d(TAG, "draw: start draw");

        Rect r = getBounds();

        Rect temp = new Rect();

        //从一个已有的bound矩形边界范围当中抠出一个我们想要的矩形
        Gravity.apply(
                //从左边扣还是从右边扣
                Gravity.LEFT,
                //目标矩形宽
                r.width() / 2,
                //目标矩形高
                r.height(),
                //被扣的地方
                r,
                //抠出来，扔这里面
                temp
        );

        canvas.save();
        canvas.clipRect(temp);
        mUnselectedDrawable.draw(canvas);
        canvas.restore();

        Gravity.apply(
                //从左边扣还是从右边扣
                Gravity.RIGHT,
                //目标矩形宽
                r.width() / 2,
                //目标矩形高
                r.height(),
                //被扣的地方
                r,
                temp
        );

        canvas.clipRect(temp);
        mSelectedDrawable.draw(canvas);

		Log.d(TAG, "draw: end draw");

	}

    @Override
    protected void onBoundsChange(Rect bounds) {
        // 定好两个Drawable图片的宽高---边界bounds
        mUnselectedDrawable.setBounds(bounds);
        mSelectedDrawable.setBounds(bounds);
        Log.d(TAG, "w = " + bounds.width());
        Log.d(TAG, "h = " + bounds.height());
    }

    @Override
    public int getIntrinsicWidth() {
        //得到Drawable的实际宽度
        Log.d(TAG, "getIntrinsicWidth: selected width : " + mSelectedDrawable.getIntrinsicWidth()
                + " unselected width" + mUnselectedDrawable.getIntrinsicWidth());
        return Math.max(mSelectedDrawable.getIntrinsicWidth(),
                mUnselectedDrawable.getIntrinsicWidth());
    }

    @Override
    public int getIntrinsicHeight() {
        //得到Drawable的实际高度
		Log.d(TAG, "getIntrinsicWidth: selected width : " + mSelectedDrawable.getIntrinsicHeight()
				+ " unselected width" + mUnselectedDrawable.getIntrinsicHeight());

		return Math.max(mSelectedDrawable.getIntrinsicHeight(),
                mUnselectedDrawable.getIntrinsicHeight());
    }

    @Override
    protected boolean onLevelChange(int level) {
        // 当设置level的时候回调---提醒自己重新绘制
        invalidateSelf();
        return true;
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

}
