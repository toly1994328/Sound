package top.toly.zutils.phone.media;

import android.content.Context;
import android.media.MediaPlayer;

import top.toly.zutils.core.io.SpUtils;
import top.toly.zutils.core.shortUtils.L;


/**
 * 作者：张风捷特烈
 * 时间：2018/4/15:23:16
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class MediaUtils {


    /**
     * 通过资源id播放音乐
     *
     * @param MusicId 资源id
     */
    public static MediaPlayer getRawById(Context ctx,int MusicId) {
        MediaPlayer mMusic = MediaPlayer.create(ctx, MusicId);
        mMusic.start();
        L.e("mMusic:" + (mMusic == null));
        return mMusic;
    }

    /**
     * 通过资源id播放音乐
     *
     * @param MusicId
     */
    /**
     * @param MusicId     资源id
     * @param spConfigStr sp配置的字符串
     */
    public static void playSoundWithSP(Context ctx,int MusicId, String spConfigStr) {
        if (SpUtils.getBoolean(ctx,spConfigStr)) {
            MediaUtils.getRawById(ctx,MusicId);
        }
    }




}
