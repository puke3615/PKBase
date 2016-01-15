package pk.base.anno.handler;

import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import pk.base.Exceptions;

/**
 * @author zijiao
 * @version 2015/12/29
 * @Mark
 */
public class AnnoHandler {

    private IClassHandler mClassHandler;
    private IMethodHandler mMethodHandler;
    private IFieldHandler mFieldHandler;
    private List<IHandleInterceptor> mInterceptors;

    private AnnoHandler(IClassHandler mClassHandler, IMethodHandler mMethodHandler, IFieldHandler mFieldHandler, List<IHandleInterceptor> mInterceptors) {
        this.mClassHandler = mClassHandler;
        this.mMethodHandler = mMethodHandler;
        this.mFieldHandler = mFieldHandler;
        this.mInterceptors = mInterceptors;
    }

    public View handle(Object obj) {
        if (obj == null) {
            Exceptions.n("obj == null");
        }

        View view = null;
        Class<?> cls = obj.getClass();
        if (mClassHandler != null) {
            if (!doInterceptor(obj, view, cls)) {
                view = mClassHandler.handleClass(obj, view, cls);
            }
        }

        if (view != null && mFieldHandler != null) {
            Field[] fields = getFields(cls);
            for (Field field : fields) {
                if (!doInterceptor(obj, view, field)) {
                    mFieldHandler.handleField(obj, view, field);
                }
            }
        }

        if (view != null && mMethodHandler != null) {
            Method[] methods = getMethods(cls);
            for (Method method : methods) {
                if (!doInterceptor(obj, view, method)) {
                    mMethodHandler.handleMethod(obj, view, method);
                }
            }
        }

        return view;
    }

    /**
     * 对注解处理器执行拦截操作
     * @param obj 处理主题对象
     * @param view view
     * @param targetType 目标类型， class, method, field
     * @return true: 拦截该次操作，不做处理；false：交由处理器处理
     */
    private boolean doInterceptor(Object obj, View view, Object targetType) {
        if (mInterceptors != null && mInterceptors.size() > 0) {
            for (IHandleInterceptor interceptor : mInterceptors) {
                if (interceptor != null && interceptor.doInterfactor(obj, view, targetType)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Method[] getMethods(Class<?> cls) {
        return cls.getDeclaredMethods();
    }

    protected Field[] getFields(Class<?> cls) {
        return cls.getDeclaredFields();
    }

    public static class Builder {
        private IClassHandler mClassHandler;
        private IMethodHandler mMethodHandler;
        private IFieldHandler mFieldHandler;
        private List<IHandleInterceptor> mInterceptors;

        public Builder setClassHandler(IClassHandler classHandler) {
            this.mClassHandler = classHandler;
            return this;
        }
        public Builder setMethodHandler(IMethodHandler methodHandler) {
            this.mMethodHandler = methodHandler;
            return this;
        }
        public Builder setFieldHandler(IFieldHandler fieldHandler) {
            this.mFieldHandler = fieldHandler;
            return this;
        }

        public Builder addInterceptor(IHandleInterceptor interceptor) {
            if (mInterceptors == null) {
                mInterceptors = new ArrayList<>();
            }
            if (mInterceptors.contains(interceptor)) {
                Exceptions.i("the interceptor already exists .");
            }
            mInterceptors.add(interceptor);
            return this;
        }

        public AnnoHandler build() {
            return new AnnoHandler(mClassHandler, mMethodHandler, mFieldHandler, mInterceptors);
        }

    }

    /**
     * 注解的类处理器
     */
    public static interface IClassHandler {
        View handleClass(Object obj, View view, Class<?> cls);
    }

    /**
     * 注解的方法处理器
     */
    public static interface IMethodHandler {
        void handleMethod(Object obj, View view, Method method);
    }

    /**
     * 注解的属性处理器
     */
    public static interface IFieldHandler {

        void handleField(Object obj, View view, Field field);
    }

    public static interface IHandleInterceptor {
        boolean doInterfactor(Object obj, View view, Object handleTarget);
    }

}
