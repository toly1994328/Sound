package top.toly.zutils.core.io;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * 作者：张风捷特烈
 * 时间：2018/2/20:17:57
 * 邮箱：1981462002@qq.com
 * 说明：SharedPreferences工具类
 */
public class SpUtils {



    private static SharedPreferences sp;

////////////////////////////////////boolean类型///////////////////////////////////////////////

    /**
     * 写入boolean变量至sp中
     *
     * @param key   存储节点名称
     * @param value 存储节点的值 boolean
     */
    public static void setBoolean(Context ctx, String key, boolean value) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }


    /**
     * 2参数：读取boolean标示从sp中
     *
     * @param key      存储节点名称
     * @param defValue 默认值
     * @return
     */
    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }


    /**
     * 1参数: 读取boolean标示从sp中
     *
     * @param key 存储节点名称
     * @return
     */
    public static boolean getBoolean(Context ctx, String key) {

        return getBoolean(ctx, key, false);
    }


////////////////////////////////////String类型///////////////////////////////////////////////

    /**
     * 写入String变量至sp中
     *
     * @param key   存储节点名称
     * @param value 存储节点的值string
     */
    public static void setString(Context ctx, String key, String value) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    /**
     * 2参数:从sp中读取String
     *
     * @param key      存储节点名称
     * @param defValue 默认值
     * @return
     */
    public static String getString(Context ctx, String key, String defValue) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }


    /**
     * 1参数:从sp中读取String
     *
     * @param key 存储节点名称
     * @return
     */
    public static String getString(Context ctx, String key) {

        return getString(ctx, key, "");
    }

///////////////////////////int类型///////////////////////////

    /**
     * 写入int变量至sp中
     *
     * @param key   存储节点名称
     * @param value 存储节点的值string
     */
    public static void setInt(Context ctx, String key, int value) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 2参数:从sp中读取int
     *
     * @param key      存储节点名称
     * @param defValue 默认值
     * @return
     */
    public static int getInt(Context ctx, String key, int defValue) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }


    /**
     * 1参数:从sp中读取int
     *
     * @param key 存储节点名称
     * @return
     */
    public static int getInt(Context ctx, String key) {

        return getInt(ctx, key, 0);
    }

    /////////////////////////////////////////////////////////////////

    /**
     * 从sp中移除指定节点
     *
     * @param key 需要移除节点的名称
     */
    public static void remove(Context ctx, String key) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();
    }

}
