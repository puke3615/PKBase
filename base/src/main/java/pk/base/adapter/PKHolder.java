package pk.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Field;

import pk.base.anno.PK;

/**
 * @author zijiao
 * @version 2016/4/5
 * @Mark
 */
public class PKHolder {

    protected Context mContext;
    protected View mView;

    public PKHolder(Context context) {
        this.mContext = context;
        handleClassAnno();
        handleFieldAnno();
    }

    protected void handleClassAnno() {
        Class cls = getClass();
        if (cls.isAnnotationPresent(PK.class)) {
            int layout = ((PK) cls.getAnnotation(PK.class)).value();
            if (layout > 0) {
                mView = LayoutInflater.from(mContext).inflate(layout, null);
            }
        }
    }

    protected void handleFieldAnno() {
        if (mView == null) {
            return;
        }
        Field[] fields = getFields();
        if (fields == null || fields.length == 0) {
            return;
        }
        for (Field field : fields) {
            try {
                if (field.isAnnotationPresent(PK.class)) {
                    PK pk = field.getAnnotation(PK.class);
                    int id = pk.value();
                    if (id > 0) {
                        field.setAccessible(true);
                        View view = mView.findViewById(id);
                        if (view != null) {
                            field.set(this, view);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取全部属性，暂定为public的
     * @return
     */
    protected Field[] getFields() {
        return getClass().getFields();
    }

    public View createView() {
        return mView;
    }

}
