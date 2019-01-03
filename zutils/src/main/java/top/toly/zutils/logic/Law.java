package top.toly.zutils.logic;

/**
 * 作者：张风捷特烈
 * 时间：2018/7/5:8:59
 * 邮箱：1981462002@qq.com
 * 说明： 定理类
 */
public class Law {


    /**
     * 正弦定理
     * 已知边及其对角，求另一已知角所对边，第一参对应所求边
     *
     * @param degA
     * @param degB
     * @param b
     * @returns {number|*}
     */
    public static float sinGetL(float degA, float degB, float b) {
        return (float) (b * Math.sin(degA) / Math.sin(degB));

    }

    /**
     * 余弦定理
     * 已知三边，第一参对应所求角
     *
     * @param a
     * @param b
     * @param c
     * @returns {number}
     */
    public static float cosGetDeg(float b, float a, float c) {
        float cosb = (a * a + c * c - b * b) / (2 * a * c);
        float degB = (float) Math.acos(cosb);
        return degB;
    }

    /**
     * 余弦定理
     * 已知两边及夹角，用余弦定理求第三边,第一参对应所求边
     *
     * @param degC 已知角度数
     * @param a    已知角一边长
     * @param b    已知角一边长
     * @returns number
     */
    public static float cosGetL(float degC, float a, float b) {
        return (float) Math.sqrt(a * a + b * b - 2 * a * b * Math.cos(degC));
    }

}
