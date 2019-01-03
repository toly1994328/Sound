package top.toly.zutils.core.helper;

import android.os.Handler;
import android.os.Looper;

/**
 * 作者：张风捷特烈
 * 时间：2018/7/12:15:08
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class Poster extends Handler {
    private static Poster sPoster;

    private Poster() {
        super(Looper.getMainLooper());
    }

    public static Poster getPoster() {
        if (sPoster == null) {
            synchronized (Poster.class) {
                if (sPoster == null) {
                    sPoster = new Poster();
                }
            }
        }
        return sPoster;
    }
}
