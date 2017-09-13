package ccj.sz28yun.com.activity;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.GoodsUploadListAdapter;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.GoodsCategoryBean;
import ccj.sz28yun.com.bean.GoodsParams;
import ccj.sz28yun.com.bean.GoodsUploadResult;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.presenter.GoodsUploadListPresenter;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by meihuali on 2017/6/13.
 */

public class GoodsUploadListActivity extends CCJActivity implements GoodsUploadListPresenter.GoodsUploadView {
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;
    @Bind(R.id.category_1_ll)
    LinearLayout category1Ll;
    @Bind(R.id.category_1_tv)
    TextView category1Tv;
    @Bind(R.id.category_2_ll)
    LinearLayout category2Ll;
    @Bind(R.id.category_2_tv)
    TextView category2Tv;
    @Bind(R.id.category_3_ll)
    LinearLayout category3Ll;
    @Bind(R.id.category_3_tv)
    TextView category3Tv;
    @Bind(R.id.clean_all_tv)
    TextView cleanAllTv;

    private String gc_id_1 = "";
    private String gc_id_2 = "";
    private String gc_id_3 = "";
    private GoodsParams goodsParams;
    GearCustomDialog gearCustomDialog;
    private Dialog deletedialog;
    private String sctype;

    private GoodsUploadListAdapter goodsUploadListAdapter;
    private GoodsUploadListPresenter goodsUploadListPresenter;

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, GoodsUploadListActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_goodsupload_list;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        goodsParams = new GoodsParams(userBean.getToken(), userBean.getStoreId());

