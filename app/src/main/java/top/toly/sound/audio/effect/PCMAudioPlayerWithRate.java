package top.toly.sound.audio.effect;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import top.toly.app.test.L;

/**
 * 作者：张风捷特烈
 * 时间：2018/7/13:15:52
 * 邮箱：1981462002@qq.com
 * 说明：CMP播放(解码)
 */
public class PCMAudioPlayerWithRate {
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
    private static PCMAudioPlayerWithRate mInstance;//单例
    private int mMinBufferSize;//最小缓存大小
    private AudioEffect mAudioEffect;

    private float rate = 1;//音调
    private File mFile;

    public PCMAudioPlayerWithRate() {
        mExecutorService = Executors.newSingleThreadExecutor();//线程池
    }


    /**
     * 获取单例对象
     *
     * @return
     */
    public static PCMAudioPlayerWithRate getInstance() {
        if (mInstance == null) {
            synchronized (PCMAudioPlayerWithRate.class) {
                if (mInstance == null) {
                    mInstance = new PCMAudioPlayerWithRate();
                }
            }
        }
        return mInstance;
    }


    /**
     * 启动播放
     *
     * @param path 文件了路径
     */
        public void startPlay(String path, int rate) {
        try {
            isStart = true;
            mFile = new File(path);

            mMinBufferSize = AudioTrack.getMinBufferSize(
                    rate, DEFAULT_CHANNEL_CONFIG, AudioFormat.ENCODING_PCM_16BIT);
            //实例化AudioTrack
            audioTrack = new AudioTrack(
                    DEFAULT_STREAM_TYPE, rate, DEFAULT_CHANNEL_CONFIG,
                    DEFAULT_AUDIO_FORMAT, mMinBufferSize, DEFAULT_PLAY_MODE);

            if (mAudioEffect == null) {
                L.d(mMinBufferSize + L.l());//7072
                mAudioEffect = new AudioEffect(2048);
            }

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

    public void setRate(float rate) {
        this.rate = rate;
    }

    //播放线程
    private class PlayRunnable implements Runnable {
        @Override
        public void run() {
            FileInputStream in = null;
            try {
                //标准较重要音频播放优先级
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
                byte[] tempBuffer = new byte[mMinBufferSize];
                in = new FileInputStream(mFile);
                int len;
                while ((len = in.read(tempBuffer)) > 0) {
                    //对读到的流进行处理
                    tempBuffer = rate == 1 ? tempBuffer :
                            mAudioEffect.process(rate, tempBuffer, DEFAULT_SAMPLE_RATE);
                    int tag = audioTrack.write(tempBuffer, 0, len);
                    if (tag == AudioTrack.ERROR_INVALID_OPERATION || tag == AudioTrack.ERROR_BAD_VALUE) {
                        continue;
                    }
                    audioTrack.play();
                }

                stopPlay();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    assert in != null;
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}