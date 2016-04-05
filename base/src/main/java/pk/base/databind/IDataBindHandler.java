package pk.base.databind;

/**
 * @author zijiao
 * @version 2016/4/5
 * @Mark 数据绑定器
 * @param <H> View持有者
 * @param <D> Data数据源
 */
public interface IDataBindHandler<H, D> {

    void bindData(H holder, D data);

}
