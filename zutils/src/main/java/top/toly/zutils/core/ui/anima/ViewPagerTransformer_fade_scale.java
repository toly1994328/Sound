package top.toly.zutils.core.ui.anima;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 作者：张风捷特烈
 * 时间：2018/4/9:11:18
 * 邮箱：1981462002@qq.com
 * 说明：ViewPager切换动画-淡入-缩放
 */
public class ViewPagerTransformer_fade_scale implements ViewPager.PageTransformer {
    private static float MIN_SCALE = 0.7f;

    //A==>B  A的position 0==>-1   B的position 1==>0
    @Override
    public  void transformPage(View page, float position) {
        int width = page.getWidth();
        int height = page.getHeight();

        if (position < -1) {//非A、B页
            page.setAlpha(1);
        } else if (position <= 0) {//A页
            page.setAlpha(1+position*2);
            page.setScaleX(1);
            page.setScaleY(1);

            page.setPivotX(0);
            page.setPivotY(height/2);

            page.setRotationX(-100*position);
            page.setRotationY(-100*position);

        } else if (position <= 1) {//B页
            page.setAlpha( 1 - position);
            page.setTranslationX(width * (-position));
//            0.75~1
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        }


    }
}
