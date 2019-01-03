package top.toly.zutils.core.base;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import top.toly.zutils.core.ui.common.ScrUtil;
import top.toly.zutils.core.ui.common.UIUtils;


/**
 * 作者：张风捷特烈
 * 时间：2018/4/22:7:59
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public abstract class BasePopWindow extends PopupWindow {


    private int mWidth;
    private int mHeight;
    public View mRootView;

    private SparseArray<View> mViews;


    public BasePopWindow(Context context, int layoutId) {
        super(context);

        mWidth = ScrUtil.getScreenWidth(context);
        mHeight = ScrUtil.getScreenHeight(context);
        mViews = new SparseArray<>();
        mRootView = UIUtils.inflate(context,layoutId);

        setContentView(mRootView);
        setWidth(mWidth);
        setHeight(mHeight);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
//        setAnimationStyle(R.style.slide_anim);//设置mPopWindow进出动画
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        initEvent();
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
            view = mRootView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    public abstract void initEvent();


//    ////////////////////////////接口/////////////////////////////
//    public interface OnPopClickListener {
//        void onPopClick(AdapterView<?> parent, View view, int position, long id);
//    }
//
//    public PopWindow.OnPopClickListener mOnPopClickListener;
//
//    public void setOnPopClickListener(PopWindow.OnPopClickListener onPopClickListener) {
//        mOnPopClickListener = onPopClickListener;
//
//    }
}
