package top.toly.sound.audio;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2019/1/3 0003:13:28<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：录制监听
 */
public interface OnRecording {
    /**
     * 录制中监听
     * @param data 数据
     * @param len 长度
     */
    void onRecording(byte[] data, int len);

    /**
     * 错误监听
     * @param e
     */
    void onError(Exception e);
}
