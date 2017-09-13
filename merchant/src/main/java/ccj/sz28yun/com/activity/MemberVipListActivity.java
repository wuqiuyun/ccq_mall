package ccj.sz28yun.com.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.MemberVipListAdapter;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.MemberVipListBean;
import ccj.sz28yun.com.presenter.MemberVipListPresenter;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.widget.PageStatusLayout;

/**
 * 会员列表
 * Created by meihuali on 2017/6/13.
 */

public class MemberVipListActivity extends CCJActivity implements MemberVipListPresenter.MemberVipListResultView, TextView.OnEditorActionListener {
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;
    @Bind(R.id.type_vip)
    RadioGroup typeVipRg;
    @Bind(R.id.search_text_et)
    EditText searchTextEt;

    private MemberVipListPresenter memberVipListPresenter;
    private MemberVipListAdapter memberVipListAdapter;
    //    private MemberVipViewHolder memberVipViewHolder;
    private String firstType = "all";
    private String secondType = "today";
    private Dialog timeChoosedialog;
    private String year;
    private String mon;
    private String day;

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, MemberVipListActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_member_viplist;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        year =   calendar.get(Calendar.YEAR) + "";
        mon =   calendar.get(Calendar.MONTH) + 1 +"";
        day =   calendar.get(Calendar.DATE) +"";
        memberVipListPresenter = new MemberVipListPresenter(getActivity(), this);

        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                memberVipListPresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                memberVipListPresenter.loadMore();
            }
        });

        memberVipListAdapter = new MemberVipListAdapter(getActivity());
        listView.setAdapter(memberVipListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MemberVipListBean memberVipListBean = memberVipListAdapter.getItem(position);
                startActivity(MemberVipDetailsActivity.startIntent(getActivity(),memberVipListBean.getMemberId()));
            }
        });

        typeVipRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch(checkedId) {
                    case R.id.all_huiyuan://全部
                        showLoading();
                        memberVipListPresenter.setType(0);
                        memberVipListPresenter.refresh();
                        break;
                    case R.id.vip_huiyuan://VIP
                        showLoading();
                        memberVipListPresenter.setType(1);
                        memberVipListPresenter.refresh();
                        break;
                    default:
                        break;
                }
            }
        });
        searchTextEt.setOnEditorActionListener(this);
        showLoading();
        memberVipListPresenter.refresh();
    }

    @OnClick({ R.id.rb_time_all, R.id.rb_month, R.id.rb_other })
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rb_time_all:
                showLoading();
                memberVipListPresenter.setSearchText("");
                memberVipListPresenter.setDate("","","");
                memberVipListPresenter.refresh();
                break;
            case R.id.rb_month:
                showLoading();
                memberVipListPresenter.setSearchText("");
                memberVipListPresenter.setDate(year,mon,"");
                memberVipListPresenter.refresh();
                break;
            case R.id.rb_other:
                ShowTimeChooseDialog();
                break;
            default:
                break;
        }
    }

    private void ShowTimeChooseDialog() {
        View view = LayoutInflater.from(MemberVipListActivity.this)
                .inflate(R.layout.dialog_vip_time_choose, null);
        EditText year_et = (EditText) view.findViewById(R.id.year_et);
        EditText mon_et = (EditText) view.findViewById(R.id.mon_et);
        EditText day_et = (EditText) view.findViewById(R.id.day_et);
        TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        TextView tv_dialog_cancel = (TextView) view
                .findViewById(R.id.tv_dialog_cancel);
        year_et.setText(year);
        mon_et.setText(mon);
        day_et.setText(day);
        tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeChoosedialog.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String year = year_et.getText().toString().trim();
                String mon = mon_et.getText().toString().trim();
                String day = day_et.getText().toString().trim();
                if(canShowTime(year,mon,day)){
                    timeChoosedialog.dismiss();
                    showLoading();
                    memberVipListPresenter.setSearchText("");
                    memberVipListPresenter.setDate(year,mon,day);
                    memberVipListPresenter.refresh();
                }
            }
        });
        timeChoosedialog = new Dialog(MemberVipListActivity.this,
                R.style.mDialogStyle);
        timeChoosedialog.setContentView(view);
        timeChoosedialog.setCanceledOnTouchOutside(false);
        timeChoosedialog.show();
    }

    public boolean canShowTime(String year,String mon,String day){
        if (TextUtils.isEmpty(year)){
            ToastUtils.showError("请填写年",getActivity());
            return false;
        }else if (!TextUtils.isEmpty(mon)){
            if (TextUtils.isEmpty(year)){
                ToastUtils.showError("填写月时请填写年",getActivity());
                return false;
            }
        }else if (!TextUtils.isEmpty(day)) {
            if (TextUtils.isEmpty(year) || TextUtils.isEmpty(mon)) {
                ToastUtils.showError("填写日时请填写年和月", getActivity());
                return false;
            }
        }else if (!TextUtils.isEmpty(mon)) {
            if (Integer.parseInt(mon) > 12) {
                ToastUtils.showError("填写月格式不正确", getActivity());
                return false;
            }
        }else if (!TextUtils.isEmpty(day)) {
            if (Integer.parseInt(day) > 31) {
                ToastUtils.showError("填写日格式不正确", getActivity());
                return false;
            }
        }
            return true;

    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        dismissProgressDialog();
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onSuccessRefresh(ArrayList<MemberVipListBean> result) {
        dismissProgressDialog();
        refreshLayout.finishRefresh();
        memberVipListAdapter.setList(result);
    }

    @Override
    public void onSuccessLoadModre(ArrayList<MemberVipListBean> result) {
        dismissProgressDialog();
        refreshLayout.finishRefreshLoadMore();
        memberVipListAdapter.addList(result);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        memberVipListPresenter.destroy();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent e) {
       /* 判断是否是“下一步”键 */
        if (actionId == EditorInfo.IME_ACTION_DONE
                || actionId == EditorInfo.IME_ACTION_GO
                || actionId == EditorInfo.IME_ACTION_SEARCH) {
			/* 隐藏软键盘 */
            InputMethodManager imm = (InputMethodManager) v.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            }
            String text = searchTextEt.getText().toString().trim();
            if (TextUtils.isEmpty(text)) {
                ToastUtils.showError("搜索文本不能为空",getApplication());
            } else {
                memberVipListPresenter.setSearchText(text);
                memberVipListPresenter.setDate("","","");
                memberVipListPresenter.refresh();
            }
            return true;
        }
        return false;
    }
}
