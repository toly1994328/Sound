package top.toly.zutils.core.io;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import top.toly.zutils.core.shortUtils.Md5Util;


/**
 * 作者：张风捷特烈
 * 时间：2018/3/24:10:01
 * 邮箱：1981462002@qq.com
 * 说明：以文件保存缓存
 */
public class CacheUtil_File {

    /**
     * 写缓存
     * @param url key
     * @param json value
     */
    public static void setCache(Context ctx,String url, String json) {
        // 以url为文件名, 以json为文件内容,保存在本地
        File cacheDir = ctx.getCacheDir();// 本应用的缓存文件夹
        // 生成缓存文件
        File cacheFile = new File(cacheDir.getPath()
                + File.separator + Md5Util.getMD5(url));

        FileWriter writer = null;
        try {
            writer = new FileWriter(cacheFile);
            // 缓存失效的截止时间
            long deadline = System.currentTimeMillis() + 24 * 60 * 60 * 1000;//一天有效期
            writer.write(deadline + "\n");// 在第一行写入缓存时间, 换行
            writer.write(json);// 写入json
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }
    }

    /**
     * 读缓存
     * @param url
     * @return
     */
    public static String getCache(Context ctx,String url) {
        // 以url为文件名, 以json为文件内容,保存在本地
        File cacheDir = ctx.getCacheDir();// 本应用的缓存文件夹
        // 生成缓存文件

        File cacheFile = new File(cacheDir.getPath() +
                File.separator + Md5Util.getMD5(url));
        // 判断缓存是否存在
        if (cacheFile.exists()) {
            // 判断缓存是否有效
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                String deadline = reader.readLine();// 读取第一行的有效期
                long deadtime = Long.parseLong(deadline);

                if (System.currentTimeMillis() < deadtime) {// 当前时间小于截止时间,
                    // 说明缓存有效
                    // 缓存有效
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    return sb.toString();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }

        }
        return null;
    }

}
