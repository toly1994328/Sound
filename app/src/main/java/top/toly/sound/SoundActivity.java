package top.toly.sound;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.toly.app.permission.Permission;
import top.toly.app.permission.PermissionActivity;
import top.toly.sound.media.MediaRecorderActivity;
import top.toly.sound.audio.PCMSoundActivity;

public class SoundActivity extends PermissionActivity {

    @BindView(R.id.id_btn_file)
    Button mIdBtnFile;
    @BindView(R.id.id_btn_change)
    Button mIdBtnChange;
    @BindView(R.id.id_btn_bit)
    Button mIdBtnBit;
    @BindView(R.id.id_btn_see_sound)
    Button mIdBtnSeeSound;
    @BindView(R.id.id_iv_recode)
    ImageView mIdIvRecode;
    private AnimationDrawable animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        ButterKnife.bind(this);
        applyPermissions(Permission._RECORD_AUDIO, Permission._CAMERA, Permission._WRITE_EXTERNAL_STORAGE);
        mIdIvRecode.setBackgroundResource(R.drawable.play);
        animation = (AnimationDrawable) mIdIvRecode.getBackground();

        mIdIvRecode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:

                        animation.start();
                        break;
                    case MotionEvent.ACTION_UP:
                        animation.stop();
                        animation.selectDrawable(0);
                        break;
                }
                return true;
            }

        });
    }

    @Override
    protected void permissionOk() {

    }

    @OnClick({R.id.id_btn_file, R.id.id_btn_change, R.id.id_btn_bit, R.id.id_btn_see_sound})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_btn_file:
                startActivity(new Intent(this, MediaRecorderActivity.class));
                break;
            case R.id.id_btn_change:
                break;
            case R.id.id_btn_bit:
                startActivity(new Intent(this, PCMSoundActivity.class));
                break;
            case R.id.id_btn_see_sound:
                startActivity(new Intent(this, PCMSoundActivity.class));
                break;
        }
    }

    @OnClick(R.id.id_iv_recode)
    public void onViewClicked() {

    }
}
