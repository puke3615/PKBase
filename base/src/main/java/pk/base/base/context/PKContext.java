package pk.base.base.context;

import android.widget.TextView;

import pk.base.base.progress.PKProgress;

/**
 * @version 16/4/3
 * @user zijiao
 * @Mark
 */
public interface PKContext extends PKProgress {

    PKProgress getPKProgress();

    void setPKProgress(PKProgress pkProgress);

    void T(Object s);

    boolean isEmpty(Object... s);

    String getStr(TextView textView);

}
