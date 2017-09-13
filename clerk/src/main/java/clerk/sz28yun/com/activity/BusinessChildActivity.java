package clerk.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import clerk.sz28yun.com.R;
import clerk.sz28yun.com.base.CCJActivity;
import clerk.sz28yun.com.fragment.MainBusinessFragment;

/**
 * Created by sue on 2016/12/1.
 */
public class BusinessChildActivity extends CCJActivity {

    public static Intent startIntent(Context context, String name, String childId) {
        Intent intent;
        intent = new Intent(context, BusinessChildActivity.class);
        intent.putExtra("childId", childId);
        intent.putExtra("name", name);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

        @Override
    public int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        String childId = getIntent().getStringExtra("childId");
        String title = getIntent().getStringExtra("name");
        setBarTitle(title + "业务");
        MainBusinessFragment mainBusinessFragment =  new MainBusinessFragment();
        mainBusinessFragment.setArguments(MainBusinessFragment.getBundle(childId));
        replaceFragment(R.id.fragment_content, mainBusinessFragment);

    }
}
