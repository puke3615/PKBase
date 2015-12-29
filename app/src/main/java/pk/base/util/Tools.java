package pk.base.util;

import android.content.Context;



/**
 * Created by dell on 2015/11/6.
 */
public class Tools {

    private static Context context = ApplicationAccessor.instance().get();

    public static int dip2px(float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getMaxMemery() {
        return (int) (Runtime.getRuntime().maxMemory());
    }

    public static int getFreeMemery() {
        return (int) (Runtime.getRuntime().freeMemory());
    }

    public static int getScreenWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @return
     */
    public static int getStatusHeight() {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

}