        getHeadBarView().addRightTextItem("新增", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(GoodsUploadActivity.startIntent(getActivity()));
            }
        });

        goodsUploadListPresenter = new GoodsUploadListPresenter(getActivity(), this);
        goodsUploadListAdapter = new GoodsUploadListAdapter(this);
        listView.setAdapter(goodsUploadListAdapter);

        goodsUploadListAdapter.setCouponMealsAdapterListener(new GoodsUploadListAdapter.GoodsUploadListAdapterListener() {

            @Override
            public void onDelete(GoodsUploadResult bean) {
                sctype = "one";
                showBlueDialog(bean.getGoodsUnionId());
            }

            @Override
            public void onEdit(GoodsUploadResult bean) {
                startActivity(GoodsUploadActivity.startIntent(getActivity(), bean.getGoodsUnionId()));
            }
        });

        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                showLoading();
                goodsUploadListPresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                goodsUploadListPresenter.loadMore();
            }
        });
        goodsUploadListPresenter.refresh();

    }

    @OnClick({R.id.category_1_ll, R.id.category_2_ll, R.id.category_3_ll, R.id.clean_all_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.category_1_ll:
                showLoading();
                goodsUploadListPresenter.getCategoryFirstLv();
                break;
            case R.id.category_2_ll:
                if (!TextUtils.isEmpty(goodsParams.goodsCategoryFirst)) {
                    showLoading();
                    goodsUploadListPresenter.getCategorySecondLv(goodsParams.goodsCategoryFirst);
                } else {
                    ToastUtils.showError("请选择第一级分类", getApplication());
                }

                break;
            case R.id.category_3_ll:
                if (!TextUtils.isEmpty(goodsParams.goodsCategorySecond)) {
                    showLoading();
                    goodsUploadListPresenter.getCategoryThirdLv(goodsParams.goodsCategorySecond);
                } else {
                    ToastUtils.showError("请选择第二级分类", getApplication());
                }
                break;
            case R.id.clean_all_tv:
                sctype = "all";
                showBlueDialog("");
                break;
            default:
                break;
        }
    }

    private void showBlueDialog(String goodsUnionId) {
        View view = LayoutInflater.from(GoodsUploadListActivity.this)
                .inflate(R.layout.dialog_blue_moban, null);
        TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        TextView tv_dialog_cancel = (TextView) view
                .findViewById(R.id.tv_dialog_cancel);
        TextView dialog_text = (TextView) view
                .findViewById(R.id.dialog_text);
        if ("all".equals(sctype)) {
            dialog_text.setText("只能删除清空状态为未上架的商品。不包含套餐！");
            tv_dialog_cancel.setText("在考虑一下");
            tv_ok.setText("我下狠心了");
        } else {
            dialog_text.setText("哎呦，老板。删除可是找不回来的呀！");
        }
        tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletedialog.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("all".equals(sctype)) {
                    showLoading();
                    goodsUploadListPresenter.alldelete();
                } else {
                    showLoading();
                    goodsUploadListPresenter.delete(goodsUnionId);
                }
                deletedialog.dismiss();
            }
        });
        deletedialog = new Dialog(GoodsUploadListActivity.this,
                R.style.mDialogStyle);
        deletedialog.setContentView(view);
        deletedialog.setCanceledOnTouchOutside(false);
        deletedialog.show();
    }


    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        dissDialog();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        refreshLayout.finishRefreshLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onSuccessDeleteOREdit(String result) {
        Toast.makeText(getActivity(), "操作成功", Toast.LENGTH_SHORT).show();
        goodsUploadListPresenter.refresh();
    }

    @Override
    public void onSuccessCategoryFirst(ArrayList<GoodsCategoryBean> list) {
        dissDialog();
        selectCategoryFirst(list);
    }

    @Override
    public void onSuccessCategorySecond(ArrayList<GoodsCategoryBean> list) {
        dissDialog();
        selectCategorySecond(list);
    }

    @Override
    public void onSuccessCategoryThird(ArrayList<GoodsCategoryBean> list) {
        dissDialog();
        selectCategoryThird(list);
    }

    @Override
    public void onSuccessRefresh(ArrayList<GoodsUploadResult> result) {
        dissDialog();
        refreshLayout.finishRefresh();
        goodsUploadListAdapter.setList(result);
    }

    @Override
    public void onSuccessLoadModre(ArrayList<GoodsUploadResult> result) {
        dissDialog();
        refreshLayout.finishRefreshLoadMore();
        goodsUploadListAdapter.addList(result);
    }

    /**
     * 第一级分类
     */
    private void selectCategoryFirst(ArrayList<GoodsCategoryBean> list) {
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .addListView(list, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;
                        GoodsCategoryBean bean = list.get(position);
                        category1Tv.setText(bean.getGcName());
                        goodsParams.goodsCategoryFirst = bean.getGcId();
                        showLoading();
                        goodsUploadListPresenter.setGcId_1(bean.getGcId());
                        goodsUploadListPresenter.setGcId_2("");
                        category2Tv.setText("请选择");
                        goodsUploadListPresenter.setGcId_3("");
                        category3Tv.setText("请选择");
                        goodsUploadListPresenter.refresh();
                    }
                })
                .setTitle("请选择第一级分类")
                .create();
        gearCustomDialog.show();
        gearCustomDialog.setCanceledOnTouchOutside(true);
    }

    /**
     * 第二级分类
     */
    private void selectCategorySecond(ArrayList<GoodsCategoryBean> list) {
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .addListView(list, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;
                        GoodsCategoryBean bean = list.get(position);
                        category2Tv.setText(bean.getGcName());
                        goodsParams.goodsCategorySecond = bean.getGcId();
                        showLoading();
                        goodsUploadListPresenter.setGcId_2(bean.getGcId());
                        goodsUploadListPresenter.setGcId_3("");
                        category3Tv.setText("请选择");
                        goodsUploadListPresenter.refresh();
                    }
                })
                .setTitle("请选择第二级分类")
                .create();
        gearCustomDialog.show();
        gearCustomDialog.setCanceledOnTouchOutside(true);
    }

    /**
     * 第三级分类
     */
    private void selectCategoryThird(ArrayList<GoodsCategoryBean> list) {
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .addListView(list, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;
                        GoodsCategoryBean bean = list.get(position);
                        category3Tv.setText(bean.getGcName());
                        goodsParams.goodsCategoryThird = bean.getGcId();
                        showLoading();
                        goodsUploadListPresenter.setGcId_3(bean.getGcId());
                        goodsUploadListPresenter.refresh();
                    }
                })
                .setTitle("请选择第三级分类")
                .create();
        gearCustomDialog.show();
        gearCustomDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading();
        goodsUploadListPresenter.refresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        goodsUploadListPresenter.destroy();
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
