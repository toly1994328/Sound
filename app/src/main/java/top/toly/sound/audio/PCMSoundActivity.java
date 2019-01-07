package top.toly.sound.audio;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.toly.app.test.StrUtil;
import top.toly.app.io.FileHelper;
import top.toly.app.permission.Permission;
import top.toly.app.permission.PermissionActivity;
import top.toly.app.visibale.PcmFileWaveView;
import top.toly.sound.R;

public class PCMSoundActivity extends PermissionActivity {

    @BindView(R.id.id_iv_recode)
    ImageView mIdIvRecode;
    @BindView(R.id.id_tv_state)
    TextView mIdTvState;
    @BindView(R.id.iv_start_play)
    ImageView mIvStartPlay;
    @BindView(R.id.id_pcm)
    PcmFileWaveView mIdPcm;
    private AnimationDrawable animation;

    private boolean isOpen = false;
    private File mFile;
    private FileOutputStream mFos;
    private PCMRecordTask mPcmRecordTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recoder);
        ButterKnife.bind(this);

        applyPermissions(Permission._RECORD_AUDIO, Permission._CAMERA, Permission._WRITE_EXTERNAL_STORAGE);

        mIdIvRecode.setBackgroundResource(R.drawable.play);
        animation = (AnimationDrawable) mIdIvRecode.getBackground();

        mPcmRecordTask = new PCMRecordTask();
        mIdPcm.showPcmFileWave(new File("/sdcard/pcm录音/keke.pcm"));
        mIdPcm.setProgress(0.5f);
        mPcmRecordTask.setOnRecording(new OnRecording() {
            @Override
            public void onRecording(byte[] data, int ret) {
                //创建文件输出流
                try {
                    mFos.write(data, 0, ret);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {

            }
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
            PCMAudioPlayer.getInstance().startPlay("/sdcard/pcm录音/keke.pcm");

        });
    }

    /**
     * 停止录制
     */
    private void stopRecode() {
        mPcmRecordTask.stopRecode();
        mIdTvState.setText("录制" + mPcmRecordTask.getWorkingTime() + "秒");
    }

    /**
     * 开启录音
     */
    private void startRecord() {
        try {
            //创建录音文件---这里创建文件不是重点，我直接用了
            mFile = FileHelper.get().createFile("pcm录音/" + StrUtil.getCurrentTime_yyyyMMddHHmmss() + ".pcm");
            mFos = new FileOutputStream(mFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mPcmRecordTask.recode();
    }

    @Override
    protected void permissionOk() {

    }

    public void setInfo(String str) {
        mIdTvState.setText(str);
    }

    public void btnPlay() {
        mIvStartPlay.setBackgroundResource(R.drawable.icon_stop_3);
    }

    public void btnStop() {
        mIvStartPlay.setBackgroundResource(R.drawable.icon_start_3);
    }
}
