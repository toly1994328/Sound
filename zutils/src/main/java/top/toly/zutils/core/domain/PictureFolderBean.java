package top.toly.zutils.core.domain;

import java.io.File;

/**
 * 作者：张风捷特烈
 * 时间：2018/4/18:11:36
 * 邮箱：1981462002@qq.com
 * 说明：文件夹bean
 */
public class PictureFolderBean {

    /**
     * 当前文件夹路径
     */
    private String dir;
    /**
     * 最多照片的文件夹路径
     */
    private File maxCountDir;
    /**
     * 最多照片的文件夹路径里的照片数
     */
    private int maxCount;
    /**
     * 当前文件夹第一个照片的路径
     */
    private String firstImgPath;
    private String name;
    /**
     * 当前文件夹内图片数量
     */
    private int count;


    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        String[] names = this.dir.split("/");
        this.name = names[names.length - 1];
    }

    public String getFirstImgPath() {
        return firstImgPath;
    }

    public void setFirstImgPath(String firstImgPath) {
        this.firstImgPath = firstImgPath;
    }

    public String getName() {
        return name;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public File getMaxCountDir() {
        return maxCountDir;
    }

    public void setMaxCountDir(File maxCountDir) {
        this.maxCountDir = maxCountDir;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }


    @Override
    public String toString() {
        return "PictureFolderBean{" +
                "dir='" + dir + '\'' +
                ", maxCountDir=" + maxCountDir +
                ", maxCount=" + maxCount +
                ", firstImgPath='" + firstImgPath + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
