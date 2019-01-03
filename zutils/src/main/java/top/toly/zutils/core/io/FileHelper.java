package top.toly.zutils.core.io;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import top.toly.zutils.core.shortUtils.L;
import top.toly.zutils.core.shortUtils.ToastUtil;

/**
 * 作者：张风捷特烈
 * 时间：2018/2/18:15:06
 * 邮箱：1981462002@qq.com
 * 说明：文件帮助类 单例模式
 */
public class FileHelper {

    private static Context mContext;

    ////////////////////////单例模式start//////////////////////////////
    private static FileHelper sFileHelper;

    private FileHelper() {
    }

    public static FileHelper get(Context ctx) {
        mContext = ctx;
        if (sFileHelper == null) {
            synchronized (FileHelper.class) {
                if (sFileHelper == null) {
                    sFileHelper = new FileHelper();
                }
            }
        }
        return sFileHelper;
    }
////////////////////////单例模式end//////////////////////////////

    /**
     * 读取InputStream
     *
     * @param is 输入流
     * @return 流转化的字符串
     * @throws IOException
     */
    private static String readIs(InputStream is) throws IOException {
        byte[] temp = new byte[1024];
        int len = 0;
        StringBuilder sb = new StringBuilder("");
        while ((len = is.read(temp)) > 0) {
            sb.append(new String(temp, 0, len));
        }
        return sb.toString();
    }


    /**
     * 写入OutputStream
     *
     * @param os
     * @param file_content
     */
    private void write(OutputStream os, String file_content) {

        try {
            if (file_content != null) {
                os.write(file_content.getBytes());
            }
            close(os);//关闭输出流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭可关闭对象
     *
     * @param io 可关闭对象
     * @return
     */
    private boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                L.e(e);
            }
        }
        return true;
    }

    /**
     * //////////////////////////从assets 中读取文件/////////////
     *
     * @param filename
     * @return
     */
    public String readInAssets(String filename) {
        InputStream is = null;
        String result = null;
        try {
            is = mContext.getAssets().open(filename);
            result = readIs(is);
            //关闭输入流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(is);
        }
        return result;
    }

///////////////////////***data/data/本包***/////////////////////////////////

    /**
     * 在data/data/本包中写入文件:覆盖原文件模式
     *
     * @param filename     文件名
     * @param file_content 文件内容
     */
    public void writeInLocal_MODE_PRIVATE(String filename, String file_content) {
        FileOutputStream fos = null;
        try {
            fos = mContext.openFileOutput(filename, Context.MODE_PRIVATE);
            write(fos, file_content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(fos);
        }
    }

    /**
     * 在data/data/本包中写入文件:追加文件模式
     *
     * @param filename     文件名
     * @param file_content 文件内容
     */
    public void writeInLocal_MODE_APPEND(String filename, String file_content) {
        FileOutputStream fos = null;
        try {
            fos = mContext.openFileOutput(filename, Context.MODE_APPEND);
            write(fos, file_content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(fos);
        }
    }

    /**
     * 在data/data/本包中读取文件
     *
     * @param filename 文件名
     * @return 文件内容
     */
    public String readInLocal(String filename) {
        FileInputStream fis = null;
        String result = null;
        try {
            fis = mContext.openFileInput(filename);
            result = readIs(fis);
            //关闭输入流
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fis);
        }
        return result;
    }

    //////////////////////************SD卡:名称格式：XX/YY/ZZ.UU************//////////////////

    /**
     * 判断是否存在SD卡，若有，保持此路径，没有则用/data/data/...下的/files
     *
     * @param savePath
     * @return
     */
    public String sdCardOrLoaclPath(String savePath) {
        if (hasSdCard()) {
            return savePath;
        } else {
            ToastUtil.show(mContext, "SD卡不存在或者不可读写");
            return PathUtils.getFilesPath(mContext) + File.separator;
        }

    }

    /**
     * 判断是否存在SD卡
     *
     * @return 是否存在SD卡
     */
    public boolean hasSdCard() {
        return Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 在SD卡中创建文件
     *
     * @param savePath     保存路径
     * @param file_content 文件内容
     */
    public File saveFileWithAbsolutePath(String savePath, String file_content) {
        savePath = sdCardOrLoaclPath(savePath);
        FileOutputStream fos = null;
        File filePic = null;
        try {
            filePic = new File(savePath);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }

            fos = new FileOutputStream(savePath);
            write(fos, file_content);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fos);
        }
        return filePic;
    }

    /**
     * 在SD卡中创建文件
     *
     * @param filename    文件名
     * @param filecontent 文件内容
     */
    public File saveFile2SD(String filename, String filecontent) {
        return saveFileWithAbsolutePath(PathUtils.getSDPath(mContext) + File.separator + filename, filecontent);
    }

    /**
     * 在SD卡中创建空文件
     */
    public File createFile(String filename) throws IOException {
        File mFile;
        if (hasSdCard()) {
            mFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator +
                    filename);
            mFile.getParentFile().mkdirs();
            mFile.createNewFile();
            return mFile;
        }
        return null;
    }

    /**
     * 在SD卡中读取文件
     *
     * @param filename 文件名
     * @return 文件内容
     */
    public String readFileWithAbsolutePath(String filename) {
        String result = null;
        FileInputStream input = null;
        if (hasSdCard()) {
            try {
                input = new FileInputStream(filename);//文件输入流
                result = readIs(input);//读取InputStream
                close(input); //关闭输入流
            } catch (IOException e) {
                e.printStackTrace();
                L.e(e.toString());
            } finally {
                close(input);
            }
        }
        return result;
    }

    /**
     * 在SD卡中读取文件
     *
     * @param filename 文件名
     * @return 文件内容
     */
    public String readFromSD(String filename) {
        return readFileWithAbsolutePath(PathUtils.getSDPath(mContext) + File.separator + filename);
    }

    /**
     * 在SD卡新建一个文件，并获得它的输出流
     *
     * @param fileName
     * @return
     */
    public FileOutputStream getFosInSD(String fileName) {

        File file = saveFile2SD(fileName, "");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fos;
    }

    ////////////////////////////////////删除文件----------------------------
    public boolean delete(File file) {
        boolean delete = file.delete();
        return delete;
    }

    /**
     * 通过File获得Uri
     *
     * @param file
     * @return
     */
    public Uri getUriFormFile(File file) {

        return (Uri.fromFile(file));
    }

}
