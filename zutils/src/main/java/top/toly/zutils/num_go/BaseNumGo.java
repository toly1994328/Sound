package top.toly.zutils.num_go;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

/**
 * 作者：张风捷特烈
 * 时间：2018/7/8:19:37
 * 邮箱：1981462002@qq.com
 * 说明：让数字在0~1跑
 */
public abstract class BaseNumGo {
    private static final String TAG = "RunNum";

    public ValueAnimator valueAnimator;
    protected boolean isReverse = true;//是否翻转
    protected int repeatCount = 2;//重复次数
    protected TimeInterpolator mInterpolator = new LinearInterpolator();//插值器

    protected int time = 2;//
    protected int mCount = 0;
    protected float mRate = 0;
    protected long mDelay = 0;

    public BaseNumGo(boolean isReverse, int repeatCount, int time) {
        this.isReverse = isReverse;
        this.repeatCount = repeatCount;
        this.time = time;
    }

    public BaseNumGo(int time) {
        this(false, 0, time);
    }

    public BaseNumGo() {
        this(false, 0, 5000);
    }

    /**
     * 开启动画
     */
    public void go(long startDelay) {
        setDelay(startDelay);
        end();
        startViewAnim(0f, 1f, time);
    }

    /**
     * 开启动画
     */
    public void go() {
        go(0L);
    }
    /**
     * 结束动画
     */
    public void end() {
        if (valueAnimator != null) {
//            clearAnimation();
            valueAnimator.setRepeatCount(0);
            valueAnimator.cancel();
//            valueAnimator.end();
            if (onStop() == 0) {
                valueAnimator.setRepeatCount(0);
                valueAnimator.cancel();
//                valueAnimator.end();
            }

        }
    }

    /**
     * 开启动画
     *
     * @param startF 0-1 100
     * @param endF
     * @param time
     * @return
     */
    private ValueAnimator startViewAnim(float startF, final float endF, long time) {
        valueAnimator = ValueAnimator.ofFloat(startF, endF);//设置数字区域
        valueAnimator.setDuration(time);//设置时长

        valueAnimator.setInterpolator(mInterpolator);//设置插值器
        valueAnimator.setStartDelay(mDelay);
        valueAnimator.setRepeatCount(repeatCount);//设置重复次数

        if (isReverse) {//设置重复模式
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);//0-->1--|1-->0
        } else {
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);//0-->1--|0-->1
        }


        //添加更新监听
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mRate = (float) valueAnimator.getAnimatedValue();
                onUpdate((float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                end();
                mCount = 0;


            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                onRepeat(mCount);
                mCount++;
            }
        });

        if (!valueAnimator.isRunning()) {
            valueAnimator.start();

        }

        return valueAnimator;
    }


    protected abstract void onUpdate(float rate);

    protected abstract void onRepeat(int count);

    protected abstract int onStop();


    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public void setReverse(boolean reverse) {
        isReverse = reverse;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public TimeInterpolator getInterpolator() {
        return mInterpolator;
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        mInterpolator = interpolator;
    }

    public void setDelay(long delay) {
        mDelay = delay;
    }
}