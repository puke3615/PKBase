package pk.base.base.progress;

/**
 * @version 16/4/3
 * @user zijiao
 * @Mark 加载进度条
 */
public interface PKProgress {

    /**
     * 显示进度条
     * @param message 显示的内容
     */
    void showProgressDialog(String message);

    /**
     * 显示进度条
     */
    void showProgressDialog();

    /**
     * 取消显示进度条
     */
    void dismissProgressDialog();

    /**
     * 当前进度条是否正在显示
     * @return
     */
    boolean isProgressShowing();

}
