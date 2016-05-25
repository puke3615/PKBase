package pk.base.test.adapter;

import android.content.Context;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

import pk.base.adapter.PKAdapter;

/**
 * @author zijiao
 * @version 2016/4/7
 * @Mark
 */
public class UserAdapter extends PKAdapter<User, UserHolder> {

    private final Set<String> mAdds = new HashSet<>();

    public UserAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindData(UserHolder holder, int position, final User data) {
        holder.name.setText(data.name);
        holder.age.setText(String.valueOf(data.age));
        holder.sex.setText(data.sex);

        holder.age.setVisibility(mAdds.contains(data.userId) ? View.GONE : View.VISIBLE);
        holder.age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdds.add(data.userId);
                //添加操作
            }
        });

    }
}
