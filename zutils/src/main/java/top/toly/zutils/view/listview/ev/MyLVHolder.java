package top.toly.zutils.view.listview.ev;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张风捷特烈
 * 时间：2018/4/6:11:30
 * 邮箱：1981462002@qq.com
 * 说明：MyLVHolder
 */
public class MyLVHolder {


    private SparseArray<View> mViews;
    private int mPosition;
    private View mItemView;
    private List<Integer> mPos;

    public MyLVHolder(Context ctx, ViewGroup parent, int layoutId, int position) {
        mPosition = position;
        mViews = new SparseArray<>();
        mItemView = LayoutInflater.from(ctx).inflate(layoutId, parent, false);
        mItemView.setTag(this);
    }

    public static MyLVHolder get(Context ctx, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new MyLVHolder(ctx, parent, layoutId, position);
        } else {
            MyLVHolder holder = (MyLVHolder) convertView.getTag();
            holder.mPosition = position;//更新position
            return holder;
        }
    }

    public List<Integer> handleCheckBox(int checkBoxId) {
        mPos = new ArrayList<>();
        final CheckBox cb = this.getView(checkBoxId);
        cb.setChecked(false);
        if (mPos.contains(this.getPosition())) {
            cb.setChecked(true);
        }
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb.isChecked()) {
                    mPos.add(getPosition());
                } else {
                    mPos.remove((Integer) getPosition());
                }
            }
        });
        return mPos;//返回选中的CheckBox位置集合
    }


    //////////////////////////////////////////


    public View getItemView() {
        return mItemView;
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

    public View getConvertView() {
        return mItemView;
    }

    public int getPosition() {
        return mPosition;
    }

    /**
     * 设置TextView文本方法
     *
     * @param viewId
     * @param text
     * @return
     */
    public MyLVHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }


    public MyLVHolder setImageViewRes(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public MyLVHolder setImageViewBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public MyLVHolder setImageViewUrl(int viewId, String url) {
        ImageView view = getView(viewId);

//        Glide.with(Zutils.getContext())
//                .load(url)
//                .skipMemoryCache(false)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .into(view);

        return this;
    }
}
