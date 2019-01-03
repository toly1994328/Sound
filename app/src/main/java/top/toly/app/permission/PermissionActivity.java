package top.toly.app.permission;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;


/**
 * 作者：张风捷特烈
 * 时间：2018/5/16 14:55
 * 邮箱：1981462002@qq.com
 * 说明：申请权限的Activity父类
 * <p>
 * 用法：1：继承 PermissionActivity
 * 2：调用applyPermissions
 * 3：permissionOk()回调
 */
public abstract class PermissionActivity extends AppCompatActivity {


    private Permission[] mModels;
    /**
     * 当前的权限个数记录码
     */
    private int count = 0;
    /**
     * 权限未允许
     */
    protected boolean noPerm = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 申请一个权限
     */
    private void applyPermission(Permission model) {

        noPerm = PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, model.permission);
        if (noPerm) {
            ActivityCompat.requestPermissions(this, new String[]{model.permission}, model.requestCode);
            count++;
        }
    }

    /**
     * 申请所有权限
     */
    public void applyPermissions(Permission... models) {
        mModels = models;
        applyPermission(models[count]);
        if (!noPerm) {
            permissionOk();
        }
    }


    /**
     * 当用户处理完授权操作时，系统会自动回调该方法
     *
     * @param requestCode  权限标识码
     * @param permissions  权限集
     * @param grantResults 允许的结果集
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (count != mModels.length) {
                applyPermission(mModels[count]);
            } else {
                permissionOk();
                count = 0;//count归零
            }
        } else {
            count--;
        }

        switch (requestCode) {
            case Permission.WRITE_EXTERNAL_STORAGE:
                showInfoDialog(permissions, grantResults[0], Permission._WRITE_EXTERNAL_STORAGE);
                break;
            case Permission.RECORD_AUDIO:
                showInfoDialog(permissions, grantResults[0], Permission._RECORD_AUDIO);
                break;
            case Permission.CAMERA:
                showInfoDialog(permissions, grantResults[0], Permission._CAMERA);
                break;
            case Permission.READ_CONTACTS:
                showInfoDialog(permissions, grantResults[0], Permission._READ_CONTACTS);
                break;
            case Permission.CALL_PHONE:
                showInfoDialog(permissions, grantResults[0], Permission._CALL_PHONE);
                break;
            case Permission.READ_SMS:
                showInfoDialog(permissions, grantResults[0], Permission._READ_SMS);
            case Permission.LOCATION:
                showInfoDialog(permissions, grantResults[0], Permission._ACCESS_COARSE_LOCATION);
                break;
        }

    }

    /**
     * 如果拒绝，弹出对话框，说明为什么需要这个权限
     *
     * @param permissions 权限集
     * @param grantResult 允许的结果
     * @param model       权限实体
     */
    private void showInfoDialog(String[] permissions, int grantResult, final Permission model) {
        if (PackageManager.PERMISSION_GRANTED != grantResult) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("权限申请")
                        .setMessage(model.explain).setPositiveButton(
                                "我知道了",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        applyPermission(model);
                                    }
                                }
                        );
                builder.setCancelable(false);
                builder.show();
            }
        }
    }


    /**
     * 权限成功回调
     */
    protected abstract void permissionOk();

}


