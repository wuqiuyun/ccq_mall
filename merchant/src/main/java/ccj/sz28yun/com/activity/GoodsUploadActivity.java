package ccj.sz28yun.com.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.ImagePublicAdapter;
import ccj.sz28yun.com.base.ActivityCode;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.CouponMealsImageBean;
import ccj.sz28yun.com.bean.GoodsCategoryBean;
import ccj.sz28yun.com.bean.GoodsParams;
import ccj.sz28yun.com.bean.GoodsSpecificationBean;
import ccj.sz28yun.com.bean.GoodsUploadImageBean;
import ccj.sz28yun.com.bean.GoodsUploadInfoResult;
import ccj.sz28yun.com.bean.ImageUploadResult;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.presenter.GoodsUploadPresenter;
import per.sue.gear2.annotation.Params;
import per.sue.gear2.bean.ImageCutBean;
import per.sue.gear2.controll.GearImageSelector;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.widget.NoScrollGridView;

/**
 * 商品上传
 * Created by sue on 2016/12/4.
 */
public class GoodsUploadActivity extends CCJActivity implements GoodsUploadPresenter.GoodsUploadView {


    private final int IMAGE_MAX_SIZE = 12;

    @Bind(R.id.name_et)
    EditText nameEt;
    @Bind(R.id.category_1_tv)
    TextView category1Tv;
    @Bind(R.id.category_2_tv)
    TextView category2Tv;
    @Bind(R.id.category_3_tv)
    TextView category3Tv;
    @Bind(R.id.price_original_et)
    EditText priceOriginalEt;
    @Bind(R.id.slaes_defult_et)
    EditText slaesDefultEt;
    @Bind(R.id.standard_tv)
    TextView standardTv;
    @Bind(R.id.pic_gv)
    NoScrollGridView picGv;

    private ImagePublicAdapter imagePublicAdapter;
    private ArrayList<Object> urls;
    private ArrayList<ImageUploadResult> oldimageUploadResultArrayList = new ArrayList<>();
    private ArrayList<ImageUploadResult> newimageUploadResultArrayList = new ArrayList<>();
    private ArrayList<ImageUploadResult> imageUploadResultArrayList = new ArrayList<>();
    ArrayList<GoodsSpecificationBean> goodsSpecificationBeanArrayList = new ArrayList<>();
    private GoodsParams goodsParams;
    private String goodsUnionId;

