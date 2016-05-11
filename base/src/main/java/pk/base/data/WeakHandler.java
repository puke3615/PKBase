package pk.base.data;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 请不要将局部对象传入WeakHandler中
 * Created by wolun on 15/4/27.
 */
public class WeakHandler extends Handler {
    private WeakReference<Callback> mCallbackWeak;

    public WeakHandler(Callback callback) {
        super();
        mCallbackWeak = new WeakReference<>(callback);
    }

    public WeakHandler(Looper looper, Callback callback) {
        super(looper);
        mCallbackWeak = new WeakReference<>(callback);
    }

    @Override
    public final void handleMessage(Message msg) {
        super.handleMessage(msg);
        Callback callback = mCallbackWeak.get();
        if (callback != null)
            callback.handleMessage(msg);
    }
}
