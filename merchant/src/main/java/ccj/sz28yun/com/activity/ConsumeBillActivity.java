package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.ConsumeBillAdapter;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.ConsumeBillBean;
import ccj.sz28yun.com.bean.NormalStatisticsBean;
import ccj.sz28yun.com.presenter.ConsumeBillPresenter;
import per.sue.gear2.controll.GearDateViewController;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.utils.date.DateUtils;

/**
 * 消费买单
 * Created by sue on 2016/12/4.
 */
public class ConsumeBillActivity extends CCJActivity implements ConsumeBillPresenter.ConsumeBillView {


    @Bind(R.id.keyword_et)
    EditText keywordEt;
    @Bind(R.id.year_tv)
    TextView yearTv;
    @Bind(R.id.month_tv)
    TextView monthTv;
    @Bind(R.id.day_tv)
    TextView dayTv;
    @Bind(R.id.consume_count_tv)
    TextView consumeCountTv;
    @Bind(R.id.consume_total_tv)
    TextView consumeTotalTv;
    @Bind(R.id.consume_payment_tv)
    TextView consumePaymentTv;
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    ConsumeBillPresenter consumeBillPresenter;
    ConsumeBillAdapter consumeBillAdapter;

    private long currentTime;

    private int checkPosition;
    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, ConsumeBillActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_consume;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        currentTime = System.currentTimeMillis();
        consumeBillPresenter = new ConsumeBillPresenter(getActivity(), this);
        keywordEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    consumeBillPresenter.setKeyword(keywordEt.getText().toString());
                    consumeBillPresenter.refresh();

                }
                return false;
            }
        });
        setListView();
        consumeBillPresenter.refresh();

        refreshDataView();
    }

    private void refreshDataView(){
        yearTv.setText(DateUtils.getYear(new Date(currentTime)) + "年");
        monthTv.setText(DateUtils.getMonth(new Date(currentTime)) + "月");
        dayTv.setText(DateUtils.getDay(new Date(currentTime)) + "日");
    }

    private void setListView(){
        consumeBillAdapter = new ConsumeBillAdapter(getActivity());
        consumeBillAdapter.setConsumeBillAdapterListener(new ConsumeBillAdapter.ConsumeBillAdapterListener() {
            @Override
            public void onCheck(ConsumeBillBean bean, int position) {
                checkPosition = position;
                consumeBillPresenter.checkBill(bean.getOrderId());
            }
        });
        listView.setAdapter(consumeBillAdapter);
        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                consumeBillPresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                consumeBillPresenter.loadMore();
            }
        });
    }


    @Override
    public void onSuccessStatistic(ArrayList<NormalStatisticsBean> result) {
        consumeCountTv.setText(result.get(0).getNum() + "张");
        consumeTotalTv.setText(new StringBuilder("￥").append(result.get(1).getOrder_amount() + ""));
        consumePaymentTv.setText(new StringBuilder("￥").append(result.get(2).getPay_amount() + ""));
    }

    @Override
    public void onSuccessChecked() {
        ToastUtils.showError("验证成功", getApplication());

        consumeBillAdapter.notifyDataSetChanged();

    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        refreshLayout.finishRefresh();
        refreshLayout.finishRefreshLoadMore();
    }

    @Override
    public void onSuccessRefresh(ArrayList<ConsumeBillBean> result) {
        refreshLayout.finishRefresh();
        consumeBillAdapter.setList(result);
    }

    @Override
    public void onSuccessLoadModre(ArrayList<ConsumeBillBean> result) {
        refreshLayout.finishRefreshLoadMore();
        consumeBillAdapter.addList(result);
    }



    @OnClick({R.id.year_tv, R.id.month_tv, R.id.day_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.year_tv:
                chooseYearTime(view);
                break;
            case R.id.month_tv:
                chooseMonthTime(view);
                break;
            case R.id.day_tv:
                chooseDayTime(view);
                break;
            default:
                break;
        }
    }

    /**
     * 选择年
     * @param view
     */
    private void chooseYearTime(View view){
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(), currentTime);
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.YEAR);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
                .setTitle("选择年")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        currentTime = dateTime;
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(dateTime);
                        refreshDataView();
                        consumeBillPresenter.setDate(DateUtils.getDate(currentTime));
                        consumeBillPresenter.refresh();

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        gearCustomDialog.show();
    }

    /**
     * 选择月
     * @param view
     */
    private void chooseMonthTime(View view){
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(), currentTime);
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.MONTH);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
                .setTitle("选择年")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        currentTime = dateTime;
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(dateTime);
                        refreshDataView();
                        consumeBillPresenter.setDate(DateUtils.getDate(currentTime));
                        consumeBillPresenter.refresh();

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        gearCustomDialog.show();
    }

    /**
     * 选择日
     * @param view
     */
    private void chooseDayTime(View view){
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(), currentTime);
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.DAY);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
                .setTitle("选择年")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        currentTime = dateTime;
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(dateTime);
                        refreshDataView();
                        consumeBillPresenter.setDate(DateUtils.getDate(currentTime));
                        consumeBillPresenter.refresh();

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        gearCustomDialog.show();
    }
}
