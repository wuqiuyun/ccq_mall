package clerk.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.adapter.MerchantUpdateDraftAdapter;
import clerk.sz28yun.com.base.CCJActivity;
import clerk.sz28yun.com.bean.MerchantParams;
import clerk.sz28yun.com.cache.MerchantDraftStorageCache;

import butterknife.Bind;

/**
 * Created by sue on 2016/12/22.
 */
public class MerchantUpdateDraftActivity extends CCJActivity {


    @Bind(R.id.list_view)
    ListView listView;
    private MerchantUpdateDraftAdapter merchantUpdateDraftAdapter;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, MerchantUpdateDraftActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_merchant_update_draft;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        merchantUpdateDraftAdapter = new MerchantUpdateDraftAdapter(getActivity());
        listView.setAdapter(merchantUpdateDraftAdapter);
        merchantUpdateDraftAdapter.setList(MerchantDraftStorageCache.getInstance().getMerchantParamsArrayList(getApplication()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MerchantParams params = merchantUpdateDraftAdapter.getItem(position);
                finish();
                startActivity(MerchantUpdateActivity.startIntent(getActivity(),params ));

            }
        });

    }


}
