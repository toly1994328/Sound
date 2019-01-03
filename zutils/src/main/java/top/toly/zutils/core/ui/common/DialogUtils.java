package top.toly.zutils.core.ui.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * 作者：张风捷特烈
 * 时间：2018/4/22:14:20
 * 邮箱：1981462002@qq.com
 * 说明：对话框工具类
 */
public class DialogUtils {
    /**
     * 弹出对话框
     */
    private void dialog(Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("五子棋提示");
        builder.setMessage("白棋胜利！");
        builder.setPositiveButton("再来一局", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }

        });
        builder.setNegativeButton("退出", null);
        builder.show();
    }

}
