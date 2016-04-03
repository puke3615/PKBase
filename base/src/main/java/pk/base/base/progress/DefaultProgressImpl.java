package pk.base.base.progress;

import android.app.Activity;
import android.app.ProgressDialog;

import pk.base.exception.Exceptions;

/**
 * @version 16/4/3
 * @user zijiao
 * @Mark PKProgressDialog 加载进度条默认的实现类
 */
public class DefaultProgressImpl implements PKProgress {

    private final Activity mActivity;
    private ProgressDialog mProgressDialog;
    private static final String DEFAULT_MESSAGE = "正在加载，请稍后..";

    public DefaultProgressImpl(Activity activity) {
        Exceptions.checkNull(activity);
        this.mActivity = activity;
    }

    //初始化操作，使用懒加载的方式（不建议在创建实例时初始化）
    private void initProgressDialogIfNeed() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mActivity);
            mProgressDialog.setMessage(DEFAULT_MESSAGE);
            mProgressDialog.setCancelable(false);
        }
    }

    @Override
    public void showProgressDialog(String message) {
        initProgressDialogIfNeed();
        if (message != null) {
            mProgressDialog.setMessage(message);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void showProgressDialog() {
        showProgressDialog(null);
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public boolean isProgressShowing() {
        return mProgressDialog == null ? false : mProgressDialog.isShowing();
    }
}
