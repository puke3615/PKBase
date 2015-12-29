package pk.base.test;

import pk.base.data.DataProvider;

/**
 * @author zijiao
 * @version 2015/12/29
 * @Mark
 */
public class TestDataProvider extends DataProvider<Test> {

    private static final byte[] mLock = new byte[0];
    private static TestDataProvider mInstance;

    private TestDataProvider() {
    }

    public static TestDataProvider instance() {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new TestDataProvider();
                }
            }
        }
        return mInstance;
    }

}

class Test {
    String name;

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                '}';
    }
}