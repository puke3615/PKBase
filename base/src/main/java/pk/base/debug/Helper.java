package pk.base.debug;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zijiao
 * @version 2016/1/14
 * @Mark
 */
public class Helper {

    private static final int MP;
    private static final int WC;
    private static final ViewGroup.LayoutParams ww;

    static {
        MP = ViewGroup.LayoutParams.MATCH_PARENT;
        WC = ViewGroup.LayoutParams.WRAP_CONTENT;
        ww = new ViewGroup.LayoutParams(MP, WC);
    }

    public static interface IR {
        void onInput(String[] r);
    }

    public static class R {
        Object obj;
        IR r;

        public R(Object obj, IR r) {
            this.obj = obj;
            this.r = r;
        }
    }

    public static void getData(Context context, final IR ir, final Object... objs) {
        if (objs == null) {
            return;
        }
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        final List<EditText> edits = new ArrayList<>(objs.length);
        for (Object obj : objs) {
            EditText edit = new EditText(context);
            edit.setHint(obj + "");
            layout.addView(edit, ww);
            edits.add(edit);
        }

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("设置参数")
                .setView(layout)
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ir == null) {
                            return;
                        }
                        String[] results = new String[objs.length];
                        for (int i = 0; i < objs.length; i ++) {
                            results[i] = edits.get(i).getText().toString().trim();
                        }
                        ir.onInput(results);
                    }
                })
                .create();
        dialog.show();
    }

}
