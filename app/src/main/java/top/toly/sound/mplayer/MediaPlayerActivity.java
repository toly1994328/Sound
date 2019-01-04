package top.toly.sound.mplayer;

import android.os.Bundle;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.toly.app.permission.Permission;
import top.toly.app.permission.PermissionActivity;
import top.toly.sound.R;
import top.toly.sound.widget.AlphaImageView;
import top.toly.sound.widget.ProgressView;

public class MediaPlayerActivity extends PermissionActivity {


    @BindView(R.id.id_pv_pre)
    ProgressView mIdPvPre;
    @BindView(R.id.id_iv_ctrl)
    ImageView mIdIvCtrl;
    @BindView(R.id.id_iv_next)
    AlphaImageView mIdIvNext;
    @BindView(R.id.id_iv_pre_list)
    AlphaImageView mIdIvPreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        ButterKnife.bind(this);
        applyPermissions(Permission._RECORD_AUDIO, Permission._CAMERA, Permission._WRITE_EXTERNAL_STORAGE);

        NetMusicPlayer musicPlayer = new NetMusicPlayer(this);//实例化

        musicPlayer.setOnSeekListener(per_100 -> {
            mIdPvPre.setProgress(per_100);

        });

        musicPlayer.setOnBufferListener(per_100 -> {
            mIdPvPre.setProgress2(per_100);

        });

        mIdPvPre.setOnDragListener(pre_100 -> {
            musicPlayer.seekTo(pre_100);
        });

        mIdIvCtrl.setOnClickListener(v -> {
            if (musicPlayer.isPlaying()) {
                musicPlayer.pause();
                mIdIvCtrl.setImageResource(R.drawable.icon_stop_2);//设置图标暂停
            } else {
                musicPlayer.start();
                mIdIvCtrl.setImageResource(R.drawable.icon_start_2);//设置图标播放
            }
        });


    }


    @Override
    protected void permissionOk() {

    }


}
