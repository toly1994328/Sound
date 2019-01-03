package top.toly.zutils.phone.media;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;


/**
 * 作者：张风捷特烈
 * 时间：2018/4/22:16:30
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class PlaySoundPool {

    private Context context;
    int streamVolume;//音效的音量
    private SoundPool soundPool; //定义SoundPool 对象
    private HashMap<Integer, Integer> soundPoolMap;//定义HASH表

    public PlaySoundPool(Context context) {
        this.context = context;
        initSounds();

    }


    public void initSounds() {
        //初始化soundPool 对象,第一个参数是允许有多少个声音流同时播放,第2个参数是声音类型,第三个参数是声音的品质
        soundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();//初始化HASH表

        //获得声音设备和设备音量
        AudioManager mgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public void loadSfx(int raw, int ID) {
        //把资源中的音效加载到指定的ID(播放的时候就对应到这个ID播放就行了)
        soundPoolMap.put(ID, soundPool.load(context, raw, ID));
    }

    public void play(int sound, int uLoop) {
        soundPool.play(soundPoolMap.get(sound), streamVolume, streamVolume, 1, uLoop, 1f);
    }


    public static void play(Context ctx,int rawId) {
        PlaySoundPool playSoundPool=new PlaySoundPool(ctx);
        playSoundPool.loadSfx(rawId, 1);
        playSoundPool.play(1, 0);
    }
}
