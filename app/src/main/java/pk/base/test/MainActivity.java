package pk.base.test;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import pk.base.R;
import pk.base.anno.AnnoHelper;
import pk.base.anno.Impl;
import pk.base.anno.Instance;
import pk.base.anno.PK;
import pk.base.anno.handler.AnnoHandler;
import pk.base.anno.handler.ClassHandler;
import pk.base.anno.handler.FieldHandler;
import pk.base.anno.handler.MethodHandler;
import pk.base.base.BaseActivity;
import pk.base.util.ApplicationAccessor;
import pk.base.util.ToastUtil;

@PK(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @PK
    private Button button1;
    @PK
    private TextView text;
    @Instance
    private IData data;

    @Override
    protected void init() {

    }

    @Override
    protected void initListener() {

    }

    int i = 0;
    public void button1() {
        data.setName(i++ + "");
        text.setText(data.getName());
    }


}
