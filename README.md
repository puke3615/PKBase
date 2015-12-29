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

