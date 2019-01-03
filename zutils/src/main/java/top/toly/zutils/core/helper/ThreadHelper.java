package top.toly.zutils.core.helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者：张风捷特烈
 * 时间：2018/7/12:15:04
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class ThreadHelper {
    private static ExecutorService sExecutorService = Executors.newSingleThreadExecutor();

    public static void execute(Runnable runnable) {
        sExecutorService.execute(runnable);
    }
}
