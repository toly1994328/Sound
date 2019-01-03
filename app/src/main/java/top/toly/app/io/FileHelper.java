package top.toly.app.io;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import top.toly.app.test.L;
import top.toly.app.test.ToastUtil;

import static top.toly.app.io.IOUtils.close;
import static top.toly.app.io.IOUtils.read;
import static top.toly.app.io.IOUtils.write;


/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/2/18:15:06<br/>
 * 邮箱：1981462002@qq.com <br/>
 * 说明：文件帮助类 单例模式<br/>
 */
public class FileHelper {


    ////////////////////////单例模式start//////////////////////////////
    /**
     * 文件帮助类对象
     */
    private static FileHelper sFileHelper;

    /**
     * 私有化构造函数
     */
    private FileHelper() {
    }

    /**
     * 单例模式获取FileHelper
     *
     * @return FileHelper
     */
    public static FileHelper get() {
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
     * 路径判断，输出路径
     *
     * @param savePath 路径
     * @return 判断是否存在SD卡，若有，保持此路径，没有则用/data/data/...下的/files
     */
    public String sdCardOrLocalPath(Context ctx, String savePath) {
        if (hasSdCard()) {
            return savePath;
        } else {
            ToastUtil.show(ctx, "SD卡不存在或者不可读写");
            return PathUtils.getFilesPath(ctx) + File.separator;
        }

    }

    //////////////////////////从assets 中读取文件/////////////

    /**
     * 从assets 中读取文件
     *
     * @param fileName 文件名
     * @return 文件内容
     */
    public String readInAssets(Context ctx, String fileName) {
        InputStream is = null;
        String result = null;
        try {
            is = ctx.getAssets().open(fileName);
            result = read(is);
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
     * 在data/data/本包中写入文件
     *
     * @param fileName    文件名
     * @param fileContent 文件内容
     * @param append      是否以追加模式
     */
    public void writeInLocal(Context ctx, String fileName, String fileContent, boolean append) {
        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput(fileName, append ? Context.MODE_APPEND : Context.MODE_PRIVATE);
            write(fos, fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(fos);
        }
    }

    /**
     * 在data/data/本包中读取文件
     *
     * @param fileName 文件名
     * @return 文件内容
     */
    public String readInLocal(Context ctx, String fileName) {
        FileInputStream fis = null;
        String result = null;
        try {
            fis = ctx.openFileInput(fileName);
            result = read(fis);
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
     * 判断是否存在SD卡
     *
     * @return 是否存在SD卡
     */
    private boolean hasSdCard() {
        return Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 在SD卡中创建文件的核心代码
     *
     * @param savePath    保存的绝对路径(路径不存在会自动创建上级文件夹)
     * @param fileContent 文件内容
     * @param append      是否以追加模式
     */
    private File writeFileWithAbsolutePath(String savePath, String fileContent, boolean append) {
        FileOutputStream fos = null;
        File filePic = null;
        try {
            filePic = new File(savePath);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            fos = append ? new FileOutputStream(savePath, true) : new FileOutputStream(savePath);
            write(fos, fileContent);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fos);
        }
        return filePic;
    }

    /**
     * 在SD卡中创建文件暴露方法
     *
     * @param filename    文件名:(形式："XX/YY/ZZ.UU")
     * @param fileContent 文件内容
     * @param append      是否以追加模式
     */
    public File writeFile2SD(String filename, String fileContent, boolean append) {
        return writeFileWithAbsolutePath(PathUtils.getSDPath() + File.separator + filename, fileContent, append);
    }

    /**
     * 在SD卡中创建文件暴露方法
     *
     * @param filename    文件名:(形式："XX/YY/ZZ.UU")
     * @param fileContent 文件内容
     */
    public File writeFile2SD(String filename, String fileContent) {
        return writeFileWithAbsolutePath(PathUtils.getSDPath() + File.separator + filename, fileContent, false);
    }


    /**
     * 在SD卡中创建空文件
     *
     * @param filename 文件名
     * @return 文件对象
     */
    public File createFile(String filename) {

        return writeFile2SD(filename, "", false);
    }

    /**
     * 在SD卡中读取文件
     *
     * @param filename 文件名
     * @return 文件内容
     */
    private String readFileWithAbsolutePath(String filename) {
        String result = null;
        FileInputStream input = null;
        if (hasSdCard()) {
            try {
                input = new FileInputStream(filename);//文件输入流
                result = read(input);//读取InputStream
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
     * @param fileName 文件名
     * @return 文件内容
     */
    public String readFromSD(String fileName) {
        return readFileWithAbsolutePath(PathUtils.getSDPath() + File.separator + fileName);
    }

}
