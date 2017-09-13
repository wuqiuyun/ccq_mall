package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.EvaluateGoodsBean;
import ccj.sz28yun.com.bean.EvaluateMerchantBean;
import ccj.sz28yun.com.presenter.EvaluateReplyPresenter;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2016/12/6.
 */
public class EvaluateReplyActivity extends CCJActivity implements EvaluateReplyPresenter.EvaluateReplyView {


    @Bind(R.id.content_et)
    EditText contentEt;

    EvaluateReplyPresenter evaluateReplyPresenter;
    EvaluateGoodsBean evaluateGoodsBean;
    EvaluateMerchantBean evaluateMerchantBean;


    public static Intent startIntent(Context context, EvaluateGoodsBean bean) {
        Intent intent = new Intent(context, EvaluateReplyActivity.class);
        intent.putExtra("EvaluateGoodsBean", bean);
        return intent;
    }

    public static Intent startIntent(Context context, EvaluateMerchantBean bean) {
        Intent intent = new Intent(context, EvaluateReplyActivity.class);
        intent.putExtra("EvaluateMerchantBean", bean);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_evaluate_reply;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        evaluateReplyPresenter = new EvaluateReplyPresenter(getActivity(), this);
        evaluateGoodsBean = (EvaluateGoodsBean)getIntent().getSerializableExtra("EvaluateGoodsBean");
        evaluateMerchantBean = (EvaluateMerchantBean)getIntent().getSerializableExtra("EvaluateMerchantBean");

    }



    @OnClick(R.id.submit_btn)
    public void onClick() {
        if(!TextUtils.isEmpty(contentEt.getText().toString())){
            if(null != evaluateMerchantBean){
                evaluateReplyPresenter.replyMerchant(evaluateMerchantBean.getSevalId(), contentEt.getText().toString());
            }else{
                evaluateReplyPresenter.replyGoods(evaluateGoodsBean.getGevalId(), contentEt.getText().toString());
            }

        }else{
            contentEt.requestFocus();
            contentEt.setError("请填写回复内容!");
        }

    }

    @Override
    public void onSuccessReplyGoods() {
        ToastUtils.showError("回复成功", getApplication());
        finish();

    }

    @Override
    public void onSuccessReplyMerchant() {
        ToastUtils.showError("回复成功", getApplication());
        finish();
    }

    @Override
    public void onSuccess(String result) {

    }
}
