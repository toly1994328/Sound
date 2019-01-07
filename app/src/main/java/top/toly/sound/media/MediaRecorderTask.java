package top.toly.sound.media;

import android.media.MediaRecorder;
import android.os.Handler;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 作者：张风捷特烈
 * 时间：2018/4/16:10:33
 * 邮箱：1981462002@qq.com
 * 说明：MediaRecorder录音帮助类
 */
public class MediaRecorderTask {
    private MediaRecorder mRecorder;
    private long mStartTime;//开始的时间
    private int mAllTime;//总共耗时
    private boolean isRecording;//是否正在录音
    private File mFile;//文件

    private Timer mTimer;
    private final Handler mHandler;

    public MediaRecorderTask() {
        mTimer = new Timer();//创建Timer
        mHandler = new Handler();//创建Handler
    }

    /**
     * 开始录音
     */
    public void start(File file) {

        doRecorder(file);
    }

    private void doRecorder(File file) {
        mAllTime = 0;
        mFile = file;
        if (mRecorder == null) {
            // [1]获取MediaRecorder类的实例
            mRecorder = new MediaRecorder();
        }
        //配置MediaRecorder
        // [2]设置音频的来源
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // [3]设置音频的输出格式
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        // [4]采样频率
        mRecorder.setAudioSamplingRate(44100);
        // [5]设置音频的编码方式
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //[6]音质编码频率:96Kbps
        mRecorder.setAudioEncodingBitRate(96000);
        //[7]设置录音文件位置
        mRecorder.setOutputFile(file.getAbsolutePath());
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mStartTime = System.currentTimeMillis();
        if (mRecorder != null) {
            mRecorder.start();
            isRecording = true;
            cbkVolume();
        }
    }

    /**
     * 每隔1秒回调一次音量
     */
    private void cbkVolume() {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isRecording) {
                    float per;
                    try {
                        //获取音量大小
                        per = mRecorder.getMaxAmplitude() / 32767f;//最大32767
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                        per = (float) Math.random();
                    }
                    if (mOnVolumeChangeListener != null) {
                        float finalPer = per;
                        mHandler.post(() -> {
                            mOnVolumeChangeListener.volumeChange(finalPer);
                        });
                    }
                }
            }
        }, 0, 1000);
    }


    public void pause() {
        mAllTime += System.currentTimeMillis() - mStartTime;
        mRecorder.pause(); // [7]暂停录
        isRecording = false;
        mStartTime = System.currentTimeMillis();

    }

    public void resume() {
        mRecorder.resume(); // [8]恢复录
        isRecording = true;

    }

    /**
     * 停止录音
     */
    public void stop() {
        try {
            mAllTime += System.currentTimeMillis() - mStartTime;
            mRecorder.stop(); // [7]停止录
            isRecording = false;
            mRecorder.release();
            mRecorder = null;
        } catch (RuntimeException e) {
            mRecorder.reset();//[8] You can reuse the object by going back
            mRecorder.release(); //[9] Now the object cannot be reused
            mRecorder = null;
            isRecording = false;
            if (mFile.exists())
                mFile.delete();
        }
    }

    public int getAllTime() {
        return mAllTime / 1000;
    }

    //---------设置音量改变监听-------------
    public interface OnVolumeChangeListener {
        void volumeChange(float per);
    }

    private OnVolumeChangeListener mOnVolumeChangeListener;

    public void setOnVolumeChangeListener(OnVolumeChangeListener onVolumeChangeListener) {
        mOnVolumeChangeListener = onVolumeChangeListener;
    }
}
