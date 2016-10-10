package pk.base.dao;

/**
 * @author zijiao
 * @version 16/8/3
 */
@SpName("dog")
public interface Test {

    @Key("name")
    void setName(String name);

    @Key("name")
    String getName();



}


class A {
    void s() {
        Test test = SpProxyFactory.getInstance().createProxy(Test.class);
        test.setName("asdasd");
        String a = test.getName();
    }
}