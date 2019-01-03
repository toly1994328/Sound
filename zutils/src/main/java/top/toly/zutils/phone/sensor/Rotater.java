package top.toly.zutils.phone.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import top.toly.zutils.core.shortUtils.ToastUtil;


/**
 * 作者：张风捷特烈
 * 时间：2018/5/9:11:07
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class Rotater implements SensorEventListener {

    ////////////////////////单例模式start//////////////////////////////
    private static Rotater sRotater;

    private Rotater() {
    }

    public static Rotater getRotater() {

        if (sRotater == null) {
            synchronized (Rotater.class) {
                if (sRotater == null) {
                    sRotater = new Rotater();

                }
            }
        }
        return sRotater;
    }
    ////////////////////////单例模式end//////////////////////////////
    private SensorManager mSensorManager;
    /**
     * 界面可见时候才监听摇晃
     */
    public Rotater resume(Context ctx) {
        mSensorManager = (SensorManager)ctx.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager == null) {
            ToastUtil.show(ctx,"您的设备暂不支持");
        }

        boolean supported = mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_FASTEST);
        if (!supported) {
            mSensorManager.unregisterListener(this);
            return null;
        }
        return this;
    }


    /**
     * 界面不可见时，需要关闭监听
     */
    public void pause() {
        if (mSensorManager != null) {

            mSensorManager.unregisterListener(this);
            mSensorManager = null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        float degree = values[0];
        if (mOnLightChangeListener != null) {
            mOnLightChangeListener.onLightChange(degree);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    ///////////监听/////////////////
    public interface OnRotateChangeListener{
        void onLightChange(float degree);
    }

    private OnRotateChangeListener mOnLightChangeListener;

    public void setOnLightChangeListener(OnRotateChangeListener onLightChangeListener) {
        mOnLightChangeListener = onLightChangeListener;
    }
}
