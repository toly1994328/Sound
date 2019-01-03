package top.toly.zutils.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import top.toly.zutils.core.domain.DrawInfo;
import top.toly.zutils.core.domain.Pos;
import top.toly.zutils.core.ui.common.ScrUtil;
import top.toly.zutils.logic.Logic;


/**
 * 作者：张风捷特烈
 * 时间：2018/7/6:10:43
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class MyCanvas {
    private static final String TAG = "MyCanvas";
    private Canvas mCanvas;

    public MyCanvas(Canvas canvas) {
        mCanvas = canvas;
    }

    /**
     * 为绘制信息设置初始值
     *
     * @param info
     */
    public void setDrawInfo(DrawInfo info) {
        //移动量
        if (!Logic.isExist(info.p)) {
            info.p = new Pos(0, 0);
        }
        //旋转量
        if (!Logic.isExist(info.rot)) {
            info.rot = 0.f;
        }
        //锚点
        if (!Logic.isExist(info.a)) {
            info.a = new Pos(0, 0);
        }

        //线粗
        if (!Logic.isExist(info.b)) {
            info.b = 2.f;
        }
        //线条颜色
        if (!Logic.isExist(info.ss)) {
            info.ss = Color.BLACK;
        }

        //移动量
        if (!Logic.isExist(info.num)) {
            info.num = 5;
        }
        //五角星外接圆半径
        if (!Logic.isExist(info.R)) {
            info.R = 100.f;
        }

        //五角星内接圆半径
        if (!Logic.isExist(info.r)) {
            info.r = 50.f;
        }


    }

    /**
     * 绘制入口
     *
     * @param info
     */
    public void s2r(DrawInfo info, final Path path) {
        mCanvas.save();

        setDrawInfo(info);


        mCanvas.translate((float) (info.p.x + 0), (float) (info.p.y + 0));

        mCanvas.translate((info.a.x).floatValue(), (info.a.y).floatValue());
        mCanvas.rotate(info.rot.floatValue());
        mCanvas.translate(-(info.a.x).floatValue(), -(info.a.y).floatValue());


        this.setOnPrepared(new OnPrepared() {
            @Override
            public void draw(Paint paint) {
                if (path != null) {
                    mCanvas.drawPath(path, paint);
                }
            }
        });


        if (mOnPrepared != null) {


            Paint paint = new Paint();
            paint.setAntiAlias(true);//抗锯齿

            if (Logic.isExist(info.ss)) {
                paint.setColor(info.ss);
                paint.setStyle(Paint.Style.STROKE);
            }

            if (Logic.isExist(info.fs)) {
                paint.setColor(info.fs);
                paint.setStyle(Paint.Style.FILL);
            }

            paint.setStrokeWidth((info.b).intValue());
            mOnPrepared.draw(paint);

        }

        mCanvas.restore();
    }


    /**
     * 将图形图形打包在一起移动
     *
     * @param px
     * @param py
     * @param drawInfos
     */
    public void groupMove(Float px, Float py, DrawInfo... drawInfos) {
        for (DrawInfo info : drawInfos) {
            info.p = new Pos(px, py);
        }
    }

    /**
     * 将图形图形打包在一起旋转
     *
     * @param ax
     * @param ay
     * @param rot
     * @param drawInfos
     */
    public void groupRot(Float ax, Float ay, Float rot, DrawInfo... drawInfos) {
        for (DrawInfo info : drawInfos) {
            info.a = new Pos(ax, ay);
            info.rot = rot;
        }
    }


    /**
     * 绘制线
     *
     * @param info
     */
    public void drawLine(DrawInfo info) {
        s2r(info, ShapePath.linePath(info));
    }

    /**
     * 绘制线多点 //add-toly:2018-7-7 09:49:50
     *
     * @param info
     */
    public void drawLines(DrawInfo info, Pos... poss) {
        s2r(info, ShapePath.linesPath(poss));

    }

    /**
     * 绘制圆
     *
     * @param info
     */
    public void drawCircle(DrawInfo info) {

        s2r(info, ShapePath.circlePath(info));

    }

    /**
     * 绘制弧
     *
     * @param info
     */
    public void drawArc(DrawInfo info) {

        s2r(info, ShapePath.arcPath(info));

    }

    /**
     * 绘制正n边形
     *
     * @param info
     */
    public void drawTrg(DrawInfo info) {

        s2r(info, ShapePath.trgPath(info));
    }

    /**
     * 绘制正n边形
     *
     * @param info
     */
    public void drawRect(DrawInfo info) {
        float width = info.x.floatValue();
        float height = info.y.floatValue();
        float r = info.r.floatValue();
        boolean exist = Logic.isExist(info.fs);

        if (exist) {

            drawLines(info,
                    new Pos(0, r), new Pos(width - r, 0),
                    new Pos(width, height - r), new Pos(r, height));
        }

        s2r(info, ShapePath.rectPath(info));

    }


    /**
     * 绘制n角星
     *
     * @param info
     */
    public void drawNStar(DrawInfo info) {

        s2r(info, ShapePath.starPath(info));
    }

    /**
     * 绘制正n角星
     *
     * @param info
     */
    public void drawRegularStar(DrawInfo info) {

        s2r(info, ShapePath.regularStarPath(info));
    }

    /**
     * 绘制正n边形
     *
     * @param info
     */
    public void drawRegularPolygon(DrawInfo info) {

        s2r(info, ShapePath.regularPolygonPath(info));
    }

    /**
     * 获取画笔：DrawUtils.getPaint(Color.GREEN, Paint.Style.STROKE, 10);
     *
     * @param color 画笔颜色
     * @param style 画笔样式
     * @param width 画笔宽
     * @return
     */
    public static Paint getPaint(int color, Paint.Style style, int width) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }

    public static Paint getPaint(int color) {
        return getPaint(color, Paint.Style.FILL_AND_STROKE, 2);
    }

    public static Paint getPaint() {
        return getPaint(Color.BLACK, Paint.Style.FILL_AND_STROKE, 2);
    }

    /**
     * 绘制网格
     *
     * @param
     * @param step
     */
    public void drawGrid(int step, Context ctx) {
        if (step == 0) {
            return;
        }
        for (int i = 0; i < ScrUtil.getScreenHeight(ctx) / step; i++) {
            mCanvas.drawLine(0, 0 + step * i, ScrUtil.getScreenWidth(ctx), 0 + step * i, getPaint());
        }
        for (int i = 0; i < ScrUtil.getScreenWidth(ctx) / step; i++) {
            mCanvas.drawLine(0 + step * i, 0, 0 + step * i, ScrUtil.getScreenHeight(ctx), getPaint());
        }
    }

    //////////////////////////////////画板准备完成监听
    interface OnPrepared {
        void draw(Paint paint);
    }

    private OnPrepared mOnPrepared;

    private void setOnPrepared(OnPrepared onPrepared) {
        mOnPrepared = onPrepared;
    }
}
