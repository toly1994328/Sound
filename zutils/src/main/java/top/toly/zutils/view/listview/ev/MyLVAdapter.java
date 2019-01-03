package top.toly.zutils.view.listview.ev;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;


/**
 * 作者：张风捷特烈
 * 时间：2018/4/6:13:51
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public abstract class MyLVAdapter<T> extends BaseAdapter{
    protected List<T> mDatas;
    protected int mItemId;
    protected Context mCtx;


    public MyLVAdapter(List<T> datas, int listId, Context ctx) {
        mCtx = ctx;
        mDatas = datas;
        mItemId = listId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public  View getView(int position, View convertView, ViewGroup parent){
       MyLVHolder holder = MyLVHolder.get(mCtx, convertView, parent, mItemId, position);
        setDatas(holder, getItem(position),position);
        return holder.getConvertView();
    }

    public abstract void setDatas(MyLVHolder holder, T data, int position);

}
