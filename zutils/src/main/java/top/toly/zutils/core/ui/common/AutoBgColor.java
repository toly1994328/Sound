package top.toly.zutils.core.ui.common;

import android.os.Handler;
import android.view.View;

/**
 * 作者：张风捷特烈
 * 时间：2018/4/12:12:16
 * 邮箱：1981462002@qq.com
 * 说明：使用Handler实现：给一个View设置自动颜色变化范围的效果
 */
public class AutoBgColor {

    private Handler mHandler = new Handler();
    private int current = 0;
    private boolean isColorOver = false;

    private View mView;
    private int mStartColor;
    private int mEndColor;
    private int mTime;
    private ColorChangeRunnable mColorChangeRunnable;


    public AutoBgColor(View view, int startColor, int endColor,int time) {
        mView = view;
        mStartColor = startColor;
        mEndColor = endColor;
        mTime = time;
        start();

    }

    private ColorChangeRunnable start() {
        new Thread() {
            @Override
            public void run() {
                mColorChangeRunnable = new ColorChangeRunnable();
                mHandler.post(new ColorChangeRunnable());

            }
        }.start();

        return mColorChangeRunnable;
    }


    class ColorChangeRunnable implements Runnable {

        @Override
        public void run() {

            if (current > 100) {
                current = 0;
                isColorOver = !isColorOver;
            }
            if (!isColorOver) {
                mView.setBackgroundColor((Integer)ColUtils.evaluateColor(current / 100f, mStartColor, mEndColor));
            } else {
                mView.setBackgroundColor((Integer) ColUtils.evaluateColor(current / 100f, mEndColor, mStartColor));
            }
            current = ++current;
            mHandler.postDelayed(this, mTime);
        }
    }
}
