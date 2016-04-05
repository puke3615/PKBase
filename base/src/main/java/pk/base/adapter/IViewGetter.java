package pk.base.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author zijiao
 * @version 2016/4/5
 * @Mark 暂不用，可拓展View和Model的抽取
 */
public interface IViewGetter {

    /**
     * 作为代理处理getView方法
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    View proxyHandle(int position, View convertView, ViewGroup parent);
}
