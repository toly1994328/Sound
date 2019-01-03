package top.toly.zutils.phone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.WindowManager;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * 作者：张风捷特烈
 * 时间：2018/5/7:20:59
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class DeviceUtils {


    /**
     * 判断是否开启了自动亮度调节
     */
    public static boolean isAutoBrightness(Context ctx) {

        boolean automicBrightness = false;
        try {
            automicBrightness = (Settings.System.getInt(
                    ctx.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE) ==
                    Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        return automicBrightness;
    }


    /**
     * 停止自动亮度调节
     */
    public static void stopAutoBrightness(Context ctx) {

        Settings.System.putInt(
                ctx.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

    }

    /**
     * 开启亮度自动调节
     */
    public static void startAutoBrightness(Context ctx) {

        Settings.System.putInt(
                ctx.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }


    /**
     * 获取屏幕的亮度
     */
    public static int getScreenBrightness(Context ctx) {

        int nowBrightnessValues = 0;
        ContentResolver resolver = ctx.getContentResolver();
        try {
            nowBrightnessValues = Settings.System.getInt(
                    resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return nowBrightnessValues;
    }

    /**
     * 修改背光亮度
     */
    public static void setBrightness(Activity activity, int brightness) {

        WindowManager.LayoutParams wl = activity.getWindow().getAttributes();
        wl.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        activity.getWindow().setAttributes(wl);

    }

    /**
     * 保存设置的亮度的值
     */
    public static void saveBrightness(Activity activity, int brightness) {
        ContentResolver resolver = activity.getContentResolver();
        Uri uri = Settings.System.getUriFor("screen_brightness");
        Settings.System.putInt(resolver, "screen_brightness", brightness);
        resolver.notifyChange(uri, null);

    }


    @SuppressLint("MissingPermission")
    public static Vibrator startVibrator(Activity activity) {// 定义震动器
        Vibrator vibrator = (Vibrator) activity.getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{500, 200, 100, 500,100, 200}, -1);
        return vibrator;
    }

    /**
     * 开关屏幕
     *
     */
    public static void wakeUp(Context ctx) {
        PowerManager.WakeLock wl = null;
        KeyguardManager.KeyguardLock kl = null;
        //获取电源管理器对象
        PowerManager pm = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
        wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
        KeyguardManager km = (KeyguardManager) ctx.getSystemService(Context.KEYGUARD_SERVICE);
        //得到键盘锁管理器对象
        //点亮屏幕
        wl.acquire();

    }
}
