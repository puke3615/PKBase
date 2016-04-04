package pk.base.global;

import android.app.Application;

/**
 * @version 16/4/4
 * @user zijiao
 * @Mark
 */
public class PKApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        App.setContext(this);
    }
}
