package pk.base.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author zijiao
 * @version 2016/4/5
 * @Mark
 */
public class ReflectHelper {

    private ReflectHelper() {
    }

    public Type[] getGenericTypes(Object obj) {
        return ((ParameterizedType) obj.getClass().getGenericSuperclass()).getActualTypeArguments();
    }

    public Field[] getAllFields(Class cls) {
        Field[] fields = null;
        if (cls != null) {
            try {
                fields = cls.getDeclaredFields();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fields;
    }

    public Field getFieldByName(Class cls, String name) {
        Field field = null;
        try {
            field = cls.getDeclaredField(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return field;
    }

    public Object getFieldValue(Object obj, Field dataField) {
        Object result = null;
        try {
            dataField.setAccessible(true);
            result = dataField.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static final class ReflectHelperHolder {
        static final ReflectHelper instance = new ReflectHelper();
    }

    public static ReflectHelper instance() {
        return ReflectHelperHolder.instance;
    }

}
