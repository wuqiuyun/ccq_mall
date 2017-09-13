package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * 测试饼状图
 * Created by sue on 2017/1/2.
 */
public class PieChartActivity extends CCJActivity {
    @Bind(R.id.type_order_piechart)
    PieChartView typeOrderPiechart;




    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, PieChartActivity.class);
        return intent;
    }


    @Override
    public int getLayoutResId() {
        return R.layout.activity_pie_chart;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        int numValues = 6;
        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
            values.add(sliceValue);
        }
        PieChartData data = new PieChartData(values);
        data.setHasLabels(true); //// 是否显示数据
        data.setHasLabelsOnlyForSelected(false); // 是否选中显示数据，一般为false
        data.setHasLabelsOutside(false); //// 数据是否显示在外面
        data.setHasCenterCircle(true); // 是否含有中圈，显示下面的内容这个必须为true
        data.setValueLabelBackgroundEnabled(false);
        data.setCenterText1("92.14%");
        data.setCenterText2("未做占比");
        typeOrderPiechart.setPieChartData(data);
    }


}
