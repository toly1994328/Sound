package top.toly.sound;

import android.media.AudioFormat;

import org.junit.Test;

import top.toly.sound.audio.PcmToWavUtil;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final int DEFAULT_SAMPLE_RATE = 44100;//采样频率
    private static final int DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;//单声道
    private static final int DEFAULT_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;//输出格式：16位pcm
    @Test
    public void addition_isCorrect() {
        String inPath = "/sdcard/pcm录音/keke.pcm";
        String outPath = "/sdcard/pcm录音/keke.wav";

        PcmToWavUtil pcmToWavUtil = new PcmToWavUtil(DEFAULT_SAMPLE_RATE,DEFAULT_CHANNEL_CONFIG,DEFAULT_AUDIO_FORMAT);
        pcmToWavUtil.pcmToWav(inPath,outPath);
    }
}