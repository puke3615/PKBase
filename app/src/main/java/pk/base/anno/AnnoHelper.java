package pk.base.anno;

import android.view.View;

import pk.base.Exceptions;
import pk.base.anno.handler.AnnoHandler;
import pk.base.anno.handler.ClassHandler;
import pk.base.anno.handler.FieldHandler;
import pk.base.anno.handler.MethodHandler;

/**
 * @author zijiao
 * @version 2015/12/29
 * @Mark
 */
public class AnnoHelper {

    private static final byte[] mLock = new byte[0];
    private static AnnoHelper mInstance;

    private AnnoHandler mHandler;

    private AnnoHelper() {
        mHandler = new AnnoHandler.Builder()
                .setClassHandler(new ClassHandler())
                .setFieldHandler(new FieldHandler())
                .setMethodHandler(new MethodHandler())
                .build();
    }

    public static AnnoHelper instance() {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new AnnoHelper();
                }
            }
        }
        return mInstance;
    }

    public View handle(Object obj) {
        View view = mHandler.handle(obj);
        if (view == null) {
            Exceptions.r("can`t find the view of " + obj.getClass().getName());
        }
        return view;
    }

}
