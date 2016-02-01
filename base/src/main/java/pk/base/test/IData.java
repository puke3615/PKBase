package pk.base.test;

import pk.base.anno.Impl;

/**
 * @author zijiao
 * @version 2015/12/29
 * @Mark
 */
@Impl(Data.class)
public interface IData {
    void setName(String name);
    String getName();
}