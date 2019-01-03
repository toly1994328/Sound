package top.toly.zutils.phone.media;

import android.media.MediaRecorder;

import java.io.File;


/**
 * 作者：张风捷特烈
 * 时间：2018/4/16:10:33
 * 邮箱：1981462002@qq.com
 * 说明：录音帮助类（单例模式）
 */
public class Recorder {

    private static final Recorder M_RECORDER = new Recorder();

    private Recorder() {
    }

    public static Recorder get() {
        return M_RECORDER;
    }


    /**
     * 获取一个录音器
     */
    public MediaRecorder saveTo(File file) {
        MediaRecorder mRecorder;

        // [1]获取MediaRecorder类的实例
        mRecorder = new MediaRecorder();
        //配置MediaRecorder
        // [2]设置音频的来源
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // [3]设置音频的输出格式
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        // [4]采样频率
        mRecorder.setAudioSamplingRate(44100);
        // [5]设置音频的编码方式
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //[6]音质编码频率
        mRecorder.setAudioEncodingBitRate(96000);
        //[7]设置录音文件位置
        mRecorder.setOutputFile(file.getAbsolutePath());
        return mRecorder;
    }

    /**
     * 开始录音
     *
     * @param recorder 要停止的录音
     */
    public void start(MediaRecorder recorder) {

        if (recorder != null) {
            recorder.start();

        }
    }

    /**
     * 停止录音
     *
     * @param recorder 要停止的录音
     */
    public void stop(MediaRecorder recorder) {
        if (recorder != null) {
            recorder.stop(); // [7]停止录
            recorder.reset(); //[8] You can reuse the object by going back
            recorder.release(); //[9] Now the object cannot be reused
        }
    }
}
