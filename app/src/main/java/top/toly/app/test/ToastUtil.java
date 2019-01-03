package top.toly.app.test;

import android.content.Context;
import android.widget.Toast;


public class ToastUtil {
	private static Toast toast;
	/**
	 * 能够连续弹的吐司，不会等上个吐司消失
	 * @param text
	 */
	public static void showAtOnce(Context ctx, String text){
		if(toast==null){
			toast = Toast.makeText(ctx, text, Toast.LENGTH_SHORT);
		}
		toast.setText(text);//将text文本设置给吐司
		toast.show();
	}

	public static Toast show(Context ctx,String msg) {

		Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
		toast.show();
		return toast;
	}
}
