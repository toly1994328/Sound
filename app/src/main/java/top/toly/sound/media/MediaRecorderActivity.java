package top.toly.sound.media;

import android.graphics.drawable.AnimationDrawable;
import android.media.AudioFormat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.toly.app.io.FileHelper;
import top.toly.app.permission.Permission;
import top.toly.app.permission.PermissionActivity;
import top.toly.app.test.StrUtil;
import top.toly.sound.R;
import top.toly.sound.audio.PcmToWavUtil;
import top.toly.sound.widget.RhythmView;

public class MediaRecorderActivity extends PermissionActivity {

    @BindView(R.id.id_iv_recode)
    ImageView mIdIvRecode;
    @BindView(R.id.id_tv_state)
    TextView mIdTvState;
    @BindView(R.id.iv_start_play)
    ImageView mIvStartPlay;
    @BindView(R.id.id_rth)
    RhythmView mIdRth;
    private AnimationDrawable animation;

    private boolean isOpen = false;
    private File mFile;
    private MediaRecorderTask mMediaRecorderTask;
    private MusicPlayer mMusicPlayer;

    private static final int DEFAULT_SAMPLE_RATE = 44100;//采样频率
    private static final int DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;//单声道
    private static final int DEFAULT_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;//输出格式：16位pcm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recoder_rth);
        ButterKnife.bind(this);
        applyPermissions(Permission._RECORD_AUDIO, Permission._CAMERA, Permission._WRITE_EXTERNAL_STORAGE);

        mIdIvRecode.setBackgroundResource(R.drawable.play);
        animation = (AnimationDrawable) mIdIvRecode.getBackground();


        mMusicPlayer = new MusicPlayer();


        String inPath = "/sdcard/pcm录音/keke.pcm";
        String outPath = "/sdcard/pcm录音/keke.wav";

        PcmToWavUtil pcmToWavUtil = new PcmToWavUtil(DEFAULT_SAMPLE_RATE,DEFAULT_CHANNEL_CONFIG,DEFAULT_AUDIO_FORMAT);
        pcmToWavUtil.pcmToWav(inPath,outPath);

        mMediaRecorderTask = new MediaRecorderTask();

        mMediaRecorderTask.setOnVolumeChangeListener(per -> {
            mIdRth.setPerHeight(per);
        });

        mIdIvRecode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        animation.start();
                        startRecord();
                        break;
                    case MotionEvent.ACTION_UP:
                        animation.stop();
                        animation.selectDrawable(0);
                        stopRecode();

                        break;
                }
                return true;
            }
        });


        mIvStartPlay.setOnClickListener(e -> {
            if (!isOpen) {
                btnPlay();
            } else {
                btnStop();
            }
            isOpen = !isOpen;
        });
    }

    /**
     * 停止录制
     */
    private void stopRecode() {
        mMediaRecorderTask.stop();
        mIdTvState.setText("录制" + mMediaRecorderTask.getAllTime() + "秒");
    }

    /**
     * 开启录音
     */
    private void startRecord() {
        //创建录音文件---这里创建文件不是重点，我直接用了
        mFile = FileHelper.get().createFile("MediaRecorder录音/" + StrUtil.getCurrentTime_yyyyMMddHHmmss() + ".mp3");
        mMediaRecorderTask.start(mFile);
    }

    @Override
    protected void permissionOk() {

    }

    public void setInfo(String str) {
        mIdTvState.setText(str);
    }

    public void btnPlay() {
        mMusicPlayer.start("/sdcard/MediaRecorder录音/20190104195319.m4a");
        mIvStartPlay.setBackgroundResource(R.drawable.icon_stop_3);
    }

    public void btnStop() {
        mIvStartPlay.setBackgroundResource(R.drawable.icon_start_3);
        mMusicPlayer.pause();
    }
}
