package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
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

/**
 * Created by sue on 2016/12/13.
 */
public class OrderHistoryActivity extends CCJActivity  {


    @Bind(R.id.order_count_tv)
    TextView orderCountTv;
    @Bind(R.id.total_tv)
    TextView totalTv;
    @Bind(R.id.order_ccj)
    RadioButton orderCcjRB;
    @Bind(R.id.order_consmue)
    RadioButton orderConsmueRB;
    @Bind(R.id.order_data)
    RadioButton orderDataRB;
    @Bind(R.id.type_rg)
    RadioGroup typeRg;


    private OrderCCJAdapter orderCCJAdapter;
    private OrderConsumeBillAdapter orderConsumeBillAdapter;


    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, OrderHistoryActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_order_history;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        orderCCJAdapter = new OrderCCJAdapter(getActivity());
        orderConsumeBillAdapter = new OrderConsumeBillAdapter(getActivity());
        typeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.order_ccj){
                    replaceFragment(R.id.content_fl, new OrderListCCJFragment());
                }else if(checkedId == R.id.order_consmue){
                    replaceFragment(R.id.content_fl, new OrderListConsumFragment());
                }

            }
        });

        orderDataRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(OrderHistoryDateActivity.startIntent(getActivity()));
            }
        });
        replaceFragment(R.id.content_fl, new OrderListCCJFragment());
    }



    public void onSuccessStatistic(ArrayList<OrderStatisticBean> list) {
        if(null != list && list.size() > 1){
            orderCountTv.setText(list.get(0).getNum() + "");
            totalTv.setText(list.get(1).getOrderamount() + "");
        }

    }


}
