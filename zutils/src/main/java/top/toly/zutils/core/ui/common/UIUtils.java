package top.toly.zutils.core.ui.common;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import top.toly.zutils.core.shortUtils.Change;


/**
 * 作者：张风捷特烈
 * 时间：2018/4/25:8:52
 * 邮箱：1981462002@qq.com
 * 说明：UI相关工具类
 */
public class UIUtils {

    // /////////////////加载资源文件 ///////////////////////////

    /**
     * 获取string.xml中的字符串
     *
     * @param id string.xml中的字符串id
     * @return
     */
    public static String getString(Context ctx, int id) {
        return ctx.getResources().getString(id);
    }

    /**
     * 获取string.xml中的字符串数组
     *
     * @param id string.xml中的字符串数组
     * @return String[]
     */
    public static String[] getStringArray(Context ctx,int id) {
        return ctx.getResources().getStringArray(id);
    }

    /**
     * 获取color.xml资源颜色
     *
     * @param id 颜色资源id
     * @return int颜色值
     */
    public static int getColor(Context ctx,int id) {
        //getContext().getResources().getColor(id);//6.0过时方法
        return ContextCompat.getColor(ctx, id);
    }
    // 获取资源图片

    /**
     * 4:获取drawable下资源图片
     *
     * @param id drawable下资源图片id
     * @return Drawable对象
     */
    public static Drawable getDrawable(Context ctx,int id) {
        //getContext().getResources().getDrawable(id);6.0过时方法
        return ContextCompat.getDrawable(ctx, id);
    }


    //根据id获取颜色的状态选择器
    public static ColorStateList getColorStateList(Context ctx,int id) {
        return ctx.getResources().getColorStateList(id);
    }

    // 获取资源尺寸
    public static int getDimen(Context ctx,int id) {
        return ctx.getResources().getDimensionPixelSize(id);// 返回具体像素值
    }

    /**
     * 获取LinearLayout布局参数（宽高包裹内容）
     *
     * @return
     */
    public static LinearLayout.LayoutParams getLMMParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        return params;
    }

    /**
     * 获取LinearLayout布局参数（宽高包裹内容）
     *
     * @return
     */
    public static RelativeLayout.LayoutParams getRMMParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        return params;
    }

    // /////////////////加载布局文件//////////////////////////
    public static View inflate(Context ctx,int id) {
        return View.inflate(ctx, id, null);
    }

    public static View inflate(Context ctx,int id, ViewGroup parent) {
        return View.inflate(ctx, id, parent);
    }


    /**
     * 设置TextView加本来的字
     *
     * @param tv
     * @param string
     */
    public static void tv_SetText(TextView tv, String string) {
        tv.setText(tv.getText() + string);
    }

    /**
     * 获取一个textview
     *
     * @param str
     * @return
     */
    public static TextView getTextView(Context ctx,String str) {
        TextView textView = new TextView(ctx);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(Change.dp2px(ctx,20));
        textView.setText(str);
        return textView;
    }

///////////////////////////////////////////////////////

}
