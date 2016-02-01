package pk.base.manager;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import pk.base.anno.Click;
import pk.base.anno.Impl;
import pk.base.anno.PK;


/**
 * Created by dell on 2015/11/13.
 */
public class InstanceManager {

    private static final Map<Class<?>, Object> mInstances = new HashMap<>();
    private static final byte[] lock = new byte[0];

    public static <T> T getSingleInstance(Class<T> cls, Object... params) {
        if (cls == null) {
            return null;
        }
        if (!mInstances.containsKey(cls)) {
            synchronized (lock) {
                if (!mInstances.containsKey(cls)) {
                    T instance = getInstance(cls, params);
                    if (instance != null) {
                        mInstances.put(cls, instance);
                    }
                }
            }
        }
        return (T) mInstances.get(cls);
    }

    public static <T> T getInstance(Class<T> cls, Object... params) {
        T instance = null;
        try {
            int size = params == null ? 0 : params.length;
            if (size == 0) {
                instance = cls.newInstance();
            } else {
                Class[] paramsType = new Class[size];
                for (int i = 0; i < size; i++) {
                    paramsType[i] = params[i].getClass();
                }
                instance = (T) cls.getConstructor(paramsType).newInstance(params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

}
