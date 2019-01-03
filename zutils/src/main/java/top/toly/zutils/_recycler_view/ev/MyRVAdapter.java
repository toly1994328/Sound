package top.toly.zutils._recycler_view.ev;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 作者：张风捷特烈
 * 时间：2018/4/10:14:28
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public abstract class MyRVAdapter<T> extends RecyclerView.Adapter<MyRVHolder> {
    protected List<T> mDatas;
    protected int mItemId;
    private static final String TAG = "MyRVAdapter";
    private View mItemView;
    private Context mCtx;

    public MyRVAdapter(List<T> datas, int itemId, Context ctx) {
        mDatas = datas;
        mItemId = itemId;
        mCtx = ctx;
    }

    @Override
    public MyRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mItemView =View.inflate(mCtx, mItemId, null);
        final MyRVHolder myRVHolder = new MyRVHolder(mItemView);
        //点击事件方式
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//使用回调实现点击监听
                if (mOnRvItemClickListener != null) {
                    mOnRvItemClickListener.OnRvItemClick(v, myRVHolder.getPos());
//                    myRVHolder.setColor(DwUtils.randomColor());
                }
            }
        });


        return myRVHolder;
    }

    @Override
    public void onBindViewHolder(MyRVHolder holder, int position) {

        setDatas(holder, mDatas.get(position),position);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract void setDatas(MyRVHolder holder, T data, int position);




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

    public View getItemView() {
        return mItemView;
    }


    /////////////////为RecyclerView设置点击监听接口/////////////////////////

    /**
     * 为RecyclerView设置点击监听接口
     */
    public interface OnRvItemClickListener {
        void OnRvItemClick(View v, int pos);//item被点击的时候回调方法
    }

    /**
     * 声明监听器接口对象
     */
    private OnRvItemClickListener mOnRvItemClickListener;

    /**
     * 设置RecyclerView某个的监听方法
     *
     * @param onRvItemClickListener
     */
    public void setOnRvItemClickListener(OnRvItemClickListener onRvItemClickListener) {
        mOnRvItemClickListener = onRvItemClickListener;
    }


}
