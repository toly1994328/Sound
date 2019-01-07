package top.toly.sound.media;

import android.media.MediaPlayer;
import android.os.Handler;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import top.toly.app.test.L;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/4 0004:10:12<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：本地音乐播放
 */
public class MusicPlayer {
    private MediaPlayer mPlayer;

    private Timer mTimer;
    private final Handler mHandler;

    private String filePath;

    public MusicPlayer() {

        mTimer = new Timer();//创建Timer
        mHandler = new Handler();//创建Handler
        init();
    }

    private void init() {
        mPlayer = new MediaPlayer();//1.无业游民


        mPlayer.setOnErrorListener((mp, what, extra) -> {
            //处理错误
            return false;
        });

        //当装载流媒体完毕的时候回调
        mPlayer.setOnPreparedListener(mp->{
            L.d("OnPreparedListener"+L.l());

        });

        //播放完成监听
        mPlayer.setOnCompletionListener(mp -> {
            L.d("CompletionListene"+L.l());
            start(filePath);
        });

        //seekTo方法完成回调
        mPlayer.setOnSeekCompleteListener(mp -> {
            L.d("SeekCompleteListener"+L.l());
        });



        //网络流媒体的缓冲变化时回调
        mPlayer.setOnBufferingUpdateListener((mp, percent) -> {
            if (mOnBufferListener != null) {
                mOnBufferListener.OnSeek(percent * 100);
            }
            L.d("BufferingUpdateListener"+L.l());
        });

    }

    /**
     * 播放
     */
    public void start(String path) {
        //未初始化和正在播放时return
        if (mPlayer!=null && mPlayer.isPlaying()) {
            return;
        }
        try {
            if (path != null) {
                filePath = path;
                mPlayer.reset();
                mPlayer.setDataSource(filePath);
                mPlayer.prepare();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.start();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isPlaying()) {
                    int pos = mPlayer.getCurrentPosition();
                    int duration = mPlayer.getDuration();
                    mHandler.post(() -> {
                        if (mOnSeekListener != null) {
                            mOnSeekListener.OnSeek((int) (pos * 1.f / duration * 100));
                        }
                    });
                }
            }
        }, 0, 1000);
    }

    /**
     * 获取当前进度
     */
    public int getCurrentPosition() {
        return mPlayer.getCurrentPosition();
    }

    public boolean isPlaying() {
        //未初始化和正在播放时return
        if (mPlayer == null) {
            return false;
        }
        return mPlayer.isPlaying();
    }

    /**
     * 销毁
     */
    public void onDestroyed() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();//释放资源
            mPlayer = null;
        }
        mTimer.cancel();
        mTimer = null;
    }

    /**
     * 停止
     */
    private void stop() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }

    /**
     * 重头开始(stop之后)
     */
    public void reStart() {
        mPlayer.seekTo(0);
        mPlayer.start();
    }

    public void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }

    /**
     * 跳转到
     * @param pre_100
     */
    public void seekTo(int pre_100) {
        pause();
        mPlayer.seekTo((int) (pre_100/100.f*mPlayer.getDuration()));
        start(filePath);
    }

    //------------设置进度监听-----------
    public interface OnSeekListener {
        void OnSeek(int per_100);
    }

    private OnSeekListener mOnSeekListener;

    public void setOnSeekListener(OnSeekListener onSeekListener) {
        mOnSeekListener = onSeekListener;
    }

    //------------设置进度监听-----------
    public interface OnBufferListener {
        void OnSeek(int per_100);
    }

    private OnBufferListener mOnBufferListener;

    public void setOnBufferListener(OnBufferListener onBufferListener) {
        mOnBufferListener = onBufferListener;
    }
}
