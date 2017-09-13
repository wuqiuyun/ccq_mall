package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.OperatingDataOrderBean;
import ccj.sz28yun.com.bean.OperatingMerchantFlowBean;
import ccj.sz28yun.com.presenter.OperatingDataPresenter;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;
import per.sue.gear2.controll.GearDateViewController;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.date.DateStyle;
import per.sue.gear2.utils.date.DateUtils;
import per.sue.gear2.widget.PageStatusLayout;

/**
 * 数据经营
 * Created by sue on 2016/12/7.
 */
public class OperatingDataActivity extends CCJActivity implements OperatingDataPresenter.OperatingDataView {

    @Bind(R.id.order_ccj_count_tv)
    TextView orderCcjCountTv;
    @Bind(R.id.order_tc_count_tv)
    TextView orderTcCountTv;
    @Bind(R.id.order_consmue_count_tv)
    TextView orderConsmueCountTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public enum OrderType {
        ORDER_CCQ("餐餐抢订单数量", R.color.app_blue_light), ORDER_PACKAGE("套餐订单数量", R.color.app_orange),
        ORDER_PAY("先消费后买单订单数量", R.color.app_green);
        public String name;
        public int colorRes;

        OrderType(String name, int color) {
            this.name = name;
            this.colorRes = color;
        }

    }


    @Bind(R.id.year_tv)
    TextView yearTv;
    @Bind(R.id.month_tv)
    TextView monthTv;
    @Bind(R.id.type_order)
    RadioButton typeOrder;
    @Bind(R.id.type_merchant)
    RadioButton typeMerchant;
    @Bind(R.id.type_rg)
    RadioGroup typeRg;
    @Bind(R.id.type_order_piechart)
    PieChartView typeOrderPiechart;
    @Bind(R.id.type_marchant_linechart)
    LineChartView typeMarchantLinechart;

    @Bind(R.id.type_order_pagestatuse)
    PageStatusLayout orderPageStatusLayout;
    @Bind(R.id.type_marchant_pagestatuse)
    PageStatusLayout marchantPageStatusLayout;


    private OperatingDataPresenter operatingDataPresenter;

