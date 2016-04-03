package pk.base.base.context;

import android.app.Activity;
import android.widget.TextView;

import pk.base.base.progress.DefaultProgressImpl;
import pk.base.base.progress.PKProgress;
import pk.base.exception.Exceptions;
import pk.base.util.ToastUtil;

/**
 * @version 16/4/3
 * @user zijiao
 * @Mark
 */
public class DefaultContextImpl implements PKContext {

    private final Activity mActivity;
    private PKProgress mPKProgress;


    public DefaultContextImpl(Activity activity) {
        Exceptions.checkNull(activity);
        this.mActivity = activity;
        mPKProgress = new DefaultProgressImpl(activity);
    }

    @Override
    public void setPKProgress(PKProgress pkProgress) {
        this.mPKProgress = pkProgress;
    }

    @Override
    public void T(Object s) {
        //先暂时这样写吧
        ToastUtil.show(s);
    }

    @Override
    public boolean isEmpty(Object... ss) {
        for (Object s : ss) {
            if (s == null) {
                return true;
            } else if (s instanceof String && ((String) s).length() == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getStr(TextView textView) {
        Exceptions.checkNull(textView);
        return textView.getText().toString();
    }

    @Override
    public void showProgressDialog(String message) {
        if (mPKProgress != null) {
            mPKProgress.showProgressDialog(message);
        }
    }

    @Override
    public void showProgressDialog() {
        if (mPKProgress != null) {
            mPKProgress.showProgressDialog();
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (mPKProgress != null) {
            mPKProgress.dismissProgressDialog();
        }
    }

    @Override
    public boolean isProgressShowing() {
        return mPKProgress == null ? null : mPKProgress.isProgressShowing();
    }
}
