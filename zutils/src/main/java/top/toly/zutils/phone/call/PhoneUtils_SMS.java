package top.toly.zutils.phone.call;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import top.toly.zutils.core.domain.SMSBean;
import top.toly.zutils.core.io.FileHelper;
import top.toly.zutils.core.shortUtils.ToastUtil;

import static android.content.Context.MODE_PRIVATE;

/**
 * 作者：张风捷特烈
 * 时间：2018/4/14:10:15
 * 邮箱：1981462002@qq.com
 * 说明：获取手机对象工具类
 */
public class PhoneUtils_SMS {

/////////////////////////***短信start****//////////////////////////
    /**
     * 获取短信：SMSBean：address发信人  date时间  body信息内容
     *
     * @return 短信bean集合 注意添加读取短信权限
     */

    public static List<SMSBean> getSMS(Context ctx) {
        List<SMSBean> mDatas = new ArrayList<>();
        mDatas = new ArrayList<>();
        ContentResolver resolver = ctx.getContentResolver();//获得ContentResolver对象
        Uri uri = Uri.parse("content://sms");//访问的url
        String[] projection = {"address", "date", "body"};//访问表的字段

        Cursor cursor = resolver.query(//查询表，获得游标结果集
                uri, projection, null, null, null);
        while (cursor.moveToNext()) {//遍历游标，获取数据，储存在bean中
            SMSBean smsBean = new SMSBean();
            smsBean.address = cursor.getString(0);
            smsBean.date = cursor.getString(1);
            smsBean.body = cursor.getString(2);
            mDatas.add(smsBean);
        }
        cursor.close();
        return mDatas;
    }

    /**
     * 备份短信
     *
     * @param os
     */
    private static void backupsSMS(Context ctx,OutputStream os) {
        XmlSerializer serializer = Xml.newSerializer();
        try {
            serializer.setOutput(os, "utf-8");
            serializer.startDocument("utf-8", true);//文档开始
            serializer.startTag(null, "SMS");//标签开始
            for (SMSBean sms : getSMS(ctx)) {//遍历
                serializer.startTag(null, "sms");
                serializer.startTag(null, "address");
                serializer.text(sms.address.trim());
                serializer.endTag(null, "address");

                serializer.startTag(null, "date");
                serializer.text(sms.date.trim());
                serializer.endTag(null, "date");

                serializer.startTag(null, "body");
                serializer.text(sms.body.trim());
                serializer.endTag(null, "body");
                serializer.endTag(null, "sms");
            }
            serializer.endTag(null, "SMS");//标签结束
            serializer.endDocument();//文档结束
        } catch (IOException e) {
            ToastUtil.show(ctx,"备份短信失败！");
            e.printStackTrace();
        }
    }

    /**
     * 备份短信到SD卡
     *
     * @param name 文件名
     */
    public static void backupsSMS2SD(Context ctx,String name) {
        backupsSMS(ctx,FileHelper.get(ctx).getFosInSD(name));
        ToastUtil.show(ctx,"备份短信成功！请在SD卡根目录下查看");
    }

    /**
     * 备份短信到本应用文件夹
     *
     * @param name 文件名
     */
    public static void backupsSMS2Local(Context ctx,String name) {
        try {
            backupsSMS(ctx,ctx.openFileOutput(name, MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
/////////////////////////***短信end****//////////////////////////////

}
