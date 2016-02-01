package pk.base.anno.handler;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import pk.base.Exceptions;
import pk.base.anno.Click;
import pk.base.anno.Impl;
import pk.base.anno.Instance;
import pk.base.anno.PK;
import pk.base.manager.InstanceManager;
import pk.base.util.ViewUtil;

/**
 * @author zijiao
 * @version 2015/12/29
 * @Mark 属性处理器
 */
public class FieldHandler implements AnnoHandler.IFieldHandler {

    @Override
    public void handleField(Object obj, View view, Field field) {
        if (obj == null || view == null || field == null) {
            Exceptions.n("obj == null || view == null || field == null");
        }
        Class<?> type = field.getType();

        if (field.isAnnotationPresent(PK.class)) {//对注解了PK的属性进行处理
            int id = getViewId(obj, field);
            if (id == 0) {
                Exceptions.n("getViewId is null");
            }

            View v = view.findViewById(id);
            if (v == null) {
                Exceptions.r("can`t find the view of " + field.getName());
            }

            addListener(obj, field, v);

            field.setAccessible(true);
            try {
                Object fieldObj = type.cast(v);
                field.set(obj, fieldObj);
            } catch (ClassCastException e) {
                Exceptions.r(field.getName() + " class cast error");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Exceptions.r(e.getMessage());
            }
        } else if (field.isAnnotationPresent(Instance.class)) {//对注解了Instance的属性进行处理
            if (type.isAnnotationPresent(Impl.class)) {
                Impl impl = type.getAnnotation(Impl.class);
                Class<?> implCls = impl.value();
                if (implCls != Void.class) {
                    Object component = impl.single() ? InstanceManager.getSingleInstance(implCls) : InstanceManager.getInstance(implCls);
                    if (component != null) {
                        field.setAccessible(true);
                        try {
                            field.set(obj, component);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }

    /**
     * 绑定事件响应
     * @param obj
     * @param field
     * @param v
     */
    protected void addListener(final Object obj, final Field field, View v) {
        //对Butto的子类和Click注解的控件绑定点击事件
        if (v instanceof Button || field.isAnnotationPresent(Click.class)) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fieldName = field.getName();
                    try {
                        Method method = obj.getClass().getMethod(fieldName, new Class[]{});
                        method.setAccessible(true);
                        method.invoke(obj);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 获取field对应的控件ID
     * @param obj
     * @param field
     * @return
     */
    protected int getViewId(Object obj, Field field) {
        int id = 0;

        //有注解时，先取注解中的值
        PK pk = field.getAnnotation(PK.class);
        id = pk.value();

        if (id == 0) {
            Context context = getContext(obj);
            if (context != null) {
                String packageName = context.getPackageName();
                id = context.getResources().getIdentifier(field.getName(), "id", packageName);
            }
        }

        return id;
    }

    /**
     * 根据主题对象获取上下文
     * @param obj 主题对象
     * @return 上下文
     */
    protected Context getContext(Object obj) {
        return ViewUtil.getContext(obj);
    }

}
