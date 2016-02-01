package pk.base.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import pk.base.Exceptions;
import pk.base.anno.AnnoHelper;

/**
 * @author zijiao
 * @version 2015/12/29
 * @Mark
 */
public abstract class BaseActivity extends Activity {

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        setContentView();
        init();
        initListener();
    }

    protected void setContentView() {
        View view = AnnoHelper.instance().handle(this);
        setContentView(view);
    }

    protected abstract void init();
    protected abstract void initListener();


    public static boolean isEmpty(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                return true;
            } else if (obj instanceof String && ((String) obj).length() == 0) {
                return true;
            }
        }
        return false;
    }

    public static String getStr(TextView text) {
        if (text == null) {
            Exceptions.n("text == null");
        }
        return text.getText().toString().trim();
    }

}
