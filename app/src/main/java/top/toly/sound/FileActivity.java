//package top.toly.sound;
//
//import android.media.MediaPlayer;
//import android.media.MediaRecorder;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import top.toly.sound.aac.MediaRecorderTask;
//import top.toly.app.Poster;
//import top.toly.app.StrUtil;
//import top.toly.app.io.FileHelper;
//import top.toly.app.test.ToastUtil;
//
//public class FileActivity extends AppCompatActivity {
//
//    @BindView(R.id.id_btn_say)
//    Button mIdBtnSay;
//    @BindView(R.id.id_tv_show)
//    TextView mIdTvShow;
//    @BindView(R.id.id_btn_play)
//    Button mIdBtnPlay;
//
//    private ExecutorService mExecutorService;
//    private MediaRecorder mRecorder;
//    private File mFile;
//    private long mStartTie, mStopTime;
//    private volatile boolean isPlaying;//正在播放
//    private MediaPlayer mMediaPlayer;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_file);
//        ButterKnife.bind(this);
//
//
//        //录音jni不具备线程安全性，所以需要用单线程
//        mExecutorService = Executors.newSingleThreadExecutor();
//
//
//        mIdBtnSay.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        startRecord();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        break;
//                    case MotionEvent.ACTION_UP:
//                    case MotionEvent.ACTION_CANCEL:
//                        stopRecord();
//                        break;
//                    default:
//                        break;
//                }
//
//                return true;//已消费
//            }
//        });
//    }
//
//
//    /**
//     * 开始录音
//     */
//    private void startRecord() {
//        //改变ui
//        mIdBtnSay.setText(R.string.speaking);
//        mIdBtnSay.setBackgroundColor(0x88888888);
//        //后台执行录音
//        mExecutorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                //释放之前录音的recorder
//                releaseRecorder();
//                //执行停止录音,失败提醒
//                if (!doStart()) {
//                    recordFail();
//                }
//            }
//        });
//
//    }
//
//    /**
//     * 停止录音
//     */
//    private void stopRecord() {
//        mIdBtnSay.setText(R.string.no_speaking);
//        mIdBtnSay.setBackgroundColor(0xeeeeeeee);
//        //后台执行停止
//        mExecutorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                //录音失败提示
//                if (!doStop()) {
//                    recordFail();
//                }
//                //释放当前录音的recorder
//                releaseRecorder();
//            }
//        });
//    }
//
//
//    /**
//     * 启动录音逻辑
//     *
//     * @return
//     */
//    private boolean doStart() {
//
//        try {
//            mFile = FileHelper.get()
//                    .createFile("录音/" + StrUtil.getCurrentTime_yyyyMMddHHmmss() + ".m4a");
//            mRecorder = MediaRecorderTask.get().saveTo(mFile);
//            //开始录音
//            mRecorder.prepare();
//            mRecorder.start();
//            //记录开始录音时间，记录时长
//            mStartTie = System.currentTimeMillis();
//
//        } catch (IOException | RuntimeException e) {
//            e.printStackTrace();
//            //捕获异常，避免闪退，返回false 提醒失败
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 录音停止
//     *
//     * @return
//     */
//    private boolean doStop() {
//
//        try {
//            //停止，统计时长。只接受3秒及以上，在UI显示
//            mStopTime = System.currentTimeMillis();
//            final int t = (int) ((mStopTime - mStartTie) / 1000);
//            if (t > 3) {
//                Poster.getPoster().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mIdTvShow.setText(mIdTvShow.getText() + "\n录音成功,共" + t + "秒！");
//
//                    }
//                });
//            } else {
//                recordFail();
//            }
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            mRecorder.stop();
//        }
//
//        return true;
//    }
//
//    /**
//     * 录音失败
//     */
//    private void recordFail() {
//        mFile.delete();
//        mFile = null;
//        //提示失败,需在主线程
//        Poster.getPoster().post(new Runnable() {
//            @Override
//            public void run() {
//                ToastUtil.show(FileActivity.this, "录音失败");
//            }
//        });
//    }
//
//    /**
//     * 释放MediaRecorder
//     */
//    private void releaseRecorder() {
//        if (mRecorder != null) {
//            mRecorder.release();
//            mRecorder = null;
//        }
//
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //Activity销毁时停止后台任务，避免内存泄漏
//        mExecutorService.shutdownNow();
//        releaseRecorder();
//        stopPlay();
//    }
//
//
//    /**
//     * 播放
//     */
//    @OnClick(R.id.id_btn_play)
//    public void onViewClicked() {
//        if (mFile != null && !isPlaying) {
//            //设置当前播放状态
//            isPlaying = true;
//            mExecutorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    doPlay(mFile);
//                }
//            });
//        }
//    }
//
//    /**
//     * 播放主逻辑
//     *
//     * @param file
//     */
//    private void doPlay(File file) {
//        try {
//            //配置MediaPlayer
//            mMediaPlayer = new MediaPlayer();
//            mMediaPlayer.setDataSource(file.getAbsolutePath());
//            //设置监听回调
//            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    //播放结束
//                    stopPlay();
//                }
//            });
//            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//                @Override
//                public boolean onError(MediaPlayer mp, int what, int extra) {
//                    //提示用户
//                    playFail();
//                    //释放播放器
//                    stopPlay();
//                    return false;
//                }
//            });
//            //配置音量，是否循环
//            mMediaPlayer.setVolume(1, 1);
//            mMediaPlayer.setLooping(false);
//            //准备
//            mMediaPlayer.prepare();
//            //开始
//            mMediaPlayer.start();
//            //异常处理
//        } catch (RuntimeException | IOException e) {
//            e.printStackTrace();
//            playFail();
//            stopPlay();
//        }
//    }
//
//
//    /**
//     * 停止播放主逻辑
//     */
//    private void stopPlay() {
//        //重置播放状态
//        isPlaying = false;
//
//        //释放播放器
//        if (mMediaPlayer != null) {
//            //重置监听器,避免内存泄漏
//            mMediaPlayer.setOnErrorListener(null);
//            mMediaPlayer.setOnCompletionListener(null);
//            mMediaPlayer.start();
//            mMediaPlayer.reset();
//            mMediaPlayer.release();
//            mMediaPlayer = null;
//        }
//
//    }
//
//    /**
//     * 提醒播放失败
//     */
//    private void playFail() {
//        //主线程toast
//        Poster.getPoster().post(new Runnable() {
//            @Override
//            public void run() {
//                ToastUtil.show(FileActivity.this, "播放失败");
//            }
//        });
//    }
//}
