//package pk.base.test;
//
//import android.widget.Button;
//import android.widget.TextView;
//
//import pk.base.R;
//import pk.base.anno.Click;
//import pk.base.anno.Instance;
//import pk.base.anno.PK;
//import pk.base.base.BaseActivity;
//import pk.base.dao.SpProxyFactory;
//import pk.base.data.IDataProvider;
//
//@PK(R.layout.activity_main)
//public class MainActivity extends BaseActivity {
//
//    @PK
//    private Button button1;
//    @PK
//    private TextView text;
//    @Instance
//    private IData data;
//    int i = 0;
//
//    @Override
//    protected void init() {
//
//    }
//
//    @Override
//    protected void initListener() {
//
//    }
//
//    //测试注入接口
//    public void button1() {
//        data.setName(i++ + "");
//        text.setText(data.getName());
//    }
//
//    //测试DataProvider
//    @Click(R.id.button2)
//    public void button2() {
//        final TestDataProvider test = TestDataProvider.instance();
//        test.registerDataListener(new IDataProvider.IDataListener<Test>() {
//            @Override
//            public void onChange(Test data) {
//                text.setText(data.toString());
//            }
//        });
//        new Thread(){
//            @Override
//            public void run() {
//                Test t = new Test();
//                t.name = "线程中做出改变";
//                test.setData(t);
//            }
//        }.start();
//    }
//
//    //测试SharedPreferences代理
//    @Click(R.id.button3)
//    public void button3() {
//        IUserDao dao = SpProxyFactory.getInstance().createProxy(IUserDao.class);
////        dao.setUsername("123456789");
//        text.setText(dao.getUsername());
////        dao.deleteUsername();
//    }
//
//
//}
