package ccj.sz28yun.com.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class CouponDoublePublishFragment extends CCJFragment implements CouponOnePublishPresenter.CouponOnePublishView, CouponGoodsPresenter.CouponGoodsView, GoodsCategoryListPresenter.GoodsCategoryListView {


    @Bind(R.id.select_goods_category_ll)
    LinearLayout selectGoodsCategoryLl;
    @Bind(R.id.grid_view)
    GridView gridView;

    @Bind(R.id.discount_percent_et)
    EditText discountPercentEt;
    @Bind(R.id.discount_count_et)
    EditText discountCountEt;
    @Bind(R.id.submit_btn)
    Button submitBtn;

    @Bind(R.id.category_1_tv)
    TextView category1Tv;
    @Bind(R.id.category_2_tv)
    TextView category2Tv;
    @Bind(R.id.category_3_tv)
    TextView category3Tv;

    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout materialRefreshLayout;

    private CouponGoodsAdapter couponGoodsAdapter;
    private CouponOnePublishPresenter couponOnePublishPresenter;
    private CouponGoodsPresenter couponGoodsPresenter;

    private GoodsCategoryListPresenter goodsCategoryListPresenter;

    private String firstCategoryId;
    private String secondCategoryId;
    private String thirdCategoryId;



    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, CouponDoublePublishFragment.class);
        return intent;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_coupon_double_publish;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        couponGoodsAdapter = new CouponGoodsAdapter(getActivity());
        couponGoodsAdapter.setSingleModel(false);
        gridView.setAdapter(couponGoodsAdapter);
        couponOnePublishPresenter = new CouponOnePublishPresenter(getActivity(), this);
        couponGoodsPresenter = new CouponGoodsPresenter(getActivity(), this);
        goodsCategoryListPresenter = new GoodsCategoryListPresenter(getActivity(), this);
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
        showLoading();
        couponGoodsPresenter.refresh();
    }

    @OnClick({R.id.category_1_tv, R.id.category_2_tv, R.id.category_3_tv, R.id.submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.category_1_tv:
                showLoading();
               goodsCategoryListPresenter.getCategoryFirstLv();
                break;
            case R.id.category_2_tv:
                if (!TextUtils.isEmpty(category1Tv.getText().toString().trim())) {
                    showLoading();
                    goodsCategoryListPresenter.getCategorySecondLv(firstCategoryId);
                } else {
                    ToastUtils.showError("请选择第一级分类", getActivity());
                }
                break;
            case R.id.category_3_tv:
                if (!TextUtils.isEmpty(category2Tv.getText().toString().trim())) {
                    showLoading();
                    goodsCategoryListPresenter.getCategoryThirdLv(secondCategoryId);
                } else {
                    ToastUtils.showError("请选择第二级分类", getActivity());
                }
                break;
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
        ToastUtils.showError("提交成功",  getActivity().getApplication());
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
        }else if(Double.valueOf(discountPercent) > 100){
            discountPercentEt.requestFocus();
            discountPercentEt.setError("结算折扣不能大于100");
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


    GearCustomDialog gearCustomDialog;
    private void showDescribeDialog(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_coupon_one_publish_describe, null);
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(view)
                .setUseDefult(false)
                .setTitle("说明")
                .create();
        gearCustomDialog.show();

    }


    @Override
    public void onSuccessCategoryFirst(ArrayList<GoodsCategoryBean> list) {
        dissDialog();
        selectCategoryFirst("第一级分类", 1, list);

    }

    @Override
    public void onSuccessCategorySecond(ArrayList<GoodsCategoryBean> list) {
        dissDialog();
        selectCategoryFirst("第二级分类", 2, list);
    }

    @Override
    public void onSuccessCategoryThird(ArrayList<GoodsCategoryBean> list) {
        dissDialog();
        selectCategoryFirst("第三级分类", 3, list);
    }

    private void selectCategoryFirst(String title, final int type, ArrayList<GoodsCategoryBean> list){
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .addListView(list,   new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;
                        GoodsCategoryBean bean = list.get(position);
                        switch(type){
                            case 1:
                                category1Tv.setText(bean.getGcName());
                                firstCategoryId = bean.getGcId();
                                break;
                            case 2:
                                category2Tv.setText(bean.getGcName());
                                secondCategoryId = bean.getGcId();
                                break;
                            case 3:
                                showLoading();
                                category3Tv.setText(bean.getGcName());
                                thirdCategoryId = bean.getGcId();
                                couponGoodsPresenter.setCategoryId(thirdCategoryId);
                                couponGoodsPresenter.refresh();
                                break;

                        }
                    }
                })
                .setTitle("请选择" + title)
                .create();
        gearCustomDialog.show();
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
