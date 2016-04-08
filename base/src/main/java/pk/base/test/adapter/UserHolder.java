package pk.base.test.adapter;

import android.content.Context;
import android.widget.TextView;

import pk.base.adapter.PKHolder;
import pk.base.anno.PK;

/**
 * @author zijiao
 * @version 2016/4/7
 * @Mark
 */
@PK(1111)//这里写对应的xml  例：R.layout.sb
public class UserHolder extends PKHolder {

    @PK(12)//这里写id  例：R.id.sb
    public TextView name;
    @PK(12)
    public TextView age;
    @PK(12)
    public TextView sex;

    public UserHolder(Context context) {
        super(context);
    }
}
