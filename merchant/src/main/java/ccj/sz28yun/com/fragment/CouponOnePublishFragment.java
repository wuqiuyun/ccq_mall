package ccj.sz28yun.com.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.CouponGoodsAdapter;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.base.CCJFragment;
import ccj.sz28yun.com.bean.CouponGoodsBean;
import ccj.sz28yun.com.bean.GoodsCategoryBean;
import ccj.sz28yun.com.presenter.CouponGoodsPresenter;
import ccj.sz28yun.com.presenter.CouponOnePublishPresenter;
import ccj.sz28yun.com.presenter.GoodsCategoryListPresenter;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.DoubleUtil;
import per.sue.gear2.utils.ToastUtils;

/**
 * 单品券发布
 * Created by sue on 2016/12/19.
 */
public class CouponOnePublishFragment extends CCJFragment implements CouponOnePublishPresenter.CouponOnePublishView, CouponGoodsPresenter.CouponGoodsView {


    @Bind(R.id.keyword_et)
    EditText keywordEt;
    @Bind(R.id.grid_view)
    GridView gridView;
    @Bind(R.id.js_tv)
    TextView jsTv;
    @Bind(R.id.discount_percent_et)
    EditText discountPercentEt;
    @Bind(R.id.discount_count_et)
    EditText discountCountEt;
    @Bind(R.id.submit_btn)
    Button submitBtn;

    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout materialRefreshLayout;

    private CouponGoodsAdapter couponGoodsAdapter;
    private CouponOnePublishPresenter couponOnePublishPresenter;
    private CouponGoodsPresenter couponGoodsPresenter;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, CouponOnePublishFragment.class);
        return intent;
    }



    @Override
    public int getLayoutResId() {
        return R.layout.fragment_coupon_one_publish;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        keywordEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    couponGoodsPresenter.setNameKey(keywordEt.getText().toString());
                    couponGoodsPresenter.refresh();
                }
                return true;
            }
        });


        couponGoodsAdapter = new CouponGoodsAdapter(getActivity());
        gridView.setAdapter(couponGoodsAdapter);
        couponOnePublishPresenter = new CouponOnePublishPresenter(getActivity(), this);
        couponGoodsPresenter = new CouponGoodsPresenter(getActivity(), this);

        materialRefreshLayout.setLoadMore(true);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                couponGoodsPresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                couponGoodsPresenter.loadMore();
            }
        });


        discountPercentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(null != couponGoodsAdapter.getLastSelectItem() && !TextUtils.isEmpty(discountPercentEt.getText()) && DoubleUtil.isDouble(discountPercentEt.getText().toString())){
                    CouponGoodsBean bean = couponGoodsAdapter.getLastSelectItem();
                    double originalPrice = Double.valueOf( discountPercentEt.getText().toString());
                    if(bean.getGoodsCostprice() >= originalPrice){
                        double offPrice = DoubleUtil.sub(bean.getGoodsCostprice(), originalPrice)   ;
                        jsTv.setText(String.format("%s元", DoubleUtil.formatPrice(offPrice) ));
                    }else{

                        discountPercentEt.requestFocus();
                        discountPercentEt.setError("结算折扣不能大于原价");
                    }
                }

            }
        });

        showLoading();
        couponGoodsPresenter.refresh();

    }

    @OnClick({ R.id.submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                if(verifyInput()){
                    showLoading();
                    submit();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        dissDialog();
        materialRefreshLayout.finishRefreshLoadMore();
        materialRefreshLayout.finishRefresh();
    }


    @Override
    public void onSuccess(String result) {
        dissDialog();
        ToastUtils.showError("提交成功", getActivity().getApplication());
        getActivity().finish();
    }

    @Override
    public void onSuccessRefresh(ArrayList<CouponGoodsBean> result) {
        dissDialog();
        materialRefreshLayout.finishRefresh();
        couponGoodsAdapter.setList(result);
    }

    @Override
    public void onSuccessLoadModre(ArrayList<CouponGoodsBean> result) {
        dissDialog();
        materialRefreshLayout.finishRefreshLoadMore();
        couponGoodsAdapter.addList(result);
    }

    private boolean verifyInput() {
        boolean canSubmit = true;
        String ids = couponGoodsAdapter.getSelectListIdArrayInJson();
        String discountPercent = discountPercentEt.getText().toString();
        String discountCount = discountCountEt.getText().toString();
        if(ids.length() <= 2){
            ToastUtils.showError("请选择商品",  getActivity().getApplication());
            canSubmit = false;
        }else if(TextUtils.isEmpty(discountPercent)){
            discountPercentEt.requestFocus();
            discountPercentEt.setError("结算折扣不能为空");
            canSubmit = false;
        }else if(!DoubleUtil.isDouble(discountPercent) ){
            discountPercentEt.requestFocus();
            discountPercentEt.setError("结算折扣只能是纯数字");
            canSubmit = false;
        }else if(!DoubleUtil.isDouble(discountCount) ){
            discountCountEt.requestFocus();
            discountCountEt.setError("折扣券数量只能是纯数字");
            canSubmit = false;
        }else if(Double.valueOf(discountCount) < 10){
            discountCountEt.requestFocus();
            discountCountEt.setError("折扣券数量不能小于10");
            canSubmit = false;
        }

        return canSubmit;
    }

    private void submit(){
        String ids = couponGoodsAdapter.getSelectListIdArrayInJson();
        ids = ids.replace("\"", "");

        if(couponGoodsAdapter.isSingleModel()){
            ids = ids.replace("[", "");
            ids = ids.replace("]", "");
        }

        String discountPercent = discountPercentEt.getText().toString();
        String discountCount = discountCountEt.getText().toString();
        couponOnePublishPresenter.submit(ids, discountPercent, discountCount);
    }

    // 加载中动画
    private Dialog loadingDialog;

    // 加载动画
    public void showLoading() {

        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.loading, null);
        ImageView image = (ImageView) view.findViewById(R.id.iv_loading);
        Animation animation = AnimationUtils.loadAnimation(
                getActivity(), R.anim.loading_anim);
        image.startAnimation(animation);
        loadingDialog = new Dialog(getActivity(),
                R.style.mDialogStyle);
        loadingDialog.setContentView(view);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

    // 关闭加载dialog
    private void dissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

}
