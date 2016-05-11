package pk.base.weakgc;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 16/5/11
 * @user zijiao
 * @Mark 监听JVM回收GC的处理器
 */
public class WeakGCHandler extends Thread {

    private static final byte[] sInstanceLock = new byte[0];
    private static WeakGCHandler sInstance;
    private final ReferenceQueue<IWeakGC> mQueue = new ReferenceQueue<>();
    private final Map<Reference, WeakGCListener> mListeners = new ConcurrentHashMap<>();

    private WeakGCHandler() {
        setPriority(Thread.MIN_PRIORITY);
        setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                if (ex instanceof RuntimeException) {
                    return;
                }
                thread.run();
            }
        });
        start();
    }

    public static WeakGCHandler instance() {
        if (sInstance == null) {
            synchronized (sInstanceLock) {
                if (sInstance == null) {
                    sInstance = new WeakGCHandler();
                }
            }
        }
        return sInstance;
    }

    public <T extends IWeakGC> WeakReference<T> getWeakReference(T reference) {
        return getWeakReference(reference, null);
    }

    /**
     * 获取弱引用
     *
     * @param reference 引用
     * @param listener  回收监听函数
     * @param <T>       真实的IWeakGC类型
     * @return 返回弱引用
     */
    public <T extends IWeakGC> WeakReference<T> getWeakReference(T reference, WeakGCListener listener) {
        WeakReference<T> weakReference = new WeakReference<>(reference, mQueue);
        if (listener != null) {
            mListeners.put(weakReference, listener);
        }
        return weakReference;
    }

    @Override
    public void run() {
        while (isAlive()) {
            try {
                Reference<? extends IWeakGC> reference = mQueue.remove();
                mListeners.get(reference).onGC(reference);
                mListeners.remove(reference);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 需要监听WeakGC的标识
     */
    public interface IWeakGC {
    }

    /**
     * 弱引用的GC监听器
     */
    public interface WeakGCListener {
        /**
         * 弱引用被GC后的回调函数
         *
         * @param reference 被GC回收的引用
         */
        void onGC(Reference<? extends IWeakGC> reference);
    }

}
