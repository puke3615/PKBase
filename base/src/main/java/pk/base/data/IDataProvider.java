package pk.base.data;

/**
 * @author zijiao
 * @version 2015/12/28
 * @Mark
 */
public interface IDataProvider<T extends ICopy<T>> {

    void setData(T data);

    T getData();

    void registerDataListener(IDataListener<T> dataListener);

    void unRegisterDataListener(IDataListener<T> dataListener);

    void unRegisterAll();

    void notifyChange();

    interface IDataListener<T> {

        void onChange(T data);

    }

}
