package top.toly.zutils.phone.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;

import top.toly.zutils.core.shortUtils.ToastUtil;


/**
 * 作者：张风捷特烈
 * 时间：2018/5/9:20:34
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class Proximity implements SensorEventListener {
    private SensorManager mSensorManager;
    ////////////////////////单例模式start//////////////////////////////
    private static Proximity sProximity;

    private Proximity() {
    }

    public static Proximity getProximity() {

        if (sProximity == null) {
            synchronized (Proximity.class) {
                if (sProximity == null) {
                    sProximity = new Proximity();

                }
            }
        }
        return sProximity;
    }

    ////////////////////////单例模式end//////////////////////////////

    /**
     * 界面可见时候才监听
     */
    public Proximity resume(Context ctx) {
        mSensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager == null) {
            ToastUtil.show(ctx,"您的设备暂不支持");
        }

        boolean supported = mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
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


    long lastTime;

    @Override
    public void onSensorChanged(SensorEvent event) {

        long currentTime = SystemClock.elapsedRealtime();//记录时间
        long spaceTime = currentTime - lastTime;//两次调用时间差
        lastTime = currentTime;

        float distance = event.values[0];
        if (spaceTime > 100) {
            if (distance == 0.0) {
                if (mOnProximityChangeListener != null) {
                    mOnProximityChangeListener.near();
                }

            } else {
                if (mOnProximityChangeListener != null) {
                    mOnProximityChangeListener.away();
                }
            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    ///////////监听/////////////////
    public interface OnProximityChangeListener{
        void near();
        void away();
    }

    private OnProximityChangeListener mOnProximityChangeListener;

    public void setOnProximityChangeListener(OnProximityChangeListener onProximityChangeListener) {
        mOnProximityChangeListener = onProximityChangeListener;
    }
}
