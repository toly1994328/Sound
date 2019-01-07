package top.toly.sound.audio;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者：张风捷特烈
 * 时间：2018/7/13:15:52
 * 邮箱：1981462002@qq.com
 * 说明：CMP播放(解码)
 */
public class PCMAudioPlayer {
    //默认配置AudioTrack-----此处是解码，要和编码的配置对应
    private static final int DEFAULT_STREAM_TYPE = AudioManager.STREAM_MUSIC;//音乐
    private static final int DEFAULT_PLAY_MODE = AudioTrack.MODE_STREAM;
    private static final int DEFAULT_SAMPLE_RATE = 44100;//采样频率
    private static final int DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_OUT_MONO;//注意是out
    private static final int DEFAULT_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private final ExecutorService mExecutorService;

    private AudioTrack audioTrack;//音轨
    private DataInputStream dis;//流
    private boolean isStart = false;
    private static PCMAudioPlayer mInstance;//单例
    private int mMinBufferSize;//最小缓存大小

    public PCMAudioPlayer() {
        mMinBufferSize = AudioTrack.getMinBufferSize(
                DEFAULT_SAMPLE_RATE, DEFAULT_CHANNEL_CONFIG, AudioFormat.ENCODING_PCM_16BIT);
        //实例化AudioTrack
        audioTrack = new AudioTrack(
                DEFAULT_STREAM_TYPE, DEFAULT_SAMPLE_RATE, DEFAULT_CHANNEL_CONFIG,
                DEFAULT_AUDIO_FORMAT, mMinBufferSize * 2, DEFAULT_PLAY_MODE);
        mExecutorService = Executors.newSingleThreadExecutor();//线程池
    }

    /**
     * 获取单例对象
     *
     * @return
     */
    public static PCMAudioPlayer getInstance() {
        if (mInstance == null) {
            synchronized (PCMAudioPlayer.class) {
                if (mInstance == null) {
                    mInstance = new PCMAudioPlayer();
                }
            }
        }
        return mInstance;
    }

    /**
     * 播放文件
     *
     * @param path
     * @throws Exception
     */
    private void setPath(String path) throws Exception {
        File file = new File(path);
        dis = new DataInputStream(new FileInputStream(file));
    }

    /**
     * 启动播放
     *
     * @param path 文件了路径
     */
    public void startPlay(String path) {
        try {
            isStart = true;
            setPath(path);//设置路径--生成流dis
            mExecutorService.execute(new PlayRunnable());//启动播放线程
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放
     */
    public void stopPlay() {
        try {
            if (audioTrack != null) {
                if (audioTrack.getState() == AudioRecord.STATE_INITIALIZED) {
                    audioTrack.stop();
                }
            }
            if (dis != null) {
                isStart = false;
                dis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        if (audioTrack != null) {
            audioTrack.release();
        }
        mExecutorService.shutdownNow();//停止线程池
    }

    //播放线程
    private class PlayRunnable implements Runnable {
        @Override
        public void run() {
            try {
                //标准较重要音频播放优先级
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
                byte[] tempBuffer = new byte[mMinBufferSize];
                int readCount = 0;
                while (dis.available() > 0) {
                    readCount = dis.read(tempBuffer);//读流
                    if (readCount == AudioTrack.ERROR_INVALID_OPERATION || readCount == AudioTrack.ERROR_BAD_VALUE) {
                        continue;
                    }
                    if (readCount != 0 && readCount != -1) {//
                        audioTrack.play();
                        audioTrack.write(tempBuffer, 0, readCount);
                    }
                }
                stopPlay();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}