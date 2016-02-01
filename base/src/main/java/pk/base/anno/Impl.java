package pk.base.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wzj on 2015/11/13.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Impl {
    /**
     * @return true:单例  false:非单例
     */
    boolean single() default true;
    Class<?> value() default Void.class;
}
