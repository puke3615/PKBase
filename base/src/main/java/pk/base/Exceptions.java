package pk.base;

/**
 * @author zijiao
 * @version 2015/12/29
 * @Mark
 */
public class Exceptions {

    /**
     * 非法参数
     * @param obj
     */
    public static void i(Object obj) {
        throw new IllegalArgumentException(String.valueOf(obj));
    }

    /**
     * 空指针
     * @param obj
     */
    public static void n(Object obj) {
        throw new NullPointerException(String.valueOf(obj));
    }

    /**
     * 运行时错误
     * @param obj
     */
    public static void r(Object obj) {
        throw new RuntimeException(String.valueOf(obj));
    }

}
