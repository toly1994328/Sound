package top.toly.zutils._recycler_view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * 作者：张风捷特烈
 * 时间：2018/7/10:9:00
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class RVUtils {


    public static final int GRIDVIEW = 0;
    public static final int LISTVIEW = 1;
    public static final int PULL = 2;

    /**
     * @param rv    RecyclerView
     * @param count count 数量  LISTVIEW可随意
     * @param style 模式 GRIDVIEW  LISTVIEW  PULL
     * @param ctx   上下文
     */
    public static GridLayoutManager setStyle4RV(RecyclerView rv, int count, int style, Context ctx) {
        switch (style) {
            case GRIDVIEW:
                rv.addItemDecoration(new RvDivider(ctx));
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ctx, count, GridLayoutManager.VERTICAL, false);
                rv.setLayoutManager(gridLayoutManager);
                return gridLayoutManager;
            case LISTVIEW:
                rv.addItemDecoration(new SampleDivider(ctx));
                rv.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false));
                return null;
            case PULL:
                rv.addItemDecoration(new RvDivider(ctx));
                rv.setLayoutManager(new StaggeredGridLayoutManager(count, StaggeredGridLayoutManager.VERTICAL));
                return null;
        }
        return null;
    }
}
