package top.toly.zutils.core.ui.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 作者：张风捷特烈
 * 时间：2018/4/18:8:42
 * 邮箱：1981462002@qq.com
 * 说明：图片加载类
 */
public class ImageLoder {

    private static ImageLoder sImageLoder;
    private static Context mCtx;
    private LruCache<String, Bitmap> mLruCache;//图片缓存核心对象
    private ExecutorService mThreadPoll;//线程池
    private static final int DEFAULT_THREAD_COUNT = 1;
    private static Type mType = Type.LIFO;//队列调度方式
    private LinkedList<Runnable> mTaskQueue;//任务队列
    private Thread mPoolThread;//后台轮循线程
    private Handler mPoolTreadHandler;
    private Handler mUIHandeler;//主线程
    //同步线程
    private Semaphore mSemaphorePollThreadHandler = new Semaphore(0);//s1==to  s2

    private Semaphore mSemaphoreThreadPoll;

    public static ImageLoder getInstance(Context ctx) {
        mCtx = ctx;
        return getInstance(3, Type.LIFO);
    }

    public enum Type {
        FIFO, LIFO
    }


    private ImageLoder(int threadCount, Type type) {

        init(threadCount, type);

    }


    /**
     * 初始化操作
     *
     * @param threadCount
     * @param type
     */
    private void init(int threadCount, Type type) {
        //后台轮训线程
        mPoolThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mPoolTreadHandler = new Handler() {//this is TAG1
                    @Override
                    public void handleMessage(Message msg) {
                        //线程池取出任务执行
                        mThreadPoll.execute(getTask());
                        try {
                            mSemaphoreThreadPoll.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                };
                mSemaphorePollThreadHandler.release();//this is s3释放信号量
                Looper.loop();
            }
        };

        mPoolThread.start();


        //初始化LruCache
        int maxMemory = (int) Runtime.getRuntime().maxMemory(); //获取该应用的最大可用内存
        int cacheMemory = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

        //初始化线程池
        mThreadPoll = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<>();
        mType = type;
        mSemaphoreThreadPoll = new Semaphore(threadCount);

    }

    /**
     * 从任务队列获取task
     *
     * @return
     */
    private Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTaskQueue.removeFirst();
        } else if (mType == Type.LIFO) {
            return mTaskQueue.removeLast();
        }
        return null;
    }


    public static ImageLoder getInstance(int threadCount, Type type) {

        if (sImageLoder == null) {
            synchronized (ImageLoder.class) {
                if (sImageLoder == null) {
                    sImageLoder = new ImageLoder(threadCount, type);
                }
            }
        }
        return sImageLoder;
    }

    /**
     * 根据path为imageView设置图片
     *
     * @param path
     * @param imageView
     */
    public void loadImage(final String path, final ImageView imageView) {
        imageView.setTag(path);//为做校验，避免混乱

        if (mUIHandeler == null) {
            mUIHandeler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    //获取图片，为imageView设置图片
                    ImgBean imgBean = (ImgBean) msg.obj;
                    Bitmap bitmap = imgBean.bitmap;
                    ImageView imageView = imgBean.imageView;
                    String path = imgBean.path;
                    //将path与imageView.getTag()进行比较
                    if (imageView.getTag().toString().equals(path)) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            };
        }

        Bitmap bm = getBitmapFromLruChe(path);

        if (bm != null) {
            refreshBitmap(bm, path, imageView);//发送消息刷新Bitmap
        } else {
            addTasks(new Runnable() {

                @Override
                public void run() {
                    //加载图片
                    //图片压缩
                    ImgBean size = getImageViewSize(mCtx,imageView);//1:获取要压缩的尺寸
                    Bitmap bm = zipBitmup(path, size.width, size.hight);//2:压缩图片
                    addBitMap2LruCache(path, bm);//将Bitmap加入LruCache
                    refreshBitmap(bm, path, imageView);// 发送消息刷新Bitmap
                    mSemaphoreThreadPoll.release();
                }
            });
        }
    }

    /**
     * 发送消息刷新Bitmap
     *
     * @param bm
     * @param path
     * @param imageView
     */
    private void refreshBitmap(Bitmap bm, String path, ImageView imageView) {
        Message message = Message.obtain();
        ImgBean imgBean = new ImgBean();
        imgBean.bitmap = bm;
        imgBean.path = path;
        imgBean.imageView = imageView;
        message.obj = imgBean;//用message传递对象
        mUIHandeler.sendMessage(message);
    }

    /**
     * 将图片加入LruCache
     *
     * @param path
     * @param bm
     */
    private void addBitMap2LruCache(String path, Bitmap bm) {
        if (getBitmapFromLruChe(path) == null) {
            if (bm != null) {
                mLruCache.put(path, bm);
            }
        }
    }

    /**
     * 压缩图片
     *
     * @param path
     * @param width
     * @param hight
     * @return
     */
    private Bitmap zipBitmup(String path, int width, int hight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//获取图片宽高信息，并不把图片加载至内存
        BitmapFactory.decodeFile(path, options);


        options.inSampleSize = caculateSize(options, width, hight);


        //再次解析图片，需加载如内存
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }


    /**
     * 根据需求的宽高和实际宽高计算SampleSize
     *
     * @param options
     * @param width
     * @param hight
     * @return
     */
    private int caculateSize(BitmapFactory.Options options, int width, int hight) {
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int inSampleSize = 1;
        if (outWidth > width || outHeight > hight) {
            int widthRadio = Math.round(outWidth * 1.0f / width);
            int heightRadio = Math.round(outHeight * 1.0f / hight);
            inSampleSize = Math.max(widthRadio, heightRadio);
        }

        return inSampleSize;
    }

    /**
     * 获取imageView要显示的尺寸
     *
     * @param imageView
     * @return
     */
    private ImgBean getImageViewSize(Context ctx,ImageView imageView) {
        ImgBean imgBean = new ImgBean();
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        int width = imageView.getWidth();
        if (width <= 0) {
            width = lp.width;//获取imageView在layout中声明的宽度
        }
        if (width <= 0) {
            width = imageView.getMaxWidth();//检查最大值
        }
        if (width <= 0) {
            width = ScrUtil.getScreenWidth(ctx);
        }

        int hight = imageView.getHeight();
        if (hight <= 0) {
            hight = lp.width;//获取imageView在layout中声明的宽度
        }
        if (hight <= 0) {
            hight = imageView.getMaxHeight();//检查最大值
        }
        if (hight <= 0) {
            hight = ScrUtil.getScreenHeight(ctx);
        }
        imgBean.width = width;
        imgBean.hight = hight;
        return imgBean;
    }

    /**
     * @param runnable
     */
    private synchronized void addTasks(Runnable runnable) {//this is s2 同步一下
        mTaskQueue.add(runnable);

        try {
            if (mPoolTreadHandler == null) {
                mSemaphorePollThreadHandler.acquire();//this is s2 请求会阻塞 to s3
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mPoolTreadHandler.sendEmptyMessage(0x11);//to TAG1
    }

    /**
     * 通过path获取图片
     *
     * @param path
     * @return
     */
    private Bitmap getBitmapFromLruChe(String path) {

        return mLruCache.get(path);
    }

    /**
     * 封装imageView信息Bean对象，用于message传递数据
     */
    private class ImgBean {
        Bitmap bitmap;
        ImageView imageView;
        String path;
        int width;
        int hight;
    }

    /**
     * 通过反射获取一个类中的属性值
     *
     * @param o
     * @param fieldName
     * @return
     */
    private static int getFieldValue(Object o, String fieldName) {
        int value = 0;

        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = field.getInt(o);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return value;

    }


}
