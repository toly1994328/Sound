package top.toly.zutils.view.viewpager.ev;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：张风捷特烈
 * 时间：2018/4/6:19:25
 * 邮箱：1981462002@qq.com
 * 说明：ViewPager
 */
public class MyViewPagerAdapter extends PagerAdapter {

    int[] mImgs = {};
    private List<View> mViews = new ArrayList<>();
    private Context mCtx;

    public MyViewPagerAdapter(List<View> views, Context ctx) {
        mViews = views;
        mCtx = ctx;
    }

    @Override
    public int getCount() {
        return mImgs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        for (View v: mViews) {
            container.addView(v);
            return v;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
