package pk.base.databind;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import pk.base.reflect.ReflectHelper;

/**
 * @author zijiao
 * @version 2016/4/5
 * @Mark 默认的数据绑定器
 */
public class DefaultDataBindHandler<H, D> implements IDataBindHandler<H, D> {

    private Class mHolderCls;
    private Class mDataCls;
    private IBind mBind;
    private final ReflectHelper mHelper = ReflectHelper.instance();
    private final Map<Field, Field> mReflectMappings = new HashMap<>();

    public interface IBind {
        void bind(View view, Object obj);
    }

    public DefaultDataBindHandler() {
        this(null);
    }

    public DefaultDataBindHandler(IBind bind) {
        try {
            mBind = bind == null ? new DefaultBind() : bind;
            Type[] types = mHelper.getGenericTypes(this);
            mHolderCls = (Class) types[0];
            mDataCls = (Class) types[1];
            Field[] dataFields = mHelper.getAllFields(mDataCls);
            if (dataFields != null) {
                for (Field dataField : dataFields) {
                    if (dataField == null) {
                        continue;
                    }
                    Field mappingHolderField = mHelper.getFieldByName(mHolderCls, dataField.getName());
                    if (mappingHolderField != null) {
                        mReflectMappings.put(dataField, mappingHolderField);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bindData(H holder, D data) {
        if (mBind == null) {
            return;
        }
        Iterator<Field> iterator = mReflectMappings.keySet().iterator();
        while (iterator.hasNext()) {
            Field dataField = iterator.next();
            Object objData = mHelper.getFieldValue(data, dataField);
            Field holderField = mReflectMappings.get(dataField);
            Class holderFieldCls = holderField.getType();
            if (View.class.isAssignableFrom(holderFieldCls)) {
                Object info = mHelper.getFieldValue(holder, holderField);
                if (info instanceof View) {
                    mBind.bind((View) info, objData);
                }
            }
        }
    }

    private static final class DefaultBind implements IBind {

        @Override
        public void bind(View view, Object data) {
            if (view == null) {
                return;
            }
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setText(data == null ? null : String.valueOf(data));
            } else if (view instanceof ImageView) {
                //待实现

            }
        }
    }

}
