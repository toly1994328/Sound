package top.toly.sound.audio.effect;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.toly.app.permission.Permission;
import top.toly.app.permission.PermissionActivity;
import top.toly.sound.R;

public class EffectActivity extends PermissionActivity {

    @BindView(R.id.id_tv_state)
    TextView mIdTvState;
    @BindView(R.id.iv_start_play)
    ImageView mIvStartPlay;
    @BindView(R.id.id_sb)
    SeekBar mIdSb;
    @BindView(R.id.iv_start_play2)
    ImageView mIvStartPlay2;
    @BindView(R.id.id_tv_state2)
    TextView mIdTvState2;
    @BindView(R.id.id_sb2)
    SeekBar mIdSb2;

    private float rate = 1;
    private float rate2 = 1;
    private PCMAudioPlayerWithRate mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_player_effect);
        ButterKnife.bind(this);

        applyPermissions(Permission._RECORD_AUDIO, Permission._CAMERA, Permission._WRITE_EXTERNAL_STORAGE);
        mInstance = PCMAudioPlayerWithRate.getInstance();


        mIdSb.setProgress((int) (100 * rate));
        mIdSb2.setProgress((int) (100 * rate));


        setInfo();
        mIdSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rate = progress / 100.f;

                setInfo();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mIdSb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rate2 = progress / 100.f;
                mInstance.setRate(rate2);
                setInfo();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mIvStartPlay.setOnClickListener(e -> {
            mInstance.startPlay("/sdcard/pcm录音/20190107075814.pcm", (int) (44100 * rate));
        });


        mIvStartPlay2.setOnClickListener(e -> {
            mInstance.startPlay("/sdcard/pcm录音/20190107075814.pcm", 44100);
        });
    }

    @Override
    protected void permissionOk() {

    }

    public void setInfo() {
        mIdTvState.setText("仅变速,当前速率：" + rate);
        mIdTvState2.setText("仅变调,当前变化分率：" + rate2);
    }


}
