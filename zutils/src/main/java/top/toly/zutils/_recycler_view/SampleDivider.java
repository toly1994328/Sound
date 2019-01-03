package top.toly.zutils._recycler_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者：张风捷特烈
 * 时间：2018/3/29:14:30
 * 邮箱：1981462002@qq.com
 * 说明：the divider of RecyclerView
 */
public class SampleDivider extends RecyclerView.ItemDecoration {
    //the attrs of divider in android
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;//Drawable of divider

    //the constructor:here,we will get divider's Drawable  by attrs
    public SampleDivider(Context ctx) {
        TypedArray t = ctx.obtainStyledAttributes(ATTRS);
        mDivider = t.getDrawable(0);
        t.recycle();//回收t占用的空间

    }

    //绘制分隔条
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();//列表项距左边缘
        int right = parent.getWidth() - parent.getPaddingRight();//列表项距右边缘

        int childCount = parent.getChildCount();//获取item总数
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);//获得当前列表项
            //获取当前item的布局参数
            RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();//获取当前item的布局参数
            int top = child.getBottom() + params.bottomMargin;//计算分隔条左上角坐标
            int bottom = top + mDivider.getIntrinsicHeight();//计算分隔条右下角坐标
            mDivider.setBounds(left, top, right, bottom);//放置位置
            mDivider.draw(c);//绘制
        }

    }
}
