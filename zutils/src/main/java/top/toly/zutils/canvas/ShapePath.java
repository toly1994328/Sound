package top.toly.zutils.canvas;

import android.graphics.Path;
import android.graphics.RectF;

import top.toly.zutils.core.domain.DrawInfo;
import top.toly.zutils.core.domain.Pos;
import top.toly.zutils.core.domain.Triangle;
import top.toly.zutils.logic.Logic;
import top.toly.zutils.logic.Parse;


/**
 * 作者：张风捷特烈
 * 时间：2018/7/6:10:49
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class ShapePath {
    private static final String TAG = "ShapePath";

    /**
     * 画线路径
     *
     * @param info
     * @return
     */
    public static Path linePath(DrawInfo info) {
        Path path = new Path();
        DrawInfo line = Parse.line(info);
        path.moveTo((line.p0.x), (line.p0.y).floatValue());
        path.lineTo((line.p1.x), (line.p1.y).floatValue());
        return path;
    }

    /**
     * 画圆路径
     *
     * @param info
     * @return
     */
    public static Path circlePath(DrawInfo info) {
        Path path = new Path();
        Float r = info.r;
        if (info.dir) {//默认逆时针方向绘制
//            path.addCircle(0+info.b/2, 0+info.b/2, r, Path.Direction.CCW);
            path.addCircle(0, 0, r, Path.Direction.CCW);
        } else {
            path.addCircle(0 + info.b, 0 + info.b, r, Path.Direction.CW);
//            path.addCircle(0+info.b/2, 0+info.b/2, r, Path.Direction.CW);
        }
        return path;
    }

    /**
     * 画圆弧路径
     *
     * @param info
     * @return
     */
    public static Path arcPath(DrawInfo info) {
        Path path = new Path();
        Float r = info.r;
        Float b = info.b;
        Float ang = info.ang;
        float deta = (float) (b / 2 - r - 1.5 * b);
//        float deta = info.b + info.r;
//        RectF rectF = new RectF((float) (0+deta), (float) (0+deta), (float) (2*(r+0)), (float) (2*(r+0)));
        RectF rectF = new RectF((float) (0 + deta), (float) (0 + deta),
                (float) (2 * (r + b) + deta), (float) (deta + 2 * (r + b)));
        path.addArc(rectF, 0, ang.floatValue());
        return path;
    }


    /**
     * 画三角形路径
     *
     * @param info
     * @return
     */
    public static Path trgPath(DrawInfo info) {
        Path path = new Path();
        Pos p0 = info.p0;
        Pos p1 = info.p1;
        Pos p2 = info.p2;

        float a = Logic.disPos2d(p1, p2);
        float b = Logic.disPos2d(p0, p2);
        float c = Logic.disPos2d(p0, p1);

        Triangle triangle = new Triangle(a, b, c);
        Parse.triangle(triangle);

        if (triangle != null) {
//            path.moveTo(p0.x, -p0.y.floatValue());
//            path.moveTo(p0.x, -p0.y.floatValue());
//            path.lineTo(p1.x, -p1.y.floatValue());
            path.lineTo(p1.x, p1.y.floatValue());
            path.lineTo(p2.x, p2.y.floatValue());
            path.lineTo(p2.x, p2.y.floatValue());
            return path;
        } else {
            return null;
        }

    }

    /**
     * 画矩形路径
     *
     * @param info x:宽 y:高 r:圆角
     * @return
     */
    public static Path rectPath(DrawInfo info) {
        Path path = new Path();
        float width = info.x;
        float height = info.y;
        float r = info.r;

        if (2 * r > width || 2 * r > height) {
            return null;
        }

        path.addArc(new RectF(0, 0, 2 * r, 2 * r), 180, 90);
        path.lineTo(width - r, 0);
        path.addArc(new RectF(width - 2 * r, 0, width, 2 * r), -90, 90);
        path.lineTo(width, height - r);
        path.addArc(new RectF(width - 2 * r, height - 2 * r, width, height), 0, 90);
        path.lineTo(r, height);
        path.addArc(new RectF(0, height - 2 * r, 2 * r, height), 90, 90);
        path.lineTo(0, r);

        return path;
    }


    /**
     * 画多线路径
     *
     * @param poss
     * @return
     */
    public static Path linesPath(Pos... poss) {
        Path path = new Path();
        path.moveTo(poss[0].x, poss[0].y.floatValue());
        for (Pos pos : poss) {
            path.lineTo(pos.x, pos.y.floatValue());
        }
        path.close();
        return path;
    }


    /**
     * 画n角星的路径
     *
     * @param info
     */
    public static Path starPath(DrawInfo info) {
        Path path = new Path();

        int num = info.num;
        float R = info.R;
        float r = info.r;

        float perDeg = 360 / num;
        float degA = perDeg / 2 / 2;
        float degB = 360 / (num - 1) / 2 - degA / 2 + degA;

        path.moveTo((float) (Math.cos((degA + perDeg * 0) / 180 * Math.PI) * R + R * Math.cos(degA / 180 * Math.PI)),
                (float) (-Math.sin((degA + perDeg * 0) / 180 * Math.PI) * R + R));


        for (int i = 0; i < num; i++) {

            path.lineTo((float) (Math.cos((degA + perDeg * i) / 180 * Math.PI) * R + R * Math.cos(degA / 180 * Math.PI)),
                    (float) (-Math.sin((degA + perDeg * i) / 180 * Math.PI) * R + R));
            path.lineTo((float) (Math.cos((degB + perDeg * i) / 180 * Math.PI) * r + R * Math.cos(degA / 180 * Math.PI)),
                    (float) (-Math.sin((degB + perDeg * i) / 180 * Math.PI) * r + R));
        }
        path.close();
        return path;
    }

    /**
     * 画正n角星的路径
     *
     * @param info
     */
    public static Path regularStarPath(DrawInfo info) {

        int num = info.num;
        float R = info.R;
        float r = info.r;

        float degA, degB;
        if (num % 2 == 1) {
            degA = 360 / num / 2 / 2;
            degB = 180 - degA - 360 / num / 2;
        } else {
            degA = 360 / num / 2;
            degB = 180 - degA - 360 / num / 2;
        }
        r = (float) (R * Math.sin(degA / 180 * Math.PI) / Math.sin(degB / 180 * Math.PI));
        info.r = r;
        return ShapePath.starPath(info);
    }

    /**
     * 画正n边形的路径
     */
    public static Path regularPolygonPath(DrawInfo info) {

        int num = info.num;
        float R = info.R;
        info.r = (float)(R * (Math.cos(360. / num / 2 / 180 * Math.PI)));//!!一点解决
        return ShapePath.starPath(info);

    }
}
