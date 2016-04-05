package pk.base.databind;

/**
 * @author zijiao
 * @version 2016/4/5
 * @Mark
 */
public class DataBinds {

    public <H, D> IDataBindHandler<H, D> dataBindHandler() {
        return new DefaultDataBindHandler<>();
    }

    public <H, D> IDataBindHandler<H, D> dataBindHandler(DefaultDataBindHandler.IBind bind) {
        return new DefaultDataBindHandler<>(bind);
    }

}
