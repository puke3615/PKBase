package pk.base.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import pk.base.anno.AnnoHelper;
import pk.base.anno.IAnnoHandler;
import pk.base.base.context.DefaultContextImpl;
import pk.base.base.context.PKContext;
import pk.base.base.progress.PKProgress;

/**
 * @author zijiao
 * @version 2015/12/29
 * @Mark
 */
public abstract class BaseActivity extends Activity implements PKContext {

    protected Context mContext;
    private PKContext mPKContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        mPKContext = getContextImpl();
        if (mPKContext == null) {
            mPKContext = new DefaultContextImpl(this);
        }
        setContentView();
        init();
        initListener();
    }

    protected PKContext getContextImpl() {
        return null;
    }

    protected void setContentView() {
        IAnnoHandler handler = getAnnoHandler();
        if (handler == null) {
            handler = AnnoHelper.instance();
        }
        View view = handler.handle(this);
        setContentView(view);
    }

    protected abstract void init();
    protected abstract void initListener();


    protected IAnnoHandler getAnnoHandler() {
        return null;
    }

    @Override
    public void setPKProgress(PKProgress pkProgress) {
        mPKContext.setPKProgress(pkProgress);
    }

    @Override
    public void T(Object s) {
        mPKContext.T(s);
    }

    @Override
    public boolean isEmpty(Object... s) {
        return mPKContext.isEmpty(s);
    }

    @Override
    public String getStr(TextView textView) {
        return mPKContext.getStr(textView);
    }

    @Override
    public void showProgressDialog(String message) {
        mPKContext.showProgressDialog(message);
    }

    @Override
    public void showProgressDialog() {
        mPKContext.showProgressDialog();
    }

    @Override
    public void dismissProgressDialog() {
        mPKContext.dismissProgressDialog();
    }

    @Override
    public boolean isProgressShowing() {
        return mPKContext.isProgressShowing();
    }
}
