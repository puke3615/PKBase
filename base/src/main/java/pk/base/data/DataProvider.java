package pk.base.data;


import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zijiao
 * @version 2015/12/28
 * @Mark 数据模型，存储Model，每当数据改变时，会触发分发事件。向所有的该数据的观察者通知
 */
public abstract class DataProvider<T extends ICopy<T>> implements IDataProvider<T> {

    private T mData;
    private final List<IDataListener<T>> mWatchers = new ArrayList<>();
    private static final Handler sHandler = new Handler(Looper.getMainLooper());

    @Override
    public synchronized void setData(T data) {
        this.mData = data;
        notifyChange();
    }

    @Override
    public void notifyChange() {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (mWatchers) {
                    for (IDataListener<T> listener : mWatchers) {
                        if (listener != null) {
                            try {
                                listener.onChange(mData);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        });
    }

    @Override
    public synchronized T getData() {
        return mData;
    }

    @Override
    public void registerDataListener(IDataListener<T> dataListener) {
        if (dataListener != null) {
            synchronized (mWatchers) {
                if (!mWatchers.contains(dataListener)) {
                    mWatchers.add(dataListener);
                }
            }
        }
    }

    @Override
    public void unRegisterDataListener(IDataListener<T> dataListener) {
        if (dataListener != null) {
            synchronized (mWatchers) {
                if (mWatchers.contains(dataListener)) {
                    mWatchers.remove(dataListener);
                }
            }
        }
    }

    @Override
    public void unRegisterAll() {
        synchronized (mWatchers) {
            mWatchers.clear();
        }
    }
}
