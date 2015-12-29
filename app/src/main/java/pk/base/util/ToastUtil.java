package pk.base.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import pk.base.R;


/**
 * Created by dell on 2015/11/6.
 */
public class ToastUtil {

    private static Toast toast;
    private static TextView text;

    private static AnimatorSet animatorSet;
    private static boolean isAiming;

    public static void show(Object s) {
        if (toast == null) {
            synchronized (ToastUtil.class) {
                if (toast == null) {
                    initToast();
                    initAnim();
                }
            }
        }
        text.setText(s + "");
        toast.show();
        text.post(new Runnable() {
            @Override
            public void run() {
                if (isAiming) {
                    animatorSet.cancel();
                }
                animatorSet.start();
            }
        });
    }

    private static void initAnim() {

        animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);

        ObjectAnimator x = ObjectAnimator.ofFloat(text, "scaleX", 0.3f, 1f);
        ObjectAnimator y = ObjectAnimator.ofFloat(text, "scaleY", 0.3f, 1f);
        ObjectAnimator a = ObjectAnimator.ofFloat(text, "alpha", 0.3f, 1f);

        animatorSet.play(x).with(y).with(a);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAiming = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAiming = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAiming = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                isAiming = true;
            }
        });
    }

    private static void initToast() {
        Context context = ApplicationAccessor.instance().get();
        text = new TextView(context);
        text.setTextColor(Color.GREEN);
        text.setBackgroundResource(R.drawable.toast_bg);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(text);
    }

}
