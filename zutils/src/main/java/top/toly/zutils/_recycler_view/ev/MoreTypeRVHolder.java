package top.toly.zutils._recycler_view.ev;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 作者：张风捷特烈
 * 时间：2018/4/11:11:32
 * 邮箱：1981462002@qq.com
 * 说明：MoreTypeRVHolder
 */
public abstract class MoreTypeRVHolder<T> extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private int mPosition;
    private View mItemView;

    public MoreTypeRVHolder(View itemView) {
        super(itemView);

        mPosition = this.getLayoutPosition();
        mViews = new SparseArray<>();
        mItemView = itemView;

    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mItemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public abstract void bindHolder(MoreTypeRVHolder<T> holder,T data);

    public View getItemView() {
        return mItemView;
    }
    /**
     * 获取pos
     * @return
     */
    public  int getPos() {
        return this.getLayoutPosition();
    }

    /**
     * 设置item背景颜色
     */
    public MoreTypeRVHolder setColor(int color) {
        mItemView.setBackgroundColor(color);
        return this;
    }
    /**
     * 设置TextView文本方法
     *
     * @param viewId
     * @param text
     * @return
     */
    public MoreTypeRVHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public MoreTypeRVHolder setImageViewRes(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    /**
     * 设置背景颜色
     */
    public MoreTypeRVHolder setBackgroundColor(int viewId, int color) {
        ImageView view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }


}
