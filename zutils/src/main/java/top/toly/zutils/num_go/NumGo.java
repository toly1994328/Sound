package top.toly.zutils.num_go;

/**
 * 作者：张风捷特烈
 * 时间：2018/7/8:19:37
 * 邮箱：1981462002@qq.com
 * 说明：让数字在0~1跑
 */
public class NumGo extends BaseNumGo {
    private static final String TAG = "RunNum";


    public NumGo(boolean isReverse, int repeatCount, int time) {
        super(isReverse, repeatCount, time);
        this.isReverse = isReverse;
    }

    public NumGo(int time) {
        super(time);
    }

    public NumGo() {
        super();
    }

    @Override
    protected void onUpdate(float rate) {
        if (mOnUpdate != null) {
            mOnUpdate.onUpdate(rate);
        }
    }

    @Override
    protected void onRepeat(int count) {
        if (mOnRepeat != null) {
            mOnRepeat.onRepeat(count+1);
        }
    }

    @Override
    protected int onStop() {
        if (mOnStop != null) {
            mOnStop.onStop();
        }
        return 0;
    }

    //////////////////////////////////////更新时回调
    public interface OnUpdate {
        void onUpdate(float rate);
    }

    private OnUpdate mOnUpdate;

    public NumGo setOnUpdate(OnUpdate onUpdate) {
        mOnUpdate = onUpdate;
        return this;
    }

    //////////////////////////////////////更新时回调
    public interface OnStop {
        void onStop();
    }

    private OnStop mOnStop;

    public NumGo setOnStop(OnStop onStop) {
        mOnStop = onStop;
        return this;
    }

    //////////////////////////////////////重复时回调
    public interface OnRepeat {
        void onRepeat(int count);
    }

    private OnRepeat mOnRepeat;

    public NumGo setOnRepeat(OnRepeat onRepeat) {
        mOnRepeat = onRepeat;
        return this;
    }
}
