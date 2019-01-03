package top.toly.zutils.core.ui.common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

/**
 * 作者：张风捷特烈
 * 时间：2018/3/27:10:00
 * 邮箱：1981462002@qq.com
 * 说明：Drawable工具类
 */
public class DwUtils {

    /**
     * 获取一个shape对象
     *
     * @param color
     * @param radius
     * @return
     */
    public static GradientDrawable getGradientDrawable(int color, int radius) {
        // xml中定义的shape标签 对应此类
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);// 矩形
        shape.setCornerRadius(radius);// 圆角半径
        shape.setColor(color);// 颜色
        return shape;
    }

    /**
     * 获取状态选择器
     *
     * @param normal 正常时的图片
     * @param press  按压时的图片
     * @return
     */
    public static StateListDrawable getSelector(Drawable normal, Drawable press) {
        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed}, press);// 按下图片
        selector.addState(new int[]{}, normal);// 默认图片
        return selector;
    }

    /**
     * 获取带圆角的状态选择器
     *
     * @param normal 正常时的颜色
     * @param press  按压时的颜色
     * @param radius 圆角半径
     * @return
     */
    public static StateListDrawable getSelector(int normal, int press, int radius) {
        GradientDrawable bgNormal = getGradientDrawable(normal, radius);
        GradientDrawable bgPress = getGradientDrawable(press, radius);
        StateListDrawable selector = getSelector(bgNormal, bgPress);
        return selector;
    }

    /**
     * @param view
     * @return
     */
    public static Drawable convertViewToDrawable(View view) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(c);
        view.setDrawingCacheEnabled(true);
        Bitmap cacheBmp = view.getDrawingCache();
        Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
        cacheBmp.recycle();
        view.destroyDrawingCache();
        return new BitmapDrawable(viewBmp);
    }
}
