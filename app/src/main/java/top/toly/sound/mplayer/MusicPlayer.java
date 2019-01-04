package top.toly.sound.mplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import top.toly.app.test.L;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/4 0004:10:12<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class MusicPlayer {
    private Timer mTimer;
    private MediaPlayer mPlayer;
    private Context mContext;

    private boolean isInitialized = false;//是否已初始化
    private Thread initThread;
    private final Handler mHandler;
    //    private final SeekThread mSeekThread;


    public MusicPlayer(Context context) {
        mContext = context;

        initThread = new Thread(this::init);
//        mSeekThread = new SeekThread();

        mTimer = new Timer();//创建Timer
        mHandler = new Handler();//创建Handler

        initThread.start();
    }

    private void init() {
//        Uri uri = Uri.parse("http://www.toly1994.com:8089/file/洛天依.mp3");

        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath(), "toly/勇气-梁静茹-1772728608-1.mp3"));
        mPlayer = MediaPlayer.create(mContext, uri);
        isInitialized = true;

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
            start();
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
     * @param pre_100
     */
    public void seekTo(int pre_100) {
        pause();
        mPlayer.seekTo((int) (pre_100/100.f*mPlayer.getDuration()));
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

    //------------设置进度监听-----------
    public interface OnBufferListener {
        void OnSeek(int per_100);
    }

    private OnBufferListener mOnBufferListener;

    public void setOnBufferListener(OnBufferListener onBufferListener) {
        mOnBufferListener = onBufferListener;
    }
}
