package pk.base.base.context;

import android.widget.TextView;

import pk.base.base.progress.PKProgress;

/**
 * @version 16/4/3
 * @user zijiao
 * @Mark 全局上下文
 */
public interface PKContext extends PKProgress {

    /**
     * 设置进度条
     * @param pkProgress
     */
    void setPKProgress(PKProgress pkProgress);

    /**
     * Toast信息提示
     * @param s
     */
    void T(Object s);

    /**
     * 判断对象是否为空
     * @param s
     * @return
     */
    boolean isEmpty(Object... s);

    /**
     * 获取TextView内容
     * @param textView
     * @return
     */
    String getStr(TextView textView);

}
