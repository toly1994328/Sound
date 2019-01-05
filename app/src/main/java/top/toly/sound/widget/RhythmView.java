package top.toly.sound.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/16 0016:9:04<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：贝塞尔三次曲线--旋律视图
 */
public class RhythmView extends View {
    private double mMaxHeight = 0;//最到点
    private double mPerHeight = 0;//最到点

    private double min;//最小x
    private double max;//最大x

    private double φ = 0;//初相
    private double A = mMaxHeight;//振幅
    private double ω;//角频率

    private Paint mPaint;//主画笔
    private Path mPath;//主路径
    private Path mReflexPath;//镜像路径
    private ValueAnimator mAnimator;
    private int mHeight;
    private int mWidth;

    public RhythmView(Context context) {
        this(context, null);
    }

    public RhythmView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();//初始化
    }

    private void init() {
        //初始化主画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dp(2));
        //初始化主路径
        mPath = new Path();
        mReflexPath = new Path();
        //数字时间流
        mAnimator = ValueAnimator.ofFloat(0, (float) (2 * Math.PI));
        mAnimator.setDuration(1000);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(a -> {
            φ = (float) a.getAnimatedValue();
            A = (float) (mMaxHeight * mPerHeight * (1 - (float) a.getAnimatedValue() / (2 * Math.PI)));
            invalidate();
        });
    }

    /**
     * 设置高的百分比
     * @param perHeight
     */
    public void setPerHeight(double perHeight) {
        mPerHeight = perHeight;
        mAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mMaxHeight = mHeight / 2 * 0.9;
        min = -mWidth / 2;
        max = mWidth / 2;
        handleColor();
        setMeasuredDimension(mWidth, mHeight);
    }


    private void handleColor() {
        int[] colors = new int[]{
                Color.parseColor("#33F60C0C"),//红
                Color.parseColor("#F3B913"),//橙
                Color.parseColor("#E7F716"),//黄
                Color.parseColor("#3DF30B"),//绿
                Color.parseColor("#0DF6EF"),//青
                Color.parseColor("#0829FB"),//蓝
                Color.parseColor("#33B709F4"),//紫
        };

        float[] pos = new float[]{
                1.f / 10, 2.f / 7, 3.f / 7, 4.f / 7, 5.f / 7, 9.f / 10, 1
        };

        mPaint.setShader(
                new LinearGradient(
                        (int) min, 0, (int) max, 0,
                        colors, pos,
                        Shader.TileMode.CLAMP
                ));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();
        mReflexPath.reset();
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        formPath();
        mPaint.setAlpha(255);
        canvas.drawPath(mPath, mPaint);
        mPaint.setAlpha(66);
        canvas.drawPath(mReflexPath, mPaint);
        canvas.restore();
    }

    /**
     * 对应法则
     *
     * @param x 原像(自变量)
     * @return 像(因变量)
     */
    private double f(double x) {
        double len = max - min;
        double a = 4 / (4 + Math.pow(rad(x / Math.PI * 800 / len), 4));
        double aa = Math.pow(a, 2.5);
        ω = 2 * Math.PI / (rad(len) / 2);
        double y = aa * A * Math.sin(ω * rad(x) - φ);
        return y;
    }

    private void formPath() {
        mPath.moveTo((float) min, (float) f(min));
        mReflexPath.moveTo((float) min, (float) f(min));
        for (double x = min; x <= max; x++) {
            double y = f(x);
            mPath.lineTo((float) x, (float) y);
            mReflexPath.lineTo((float) x, -(float) y);
        }

    }

    private double rad(double deg) {
        return deg / 180 * Math.PI;
    }

    protected float dp(float dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}
