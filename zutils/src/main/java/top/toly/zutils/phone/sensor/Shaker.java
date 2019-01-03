package top.toly.zutils.phone.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import top.toly.zutils.core.shortUtils.L;
import top.toly.zutils.core.shortUtils.ToastUtil;


/**
 * 作者：张风捷特烈
 * 时间：2018/5/8:15:01
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class Shaker implements SensorEventListener {

    private long lastUpdateTime;
    // 速度阈值，当摇晃速度达到这值后产生作用
    private int speed_lever = 10;
    // 手机上一个位置时重力感应坐标
    private float lastX;
    private float lastY;
    private float lastZ;
    private SensorManager mSensorManager;
    private double mSpeed;


    ////////////////////////单例模式start//////////////////////////////
    private static Shaker sShaker;

    private Shaker() {
    }

    public static Shaker getShaker() {

        if (sShaker == null) {
            synchronized (Shaker.class) {
                if (sShaker == null) {
                    sShaker = new Shaker();

                }
            }
        }
        return sShaker;
    }
////////////////////////单例模式end//////////////////////////////


    public Shaker setSpeed_lever(int speed_lever) {
        this.speed_lever = speed_lever;
        return this;
    }

    /**
     * 界面可见时候才监听摇晃
     */
    public Shaker resume(Context ctx) {
        this.speed_lever = speed_lever;
        mSensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager == null) {
            ToastUtil.show(ctx,"您的设备暂不支持");
        }

        boolean supported = mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
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
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // 现在检测时间
            long currentUpdateTime = System.currentTimeMillis();
            // 两次检测的时间间隔
            long timeInterval = currentUpdateTime - lastUpdateTime;
            // 现在的时间变成last时间
            lastUpdateTime = currentUpdateTime;
            // 获得x,y,z坐标
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // 获得x,y,z的变化值
            float deltaX = x - lastX;
            float deltaY = y - lastY;
            float deltaZ = z - lastZ;
            // 将现在的坐标变成last坐标
            lastX = x;
            lastY = y;
            lastZ = z;

            mSpeed = Math.sqrt(
                    deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ)
                    / timeInterval * 10000;

            if (mSpeed >= speed_lever * 500) {

                L.d("mSpeed:"+mSpeed);
                if (mShakeListener != null) {//回调我们的listener
                    mShakeListener.onShake();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public double getSpeed() {
        return mSpeed;
    }


    /////////////////////设置监听器//////////////////////
    public interface OnShakeListener {
        public void onShake();
    }

    private OnShakeListener mShakeListener;

    public void setOnShakeListener(OnShakeListener listener) {
        mShakeListener = listener;
    }
}
