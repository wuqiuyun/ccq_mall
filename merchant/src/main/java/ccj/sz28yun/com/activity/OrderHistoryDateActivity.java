package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.OrderCCJAdapter;
import ccj.sz28yun.com.adapter.OrderConsumeBillAdapter;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.OrderCCJBean;
import ccj.sz28yun.com.bean.OrderConsumeBillBean;
import ccj.sz28yun.com.bean.OrderStatisticBean;
import ccj.sz28yun.com.fragment.OrderListCCJFragment;
import ccj.sz28yun.com.fragment.OrderListConsumFragment;
import ccj.sz28yun.com.presenter.OrderHistoryPresenter;
import ccj.sz28yun.com.widget.BottomLineRadioButton;
import per.sue.gear2.controll.GearDateViewController;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.date.DateUtils;

/**
 * Created by sue on 2016/12/13.
 */
public class OrderHistoryDateActivity extends CCJActivity  {


    @Bind(R.id.year_tv)
    TextView yearTv;
    @Bind(R.id.month_tv)
    TextView monthTv;
    @Bind(R.id.day_tv)
    TextView dayTv;


    @Bind(R.id.order_type_ccj_rb)
    BottomLineRadioButton orderTypeCcjRb;
    @Bind(R.id.order_type_consume_bill_rb)
    BottomLineRadioButton orderTypeConsumeBillRb;
    @Bind(R.id.order_type_rg)
    RadioGroup orderTypeRg;
    private long currentTime;
    private OrderListCCJFragment orderListCCJFragment;
    private OrderListConsumFragment orderListConsumFragment;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, OrderHistoryDateActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_order_history_date;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        currentTime = System.currentTimeMillis();
        yearTv.setText(DateUtils.getYear(new Date(currentTime)) + "");
        monthTv.setText(DateUtils.getMonth(new Date(currentTime)) + "");
        dayTv.setText(DateUtils.getDay(new Date(currentTime)) + "");


        orderTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.order_type_ccj_rb){
                    getSupportFragmentManager().beginTransaction().show(orderListCCJFragment).commit();
                    getSupportFragmentManager().beginTransaction().hide(orderListConsumFragment).commit();

                }else{
                    getSupportFragmentManager().beginTransaction().hide(orderListCCJFragment).commit();
                    getSupportFragmentManager().beginTransaction().show(orderListConsumFragment).commit();
                }

            }
        });
        orderListCCJFragment =  new OrderListCCJFragment();
        orderListConsumFragment = new OrderListConsumFragment();
        addFragment(R.id.content_fl,orderListCCJFragment , "ccj");
        addFragment(R.id.content_fl,  orderListConsumFragment, "consum");

        getSupportFragmentManager().beginTransaction().show(orderListCCJFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(orderListConsumFragment).commit();

    }


    @OnClick({R.id.year_tv, R.id.month_tv, R.id.day_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.year_tv:
                chooseYearTime();
                break;
            case R.id.month_tv:
                chooseMonthTime();
                break;
            case R.id.day_tv:
                chooseDayTime();
                break;

        }
    }


    /**
     * 选择年
     */
    private void chooseYearTime() {
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(), currentTime);
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.YEAR);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
                //.setTitle("选择年")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        currentTime = dateTime;
                        yearTv.setText(DateUtils.getYear(new Date(currentTime)) + "");

                        orderListCCJFragment.setDate(currentTime);
                        orderListConsumFragment.setDate(currentTime);

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
    private void chooseMonthTime() {
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(), currentTime);
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.MONTH);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
                //.setTitle("选择年")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        currentTime = dateTime;
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(dateTime);
                        monthTv.setText(DateUtils.getMonth(new Date(currentTime)) + "");
                        orderListCCJFragment.setDate(currentTime);
                        orderListConsumFragment.setDate(currentTime);

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
     */
    private void chooseDayTime() {
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(), currentTime);
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.DAY);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
                // .setTitle("选择年")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        currentTime = dateTime;

                        dayTv.setText(DateUtils.getDay(new Date(currentTime)) + "");
                        orderListCCJFragment.setDate(currentTime);
                        orderListConsumFragment.setDate(currentTime);

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
