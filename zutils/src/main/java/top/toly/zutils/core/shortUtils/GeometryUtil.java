package top.toly.zutils.core.shortUtils;

import android.graphics.PointF;

/**
 * 几何图形工具
 */
public class GeometryUtil {

    /**
     * As meaning of method name.
     * 获得两点之间的距离
     *
     * @param p0
     * @param p1
     * @return
     */
    public static float getDistanceBetween2Points(PointF p0, PointF p1) {
        float distance = (float) Math.sqrt(Math.pow(p0.y - p1.y, 2) + Math.pow(p0.x - p1.x, 2));
        return distance;
    }

    /**
     * Get middle point between p1 and p2.
     * 获得两点连线的中点
     *
     * @param p1
     * @param p2
     * @return
     */
    public static PointF getMiddlePoint(PointF p1, PointF p2) {
        return new PointF((p1.x + p2.x) / 2.0f, (p1.y + p2.y) / 2.0f);
    }

    /**
     * Get point between p1 and p2 by percent.
     * 根据百分比获取两点之间的某个点坐标
     *
     * @param p1
     * @param p2
     * @param percent
     * @return
     */
    public static PointF getPointByPercent(PointF p1, PointF p2, float percent) {
        return new PointF(evaluateValue(percent, p1.x, p2.x), evaluateValue(percent, p1.y, p2.y));
    }

    /**
     * 根据分度值，计算从start到end中，fraction位置的值。fraction范围为0 -> 1
     *
     * @param fraction
     * @param start
     * @param end
     * @return
     */
    public static float evaluateValue(float fraction, Number start, Number end) {
        return start.floatValue() + (end.floatValue() - start.floatValue()) * fraction;
    }

    /**
     * 得到两点之间的弧度
     *
     * @param px1 第一个点的X坐标
     * @param py1 第一个点的Y坐标
     * @param px2 第二个点的X坐标
     * @param py2 第二个点的Y坐标
     * @return
     */
    public static double getRadOf2Point(float px1, float py1, float px2, float py2) {
        //得到两点X的距离
        float dx = px2 - px1;
        //得到两点Y的距离
        float dy = py1 - py2;
        //算出斜边长
        float d = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        //得到这个角度的余弦值（通过三角函数中的定理 ：邻边/斜边=角度余弦值）
        float cosAngle = dx / d;
        //通过反余弦定理获取到其角度的弧度
        float rad = (float) Math.acos(cosAngle);
        //当触屏的位置Y坐标<摇杆的Y坐标我们要取反值-0~-180
        if (py2 < py1) {
            rad = -rad;
        }
        return rad;
    }

    /**
     * 得到两点之间的角度
     *
     * @param px1 第一个点的X坐标
     * @param py1 第一个点的Y坐标
     * @param px2 第二个点的X坐标
     * @param py2 第二个点的Y坐标
     * @return
     */
    public static double getDegOf2Point(float px1, float py1, float px2, float py2) {

        return Change.radian2degree(getRadOf2Point(px1, py1, px2, py2));
    }

    /**
     * Get the point of intersection between circle and line.
     * 获取 通过指定圆心，斜率为lineK的直线与圆的交点。
     *
     * @param pMiddle The circle center point.
     * @param radius  The circle radius.
     * @param lineK   The slope of line which cross the pMiddle.
     * @return
     */
    public static PointF[] getIntersectionPoints(PointF pMiddle, float radius, Double lineK) {
        PointF[] points = new PointF[2];

        float radian, xOffset = 0, yOffset = 0;
        if (lineK != null) {
            radian = (float) Math.atan(lineK);//得到该角的角度
            xOffset = (float) (Math.sin(radian) * radius);//得到对边的长
            yOffset = (float) (Math.cos(radian) * radius);//得到邻边的长
        } else {
            xOffset = radius;
            yOffset = 0;
        }
        points[0] = new PointF(pMiddle.x + xOffset, pMiddle.y - yOffset);
        points[1] = new PointF(pMiddle.x - xOffset, pMiddle.y + yOffset);

        return points;
    }
}