package top.toly.zutils.core.domain;

/**
 * 作者：张风捷特烈
 * 时间：2018/6/19:15:36
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class Triangle {
    public Float a;
    public Float b;
    public Float c;
    public Float degA;
    public Float degB;
    public Float degC;

    public Triangle() {
    }

    public Triangle(Float a, Float b, Float c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }


    @Override
    public String toString() {
        return "Triangle{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", degA=" + degA +
                ", degB=" + degB +
                ", degC=" + degC +
                '}';
    }
}
