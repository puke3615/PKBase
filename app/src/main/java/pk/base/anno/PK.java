package pk.base.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dell on 2015/11/3.
 * 视图相关注解：
 * 1. Fragment、v4.Fragment、Activity、CustomView等类上注解layout，注入布局文件
 * 2. Field上注入id，注入属性
 * 3. Method上注入id，注入点击事件
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface PK {
    int value() default 0;
}
