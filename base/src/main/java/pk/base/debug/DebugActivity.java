package pk.base.debug;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pk.base.base.BaseActivity;
import pk.base.util.ToastUtil;

/**
 * @author zijiao
 * @version 2016/1/14
 * @Mark
 */
public abstract class DebugActivity extends BaseActivity {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public static @interface Debug {
    }

    protected LinearLayout layout;
    private ViewGroup.LayoutParams mw, mm;

    @Override
    protected void setContentView() {
        final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
        final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
        mm = new ViewGroup.LayoutParams(MP, MP);
        mw = new ViewGroup.LayoutParams(MP, WC);
        ScrollView scroll = new ScrollView(this);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        onLayoutCreate(layout);
        scroll.addView(layout, mm);

        List<Item> items = getItems(new Item.Builder()).items;
        if (items != null && items.size() > 0) {
            for (final Item item : items) {
                addView(item);
            }
        } else {
            try {
                Method[] methods = getClass().getMethods();
                for (final Method method : methods) {
                    if (method.isAnnotationPresent(Debug.class)) {
                        method.setAccessible(true);
                        Item item = new Item(method.getName(), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    method.invoke(mContext);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        addView(item);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        setContentView(scroll, mm);
    }

    public static void T(Object s) {
        ToastUtil.show(s);
    }

    private void addView(final Item item) {
        if (item == null) {
            return;
        }
        Button button = new Button(this);
        button.setText(item.name);
        button.setOnClickListener(item.listener == null ? new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.target != null && Activity.class.isAssignableFrom(item.target)) {
                    startActivity(new Intent(DebugActivity.this, item.target));
                }
            }
        } : item.listener);
        layout.addView(button, mw);
    }

    protected void onLayoutCreate(LinearLayout layout) {

    }

    @Override
    protected void init() {
    }

    @Override
    protected void initListener() {
    }

    protected Item.Builder getItems(Item.Builder builder) {
        return null;
    }

    public static class Item {
        public String name;
        public View.OnClickListener listener;
        public Class target;

        public Item(String name, Class target) {
            this.target = target;
            this.name = name;
        }

        public Item(String name, View.OnClickListener listener) {

            this.name = name;
            this.listener = listener;
        }

        public static class Builder {
            private List<Item> items = new ArrayList<>();

            public Builder add(Item... items) {
                if (items != null) {
                    this.items.addAll(Arrays.asList(items));
                }
                return this;
            }
        }
    }

}
