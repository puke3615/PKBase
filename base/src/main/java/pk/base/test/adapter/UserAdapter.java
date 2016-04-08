package pk.base.test.adapter;

import android.content.Context;

import pk.base.adapter.PKAdapter;

/**
 * @author zijiao
 * @version 2016/4/7
 * @Mark
 */
public class UserAdapter extends PKAdapter<User, UserHolder> {

    public UserAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindData(UserHolder holder, int position, User data) {
        holder.name.setText(data.name);
        holder.age.setText(String.valueOf(data.age));
        holder.sex.setText(data.sex);
    }
}
