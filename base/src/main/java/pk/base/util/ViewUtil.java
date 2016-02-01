package pk.base.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author zijiao
 * @version 2015/12/29
 * @Mark
 */
public class ViewUtil {

    public static Context getContext(Object obj) {
        Context context = null;
        if (obj instanceof Fragment) {
            context = ((Fragment) obj).getActivity();
        } else if (obj instanceof android.support.v4.app.Fragment) {
            context = ((android.support.v4.app.Fragment) obj).getActivity();
        } else if (obj instanceof Activity) {
            context = (Context) obj;
        } else if (obj instanceof ViewGroup) {
            context = ((ViewGroup) obj).getContext();
        } else if (obj instanceof View) {
            context = ((View) obj).getContext();
        }
        return context;
    }

    public static void VISIBLE(View view) {
        if (view != null) {
            if (view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void INVISIBLE(View view) {
        if (view != null) {
            if (view.getVisibility() != View.INVISIBLE) {
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    public static void GONE(View view) {
        if (view != null) {
            if (view.getVisibility() != View.GONE) {
                view.setVisibility(View.GONE);
            }
        }
    }

}
