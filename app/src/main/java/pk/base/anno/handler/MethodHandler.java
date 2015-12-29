package pk.base.anno.handler;

import android.content.Context;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import pk.base.Exceptions;
import pk.base.anno.Click;
import pk.base.util.ViewUtil;

/**
 * @author zijiao
 * @version 2015/12/29
 * @Mark 方法处理器
 */
public class MethodHandler implements AnnoHandler.IMethodHandler {
    @Override
    public void handleMethod(final Object obj, View view, final Method method) {
        if (obj == null || view == null || method == null) {
            Exceptions.n("obj == null || view == null || method == null");
        }
        if (method.isAnnotationPresent(Click.class)) {
            Click click = method.getAnnotation(Click.class);
            int id = click.value();
            if (id != 0) {
                View v = view.findViewById(id);
                if (v != null) {
                    method.setAccessible(true);
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                method.invoke(obj);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }


}
