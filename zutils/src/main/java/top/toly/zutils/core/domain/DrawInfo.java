package top.toly.zutils.core.domain;

/**
 * 作者：张风捷特烈
 * 时间：2018/7/6:10:50
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class DrawInfo {
    public Pos p0;
    public Pos p1;
    public Pos p2;
    public Float c;//周长
    public Float ang;//线与X轴夹角(弧度数)
    public Float k;//斜率
    public Float b0;//直线解析式x=0时，y的值
    public Float x;//直线解析式x=0时，y的值
    public Float y;//直线解析式x=0时，y的值

    public Float b;//线宽
    public Pos p;//图形距画布左顶点偏移量
    public Pos a;//锚点
    public Float rot;//旋转量
    public Integer fs;//填充颜色
    public Integer ss;//线条颜色

    public boolean dir = true;
    /**
     * 五角星
     **/
    public Integer num;//角数
    public Float R;//外接圆半径
    public Float r;//内接圆半径

    public DrawInfo() {
    }

    /**
     * 画线
     *
     * @param p0
     * @param p1
     */
    public DrawInfo(Pos p0, Pos p1) {
        this.p0 = p0;
        this.p1 = p1;
    }

    /**
     * 画三角形
     *
     * @param p0
     * @param p1
     * @param p2
     */
    public DrawInfo(Pos p0, Pos p1, Pos p2) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
    }


    @Override
    public String toString() {
        return "DrawInfo{" +
                "p0=" + p0 +
                ", p1=" + p1 +
                ", c=" + c +
                ", ang=" + ang +
                ", k=" + k +
                ", b0=" + b0 +
                ", x=" + x +
                ", y=" + y +
                ", b=" + b +
                ", p=" + p +
                ", fs=" + fs +
                ", ss=" + ss +
                ", num=" + num +
                ", R=" + R +
                ", r=" + r +
                '}';
    }
}
