package pk.base.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pk.base.anno.AnnoHelper;
import pk.base.anno.handler.AnnoHandler;
import pk.base.base.context.DefaultContextImpl;
import pk.base.base.context.PKContext;
import pk.base.base.progress.PKProgress;

/**
 * @version 16/4/3
 * @user zijiao
 * @Mark
 */
public abstract class BaseV4Fragment extends Fragment implements PKContext {

    protected Activity mActivity;
    protected View mMainView;
    private PKContext mPKContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView = AnnoHelper.instance().handle(this);
        init();
        initListener();
        return mMainView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mPKContext = new DefaultContextImpl(mActivity);
    }

    protected abstract void init();
    protected abstract void initListener();

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
