package top.toly.zutils.logic;


import top.toly.zutils.core.domain.Pos;

/**
 * 作者：张风捷特烈
 * 时间：2018/7/5:8:59
 * 邮箱：1981462002@qq.com
 * 说明： 逻辑类
 */
public class Logic {

    /**
     * 弧度制化为角度制
     *
     * @param rad 弧度
     * @returns {number} 角度
     */
    public static float deg(float rad) {
        return (float) (rad * 180 / Math.PI);
    }

    /**
     * 角度制化为弧度制
     *
     * @param deg 角度
     * @returns {number} 弧度
     */
    public static float rad(float deg) {
        return (float) (deg * Math.PI / 180);
    }

    /**
     * 两点间夹角，以p0为旋转点 ，逆时针度数
     *
     * @param p0 第一点
     * @param p1 第二点
     * @returns {number}
     */
    public static float angleOf2Pos(Pos p0, Pos p1) {
        return (float) Math.atan((p1.x - p0.x) / (p1.y - p0.y));
    }

    /**
     * 两点间距离函数
     *
     * @param p0 第一点
     * @param p1 第二点
     * @returns number
     */
    public static float disPos2d(Pos p0, Pos p1) {
        return (float) Math.sqrt((p0.x - p1.x) * (p0.x - p1.x) + (p0.y - p1.y) * (p0.y - p1.y));
    }


    /**
     * 一元二次函数解
     *
     * @param a 二次项系数
     * @param b 一次项系数
     * @param c 常数项参数
     * @returns {{x1: number, x2: number}}
     */
    public static Pos getOyTc(float a, float b, float c) {
        float delta = b * b - 4 * a * c;
        if (delta >= 0) {
            float x1 = (float) (-b / (2 * a) + Math.sqrt(delta) / (2 * a));
            float x2 = (float) (-b / (2 * a) - Math.sqrt(delta) / (2 * a));
            return  new Pos(x1,x2);
        } else {
            return null;
        }
    }

    /**
     * 判断参数是否存在
     */
    public static boolean isExist(Object... objs) {
        for (Object o : objs) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }

}
