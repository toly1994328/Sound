package top.toly.zutils.core.ui.anima;

import android.os.SystemClock;

import top.toly.zutils.core.domain.Pos;


/**
 * 计算位移距离的工具类
 *
 * @author leo
 */
public class AniHelper {

    private Pos mP0 = new Pos(0, 0);//起始点
    private Pos mDetaP = new Pos(0, 0);//移动差
    private Pos mP = new Pos(0, 0);//终点

    /**
     * 开始执行动画的时间
     */
    private long startTime;
    /**
     * 判断是否正在执行动画
     * true 是还在运行
     * false  已经停止
     */
    private boolean isFinish;


    /**
     * 开移移动
     *
     * @param p0
     * @param dp
     */
    public void startAni(Pos p0, Pos dp) {
        mP0 = p0;
        mDetaP = dp;

        this.startTime = SystemClock.uptimeMillis();
        this.isFinish = false;
    }

    /**
     * 默认运行的时间
     * 毫秒值
     */
    private int duration = 500;


    /**
     * 计算一下当前的运行状况
     * 返回值：
     * true  还在运行
     * false 运行结束
     */
    public boolean computeScrollOffset() {

        if (isFinish) {
            return false;
        }

        // 获得所用的时间
        long passTime = SystemClock.uptimeMillis() - startTime;

        // 如果时间还在允许的范围内
        if (passTime < duration) {

            // 当前的位置  =  开始的位置  +  移动的距离（距离 = 速度*时间）
            mP.x = mP0.x + mDetaP.x * passTime / duration;
            mP.y = mP0.y + mDetaP.y * passTime / duration;

        } else {
            mP.x = mP0.x + mDetaP.x;
            mP.y = mP0.y + mDetaP.y;
            isFinish = true;
        }

        return true;
    }

    public void setP(Pos p) {
        mP = p;
    }

    public Pos getP() {
        return mP;
    }
}
