package top.toly.zutils.core.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import top.toly.zutils.core.shortUtils.L;


/**
 * 作者：张风捷特烈
 * 时间：2018/2/28:11:31
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class IOUtils {
    /**
     * 将一个InputStream转化为字符串
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static String readIs(InputStream is) {

        try {
            byte[] buff = new byte[1024];
            int hasRead = 0;
            StringBuilder sb = new StringBuilder("");
            while ((hasRead = is.read(buff)) != -1) {
                sb.append(new String(buff, 0, hasRead));
            }
            close(is);
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(is);
        }
        return null;
    }

    /**
     * @param io 可关闭对象
     * @return
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

//    private String readData(InputStream inputStream) throws IOException {
//        byte[] buffer = new byte[1024];
//        int len;
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();//字节输出流：拿到字节数组
//        while ((len = inputStream.read(buffer)) != -1) {
//            bos.write(buffer, 0, len);
//        }
//        bos.close();
//        inputStream.close();
//        return new String(bos.toByteArray());
//    }
}
