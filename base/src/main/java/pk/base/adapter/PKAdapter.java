package pk.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author zijiao
 * @version 2016/4/5
 * @Mark
 */
public abstract class PKAdapter<T, H extends PKHolder> extends PKAbstractAdapter<T> {

    private Class mHolderClass;

    public PKAdapter(Context context) {
        super(context);
        init();
    }

    public PKAdapter(Context context, List<T> data) {
        super(context, data);
        init();
    }

    private void init() {
        try {
            ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
            mHolderClass = (Class) pt.getActualTypeArguments()[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        H holder = null;
        if (view == null) {
            try {
                Constructor<H> holderConstructor = mHolderClass.getConstructor(Context.class);
                if (holderConstructor != null) {
                    holder = (H) holderConstructor.newInstance(mContext);
                    view = wrapView(holder.createView());
                    if (view != null) {
                        view.setTag(holder);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            holder = (H) view.getTag();
        }

        T data;
        if (holder != null && (data = getItem(position)) != null) {
            onBindData(holder, position, data);
        }
        return view;
    }

    protected abstract void onBindData(H holder, int position, T data);

    protected View wrapView(View view) {
        return view;
    }

}
