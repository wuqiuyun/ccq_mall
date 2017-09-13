package clerk.sz28yun.com.base;
import android.os.Bundle;
import android.support.annotation.Nullable;


import clerk.sz28yun.com.R;
import per.sue.gear2.adapter.SelectListAdapter;
import per.sue.gear2.fragment.SelectListFragment;


/**
 * Created by SUE on 2016/8/16 0016.
 */
public abstract class SelectActivity<T> extends  CCJActivity implements SelectListFragment.OnSelectFragmentListerner<T>,SelectListAdapter.SelectListAdapterBindView<T> {


    protected SelectListFragment<T> selectListFragment;
    public boolean isNotPage;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        selectListFragment = new SelectListFragment<T>();
        selectListFragment.init(this, this);
        if(isNotPage)selectListFragment.isNotPage();
        replaceFragment(R.id.fragment_content, selectListFragment);
    }



}
