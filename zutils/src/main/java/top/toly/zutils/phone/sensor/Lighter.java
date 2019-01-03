package top.toly.zutils.phone.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import top.toly.zutils.core.shortUtils.ToastUtil;


/**
 * 作者：张风捷特烈
 * 时间：2018/5/8:21:34
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class Lighter implements SensorEventListener {
    private SensorManager mSensorManager;
    ////////////////////////单例模式start//////////////////////////////
    private static Lighter sLighter;

    private Lighter() {
    }

    public static Lighter getLighter() {

        if (sLighter == null) {
            synchronized (Lighter.class) {
                if (sLighter == null) {
                    sLighter = new Lighter();

                }
            }
        }
        return sLighter;
    }
    ////////////////////////单例模式end//////////////////////////////


    /**
     * 界面可见时候才监听
     */
    public Lighter resume(Context ctx) {
        mSensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager == null) {
            ToastUtil.show(ctx,"您的设备暂不支持");
        }

        boolean supported = mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);
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
        float value = values[0];
        if (mOnLightChangeListener != null) {
            mOnLightChangeListener.onLightChange(value);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    ///////////监听/////////////////
   public interface OnLightChangeListener{
        void onLightChange(float value);
    }

    private OnLightChangeListener mOnLightChangeListener;

    public void setOnLightChangeListener(OnLightChangeListener onLightChangeListener) {
        mOnLightChangeListener = onLightChangeListener;
    }
}
