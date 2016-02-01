package pk.base.dao;

import android.content.SharedPreferences;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import pk.base.Exceptions;
import pk.base.util.ApplicationAccessor;

/**
 * @author wzj
 * @version 2015/11/20
 * @Mark SharedPreferences基础操作类生成工厂
 * 通过接口生成动态代理对象
 * 接口的规则：
 * 1. 接口上必须加注解SpName，标识SharedPreferences的名字
 * 2. 方法中添加注解Key或Delete，Key注解必须注入键，Delete可不带
 *      a. Key注解时，若方法带参数，则为保存该Key对应的值
 *      b. 若不带，则为取出该Key对应的值
 *      c. Delete注解时，若注解有值，则删除与此对应的value值
 *      d. 若没值，则清除该SpName下的所有值
 * 3. 代理类的生成：通过SpProxyFactory.getInstance().createProxy()方法生成对应的代理类，生成之后方法直接调用即可
 */
public class SpProxyFactory {

    private static final Map<Class, Object> mProxys = new HashMap<>();
    private static final byte[] lock = new byte[0];

    private SpProxyFactory() {
    }

    private static class SpProxyFactoryHolder {
        static SpProxyFactory spProxyFactory = new SpProxyFactory();
    }

    public static SpProxyFactory getInstance() {
        return SpProxyFactoryHolder.spProxyFactory;
    }

    public final <T> T createProxy(Class<T> cls) {
        if (cls == null && !cls.isInterface()) {
            Exceptions.r("传入的类型为null或者不是接口");
        }

        if (!mProxys.containsKey(cls)) {
            synchronized (lock) {
                if (!mProxys.containsKey(cls)) {
                    mProxys.put(cls, Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new DefaultInvocationHandler(cls)));
                }
            }
        }
        return (T) mProxys.get(cls);
    }

    private class DefaultInvocationHandler implements InvocationHandler {

        SharedPreferences sp;

        DefaultInvocationHandler(Class cls) {
            String spName = null;
            if (!cls.isAnnotationPresent(SpName.class)
                    || (spName = ((SpName) cls.getAnnotation(SpName.class)).value()) == null
                    || spName.length() == 0) {
                Exceptions.r("未设置" + SpName.class.getSimpleName() + "注解");
            }

            sp = ApplicationAccessor.instance().get().getSharedPreferences(spName, 0);
            if (sp == null) {
                Exceptions.r(cls.getSimpleName() + "代理对象生成失败");
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Object result = null;

            if (method.isAnnotationPresent(Delete.class)) {
                Delete delete = method.getAnnotation(Delete.class);
                String deleteKey = delete.value();
                if (deleteKey.length() == 0) {
                    result = sp.edit().clear().commit();
                } else {
                    if (sp.contains(deleteKey)) {
                        result = sp.edit().remove(deleteKey).commit();
                    }
                }
            } else if (method.isAnnotationPresent(Key.class)) {
                String key = method.getAnnotation(Key.class).value();
                if (key.length() == 0) {
                    Exceptions.r("注解不能为\"\"");
                }
                Type[] paramTypes = method.getParameterTypes();
                if (paramTypes.length == 0) {//get method, when the params length is 0
                    if (sp.contains(key)) {
                        result = sp.getAll().get(key);
                    }
                } else {//set method
                    Object v = args[0];
                    SharedPreferences.Editor editor = sp.edit();
                    if (v instanceof Integer) {
                        editor.putInt(key, (Integer) v);
                    } else if (v instanceof String) {
                        editor.putString(key, (String) v);
                    } else if (v instanceof Boolean) {
                        editor.putBoolean(key, (Boolean) v);
                    } else if (v instanceof Long) {
                        editor.putLong(key, (Long) v);
                    } else if (v instanceof Float) {
                        editor.putFloat(key, (Float) v);
                    }
                    result = editor.commit();
                }
            }


            return result;
        }
    }

}
