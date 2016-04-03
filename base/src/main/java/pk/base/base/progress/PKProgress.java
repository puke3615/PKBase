package pk.base.base.progress;

/**
 * @version 16/4/3
 * @user zijiao
 * @Mark
 */
public interface PKProgress {

    void showProgressDialog(String message);

    void showProgressDialog();

    void dismissProgressDialog();

    boolean isProgressShowing();

}
