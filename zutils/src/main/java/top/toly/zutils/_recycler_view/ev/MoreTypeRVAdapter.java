package top.toly.zutils._recycler_view.ev;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * 作者：张风捷特烈
 * 时间：2018/4/11:9:11
 * 邮箱：1981462002@qq.com
 * 说明：多样式的RecyclerView适配器
 * <p>
 * 1.准备一个有type字段的bean
 */
public abstract class MoreTypeRVAdapter<T> extends RecyclerView.Adapter<MoreTypeRVHolder<T>> {
    private List<T> mDatas;
    private static final String TAG = "MoreTypeRVAdapter";

    public MoreTypeRVAdapter(List<T> datas) {
        mDatas = datas;
    }

    @Override
    public MoreTypeRVHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {


        return moreType(mDatas, viewType);
    }

    @Override
    public void onBindViewHolder(MoreTypeRVHolder<T> holder, final int position) {
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.OnRvItemClick(v,position);
                }
            }
        });

        holder.bindHolder(holder,mDatas.get(position));
    }

    @Override
    public int getItemViewType(int position) {

        return setType(mDatas.get(position));
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract MoreTypeRVHolder<T> moreType(List<T> datas, int viewType);

    public abstract int setType(T t);
///////////////////////////////////////////////////////////

    /**
     * 添加item
     *
     * @param i
     * @param aNew
     */
    public void addData(int i, T aNew) {
        mDatas.add(i, aNew);
        notifyItemInserted(i);//刷新数据
    }

    /**
     * 删除item
     *
     * @param i
     */
    public void deleteData(int i) {
        mDatas.remove(i);
        notifyItemRemoved(i);//刷新数据
    }

/////////////////为RecyclerView设置点击监听接口/////////////////////////

    /**
     * 为RecyclerView设置点击监听接口
     */
    public interface OnItemClickListener {
        void OnRvItemClick(View v, int pos);//item被点击的时候回调方法
    }

    /**
     * 声明监听器接口对象
     */
    private OnItemClickListener mOnItemClickListener;

    /**
     * 设置RecyclerView某个的监听方法
     *
     * @param onItemClickListener
     */
    public void setOnRvItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

}
