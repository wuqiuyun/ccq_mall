package ccj.sz28yun.com.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
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
import ccj.sz28yun.com.activity.FinanceDepositDetailsActivity;
import ccj.sz28yun.com.adapter.FinanceTypeDepositsAdapter;
import ccj.sz28yun.com.adapter.FinanceTypeFreezeAdapter;
import ccj.sz28yun.com.base.CCJFragment;
import ccj.sz28yun.com.bean.FinanceDepositsBean;
import ccj.sz28yun.com.bean.FinanceFreezeBean;
import ccj.sz28yun.com.cache.GlobalDataCache;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.presenter.FinanceTypeFreezePresenter;
import per.sue.gear2.controll.GearDateViewController;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.DoubleUtil;
import per.sue.gear2.utils.date.DateUtils;

/**
 * 财务冻结统计
 * Created by sue on 2016/12/19.
 */
public class FinanceTypeFreezeFragment extends CCJFragment implements FinanceTypeFreezePresenter.FinanceTypeFreezeView {

    @Bind(R.id.year_tv)
    TextView yearTv;
    @Bind(R.id.month_tv)
    TextView monthTv;
    @Bind(R.id.status_tv)
    TextView statusTv;
    @Bind(R.id.total_tv)
    TextView totalTv;
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout materialRefreshLayout;
    GearCustomDialog gearCustomDialog;

    private int type = 1;

    private FinanceTypeFreezeAdapter financeTypeFreezeAdapter;
    private FinanceTypeFreezePresenter financeTypeFreezePresenter;


    public static Bundle getBundle(int type){
        Bundle bundle = new Bundle();
        bundle.putInt("type", type );
        return bundle;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_finance_type_freeze;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        type = getArguments().getInt("type", 1);
        financeTypeFreezePresenter = new FinanceTypeFreezePresenter(getActivity(), this);
        financeTypeFreezePresenter.setType(type);
        initViews();

        financeTypeFreezePresenter.refresh();
    }

    private void initViews(){
        Date date = new Date(financeTypeFreezePresenter.getCurrentTime());
        yearTv.setText(DateUtils.getYear(date) + "年");
        monthTv.setText(DateUtils.getMonth(date) + "月");

        financeTypeFreezeAdapter = new FinanceTypeFreezeAdapter(getActivity());
        listView.setAdapter(financeTypeFreezeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FinanceFreezeBean bean = financeTypeFreezeAdapter.getItem(position);
                startActivity(FinanceDepositDetailsActivity.startIntent(getActivity(), bean.getId(), "冻结详情"));
            }
        });
        materialRefreshLayout.setLoadMore(true);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                financeTypeFreezePresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                financeTypeFreezePresenter.loadMore();
            }
        });

    }



    @OnClick({R.id.year_tv, R.id.month_tv, R.id.status_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.year_tv:
                chooseYearTime();
                break;
            case R.id.month_tv:
                chooseMonthTime();
                break;
            case R.id.status_tv:
                chooseStatus();
                break;
        }
    }


    /**
     * 选择年
     */
    private void chooseYearTime(){
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(), financeTypeFreezePresenter.getCurrentTime());
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.YEAR);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
                //.setTitle("选择年")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        yearTv.setText(DateUtils.getYear(new Date(dateTime))+ "年");
                        financeTypeFreezePresenter.setCurrentTime(dateTime);
                        financeTypeFreezePresenter.refresh();
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
     */
    private void chooseMonthTime(){
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(),  financeTypeFreezePresenter.getCurrentTime());
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.MONTH);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
               // .setTitle("选择年")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(dateTime);
                        monthTv.setText(DateUtils.getMonth(new Date(dateTime)) + "月");
                        financeTypeFreezePresenter.setCurrentTime(dateTime);
                        financeTypeFreezePresenter.refresh();
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

    private void chooseStatus(){
        ArrayList<String> data = new ArrayList<>();
        data.add("全部");
        data.add("成功");
        data.add("失败");
        data.add("审核中");
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .addListView(data,   new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;
                        statusTv.setText(data.get(position));
                        financeTypeFreezePresenter.setStatus(position);
                        financeTypeFreezePresenter.refresh();
                    }
                })
                .setTitle("请选择规格")
                .create();
        gearCustomDialog.show();
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        materialRefreshLayout.finishRefreshLoadMore();
        materialRefreshLayout.finishRefresh();
    }

    @Override
    public void onSuccessRefresh(ArrayList<FinanceFreezeBean> result) {
        materialRefreshLayout.finishRefresh();
        financeTypeFreezeAdapter.setList(result);

        double sum = GlobalDataCache.getInstance().count;
        totalTv.setText(String.format("合计: %s元", DoubleUtil.formatPrice(sum)));
    }

    @Override
    public void onSuccessLoadModre(ArrayList<FinanceFreezeBean> result) {
        materialRefreshLayout.finishRefreshLoadMore();
        financeTypeFreezeAdapter.addList(result);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        financeTypeFreezePresenter.destroy();
    }
}
