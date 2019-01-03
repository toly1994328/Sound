package top.toly.zutils.core.base;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
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
 * 2：调用applyStoragePermission()
 * 3：setOnPermissionListener()回调
 */
public class PermissionActivity extends AppCompatActivity {
    private static final int WRITE_EXTERNAL_STORAGE = 0x01;
    private static final int RECORD_AUDIO = 0x02;
    private static final int CAMERA = 0x03;
    private PermissionBean mStorageModel, mRecorderModel, mCameraModel;
    private PermissionBean[] mModels;
    private int count = 0;
    boolean noPerm = true;
    protected boolean isfirst = Build.VERSION.SDK_INT >= 23;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    /**
     * 申请权限
     */
    public void applyPermission(PermissionBean model) {

        noPerm = PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, model.permission);
        if (noPerm) {
            ActivityCompat.requestPermissions(this, new String[]{model.permission}, model.requestCode);
            count++;
        }

    }

    /**
     * 申请权限
     */
    public void applyPermissions(PermissionBean... models) {

        mModels = models;
        applyPermission(models[count]);
        if (mOnPermissionListener != null) {
            mOnPermissionListener.ok();
        }
    }

    /**
     * 申请SD卡读写权限
     */
    public PermissionBean _WRITE_EXTERNAL_STORAGE() {
        mStorageModel = new PermissionBean(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                "我们需要您允许我们读写你的存储卡，以方便我们临时保存一些数据!", WRITE_EXTERNAL_STORAGE);
        return mStorageModel;


    }

    /**
     * 申请录音卡读写权限
     */
    public PermissionBean _RECORD_AUDIO() {
        mRecorderModel = new PermissionBean(Manifest.permission.RECORD_AUDIO,
                "我们需要您允许我们访问录音设备，以方便录音功能使用!", RECORD_AUDIO);
        return mRecorderModel;
    }

    /**
     * 申请录音卡读写权限
     */
    public PermissionBean _CAMERA() {
        mCameraModel = new PermissionBean(Manifest.permission.CAMERA,
                "我们需要您允许我们访问相机，以方便拍照功能使用!", CAMERA);
        return mCameraModel;
    }

    /**
     * 当用户处理完授权操作时，系统会自动回调该方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (count != mModels.length) {
                applyPermission(mModels[count]);
            }
        } else {
            count--;
        }




        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE:
                showInfoDialog(permissions, grantResults[0], mStorageModel);
                break;
            case RECORD_AUDIO:
                showInfoDialog(permissions, grantResults[0], mRecorderModel);
                break;
            case CAMERA:
                showInfoDialog(permissions, grantResults[0], mCameraModel);
                break;
        }

    }

    /**
     * 如果拒绝，弹出对话框，说明为什么需要这个权限
     *
     * @param permissions
     * @param grantResult
     * @param model
     */
    private void showInfoDialog(String[] permissions, int grantResult, final PermissionBean model) {
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
        return;
    }

    /**
     * Permission的bean对象
     */
    public static class PermissionBean {

        public String permission;//请求的权限
        public String explain;//解析为什么请求这个权限
        public int requestCode;//请求代码

        public PermissionBean(String permission, String explain, int requestCode) {
            this.permission = permission;
            this.explain = explain;
            this.requestCode = requestCode;
        }

        @Override
        public String toString() {
            return "PermissionBean{" +
                    "permission='" + permission + '\'' +
                    ", explain='" + explain + '\'' +
                    ", requestCode=" + requestCode +
                    '}';
        }
    }


    /**
     * 权限申请事件监听
     */
    public interface OnPermissionListener {
        void ok();//成功时回调方法
    }

    private OnPermissionListener mOnPermissionListener;

    public void setOnPermissionListener(OnPermissionListener onPermissionListener) {
        mOnPermissionListener = onPermissionListener;
    }
}


