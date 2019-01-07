package top.toly.sound.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import top.toly.app.test.L;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/4 0004:10:12<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：网络音乐播放测试
 */
public class NetMusicPlayer {
    private Timer mTimer;
    private MediaPlayer mPlayer;
    private Context mContext;

    private boolean isInitialized = false;//是否已初始化
    private final Handler mHandler;

    public NetMusicPlayer(Context context) {
        mContext = context;
        mTimer = new Timer();//创建Timer
        mHandler = new Handler();//创建Handler
        init();
    }

    private void init() {
        mPlayer = new MediaPlayer();//1.无业游民
        Uri uri = Uri.parse("http://www.toly1994.com:8089/file/洛天依.mp3");
        try {
            mPlayer.setDataSource(mContext, uri);//2.找到工作
            mPlayer.prepareAsync();//3.异步准备明天的工作
        } catch (IOException e) {
            e.printStackTrace();
        }


        mPlayer.setOnErrorListener((mp, what, extra) -> {
            //处理错误
            return false;
        });

        //当装载流媒体完毕的时候回调
        mPlayer.setOnPreparedListener(mp -> {//4.准备OK
            L.d("OnPreparedListener" + L.l());
            isInitialized = true;
        });

        //播放完成监听
        mPlayer.setOnCompletionListener(mp -> {
            L.d("CompletionListene" + L.l());
            start();
        });

        //seekTo方法完成回调
        mPlayer.setOnSeekCompleteListener(mp -> {
            L.d("SeekCompleteListener" + L.l());
        });


        //网络流媒体的缓冲变化时回调
        mPlayer.setOnBufferingUpdateListener((mp, percent) -> {
            if (mOnBufferListener != null) {
                mOnBufferListener.OnSeek(percent);
            }
            L.d("BufferingUpdateListener"+percent+L.l());
        });

    }

    /**
     * 播放
     */
    public void start() {
        //未初始化和正在播放时return
        if (!isInitialized && mPlayer.isPlaying()) {
            return;
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
        if (!isInitialized) {
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
        isInitialized = false;

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
     *
     * @param pre_100
     */
    public void seekTo(int pre_100) {
        pause();
        mPlayer.seekTo((int) (pre_100 / 100.f * mPlayer.getDuration()));
        start();
    }

    //------------设置进度监听-----------
    public interface OnSeekListener {
        void OnSeek(int per_100);
    }

    private OnSeekListener mOnSeekListener;

    public void setOnSeekListener(OnSeekListener onSeekListener) {
        mOnSeekListener = onSeekListener;
    }


    //------------设置缓存进度监听-----------
    public interface OnBufferListener {
        void OnSeek(int per_100);
    }

    private MusicPlayer.OnBufferListener mOnBufferListener;

    public void setOnBufferListener(MusicPlayer.OnBufferListener onBufferListener) {
        mOnBufferListener = onBufferListener;
    }
}
