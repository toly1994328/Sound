package top.toly.zutils.core.manager;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/26.
 */
public class ActivityCollector {
    public static ArrayList<Activity> activities = new ArrayList<>();
    /**
     * a1：添加Activity对象
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * a2:移除Activity对象
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * a3：结束所有Activity
     */
    public static void finishAll() {
        for (Activity act:activities) {
            if (!act.isFinishing()) {
                act.finish();
            }
        }
    }
}
