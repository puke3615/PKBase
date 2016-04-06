package pk.base.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

/**
 * @version 16/4/4
 * @user zijiao
 * @Mark
 */
public class App {

    private static final Handler sHandler = new Handler(Looper.getMainLooper());
    private static final Gson sGson = new Gson();
    private static Context sContext;

    public static Handler handler() {
        return sHandler;
    }


    public static Context context() {
        return sContext;
    }

    public static Gson gson() {
        return sGson;
    }

    static void setContext(Application application) {
        sContext = application.getApplicationContext();
    }

}
