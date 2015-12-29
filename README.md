# PKBase
Android开发公共SDK抽取

注解：
1. 视图注解：
在Fragment、Activity或自定义复合视图中
例：

    @PK(R.layout.activity_main)
    public class MainActivity extends Activity {

        @PK
        private Button button1;
        @PK
        private TextView text;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //可以写在BaseActivity里，简化代码量
            View view = AnnoHelper.instance().handle(this);
            setContentView(view);
        }

        public void button1() {
            text.setText("测试一下");
        }
    }

Activity中设置layout布局可以使用@PK方式在类上注入，类中视图的注入使用@PK(R.id.text)，当PK中value缺省时，默认通过反射获取R文件中的对应属性名的id值

2. 接口注解：
面向接口可以提高程序的拓展性和开发效率，特别是在MVP模式中，接口交互大大降低了代码耦合度。在这里，为接口实例化提供了通过注解注入的方式
例：

接口声明：注意在接口声明时通过注解@Impl(Data.class)指定该接口的默认实现类为Data

    @Impl(Data.class)
    public interface IData {
        void setName(String name);
        String getName();
    }

接口实例化：Data代码，简单实现set、get方法

    public class Data implements IData {

        String name;

        @Override
        public void setName(String name) {
            this.name = name;
        }
    
        @Override
        public String getName() {
            return name;
        }
    }

在使用时的调用

    @PK(R.layout.activity_main)
    public class MainActivity extends Activity {

        @Instance
        private IData data;

        private static final String TAG = "TAG";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //可以写在BaseActivity里，简化代码量
            View view = AnnoHelper.instance().handle(this);
            setContentView(view);

            data.setName("测试内容");
            Log.i(TAG, data.getName());
        }
    }

3. 拓展注解：
默认提供的注解不足以满足使用时，可拓展注解处理器AnnoHandler
实现IClassHandler、IMethodHandler或IFieldHandler接口可以对相应的Class、Method、Field进行处理
当某些类需要进行特殊处理时，可以设置IHandleInterceptor拦截器，进行定制处理

//===============================================================================

观察者：
在开发中，会出现这种情况，有多个地方要显示的数据都来源于一个数据模型，
例如：用户名要在主页的某个Fragment中显示，还要在个人页面显示，详情页显示等等；
还可能出现这种情况，在列表页显示的一条数据，去详情页进行修改后，再返回到列表页数据与实际的数据不一致。一般的处理逻辑就是在列表页onResume或者onStart方法中再次请求数据；或者通过startActivityForResult + onActivityResult方式进行修改后的数据回传；
又或者是，在在列表页注册一个广播，然后每当数据改变时，发送一条数据改变的广播，通知列表页更改UI。
粗略看一下以上的几种方法，实现起来并不困难，只是会增加很多开发时间，而且还会让代码量臃肿不堪。
对于UI能与数据保持一致，可以尝试使用观察者模式，这里只需要定义好自己的Model数据后，创建与之对应的DataProvider就行了
例：
创建数据模型Test

    class Test {
        String name;

        @Override
        public String toString() {
            return "Test{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

然后创建对应的数据提供者

    public class TestDataProvider extends DataProvider<Test> {

        private static final byte[] mLock = new byte[0];
        private static TestDataProvider mInstance;

        private TestDataProvider() {
        }

        public static TestDataProvider instance() {
            if (mInstance == null) {
                synchronized (mLock) {
                    if (mInstance == null) {
                        mInstance = new TestDataProvider();
                    }
                }
            }
            return mInstance;
        }

    }

这里注意要保证这里的数据模型必须是基于该数据对应的观察者上是唯一的（即要保证data的在观察者范围类的唯一性），这里采取的策略是使用单例。
接下来测试一下

        final TestDataProvider test = TestDataProvider.instance();
        test.registerDataListener(new IDataProvider.IDataListener<Test>() {
            @Override
            public void onChange(Test data) {
                text.setText(data.toString());
            }
        });
        new Thread(){
            @Override
            public void run() {
                Test t = new Test();
                t.name = "线程中做出改变";
                test.setData(t);
            }
        }.start();

当在线程中更改数据时，数据更改的回调通知会在主线程调用，修改text的UI信息。

//==================================================================================

SharedPreferences：
经常使用的SharedPreferences进行简单的持久化存储，这里为了提高开发效率，将动态代理也运用其中，
 * 通过接口生成动态代理对象
 * 接口的规则：
 * 1. 接口上必须加注解SpName，标识SharedPreferences的名字
 * 2. 方法中添加注解Key或Delete，Delete可不带
 *      a. Key注解时，若方法带参数，则为保存该Key对应的值
 *      b. 若不带，则为取出该Key对应的值
 *      c. Delete注解时，若注解有值，则删除与此对应的value值
 *      d. 若没值，则清除该SpName下的所有值
 * 3. 代理类的生成：通过SpProxyFactory.getInstance().createProxy()方法生成对应的代理类，生成之后方法直接调用即可
 例：


    @SpName("user")
    public interface IUserDao {
        String USERNAME = "username";
        String PASSWORD = "password";

        @Key(USERNAME)
        void setUsername(String username);

        @Key(USERNAME)
        String getUsername();

        @Delete(USERNAME)
        void deleteUsername();

        @Key(PASSWORD)
        void setPassword(String password);

        @Delete
        void deleteAll();
    }
    

动态代理对象的生成是依靠SpProxyFactory的，下面是使用代码：

    IUserDao dao = SpProxyFactory.getInstance().createProxy(IUserDao.class);
    dao.setUsername("asdasdas");
    text.setText(dao.getUsername());

