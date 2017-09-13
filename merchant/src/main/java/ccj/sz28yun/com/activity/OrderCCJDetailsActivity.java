package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.OrderCCJBean;
import ccj.sz28yun.com.presenter.OrderVerifyPresenter;
import per.sue.gear2.controll.GearImageLoad;
import per.sue.gear2.utils.DoubleUtil;
import per.sue.gear2.utils.ToastUtils;

/**
 * 订单详情
 * Created by sue on 2016/12/15.
 */
public class OrderCCJDetailsActivity extends CCJActivity implements OrderVerifyPresenter.OrderVerifyView {

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

    OrderCCJBean bean;
    @Bind(R.id.goods_name_tv)
    TextView goodsNameTv;
    @Bind(R.id.shou_pay_tv)
    TextView shouPayTv;
    @Bind(R.id.code_tv)
    TextView codeTv;

    private OrderVerifyPresenter orderVerifyPresenter;

    public static Intent startIntent(Context context, OrderCCJBean bean) {
        Intent intent = new Intent(context, OrderCCJDetailsActivity.class);
        intent.putExtra("OrderCCJBean", bean);
        return intent;
    }
    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, OrderCCJDetailsActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_order_ccj_datails;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        bean = (OrderCCJBean) getIntent().getSerializableExtra("OrderCCJBean");

        orderNumTv.setText(bean.getOrderSn());
        userNameTv.setText(bean.userName );
        consmuePayTv.setText(bean.markPrice + "元");
        shouPayTv.setText(bean.getOrderAmount() + "元");
        codeTv.setText(bean.checkNumber);
        percentTv.setText(bean.getDiscount() + "折");
        timeTv.setText(bean.getAddTime());
        adressTv.setText(bean.getAddress());
        goodsNameTv.setText(bean.getGoodsName());
        GearImageLoad.getSingleton(getActivity()).load(bean.getImage(), imageIv);
        verifyStatusTv.setText(bean.getOrderState());
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
