package top.toly.app.io;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import top.toly.app.test.L;


/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/2/28:11:31<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：IO工具<br/>
 */
public class IOUtils {
    /**
     * 将一个InputStream转化为字符串
     *
     * @param is 输入流
     * @return 流转化的字符串
     */
    public static String read(InputStream is) {

        try {
            byte[] temp = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder("");
            while ((len = is.read(temp)) != -1) {
                sb.append(new String(temp, 0, len));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR";
        } finally {
            close(is);
        }
    }


    public static byte[] read2bytes(InputStream is) {
        ByteArrayOutputStream baos = null;
        try {
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR".getBytes();
        } finally {
            close(is);
            close(baos);
        }
    }

    /**
     * 写入OutputStream
     *
     * @param os          输出流
     * @param fileContent 文本内容
     */
    public static void write(OutputStream os, String fileContent) {

        try {
            if (fileContent != null) {
                os.write(fileContent.getBytes());
            }
            close(os);//关闭输出流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param io 可关闭对象
     * @return 正确关闭
     */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                L.e(e);
            }
        }
        return true;
    }
}
