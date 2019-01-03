//package top.toly.zutils.core.ui.anima;
//
//import android.view.View;
//
//import com.nineoldandroids.view.ViewPropertyAnimator;
//
//
///**
// * 作者：张风捷特烈
// * 时间：2018/4/13:13:14
// * 邮箱：1981462002@qq.com
// * 说明：
// */
//public class VPA {
//
//
//    public static void setScaleX(View view,float to,int time) {
//        ViewPropertyAnimator.animate(view).scaleX(to).setDuration(time).start();
//    }
//
//    public static void setScaleY(View view,float to,int time) {
//        ViewPropertyAnimator.animate(view).scaleY(to).setDuration(time).start();
//    }
//
//
//
//    public static void setScale(View view,float from ,float to,int time) {
//        view.setScaleX(from);
//        view.setScaleY(from);
//        setScaleX(view, to, time);
//        setScaleY(view, to, time);
//    }
//    public static void setAlpha(View view,float from ,float to,int time) {
//        view.setAlpha(from);
//        setScaleX(view, to, time);
//        setScaleY(view, to, time);
//        ViewPropertyAnimator
//                .animate(view)
//                .alpha(to)
//                .setDuration(time)
//                .start();
//    }
//
//
//}
