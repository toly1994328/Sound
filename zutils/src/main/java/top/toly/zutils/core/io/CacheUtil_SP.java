package top.toly.zutils.core.io;

import android.content.Context;

/**
 * 作者：张风捷特烈
 * 时间：2018/3/24:9:53
 * 邮箱：1981462002@qq.com
 * 说明：以SharedPreferences保存缓存
 */
public class CacheUtil_SP {
    /**
     * 以url为key, 以json为value,保存在本地
     *
     * @param url  域名
     * @param json json字符串
     */
    public static void setCache(Context ctx, String url, String json) {
        //也可以用文件缓存: 以MD5(url)为文件名, 以json为文件内容
        SpUtils.setString(ctx, url, json);
    }

    /**
     * 获取缓存
     *
     * @return
     */
    public static String getCache(Context ctx, String url) {
        //文件缓存: 查找有没有一个文件叫做MD5(url)的, 有的话,说明有缓存
        return SpUtils.getString(ctx, url, "");
    }
}
