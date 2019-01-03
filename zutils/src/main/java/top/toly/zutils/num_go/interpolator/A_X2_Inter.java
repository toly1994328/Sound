package top.toly.zutils.num_go.interpolator;

import android.animation.TimeInterpolator;

/**
 * 作者：张风捷特烈
 * 时间：2018/7/9:10:08
 * 邮箱：1981462002@qq.com
 * 说明：三角函数实现加速插值器
 */
public class A_X2_Inter implements TimeInterpolator {

    @Override
    public float getInterpolation(float input) {

        return input * input;

        // 返回的result值 = 随着动画进度呈先减速后加速的变化趋势
    }
}
