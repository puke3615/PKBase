package pk.base.anno.handler;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pk.base.Exceptions;
import pk.base.anno.PK;
import pk.base.util.ViewUtil;

/**
 * @author zijiao
 * @version 2015/12/29
 * @Mark 注解的类处理器
 */
public class ClassHandler implements AnnoHandler.IClassHandler {

    @Override
    public View handleClass(Object obj, View view, Class<?> cls) {
        if (obj == null || cls == null) {
            Exceptions.n("obj == null || cls == null");
        }
        if (obj.getClass() != cls) {
            Exceptions.i("obj is not instance of cls");
        }

        Context context = getContext(obj);
        if (context == null) {
            Exceptions.r("context == null");
        }

        int layout = findLayout(obj, cls);
        if (layout == 0) {
            Exceptions.r("can`t find resource layout");
        }

        view = LayoutInflater.from(context).inflate(layout, null);

        return view;
    }

    /**
     * 获取layout资源文件
     * @param obj
     * @param cls
     * @return
     */
    protected int findLayout(Object obj, Class<?> cls) {
        int layout = 0;
        if (cls != null && cls.isAnnotationPresent(PK.class)) {
            PK pk = cls.getAnnotation(PK.class);
            layout = pk.value();
        }
        return layout;
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
