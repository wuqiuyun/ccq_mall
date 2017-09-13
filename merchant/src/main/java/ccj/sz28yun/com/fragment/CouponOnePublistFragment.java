package ccj.sz28yun.com.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.Bind;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.activity.CouponMealsPublishActivity;
import ccj.sz28yun.com.adapter.CouponMealsAdapter;
import ccj.sz28yun.com.base.CCJFragment;
import ccj.sz28yun.com.bean.CouponMealsBean;
import ccj.sz28yun.com.presenter.CouponOnePublistPresenter;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2016/12/19.
 */
public class CouponOnePublistFragment extends CCJFragment implements CouponOnePublistPresenter.CouponDanGoodsView {


    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    private CouponMealsAdapter couponMealsAdapter;

    private CouponOnePublistPresenter couponOnePublistPresenter;
    private String type;

    public static Bundle getBundle(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        return bundle;

    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_coupon_meals_list;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        couponOnePublistPresenter = new CouponOnePublistPresenter(getActivity(),this);

//        couponMealsListPresenter = new CouponMealsListPresenter(getActivity(), this);
        type = getArguments().getString("type");
        couponOnePublistPresenter.setType(type);
        couponMealsAdapter = new CouponMealsAdapter(getActivity());
        listView.setAdapter(couponMealsAdapter);

        couponMealsAdapter.setCouponMealsAdapterListener(new CouponMealsAdapter.CouponMealsAdapterListener() {
            @Override
            public void onDelete(CouponMealsBean bean) {
                showLoading();
                couponOnePublistPresenter.setGoods_id(bean.goodsId + "");
                couponOnePublistPresenter.delete();
            }

            @Override
            public void onXiajia(CouponMealsBean bean) {
                showLoading();
                couponOnePublistPresenter.setGoods_id(bean.goodsId + "");
                couponOnePublistPresenter.xiajia();
            }

            @Override
            public void onEdit(CouponMealsBean bean) {
                startActivity(CouponMealsPublishActivity.startIntent(getActivity(), bean.goodsId + ""));
//                startActivityForResult(CouponMealsPublishActivity.startIntent(getActivity(), bean.goodsId + ""),200);
            }

            @Override
            public void onLookJujueReason(CouponMealsBean bean) {
                {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_lookjujue, null);
                    TextView tv_url = (TextView) view.findViewById(R.id.tv_url);
                    TextView tv_dialog_cancel = (TextView) view.findViewById(R.id.tv_dialog_cancel);
                    TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
                    tv_ok.setVisibility(View.GONE);
                    tv_url.setText(bean.goodsverifyremark);
                    tv_dialog_cancel.setText("知道了");

                    Dialog dialog = new Dialog(getActivity(), R.style.mDialogStyle);
                    dialog.setContentView(view);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onEditKucun(CouponMealsBean bean) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_editkucun, null);
                TextView tv_pro_name = (TextView) view.findViewById(R.id.tv_pro_name);
                TextView tv_yuan_kc = (TextView) view.findViewById(R.id.tv_yuan_kc);
                EditText et_xz_kc = (EditText) view.findViewById(R.id.et_xz_kc);
                TextView tv_finnal_kc = (TextView) view.findViewById(R.id.tv_finnal_kc);
                TextView baocun_btn = (TextView) view.findViewById(R.id.baocun_btn);
                ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);
                tv_pro_name.setText(bean.goodsName);
                tv_yuan_kc.setText(bean.goodsStorage +"");
                tv_finnal_kc.setText(bean.goodsStorage +"");

                Dialog dialog = new Dialog(getActivity(), R.style.mDialogStyle);
                dialog.setContentView(view);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                et_xz_kc.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String string = et_xz_kc.getText().toString().trim();
                        if (!TextUtils.isEmpty(string) && !"-".equals(string)  && !"+".equals(string)) {
                            int finnal = Integer.parseInt(string) + Integer.parseInt(tv_yuan_kc.getText().toString().trim());
                            if (finnal >= 0){
                                tv_finnal_kc.setText(finnal + "");
                            }else{
                                et_xz_kc.requestFocus();
                                et_xz_kc.setError("负数值不能超过原有库存");
                            }
                        }else{

                            tv_finnal_kc.setText(tv_yuan_kc.getText().toString().trim());
                        }
                    }
                });
                baocun_btn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(et_xz_kc.getText().toString().trim())){
                            et_xz_kc.requestFocus();
                            et_xz_kc.setError("输入框不能为空");
                        }else{
                            couponOnePublistPresenter.setGoods_id(bean.goodsId +"");
                            couponOnePublistPresenter.setKc_num(tv_finnal_kc.getText().toString().trim());
                            couponOnePublistPresenter.editKC();
                            dialog.dismiss();
                        }
                    }
                });
                iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                showLoading();
                couponOnePublistPresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                couponOnePublistPresenter.loadMore();
            }
        });
        couponOnePublistPresenter.refresh();
    }


    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        dissDialog();
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
    }


    @Override
    public void onSuccessRefresh(ArrayList<CouponMealsBean> result) {
        dissDialog();
        refreshLayout.finishRefresh();
        couponMealsAdapter.setList(result);
    }

    @Override
    public void onSuccessLoadModre(ArrayList<CouponMealsBean> result) {
        dissDialog();
        refreshLayout.finishRefreshLoadMore();
        couponMealsAdapter.addList(result);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        couponOnePublistPresenter.destroy();
    }

    @Override
    public void onSuccessDeleteORXiajia(String result) {
        Toast.makeText(getActivity(), "操作成功", Toast.LENGTH_SHORT).show();
        showLoading();
        couponOnePublistPresenter.refresh();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        showLoading();
//        couponOnePublistPresenter.refresh();
//    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading();
        couponOnePublistPresenter.refresh();
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
