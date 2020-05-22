package com.dongnao.revealviewdemo;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;

public class RevealDrawable2 extends Drawable {

    public static final String TAG = RevealDrawable2.class.getSimpleName();

    private Drawable selectedDrawable;
    private Drawable unSelectedDrawable;

    public RevealDrawable2(Drawable selectedDrawable, Drawable unSelectedDrawable) {

        this.selectedDrawable = selectedDrawable;
        this.unSelectedDrawable = unSelectedDrawable;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        Log.d(TAG, "draw: start draw");
        // 1 获取 宽高

        final Rect bounds = getBounds();

        // 2 将左半边按照未选择的区域裁剪出来
        Rect drawableRectLeft = new Rect();
        Gravity.apply(Gravity.LEFT, bounds.width() / 2, bounds.height(), bounds, drawableRectLeft);
        // 3 选中左边裁剪出区域的canvas
        canvas.save();
        canvas.clipRect(drawableRectLeft);

        // 4 将未选中的drawable 绘制到canvas上。
        unSelectedDrawable.draw(canvas);

        // 5 右边的选中drawable 同第 2-4 步
        Rect drawableRectRight = new Rect();
        Gravity.apply(Gravity.RIGHT, bounds.width() / 2, bounds.height(), bounds, drawableRectRight);

        canvas.restore();
        canvas.clipRect(drawableRectRight);
        selectedDrawable.draw(canvas);

        Log.d(TAG, "draw: end draw");

    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        unSelectedDrawable.setBounds(bounds);
        selectedDrawable.setBounds(bounds);
        Log.d(TAG, "w = " + bounds.width());
        Log.d(TAG, "h = " + bounds.height());
    }

    @Override
    public int getIntrinsicHeight() {
        Log.d(TAG, "getIntrinsicHeight: " + selectedDrawable.getIntrinsicHeight());
        return Math.max(selectedDrawable.getIntrinsicHeight(), unSelectedDrawable.getIntrinsicHeight());
    }

    @Override
    public int getIntrinsicWidth() {
        Log.d(TAG, "getIntrinsicWidth: " + selectedDrawable.getIntrinsicWidth());
        return Math.max(selectedDrawable.getIntrinsicHeight(), unSelectedDrawable.getIntrinsicHeight());
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

}
