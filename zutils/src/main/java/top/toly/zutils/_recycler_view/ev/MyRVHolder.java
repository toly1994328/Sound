package top.toly.zutils._recycler_view.ev;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 作者：张风捷特烈
 * 时间：2018/4/10:14:22
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class MyRVHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private int mPosition;
    private View mItemView;

    public MyRVHolder(View itemView) {

        super(itemView);
        mItemView = itemView;
        mPosition = this.getLayoutPosition();
        mViews = new SparseArray<>();
    }


    /**
     * 获取pos
     *
     * @return
     */
    public int getPos() {
        return this.getLayoutPosition();
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

    public View getItemView() {
        return mItemView;
    }

    /**
     * 设置item背景颜色
     */
    public MyRVHolder setColor(int color) {
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
    public MyRVHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public MyRVHolder setImageViewRes(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }
    public MyRVHolder setImageViewBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }
    public MyRVHolder setImageViewUrl(int viewId, String url) {
        ImageView view = getView(viewId);

//        Glide.with(Zutils.getContext())
//                .load(url)
//                .skipMemoryCache(false)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .into(view);

        return this;
    }


}
