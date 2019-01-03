package top.toly.zutils.phone.call;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import top.toly.zutils.core.domain.PictureFolderBean;


/**
 * 作者：张风捷特烈
 * 时间：2018/4/14:10:15
 * 邮箱：1981462002@qq.com
 * 说明：获取手机，联系人工具类
 */
public class PhoneUtils_Picture {

    //////////////////////////获取手机的图片///////////////////////////////////

    public static List<PictureFolderBean> getAllImageFromPhone(Context ctx) {
        List<PictureFolderBean> mFolderBeans = new ArrayList<>();
        File mCurrentDir = null;
        int mMaxCount = 0;
        //查询获得游标
        Uri mIngUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver resolver = ctx.getContentResolver();
        Cursor cursor = resolver.query(mIngUri, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);

        //通过游标获取path，创建folderBean对象并赋值
        Set<String> mDirPaths = new HashSet<>();//为避免重复扫描，将dirPath放入集合
        while (cursor.moveToNext()) {
            String path = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            File parentFile = new File(path).getParentFile();
            if (parentFile == null) continue;

            PictureFolderBean folderBean = null;

            String dirPath = parentFile.getAbsolutePath();
            if (mDirPaths.contains(dirPath)) {
                continue;
            } else {
                mDirPaths.add(dirPath);
                folderBean = new PictureFolderBean();
                folderBean.setDir(dirPath);
                folderBean.setFirstImgPath(path);
            }

            if (parentFile.list() == null) {
                continue;
            } else {
                int imgCount = parentFile.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        if (name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg")) {
                            return true;
                        }
                        return false;
                    }
                }).length;

                folderBean.setCount(imgCount);
                mFolderBeans.add(folderBean);
            }
        }
        cursor.close();
        return mFolderBeans;
    }
}
