package ccj.sz28yun.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.AbstractWheelView;
import antistatic.spinnerwheel.OnWheelChangedListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.CouponMealsItemAdapter;
import ccj.sz28yun.com.adapter.GoodsCategoryAdapter;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.CouponGoodsBean;
import ccj.sz28yun.com.bean.CouponMealsGoodsBean;
import ccj.sz28yun.com.bean.CouponMealsItemBean;
import ccj.sz28yun.com.bean.GoodsCategoryBean;
import ccj.sz28yun.com.presenter.CouponGoodsPresenter;
import ccj.sz28yun.com.presenter.CouponMealsItemPresenter;
import per.sue.gear2.controll.GearArrayWheelController;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2017/1/4.
 */
public class CouponMealsItemActivity extends CCJActivity implements CouponGoodsPresenter.CouponGoodsView, CouponMealsItemPresenter.CouponMealsItemView {

    @Bind(R.id.category_1_tv)
    TextView category1Tv;
    @Bind(R.id.category_2_tv)
    TextView category2Tv;
    @Bind(R.id.category_3_tv)
    TextView category3Tv;
    @Bind(R.id.count_tv)
    TextView countTv;
    @Bind(R.id.choose_tv)
    TextView chooseTv;


    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;
    private CouponGoodsPresenter couponGoodsPresenter;
    private CouponMealsItemPresenter couponMealsItemPresenter;
    private CouponMealsItemAdapter couponMealsItemAdapter;

    private String firstCategoryId;
    private String secondCategoryId;
    private String thirdCategoryId;

    private GoodsCategoryBean thirdGoodsCategoryBean;

    private ArrayList<GoodsCategoryBean> categoryBeanArrayList;

