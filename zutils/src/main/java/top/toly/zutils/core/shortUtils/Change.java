package top.toly.zutils.core.shortUtils;

import android.content.Context;
import android.util.TypedValue;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 作者：张风捷特烈
 * 时间：2018/3/2:10:13
 * 邮箱：1981462002@qq.com
 * 说明：换算相关工具类
 */
public class Change {
    /**
     * dip为单位的值转化为px为单位的值
     *
     * @param dip 值(dip)
     * @return 值(px)
     */
    public static int dip2px(Context ctx,float dip) {
        float density = ctx.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    /**
     * dp转px
     *
     * @param dpVal
     * @return
     */
    public static int dp2px(Context ctx,float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, ctx.getResources().getDisplayMetrics());
    }

    /**
     * dp转px
     *
     * @param radian
     * @return
     */
    public static double radian2degree(double radian) {
        double v = radian * 180 / Math.PI;
        BigDecimal bigDecimal = new BigDecimal(v);


        double v1 = bigDecimal.setScale(3, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        return v1;
    }

    /**
     * px为单位的值转化为dip为单位的值
     *
     * @param px 值(px)
     * @return 值(dip)
     */
    public static float getDip(Context ctx,int px) {
        float density = ctx.getResources().getDisplayMetrics().density;
        return (px / density);
    }

    /**
     * long类型的值转化为以M单位
     *
     * @param size 文件大小（long）
     * @return 以M单位
     */
    public static double getBit2M(long size) {
        double fSize = size / (double) 1024 / 1024;
        fSize = new BigDecimal(fSize).
                divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return fSize;
    }

    /**
     * 切割小数保留两位
     *
     * @param f
     * @return
     */
    public static String cutNum2String(float f) {
        return new DecimalFormat("#.00").format(f);

    }

    /**
     * 切割小数保留两位
     *
     * @param f
     * @return
     */
    public static double cutNum2Double(float f) {
        BigDecimal bg = new BigDecimal(f);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * long类型的值转化为时间
     *
     * @param timeNum 时间 （long）
     * @return 时间（String）
     */
    public static String getTime(long timeNum) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * long类型的值转化为时间,自定义模式
     *
     * @param mode    时间模式
     * @param timeNum 时间 （long）
     * @return 时间（String）
     */
    public static String getTime(String mode, long timeNum) {
        SimpleDateFormat formatter = new SimpleDateFormat(mode);
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }
}
