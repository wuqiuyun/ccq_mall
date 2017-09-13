package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.OrderConsumeBillBean;
import ccj.sz28yun.com.presenter.OrderVerifyPresenter;
import per.sue.gear2.controll.GearImageLoad;
import per.sue.gear2.utils.DoubleUtil;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.utils.date.DateStyle;
import per.sue.gear2.utils.date.DateUtils;

/**
 * 订单详情
 * Created by sue on 2016/12/15.
 */
public class OrderConsumDetailsActivity extends CCJActivity implements OrderVerifyPresenter.OrderVerifyView {

    @Bind(R.id.order_num_tv)
    TextView orderNumTv;
    @Bind(R.id.user_name_tv)
    TextView userNameTv;
    @Bind(R.id.consmue_pay_tv)
    TextView consmuePayTv;
    @Bind(R.id.percent_tv)
    TextView percentTv;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.adress_tv)
    TextView adressTv;
    @Bind(R.id.image_iv)
    ImageView imageIv;
    @Bind(R.id.verify_status_tv)
    TextView verifyStatusTv;

    OrderConsumeBillBean bean;
    private OrderVerifyPresenter orderVerifyPresenter;

    public static Intent startIntent(Context context, OrderConsumeBillBean bean) {
        Intent intent = new Intent(context, OrderConsumDetailsActivity.class);
        intent.putExtra("OrderConsumeBillBean", bean);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_order_consum_datails;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        bean = (OrderConsumeBillBean)getIntent().getSerializableExtra("OrderConsumeBillBean");

        orderNumTv.setText(bean.getOrderSn());
        userNameTv.setText(bean.userName + "");
        consmuePayTv.setText(String.format("￥%s", DoubleUtil.formatPrice(bean.getGoodsAmount())));
        percentTv.setText(String.format("￥%s",  DoubleUtil.formatPrice( bean.getOrderAmount())));
        timeTv.setText(bean.getAddTime()
        );
        adressTv.setText(bean.getAddress());
        verifyStatusTv.setText(bean.getOrderState());

        //GearImageLoad.getSingleton(getActivity()).load(bean. imageIv);
        orderVerifyPresenter = new OrderVerifyPresenter(getActivity(), this);
        if("验证".equals(bean.getOrderState())){
            verifyStatusTv.setOnClickListener(
                    view -> {
                        orderVerifyPresenter.verifyCode(bean.getOrderId());
                    }
            );

        }
    }


    @Override
    public void onSuccessVerify(String result) {
        ToastUtils.showShortMessage("验证成功", getApplicationContext());
        finish();

    }


}