    private Map<String, CouponMealsGoodsBean> cacheDataMap = new HashMap<>();//缓存数据
    private int currentTotal;


    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, CouponMealsItemActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_coupon_meals_item;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        getHeadBarView().addRightTextItem("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<CouponMealsItemBean> list = new ArrayList<CouponMealsItemBean>();
                CouponMealsItemBean couponMealsItemBean;
                for (String key : cacheDataMap.keySet()) {
                    CouponMealsGoodsBean couponMealsGoodsBean = cacheDataMap.get(key);
                    couponMealsItemBean = new CouponMealsItemBean();
                    couponMealsItemBean.chooseNum = couponMealsGoodsBean.choose;
                    couponMealsItemBean.sum = couponMealsGoodsBean.total;
                    couponMealsItemBean.title = String.format("%s%s选%s", couponMealsGoodsBean.goodsCategoryBean.getGcName(), couponMealsGoodsBean.total, couponMealsGoodsBean.choose);
                    ArrayList<CouponGoodsBean> goodslist = couponMealsGoodsBean.couponGoodsArrayList;
                    StringBuilder contentBuilder = new StringBuilder();
                    for (CouponGoodsBean bean : goodslist) {
                        contentBuilder.append(bean.getGoodsUnionId()).append("|").append(bean.countChoose).append(",");
                    }
                    couponMealsItemBean.content = contentBuilder.toString();
                    couponMealsItemBean.content = couponMealsItemBean.content.substring(0, couponMealsItemBean.content.lastIndexOf(","));
                    list.add(couponMealsItemBean);
                }
                Intent intent = new Intent();
                intent.putExtra("data", list);
                setResult(Activity.RESULT_OK, intent);

                finish();
            }
        });

        couponGoodsPresenter = new CouponGoodsPresenter(getActivity(), this);
        couponMealsItemPresenter = new CouponMealsItemPresenter(getActivity(), this);

        couponMealsItemAdapter = new CouponMealsItemAdapter(getActivity());
        couponMealsItemAdapter.setCouponMealsItemAdapterListener(
                total -> {
                    currentTotal = total;
                    countTv.setText(total + "");
                    if (total == 0) {
                        if (cacheDataMap.containsKey(thirdCategoryId)) {
                            cacheDataMap.remove(thirdCategoryId);
                        }
                    } else {
                        cacheDataMap.put(thirdCategoryId, new CouponMealsGoodsBean(thirdGoodsCategoryBean, couponMealsItemAdapter.getSelectItems(), currentTotal, countChoose));
                    }
                }
        );
        listView.setAdapter(couponMealsItemAdapter);

        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
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
    }

    @OnClick({R.id.category_1_tv, R.id.category_2_tv, R.id.category_3_tv, R.id.choose_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.category_1_tv:
                couponMealsItemPresenter.getCategoryFirstLv();
                break;
            case R.id.category_2_tv:
                if (!TextUtils.isEmpty(firstCategoryId)) {
                    couponMealsItemPresenter.getCategorySecondLv(firstCategoryId);
                } else {
                    ToastUtils.showError("请选择第一级分类", getApplicationContext());
                }

                break;
            case R.id.category_3_tv:
                if (!TextUtils.isEmpty(secondCategoryId)) {
                    showThird(thirdlist);
//                    couponMealsItemPresenter.getCategoryThirdLv(secondCategoryId);
                } else {
                    ToastUtils.showError("请选择第二级分类", getApplicationContext());
                }

                break;
         /*   case R.id.count_tv:
                selectCountTotal();
                break;*/
            case R.id.choose_tv:
                selectCountChoose();
                break;
        }
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        refreshLayout.finishRefresh();
        refreshLayout.finishRefreshLoadMore();
    }

    @Override
    public void onSuccessRefresh(ArrayList<CouponGoodsBean> result) {
        refreshLayout.finishRefresh();
        couponMealsItemAdapter.setList(result);

        if (cacheDataMap.containsKey(thirdCategoryId) && null != cacheDataMap.get(thirdCategoryId)) {
            CouponMealsGoodsBean couponMealsGoodsBean = cacheDataMap.get(thirdCategoryId);
            ArrayList<CouponGoodsBean> list = couponMealsGoodsBean.couponGoodsArrayList;
            if (null != list) {
                for (CouponGoodsBean goodsBean : list) {
                    for (CouponGoodsBean goodsBean1 : result) {
                        if (goodsBean.getGoodsUnionId().equals(goodsBean1.getGoodsUnionId())) {
                            goodsBean1.countChoose = goodsBean.countChoose;
                        }
                    }
                }
            }

        }
    }

    @Override
    public void onSuccessLoadModre(ArrayList<CouponGoodsBean> result) {
        refreshLayout.finishRefreshLoadMore();
        couponMealsItemAdapter.addList(result);

        if (cacheDataMap.containsKey(thirdCategoryId) && null != cacheDataMap.get(thirdCategoryId)) {
            CouponMealsGoodsBean couponMealsGoodsBean = cacheDataMap.get(thirdCategoryId);
            ArrayList<CouponGoodsBean> list = couponMealsGoodsBean.couponGoodsArrayList;
            if (null != list) {
                for (CouponGoodsBean goodsBean : list) {
                    for (CouponGoodsBean goodsBean1 : result) {
                        if (goodsBean.getGoodsUnionId().equals(goodsBean1.getGoodsUnionId())) {
                            goodsBean1.countChoose = goodsBean.countChoose;
                        }
                    }
                }
            }

        }
    }

    @Override
    public void onSuccessCategoryFirst(ArrayList<GoodsCategoryBean> list) {
        selectCategoryFirst(list);
    }

    @Override
    public void onSuccessCategorySecond(ArrayList<GoodsCategoryBean> list) {
        selectCategorySecond(list);
    }

    private ArrayList<GoodsCategoryBean> thirdlist;

    @Override
    public void onSuccessCategoryThird(ArrayList<GoodsCategoryBean> list) {
        selectCategoryThird(list);
        thirdlist = list;
    }

    @Override
    public void onSuccess(String result) {

    }

    GearCustomDialog gearCustomDialog;

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
                        firstCategoryId = bean.getGcId();
                    }
                })
                .setTitle("请选择第一级分类")
                .create();
        gearCustomDialog.show();
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
                        secondCategoryId = bean.getGcId();
                        couponMealsItemPresenter.getCategoryThirdLv(secondCategoryId);
                    }
                })
                .setTitle("请选择第二级分类")
                .create();
        gearCustomDialog.show();
    }


    /**
     * 第三级分类
     */
    private void selectCategoryThird(ArrayList<GoodsCategoryBean> list) {
        GoodsCategoryBean bean = list.get(0);
        thirdGoodsCategoryBean = bean;
        category3Tv.setText(bean.getGcName());
        thirdCategoryId = bean.getGcId();
        couponGoodsPresenter.setCategoryId(thirdCategoryId);
        couponGoodsPresenter.refresh();
    }

    private void showThird(ArrayList<GoodsCategoryBean> list) {
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .addListView(list, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;
                        GoodsCategoryBean bean = list.get(position);
                        thirdGoodsCategoryBean = bean;
                        category3Tv.setText(bean.getGcName());
                        thirdCategoryId = bean.getGcId();
                        couponGoodsPresenter.setCategoryId(thirdCategoryId);
                        couponGoodsPresenter.refresh();

                        refreshTotalAndChooseView(thirdCategoryId);
                    }

                })
                .setTitle("请选择第3级分类")
                .create();
        gearCustomDialog.show();
    }

    private void refreshTotalAndChooseView(String thirdCategoryId) {
        CouponMealsGoodsBean couponMealsGoodsBean = cacheDataMap.get(thirdCategoryId);
        if (null != couponMealsGoodsBean) {
            currentTotal = couponMealsGoodsBean.total;
            countChoose = couponMealsGoodsBean.choose;
            countTv.setText(currentTotal + "");
            chooseTv.setText(countChoose + "");
        } else {
            countTv.setText("0");
            chooseTv.setText("0");
        }

    }

    int countChoose;

    private void selectCountChoose() {
        GearArrayWheelController gearArrayWheelController = new GearArrayWheelController(getActivity());
        gearArrayWheelController.add((AbstractWheelView) LayoutInflater.from(getActivity()).inflate(R.layout.wheel_view_horizontal, null), couponMealsItemAdapter.getCountChoose(), 0, 10, new OnWheelChangedListener() {
            @Override
            public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
                countChoose = newValue;
            }
        });
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearArrayWheelController.createView())
                .setTitle("请选择可选数量")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (currentTotal < countChoose) {
                                ToastUtils.showShortMessage("选择数量不能大于可选数量", getApplicationContext());
                        } else {
                            gearCustomDialog.dismiss();
                            gearCustomDialog = null;
                            chooseTv.setText(countChoose + "");
                            if (cacheDataMap.containsKey(thirdCategoryId) && null != cacheDataMap.get(thirdCategoryId)) {
                                CouponMealsGoodsBean couponMealsGoodsBean = cacheDataMap.get(thirdCategoryId);
                                couponMealsGoodsBean.choose = countChoose;
                            }
                            couponMealsItemAdapter.setCountChoose(countChoose);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;
                    }
                })
                .create();

        gearCustomDialog.show();
    }

}
