package top.toly.sound.soundpool;

import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.toly.app.test.L;
import top.toly.sound.R;

public class EffectSoundActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.button)
    Button mButton;
    private SoundPool mSp;
    private HashMap<String, Integer> mSoundMap = new HashMap<>();
    private boolean isOne;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effect_sound);
        ButterKnife.bind(this);

        initSound();
    }

    private void initSound() {
        SoundPool.Builder spb = new SoundPool.Builder();
        //设置可以同时播放的同步流的最大数量
        spb.setMaxStreams(10);
        //创建SoundPool对象
        mSp = spb.build();
        mSoundMap.put("effect1", mSp.load(this, R.raw.fall, 1));
        mSoundMap.put("effect2", mSp.load(this, R.raw.luozi, 1));
        mSp.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            L.d(sampleId+":"+status+L.l());
        });
    }


    @OnClick(R.id.button)
    public void onViewClicked() {



        //资源Id，左音量，右音量，优先级，循环次数,速率
        int id = mSoundMap.get(isOne ? "effect1" : "effect2");
        mSp.play(id, 1.0f, 1.0f, 1, 2, 1.0f);
        isOne = !isOne;
    }
}
