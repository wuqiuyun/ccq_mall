package clerk.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import clerk.sz28yun.com.R;
import clerk.sz28yun.com.adapter.StatisticMemberAdapter;
import clerk.sz28yun.com.base.CCJActivity;
import clerk.sz28yun.com.bean.MemberStatisticBean;
import clerk.sz28yun.com.bean.StatisticBean;
import clerk.sz28yun.com.cache.GlobalDataCache;
import clerk.sz28yun.com.presenter.StatisticMemberPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import per.sue.gear2.presenter.ListPresenter;
import per.sue.gear2.widget.PageStatusLayout;

/**
 * 会员统计
 * Created by sue on 2016/11/21.
 */
public class StatisticMemberActivity extends CCJActivity implements ListPresenter.ListResultView<ArrayList<MemberStatisticBean>> {

    TextView statisticCountMemberTv;
    TextView statisticCountVipTv;
    EditText keyWordEt;


    @Bind(R.id.statistic_list_view)
    ListView statisticListView;
    @Bind(R.id.statistic_refresh_layout)
    MaterialRefreshLayout statisticRefreshLayout;
    @Bind(R.id.statistic_page_status_layout)
    PageStatusLayout statisticPageStatusLayout;

    private StatisticMemberAdapter statisticMemberAdapter;
    private StatisticMemberPresenter statisticMemberPresenter;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, StatisticMemberActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_statistic_member;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        statisticMemberPresenter = new StatisticMemberPresenter(getActivity(), this);
        statisticMemberAdapter = new StatisticMemberAdapter(getActivity());
        statisticListView.setAdapter(statisticMemberAdapter);
        View topView = LayoutInflater.from(getActivity()).inflate(R.layout.view_member_statistic_top, null);
        statisticCountMemberTv = (TextView) topView.findViewById(R.id.statistic_count_member_tv);
        statisticCountVipTv = (TextView) topView.findViewById(R.id.statistic_count_vip_tv);
        keyWordEt = (EditText) topView.findViewById(R.id.key_word_et);
        statisticListView.addHeaderView(topView);

        if(null != GlobalDataCache.getInstance().getStatisticBean()){
            StatisticBean bean = GlobalDataCache.getInstance().getStatisticBean();
            statisticCountMemberTv.setText(bean.memberCount + "名");
            statisticCountVipTv.setText(bean.memberVipCount + "名");
        }

        statisticRefreshLayout.setLoadMore(true);
        statisticRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                statisticMemberPresenter.setKeyword(keyWordEt.getText().toString());
                statisticMemberPresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                statisticMemberPresenter.loadMore();
            }
        });

        keyWordEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    statisticMemberPresenter.setKeyword(keyWordEt.getText().toString());
                    statisticMemberPresenter.refresh();
                }
                 return false;
            }
        });

        statisticMemberPresenter.refreshWithLoading();
    }

    @Override
    public void showLoading() {
        statisticPageStatusLayout.showLoading();
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        statisticRefreshLayout.finishRefreshLoadMore();
        statisticRefreshLayout.finishRefresh();
        if(statisticMemberPresenter.isWithLoad()){
            statisticPageStatusLayout.showFailed(message, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    statisticMemberPresenter.refreshWithLoading();
                }
            });
        }
    }


    @Override
    public void onSuccessRefresh(ArrayList<MemberStatisticBean> result) {
        statisticRefreshLayout.finishRefresh();
        if(statisticMemberPresenter.isWithLoad()){
            statisticPageStatusLayout.showContent();
        }
        if(null == result ){
            result = new ArrayList<>();
        }
        statisticMemberAdapter.setList(result);

    }

    @Override
    public void onSuccessLoadModre(ArrayList<MemberStatisticBean> result) {
        statisticRefreshLayout.finishRefreshLoadMore();
        if(null == result ){
            result = new ArrayList<>();
        }
        statisticMemberAdapter.addList(result);

    }
}
