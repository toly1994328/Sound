package top.toly.sound.audio.effect;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/7 0007:9:50<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：处理音调的变化
 */
public class AudioEffect {
    private  int mBufferSize;
    private  byte[] mOutBuffer;
    private  float[] mTempInBuffer;
    private  float[] mTempOutBuffer;

    static {
        //加载so库
        System.loadLibrary("audio-effect");
    }

    public AudioEffect(int bufferSize) {
        mBufferSize = bufferSize;
        mOutBuffer = new byte[mBufferSize];

        mTempInBuffer = new float[mBufferSize/2];
        mTempOutBuffer = new float[mBufferSize/2];
    }

    /**
     * 数据处理
     * @param rate 变换参数
     * @param in 数据
     * @param simpleRate 采样频率
     * @return 处理后的数据流
     */
    public synchronized byte[] process(float rate,byte[] in,int simpleRate) {
        native_process(rate,in,mOutBuffer,mBufferSize,simpleRate,mTempInBuffer,mTempOutBuffer);
        return mOutBuffer;
    }

    private static native void native_process(float rate, byte[] in, byte[] out, int size, int simpleRate,float[] tempIn, float[] tempOut);

}
