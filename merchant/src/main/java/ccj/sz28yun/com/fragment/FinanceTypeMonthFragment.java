package ccj.sz28yun.com.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.activity.FinanceTypeActivity;
import ccj.sz28yun.com.activity.FinanceTypeDayActivity;
import ccj.sz28yun.com.adapter.FinanceTypeMonthAdapter;
import ccj.sz28yun.com.base.CCJFragment;
import ccj.sz28yun.com.bean.FinanceMonthBean;
import ccj.sz28yun.com.bean.FinanceStatisticBean;
import ccj.sz28yun.com.presenter.FinanceTypeMonthPresenter;
import per.sue.gear2.controll.GearDateViewController;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.date.DateUtils;

/**
 * 财务月统计
 * Created by sue on 2016/12/19.
 */
public class FinanceTypeMonthFragment extends CCJFragment implements FinanceTypeMonthPresenter.FinanceMonthView {

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

    private int type = 1;

    private FinanceTypeMonthAdapter financeMonthAdapter;
    private FinanceTypeMonthPresenter financeMonthPresenter;


    public static Bundle getBundle(int type){
        Bundle bundle = new Bundle();
        bundle.putInt("type", type );
        return bundle;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_finance_type_month;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        type = getArguments().getInt("type", 1);
        financeMonthPresenter = new FinanceTypeMonthPresenter(getActivity(), this);
        financeMonthPresenter.setType(type);
        initViews();
        financeMonthPresenter.getFinanceTypeMonth();
    }

    private void initViews(){
        Date date = new Date(financeMonthPresenter.getCurrentTime());
        yearTv.setText(DateUtils.getYear(date) + "年");
        monthTv.setText(DateUtils.getMonth(date) + "月");

        financeMonthAdapter = new FinanceTypeMonthAdapter(getActivity());
        listView.setAdapter(financeMonthAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FinanceMonthBean financeMonthBean = financeMonthAdapter.getItem(position);
                startActivity(FinanceTypeDayActivity.startIntent(getActivity(), type, financeMonthBean.getDate()));
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
                break;
        }
    }


    /**
     * 选择年
     */
    private void chooseYearTime(){
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(), financeMonthPresenter.getCurrentTime());
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.YEAR);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
               // .setTitle("选择年")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        yearTv.setText(DateUtils.getYear(new Date(dateTime))+ "年");
                        financeMonthPresenter.setCurrentTime(dateTime);
                        financeMonthPresenter.getFinanceTypeMonth();
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
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(),  financeMonthPresenter.getCurrentTime());
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.MONTH);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
               // .setTitle("选择年")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        monthTv.setText(DateUtils.getMonth(new Date(dateTime)) + "月");
                        financeMonthPresenter.setCurrentTime(dateTime);
                        financeMonthPresenter.getFinanceTypeMonth();
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

    @Override
    public void onSuccessStatistic(FinanceStatisticBean statisticBean) {
        if(getActivity() instanceof FinanceTypeActivity){
            ( (FinanceTypeActivity)getActivity()).setStatisticData(statisticBean);

        }
    }

    @Override
    public void onSuccessTotal(String sum) {
        totalTv.setText(String.format("合计: %s元", sum));
    }

    @Override
    public void onSuccess(ArrayList<FinanceMonthBean> result) {
        financeMonthAdapter.setList(result);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        financeMonthPresenter.destroy();
    }
}
