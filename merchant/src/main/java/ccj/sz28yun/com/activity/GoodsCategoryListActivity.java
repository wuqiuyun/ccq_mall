package ccj.sz28yun.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



import java.util.ArrayList;

import butterknife.Bind;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.GoodsCategoryAdapter;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.GoodsCategoryBean;
import ccj.sz28yun.com.presenter.GoodsCategoryPresenter;
import per.sue.gear2.widget.PageStatusLayout;

/**
 * Created by SUE on 2016/8/3 0003.
 */
public class GoodsCategoryListActivity extends CCJActivity implements GoodsCategoryPresenter.GoodsCategoryView {

    @Bind(R.id.categoey_list_view_first)
    ListView categoeyFirstListView;
    @Bind(R.id.categoey_list_view_second)
    ListView categoeySecondListView;

    @Bind(R.id.page_status_layout)
    PageStatusLayout pageStatusLayout;

    private GoodsCategoryAdapter goodsCategoryFirstAdapter;
    private GoodsCategoryAdapter goodsCategorySecondAdapter;
    private GoodsCategoryPresenter goodsCategoryPresenter;

    private GoodsCategoryBean firstBean;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, GoodsCategoryListActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_goods_category;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        goodsCategoryFirstAdapter = new GoodsCategoryAdapter(getActivity());
        goodsCategoryFirstAdapter.setColorStateList(getResources().getColorStateList(R.color.color_text_white_normal));
        goodsCategoryFirstAdapter.setBackgroundResId(R.drawable.selector_primary_white);
        goodsCategorySecondAdapter = new GoodsCategoryAdapter(getActivity());
        categoeyFirstListView.setAdapter(goodsCategoryFirstAdapter);
        categoeySecondListView.setAdapter(goodsCategorySecondAdapter);
        categoeyFirstListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        categoeyFirstListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                firstBean = goodsCategoryFirstAdapter.getItem(position);
                goodsCategoryFirstAdapter.setSelectedIndex(position);
                goodsCategoryPresenter.loadSecondData(position);
            }
        });

        categoeySecondListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsCategoryBean bean = (GoodsCategoryBean)parent.getAdapter().getItem(position);
                Intent intent = new Intent();
                intent.putExtra("first",firstBean );
                intent.putExtra("second",bean );
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });
        goodsCategoryPresenter = new GoodsCategoryPresenter(getActivity(), this);
        pageStatusLayout.showLoading();
        goodsCategoryPresenter.syncData();
    }
    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        pageStatusLayout.showFailed(message, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageStatusLayout.showLoading();
                goodsCategoryPresenter.syncData();
            }
        });
    }


    @Override
    public void loadSecondList(ArrayList<GoodsCategoryBean> list) {
        goodsCategorySecondAdapter.setList(list);

    }

    @Override
    public void onSuccess(ArrayList<GoodsCategoryBean> result) {
        pageStatusLayout.showContent();
        goodsCategoryFirstAdapter.setList(result);
        goodsCategoryPresenter.loadSecondData(0);
    }
}
