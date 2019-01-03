package top.toly.zutils.core.ui.anima;

import android.app.Activity;
import android.view.WindowManager;

/**
 * 作者：张风捷特烈
 * 时间：2018/4/21:16:27
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class AimUtils {

    /**
     * 内容区域变亮
     */
    public static void lightOn(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 1.0f;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 内容区域变暗
     */
    public static  void lightOff(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = .3f;
        activity.getWindow().setAttributes(lp);

    }
}
