package top.toly.zutils.core.base;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import top.toly.zutils.core.shortUtils.L;
import top.toly.zutils.core.shortUtils.ToastUtil;


/**
 * Created by Administrator on 2017/11/26.
 */
public class BaseActivity extends AppCompatActivity {

    public static ArrayList<Activity> activities = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getNameOfStartActivity();
        activities.add(this);//Activity创建时加入集合
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 1.获取启动的子类的Activity类名
     */
    private void getNameOfStartActivity() {
//        L.e("ActivityName","启动的Activity名称：  " + getClass().getSimpleName());
    }

    /**
     * 是否允许权限
     * @param permission 权限集合
     * @return
     */
    public boolean hasPermission(String... permission) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            //申请权限处理
            ActivityCompat.requestPermissions(this, permission, 1);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 权限申请结果
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doPermission();
                } else {
                    ToastUtil.show(this,"您未允许相应权限\n该功能不能使用");
                }
                break;

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 权限处理
     */
    public void doPermission() {
        L.e("success");

    }

    /**
     * a3：结束所有Activity
     */
    public static void finishAll() {
        for (Activity act:activities) {
            if (!act.isFinishing()) {
                act.finish();
            }
        }
    }
}