    private long currentTime;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, OperatingDataActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }


    @Override
    public int getLayoutResId() {
        return R.layout.activity_operating_data;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        currentTime = System.currentTimeMillis();

        yearTv.setText(DateUtils.getYear(new Date(currentTime)) + "");
        monthTv.setText(DateUtils.getMonth(new Date(currentTime)) + "");
        operatingDataPresenter = new OperatingDataPresenter(getActivity(), this);
        orderPageStatusLayout.showLoading();
        marchantPageStatusLayout.showLoading();
        operatingDataPresenter.getMercgantFlowStatisticData();
        operatingDataPresenter.getOrderStatisticData();
        typeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.type_order) {
                    orderPageStatusLayout.setVisibility(View.VISIBLE);
                    marchantPageStatusLayout.setVisibility(View.GONE);
                } else {
                    orderPageStatusLayout.setVisibility(View.GONE);
                    marchantPageStatusLayout.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    @OnClick({R.id.year_tv, R.id.month_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.year_tv:
                chooseYearTime();
                break;
            case R.id.month_tv:
                chooseMonthTime();
                break;
        }
    }


    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        if (code == -1) {
            orderPageStatusLayout.showEmpty();
        }
        if (code == -2) {
            marchantPageStatusLayout.showEmpty();
        }

    }

    @Override
    public void onSuccessMerchantFlow(ArrayList<OperatingMerchantFlowBean> list) {

        if(null != list && list.size() > 0){
            marchantPageStatusLayout.showContent();
            bindLinChart(list);
        }else{
            marchantPageStatusLayout.showEmpty();
        }

    }

    @Override
    public void onSuccess(OperatingDataOrderBean result) {
        bindPieChart(result);
    }


    private void bindPieChart(OperatingDataOrderBean result) {
        orderPageStatusLayout.showContent();
        List<SliceValue> values = new ArrayList<>();
        PieChartData data = new PieChartData();
        values.add(new SliceValue(result.getCcqOrder(), getResources().getColor(OrderType.ORDER_CCQ.colorRes), 8));
        values.add(new SliceValue(result.getPackageOrder(), getResources().getColor(OrderType.ORDER_PACKAGE.colorRes), 8));
        values.add(new SliceValue(result.getPayOrder(), getResources().getColor(OrderType.ORDER_PAY.colorRes), 8));
        data.setValues(values);
        data.setHasLabels(true); //// 是否显示数据
        data.setHasLabelsOnlyForSelected(false); // 是否选中显示数据，一般为false
        data.setHasLabelsOutside(true); //// 数据是否显示在外面
        data.setHasCenterCircle(true); // 是否含有中圈，显示下面的内容这个必须为true
        // 设置不显示数据的背景颜色
        data.setValueLabelBackgroundEnabled(false);
        data.setCenterText1("订单分析");
        data.setCenterText1FontSize(18);
        typeOrderPiechart.setPieChartData(data);

        orderCcjCountTv.setText(result.getCcqOrder() + "");
        orderConsmueCountTv.setText(result.getPayOrder() + "");
        orderTcCountTv.setText(result.getPackageOrder() + "");
    }

    /**
     * http://blog.csdn.net/xygy8860/article/details/50394194 参考代码
     *
     * @param result
     */
    private void bindLinChart(ArrayList<OperatingMerchantFlowBean> result) {
        marchantPageStatusLayout.showContent();

        List<Line> lines = new ArrayList<Line>();
        List<AxisValue> axisValueList = new ArrayList<>();
        OperatingMerchantFlowBean operatingMerchantFlowBean;
        List<PointValue> values = new ArrayList<PointValue>();
        axisValueList.clear();
        for (int j = 0; j < result.size(); j++) {
            operatingMerchantFlowBean = result.get(j);
            Float val = (float) operatingMerchantFlowBean.getSum();
            if (null != val) {
                PointValue pointValue = new PointValue(j, val);
                pointValue.setLabel(operatingMerchantFlowBean.getStattime() + "/" + val);
                pointValue.setTarget(20, 10);
                values.add(pointValue);
            }
        }

        Line line = new Line(values);
        line.setColor(getResources().getColor(R.color.app_blue_light));
        //line.setShape(true);
        // line.setCubic(true);
        //line.setFilled(true);

        line.setHasLabels(true);
        line.setHasLabelsOnlyForSelected(true);
        line.setHasLines(true);
        line.setHasPoints(true);
        //line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
        line.setPointColor(getResources().getColor(R.color.app_blue_light));
        lines.add(line);


        for (int j = 0; j < result.size(); j++) {
            if (null != result.get(j))
                axisValueList.add(new AxisValue(j).setLabel(DateUtils.getDate(DateUtils.StringToDate(result.get(j).getStattime()), DateStyle.YYYY_MM_DD)));
        }
        LineChartData data = new LineChartData(lines);
        Axis axisX = new Axis(axisValueList);
        axisX.setMaxLabelChars(8);
        axisX.setHasLines(true);
        axisX.setHasTiltedLabels(true);
        axisX.setName("日期");
        axisX.setTextColor(getResources().getColor(R.color.app_grey));

        Axis axisY = new Axis().setHasLines(true);

        axisY.setName("流量数目");
        axisY.setTextColor(getResources().getColor(R.color.app_grey));
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        typeMarchantLinechart.setLineChartData(data);

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

                        operatingDataPresenter.setYear(DateUtils.getYear(new Date(currentTime)) + "");
                        operatingDataPresenter.setMonth(DateUtils.getMonth(new Date(currentTime)) + "");
                        operatingDataPresenter.getMercgantFlowStatisticData();
                        operatingDataPresenter.getOrderStatisticData();

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
                        monthTv.setText(DateUtils.getMonth(new Date(currentTime)) + "");
                        operatingDataPresenter.setYear(DateUtils.getYear(new Date(currentTime)) + "");
                        operatingDataPresenter.setMonth(DateUtils.getMonth(new Date(currentTime)) + "");
                        operatingDataPresenter.getMercgantFlowStatisticData();
                        operatingDataPresenter.getOrderStatisticData();

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
