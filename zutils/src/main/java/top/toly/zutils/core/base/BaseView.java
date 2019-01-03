package top.toly.zutils.core.base;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Date;

import top.toly.zutils.core.domain.Pos;
import top.toly.zutils.core.shortUtils.Change;
import top.toly.zutils.logic.Logic;

import static top.toly.zutils.core.ui.common.ScrUtil.getScreenHeight;
import static top.toly.zutils.core.ui.common.ScrUtil.getScreenWidth;

/**
 * 作者：张风捷特烈
 * 时间：2018/7/10:9:52
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public abstract class BaseView extends View {

    protected Pos mWinSize = new Pos(getScreenWidth(getContext()), getScreenHeight(getContext()));
    private boolean isOnTouchEvent = true;


    public BaseView(Context context) {
        this(context, null, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        init();
    }


    public float dip2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public float getFontlength(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    public float getFontHeight(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();

    }

    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }


    protected Pos p0 = new Pos(0, 0);//最后一次坐标点
    protected Pos p = new Pos(0, 0);//最后一次坐标点
    protected float dy = 0;//下移总量
    protected float dx = 0;//右移总量
    protected double vMAX;//最大速度
    protected boolean isDown = false;//是否按下
    protected boolean isMove = false;//是否移动
    protected boolean isToLeft = false;//是否向左滑
    protected boolean isToLR = false;//是否左右滑动
    protected boolean isToDown = false; //是否向下滑
    protected int speedLever = 0;//滑动速度等级
    protected Pos downP = new Pos(0, 0);//滑动速度等级

    long lastTimestamp = 0L;//最后一次的时间戳
    protected Pos tempP0 = new Pos(0, 0);//临时点--记录按下时点
    protected double tempV = 0;


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        parseEvent(event);
        return isOnTouchEvent;
    }


    public void setOnTouchEvent(boolean onTouchEvent) {
        isOnTouchEvent = onTouchEvent;
    }

    /**
     * 添加自己的事件解析
     *
     * @param event
     */
    public void parseEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x0 = event.getX();
                float y0 = event.getY();
                p0.x = x0;
                p0.y = y0;
                tempP0 = p0;
                downP = p0;
                lastTimestamp = new Date().getTime();
                isDown = true;
                if (mOnEventListener != null) {
                    mOnEventListener.down();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                p = new Pos(x, y);//最后一次坐标点

                long curTimestamp = new Date().getTime();
                long t = curTimestamp - lastTimestamp;
                double s = Logic.disPos2d(p0, p);
                double v = s / t * 1000;
                //慢速 LEVER 1:   v < 500
                //稍快 LEVER 2:   v > 500 && v < 1000
                //快速 LEVER 3:   v > 1000 && v < 4000
                //极速 LEVER 4:   v > 4000
                vMAX = tempV > v ? tempV : v;

                if (vMAX > 500) {
                    speedLever = 1;
                }
                if (vMAX > 500 && vMAX < 1500) {
                    speedLever = 2;
                }
                if (vMAX > 1500 && vMAX < 4000) {
                    speedLever = 3;
                }
                if (vMAX > 4000) {
                    speedLever = 4;
                }
                float dataX = (float) (p.x - p0.x);
                float dataY = (float) (p.y - p0.y);
                dx = (float) (p.x - tempP0.x);
                dy = (float) (p.y - tempP0.y);


                //x的移动量大于y,并且x有一定的移动量，此时向左或向右
                if (Math.abs(dx) > Math.abs(dy) && Math.abs(dx) > Change.dp2px(getContext(), 8)) {
                    isToLeft = dx < 0;
                    isToLR = true;
                } else {
                    isToDown = dy > 0;
                    isToLR = false;
                    isToLeft = false;
                }
                if (mOnEventListener != null) {
                    mOnEventListener.move();
                }
                if (Math.abs(dy) > 50) {
                    isMove = true;
                }
                p0 = p;//更新位置
                lastTimestamp = curTimestamp;//更新时间
                tempV = vMAX;
                break;
            case MotionEvent.ACTION_UP:

                if (mOnEventListener != null) {
                    mOnEventListener.up();
                }
                isDown = false;
                //重置：tempP0
                tempP0.x = 0.f;
                tempP0.y = 0.f;
                tempV = 0;
                speedLever = 0;
                break;
        }
    }

    public abstract void init();

    public interface OnEventListener {
        void down();

        void up();

        void move();
    }

    private OnEventListener mOnEventListener;

    public void setOnEventListener(OnEventListener onEventListener) {
        mOnEventListener = onEventListener;
    }

}
