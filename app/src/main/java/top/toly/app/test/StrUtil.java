package top.toly.app.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者：张风捷特烈
 * 时间：2018/2/20:17:57
 * 邮箱：1981462002@qq.com
 * 说明：字符串相关工具类
 */
public class StrUtil {

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime(String pattern) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
     *
     * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime_yyyy_MM_dd_HH_mm_ss() {

        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 返回短时间字符串格式yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentTime_yyyyMMddHHmmss() {
        return getCurrentTime("yyyyMMddHHmmss");
    }

    /**
     * 判断字符串是否有值
     *
     * @param value
     * @return 如果为null, 空字符串, 只有空格, 为"null"字符串，则返回true
     */
    public static boolean isEmpty(String value) {
        if (value != null && !"".equalsIgnoreCase(value.trim())
                && !"null".equalsIgnoreCase(value.trim())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 字符串数组转集合
     *
     * @param strs
     * @return 字符串集合
     */
    public static List<String> strs2List(String[] strs) {

        List<String> strings = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            strings.add(strs[i]);
        }
        return strings;
    }

    /**
     * 字符串数组转集合
     *
     * @param strslist
     * @return 字符串集合
     */
    public static String[] List2strs(List<String> strslist) {
        String[] strs = new String[strslist.size()];
        for (int i = 0; i < strslist.size(); i++) {
            strs[i] = strslist.get(i);
        }

        return strs;
    }

    public InputStream getInputStreamFromString(String str) {

        return new ByteArrayInputStream(str.getBytes());

    }

}
