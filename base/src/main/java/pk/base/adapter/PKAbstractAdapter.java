package pk.base.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import pk.base.exception.Exceptions;

/**
 * @author zijiao
 * @version 2016/4/5
 * @Mark 数据处理
 */
public abstract class PKAbstractAdapter<T> extends BaseAdapter {

    protected final List<T> mData;
    protected final Context mContext;

    public PKAbstractAdapter(Context context) {
        this(context, null);
    }

    public PKAbstractAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mData = data == null ? new ArrayList<T>() : data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<T> data) {
        if (data != null) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> data) {
        if (data != null && data.size() > 0) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addData(T... datas) {
        if (datas != null && datas.length > 0) {
            for (T data : datas) {
                Exceptions.checkNull(data);
                mData.add(data);
            }
            notifyDataSetChanged();
        }
    }

    public void addData(T data, int position) {
        Exceptions.checkNull(data);
        mData.add(position, data);
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }

    public void removeData(T data) {
        if (data != null && mData.contains(data)) {
            mData.remove(data);
            notifyDataSetChanged();
        }
    }


}