    private GearImageSelector gearImageSelector;
    GoodsUploadPresenter goodsUploadPresenter;
    GearCustomDialog gearCustomDialog;

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, GoodsUploadActivity.class);
        return intent;
    }

    public static Intent startIntent(Context context, String goodsUnionId) {
        Intent intent;
        intent = new Intent(context, GoodsUploadActivity.class);
        intent.putExtra("goodsUnionId", goodsUnionId);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_goods_upload;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        goodsUnionId = getIntent().getStringExtra("goodsUnionId");
        if (TextUtils.isEmpty(goodsUnionId)) {
            setBarTitle("商品上传");
            goodsParams = new GoodsParams(userBean.getToken(), userBean.getStoreId());
        } else {
            setBarTitle("商品修改");
            showLoading();
            goodsParams = new GoodsParams(userBean.getToken(), userBean.getStoreId());
        }

        initializeImageGridView();
        goodsUploadPresenter = new GoodsUploadPresenter(getActivity(), this);
        goodsUploadPresenter.getInitializeData(goodsUnionId);
    }

    private void initializeImageGridView() {
        gearImageSelector = new GearImageSelector(getActivity());
        imagePublicAdapter = new ImagePublicAdapter(getActivity());
        imagePublicAdapter.setImagesMaxCount(IMAGE_MAX_SIZE);
        picGv.setAdapter(imagePublicAdapter);
        urls = new ArrayList<>();
        urls.add(R.mipmap.ic_image_add);
        imagePublicAdapter.setList(urls);

        imagePublicAdapter.setImageOnClickListener(new ImagePublicAdapter.OnClickImageListener() {
            @Override
            public void onClickAddImage(View v) {
                if (verifyImagesCount()) {
                    gearImageSelector.setCut(false);
                    gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(GoodsUploadActivity.this, 270, 234);//设置裁剪范围
//                    gearImageSelector.imageCutBean = new ImageCutBean(270, 234);
                    gearImageSelector.showImageLoadPannel();
                }
            }

            @Override
            public void onClickDeleteImage(View v, int position) {
                urls.remove(position);
                if (position < oldimageUploadResultArrayList.size() ) {
                    oldimageUploadResultArrayList.remove(position);
                } else {
                    newimageUploadResultArrayList.remove(position - oldimageUploadResultArrayList.size());
                }
                imageUploadResultArrayList.remove(position);
                imagePublicAdapter.notifyDataSetChanged();
            }
        });
        gearImageSelector.setOnImgaeSelectListener(new GearImageSelector.OnImgaeSelectListener() {
            @Override
            public void onSuccessGetBitmap(File file, Bitmap bitmap) {
                showProgressDialog("上传图片");
                goodsUploadPresenter.uploadImage(file);
            }

            @Override
            public void onFailedGetBitmap(String msg) {
                ToastUtils.showError(msg, getApplicationContext());

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        gearImageSelector.onActivityResult(requestCode, resultCode, data);
        // super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.standard_tv, R.id.submit_btn, R.id.category_1_tv, R.id.category_2_tv, R.id.category_3_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.category_1_tv:
                showLoading();
                goodsUploadPresenter.getCategoryFirstLv();
                break;
            case R.id.category_2_tv:
                if (!TextUtils.isEmpty(goodsParams.goodsCategoryFirst)) {
                    showLoading();
                    goodsUploadPresenter.getCategorySecondLv(goodsParams.goodsCategoryFirst);
                } else {
                    ToastUtils.showError("请选择第一级分类", getApplication());
                }

                break;
            case R.id.category_3_tv:
                if (!TextUtils.isEmpty(goodsParams.goodsCategorySecond)) {
                    showLoading();
                    goodsUploadPresenter.getCategoryThirdLv(goodsParams.goodsCategorySecond);
                } else {
                    ToastUtils.showError("请选择第二级分类", getApplication());
                }
                break;

            case R.id.standard_tv:
                showSpecificationDialog();
                break;
            case R.id.submit_btn:
                if (verifyInput()) {
                    showLoading();
                    submit();
                }
                break;
            default:
                break;
        }
    }

    private boolean verifyImagesCount() {
        boolean canSubmit = true;
        if ((urls.size() - 1) >= IMAGE_MAX_SIZE) {
            canSubmit = false;
            ToastUtils.showShortMessage("抱歉，上传图片数量不能多于12张.", getActivity());
        }
        return canSubmit;
    }

    private boolean verifyInput() {
        boolean canSubmit = true;
        String name = nameEt.getText().toString();
        String originalPrice = priceOriginalEt.getText().toString();
        String saleCount = slaesDefultEt.getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameEt.requestFocus();
            nameEt.setError("请输入商品名称");
            canSubmit = false;
        } else if (TextUtils.isEmpty(originalPrice)) {
            priceOriginalEt.requestFocus();
            priceOriginalEt.setError("请输入商品原始价格.");
            canSubmit = false;
        } else if ("0".equals(originalPrice)) {
            priceOriginalEt.requestFocus();
            priceOriginalEt.setError("商品原始价格必须大于0.");
            canSubmit = false;
        } else if (TextUtils.isEmpty(goodsParams.goodsCategoryFirst)) {
            canSubmit = false;
            ToastUtils.showError("请选择分类.", getApplication());
        } else if (TextUtils.isEmpty(goodsParams.goodsStandard)) {
            canSubmit = false;
            ToastUtils.showError("请选择商品规格.", getApplication());
        } else if (TextUtils.isEmpty(saleCount)) {
            slaesDefultEt.requestFocus();
            slaesDefultEt.setError("请输入商品默认销量.");
            canSubmit = false;
        } else if (imageUploadResultArrayList.size() <= 0) {
            canSubmit = false;
            ToastUtils.showError("请至少选择一张图片.", getApplication());
        } else {
            goodsParams.goodsName = name;
            goodsParams.goodsOriginalPrice = Double.valueOf(originalPrice);
            goodsParams.goodsSaleCount = Integer.valueOf(saleCount);
//            StringBuilder ids = new StringBuilder("[");
            StringBuilder oldids = new StringBuilder("[");
            StringBuilder newids = new StringBuilder("[");
//            for(int i = 0; i < imageUploadResultArrayList.size(); i++ ){
//                ImageUploadResult result = imageUploadResultArrayList.get(i);
//                ids.append(result.getId());
//                if(i < imageUploadResultArrayList.size() -1 ){
//                    ids.append(",");
//                }
//            }
//            ids.append("]");
            if (oldimageUploadResultArrayList.size() > 0) {
                for (int i = 0; i < oldimageUploadResultArrayList.size(); i++) {
                    ImageUploadResult result = oldimageUploadResultArrayList.get(i);
                    oldids.append(result.getId());
                    if (i < oldimageUploadResultArrayList.size() - 1) {
                        oldids.append(",");
                    }
                }
            }
            if (newimageUploadResultArrayList.size() > 0) {
                for (int i = 0; i < newimageUploadResultArrayList.size(); i++) {
                    ImageUploadResult result = newimageUploadResultArrayList.get(i);
                    newids.append(result.getId());
                    if (i < newimageUploadResultArrayList.size() - 1) {
                        newids.append(",");
                    }
                }
            }
//            ids.append("]");
            oldids.append("]");
            newids.append("]");
//            String tmp = ids.toString();
            String oldtmp = oldids.toString();
            String newtmp = newids.toString();
            goodsParams.goodsImages = newtmp;
            goodsParams.originImages = oldtmp;
        }
        return canSubmit;
    }

    private void submit() {
        goodsUploadPresenter.submit(goodsParams);
    }

    @Override
    public void onError(int code, String message) {
        dissDialog();
        switch (code) {
            case 0:
                ToastUtils.showError("获取初始数据失败! 请退出重试.", getApplication());
                break;
            case -2:
                ToastUtils.showError("上传图片失败, 请重试.", getApplication());
                break;
            case -1:
                ToastUtils.showError("上传商品失败, 请联系开发者.", getApplication());
                break;

        }

    }

    @Override
    public void onSuccessUploadImage(ImageUploadResult imageUploadResult) {
        dissDialog();
        dismissProgressDialog();
        imageUploadResultArrayList.add(imageUploadResult);
        newimageUploadResultArrayList.add(imageUploadResult);
//        imageUploadResultArrayList.add(0,imageUploadResult);
//        urls.add(0, imageUploadResult.getUrl());
        urls.add(urls.size() - 1, imageUploadResult.getUrl());
        imagePublicAdapter.notifyDataSetChanged();
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
    public void onSuccessSpecification(ArrayList<GoodsSpecificationBean> list) {
        dissDialog();
        goodsSpecificationBeanArrayList = list;
    }

    @Override
    public void onSuccessGoodsUploadInfo(GoodsUploadInfoResult result) {
        dissDialog();
        goodsParams.goodsName = result.getGoodsName();
        goodsParams.goodsCategoryFirst = result.getGcId1();
        goodsParams.goodsCategorySecond = result.getGcId2();
        goodsParams.goodsCategoryThird = result.getGcId3();
        goodsParams.merchatId = result.getStoreId();
        goodsParams.goodsUnionId = result.getGoodsUnionId();
        goodsParams.goodsOriginalPrice = result.getGoodsCostprice();
        goodsParams.goodsSaleCount = result.getGoodsSalenum();
        goodsParams.goodsStandard = result.getSpecId();

        nameEt.setText(goodsParams.goodsName);
        category1Tv.setText(result.getGcName1());
        category2Tv.setText(result.getGcName2());
        category3Tv.setText(result.getGcName3());
        priceOriginalEt.setText(goodsParams.goodsOriginalPrice + "");
        slaesDefultEt.setText(goodsParams.goodsSaleCount + "");
        standardTv.setText(result.getSpecName());

        ArrayList<GoodsUploadImageBean> imageList = result.getGoodsUploadImageBean();
        if (imageList != null) {
            for (GoodsUploadImageBean bean : imageList) {
                ImageUploadResult imageUploadResult = new ImageUploadResult(bean.goods_image_id + "", bean.abs_goods_image);
                imageUploadResultArrayList.add(imageUploadResult);
                oldimageUploadResultArrayList.add(imageUploadResult);
                urls.add(urls.size() - 1, imageUploadResult.getUrl());
                imagePublicAdapter.notifyDataSetChanged();
            }
        }

        StringBuilder ids = new StringBuilder("[");
        for (int i = 0; i < result.getGoodsUploadImageBean().size(); i++) {
            GoodsUploadImageBean bean = result.getGoodsUploadImageBean().get(i);
            ids.append(bean.goods_image_id);
            if (i < imageUploadResultArrayList.size() - 1) {
                ids.append(",");
            }
        }
        ids.append("]");
        String tmp = ids.toString();
//        goodsParams.goodsImages = tmp;
        goodsParams.originImages = tmp;
    }

    @Override
    public void onSuccess(String result) {
        dissDialog();
        ToastUtils.showError("上传商品成功.", getApplication());
        finish();
    }

    /**
     * 选择规格
     */
    private void showSpecificationDialog() {
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .addListView(goodsSpecificationBeanArrayList, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;
                        GoodsSpecificationBean bean = goodsSpecificationBeanArrayList.get(position);
                        standardTv.setText(bean.getSpecName());
                        goodsParams.goodsStandard = bean.getSpecId();
                    }
                })
                .setTitle("请选择规格")
                .create();
        gearCustomDialog.show();
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
                        category2Tv.setText("");
                        category3Tv.setText("");
                        goodsParams.goodsCategoryFirst = bean.getGcId();
                        goodsParams.goodsCategorySecond = "";
                        goodsParams.goodsCategoryThird = "";
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
                        category3Tv.setText("");
                        goodsParams.goodsCategorySecond = bean.getGcId();
                        goodsParams.goodsCategoryThird = "";
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
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .addListView(list, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;
                        GoodsCategoryBean bean = list.get(position);
                        category3Tv.setText(bean.getGcName());
                        goodsParams.goodsCategoryThird = bean.getGcId();
                    }
                })
                .setTitle("请选择第三级分类")
                .create();
        gearCustomDialog.show();
    }

    // 加载中动画
    private Dialog loadingDialog;

    // 加载动画
    public void showLoading() {

        View view = LayoutInflater.from(GoodsUploadActivity.this).inflate(
                R.layout.loading, null);
        ImageView image = (ImageView) view.findViewById(R.id.iv_loading);
        Animation animation = AnimationUtils.loadAnimation(
                GoodsUploadActivity.this, R.anim.loading_anim);
        image.startAnimation(animation);
        loadingDialog = new Dialog(GoodsUploadActivity.this,
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
