package top.toly.sound.media;

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
    private NetMusicPlayer mMusicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        ButterKnife.bind(this);
        applyPermissions(Permission._RECORD_AUDIO, Permission._CAMERA, Permission._WRITE_EXTERNAL_STORAGE);

        //实例化
        mMusicPlayer = new NetMusicPlayer(this);

        mMusicPlayer.setOnSeekListener(per_100 -> {
            mIdPvPre.setProgress(per_100);

        });

        mMusicPlayer.setOnBufferListener(per_100 -> {
            mIdPvPre.setProgress2(per_100);

        });

        mIdPvPre.setOnDragListener(pre_100 -> {
            mMusicPlayer.seekTo(pre_100);
        });

        mIdIvCtrl.setOnClickListener(v -> {
            if (mMusicPlayer.isPlaying()) {
                mMusicPlayer.pause();
                mIdIvCtrl.setImageResource(R.drawable.icon_stop_2);//设置图标暂停
            } else {
                mMusicPlayer.start();
                mIdIvCtrl.setImageResource(R.drawable.icon_start_2);//设置图标播放
            }
        });


    }


    @Override
    protected void permissionOk() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mMusicPlayer.onDestroyed();
    }
}
