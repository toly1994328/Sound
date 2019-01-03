package top.toly.zutils.core.domain;

/**
 * 作者：张风捷特烈
 * 时间：2018/7/5:9:07
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class Pos {
    public Float x;
    public Float y;

    public Pos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Pos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
