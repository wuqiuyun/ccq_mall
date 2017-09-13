package clerk.sz28yun.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.adapter.MerchantCategoryAdapter;
import clerk.sz28yun.com.base.CCJActivity;
import clerk.sz28yun.com.bean.MerchantCategoryBean;
import clerk.sz28yun.com.presenter.MerchantCategoryPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import per.sue.gear2.widget.PageStatusLayout;

/**
 * 行业分类
 * Created by SUE on 2016/8/3 0003.
 */
public class MerchantCategoryListActivity extends CCJActivity implements MerchantCategoryPresenter.MerchantCategoryView {

    @Bind(R.id.merchant_categoey_list_view_first)
    ListView merchantCategoeyFirstListView;
    @Bind(R.id.merchant_categoey_list_view_second)
    ListView merchantCategoeySecondListView;

    @Bind(R.id.page_status_layout)
    PageStatusLayout pageStatusLayout;
    private MerchantCategoryAdapter merchantCategoryFirstAdapter;
    private MerchantCategoryAdapter merchantCategorySecondAdapter;
    private MerchantCategoryPresenter merchantCategoryPresenter;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, MerchantCategoryListActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_merchant_category;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        merchantCategoryFirstAdapter = new MerchantCategoryAdapter(getActivity());
        merchantCategoryFirstAdapter.setColorStateList(getResources().getColorStateList(R.color.color_text_normal));
        merchantCategorySecondAdapter = new MerchantCategoryAdapter(getActivity());
        merchantCategoeyFirstListView.setAdapter(merchantCategoryFirstAdapter);
        merchantCategoeySecondListView.setAdapter(merchantCategorySecondAdapter);
        merchantCategoeyFirstListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        merchantCategoeyFirstListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                merchantCategoryPresenter.loadSecondData(position);
                merchantCategoryFirstAdapter.setSelectedIndex(position);
            }
        });

        merchantCategoeySecondListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MerchantCategoryBean bean = (MerchantCategoryBean)parent.getAdapter().getItem(position);
                Intent intent = new Intent();
                intent.putExtra("data",bean );
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });
        merchantCategoryPresenter = new MerchantCategoryPresenter(getActivity(), this);
        pageStatusLayout.showLoading();
        merchantCategoryPresenter.syncData();
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        pageStatusLayout.showFailed(message, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageStatusLayout.showLoading();
                merchantCategoryPresenter.syncData();
            }
        });
    }

    @Override
    public void loadSecondList(ArrayList<MerchantCategoryBean> list) {
        merchantCategorySecondAdapter.setList(list);
    }

    @Override
    public void onSuccess(ArrayList<MerchantCategoryBean> result) {
        pageStatusLayout.showContent();
        merchantCategoryFirstAdapter.setList(result);

    }
}
