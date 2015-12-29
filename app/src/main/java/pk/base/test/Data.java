package pk.base.test;

/**
 * @author zijiao
 * @version 2015/12/29
 * @Mark
 */
public class Data implements IData {

    String name;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}