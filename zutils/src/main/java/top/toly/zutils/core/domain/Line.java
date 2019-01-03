package top.toly.zutils.core.domain;


/**
 * 作者：张风捷特烈
 * 时间：2018/7/5:9:15
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class Line  {
    public Pos p0;
    public Pos p1;
    public Double c;//周长
    public Double ang;//线与X轴夹角(弧度数)
    public Double k;//斜率
    public Double b0;//直线解析式x=0时，y的值
    public Double x;//直线解析式x=0时，y的值
    public Double y;//直线解析式x=0时，y的值


    @Override
    public String toString() {
        return "Line{" +
                "p0=" + p0 +
                ", p1=" + p1 +
                ", c=" + c +
                ", ang=" + ang +
                ", k=" + k +
                ", b0=" + b0 +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
