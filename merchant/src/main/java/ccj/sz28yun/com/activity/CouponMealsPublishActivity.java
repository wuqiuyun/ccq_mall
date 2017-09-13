package ccj.sz28yun.com.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.CouponChainAdapter;
import ccj.sz28yun.com.adapter.ImagePublicAdapter;
import ccj.sz28yun.com.base.ActivityCode;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.CouponChainBean;
import ccj.sz28yun.com.bean.CouponMealsImageBean;
import ccj.sz28yun.com.bean.CouponMealsInfoResult;
import ccj.sz28yun.com.bean.CouponMealsItemBean;
import ccj.sz28yun.com.bean.CouponMealsParams;
import ccj.sz28yun.com.bean.GoodsSpecificationBean;
import ccj.sz28yun.com.bean.ImageUploadResult;
import ccj.sz28yun.com.presenter.CouponMealsPublishPresenter;
import per.sue.gear2.bean.ImageCutBean;
import per.sue.gear2.controll.GearDateViewController;
import per.sue.gear2.controll.GearImageSelector;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.DoubleUtil;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.utils.date.DateStyle;
import per.sue.gear2.utils.date.DateUtils;
import per.sue.gear2.widget.NoScrollGridView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by sue on 2017/1/4.
 */
public class CouponMealsPublishActivity extends CCJActivity implements CouponMealsPublishPresenter.CouponMealsPublishView, TextWatcher {

    @Bind(R.id.coupon_name_et)
    EditText couponNameEt;
    @Bind(R.id.is_recommend_cb)
    CheckBox isRecommendCb;
    @Bind(R.id.price_store_et)
    EditText priceStoreEt;
    @Bind(R.id.price_ccj_et)
    EditText priceCcjEt;
    @Bind(R.id.price_discount_tv)
    TextView priceDiscountTv;
    @Bind(R.id.person_num_one_rb)
    RadioButton personNumOneRb;
    @Bind(R.id.person_num_two_rb)
    RadioButton personNumTwoRb;
    @Bind(R.id.person_num_more_rb)
    RadioButton personNumMoreRb;
    @Bind(R.id.person_num_rg)
    RadioGroup personNumRg;
    @Bind(R.id.chains_grid_view)
    NoScrollGridView chainGriView;
    @Bind(R.id.supply_start_time_tv)
    TextView supplyStartTimeTv;
    @Bind(R.id.supply_end_time_tv)
    TextView supplyEndTimeTv;
    @Bind(R.id.effect_start_time_tv)
    TextView effectStartTimeTv;
    @Bind(R.id.effect_end_time_tv)
    TextView effectEndTimeTv;
    @Bind(R.id.favorable_tv)
    TextView favorableTv;
    @Bind(R.id.sex_limit_tv)
    TextView sexLimitTv;
    @Bind(R.id.certificate_tv)
    TextView certificateTv;
    @Bind(R.id.sf_yuyue_tv)
    TextView sfYuyueTv;

    @Bind(R.id.coupon_details_tv)
    TextView couponDetailsTv;

    @Bind(R.id.coupon_count_limit_et)
    EditText couponCountLimitEt;
    @Bind(R.id.coupon_count_defult_et)
    EditText couponCountDefultEt;
    @Bind(R.id.describe_et)
    EditText describeEt;

    @Bind(R.id.pic_gv)
    NoScrollGridView picGv;
    private ImagePublicAdapter imagePublicAdapter;
    private ArrayList<Object> urls;
    private GearImageSelector gearImageSelector;
    private ArrayList<ImageUploadResult> imageUploadResultArrayList = new ArrayList<>();
    private final int IMAGE_MAX_SIZE = 12;
    private CouponMealsParams couponMealsParams;

    private CouponMealsPublishPresenter couponMealsPublishPresenter;

//    private CouponChainAdapter couponChainAdapter;

    private String couponId;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, CouponMealsPublishActivity.class);
        return intent;
    }

    public static Intent startIntent(Context context, String couponId) {
        Intent intent = new Intent(context, CouponMealsPublishActivity.class);
        intent.putExtra("couponId", couponId);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_coupon_meals_publish;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        couponMealsPublishPresenter = new CouponMealsPublishPresenter(getActivity(), this);

        initializeImageGridView();

        //分店
//        couponChainAdapter = new CouponChainAdapter(getActivity());
//        chainGriView.setAdapter(couponChainAdapter);

        couponId = getIntent().getStringExtra("couponId");
        if (TextUtils.isEmpty(couponId)) {
            setBarTitle("新增套餐券");
            couponMealsParams = new CouponMealsParams();
        } else {
            couponMealsParams = new CouponMealsParams();
            showLoading();
            setBarTitle("修改套餐券");
        }

        couponMealsParams.sexType = 0;
        couponMealsParams.certificate = 0;
        couponMealsParams.sfyuyue = 0;
        couponMealsParams.favorable = "无";

        priceStoreEt.addTextChangedListener(this);
        priceCcjEt.addTextChangedListener(this);

        personNumRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.person_num_one_rb:
                        couponMealsParams.people = 0;
                        break;
                    case R.id.person_num_two_rb:
                        couponMealsParams.people = 1;
                        break;
                    case R.id.person_num_more_rb:
                        couponMealsParams.people = 2;
                        break;
                }
            }
        });
        isRecommendCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    couponMealsParams.isRecommend = 1;
                } else {
                    couponMealsParams.isRecommend = 0;
                }
            }
        });

        couponMealsPublishPresenter.getInitData(couponId);
    }

    final int RC_CAMERA_PERM = 0;
    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void  verifyPermissionForCamera(){
        String[] perms = { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA };
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            if (verifyImagesCount()) {
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean =GearImageSelector.getImageCutBeanByProportion(CouponMealsPublishActivity.this,270, 234);//设置裁剪范围，按比例裁剪
//                    gearImageSelector.imageCutBean = new ImageCutBean(270, 234);//按尺寸裁剪
                gearImageSelector.showImageLoadPannel();
            }
        }else{
            EasyPermissions.requestPermissions(this, "程序需要打开摄像头权限和访问存储空间权限", RC_CAMERA_PERM, perms);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



    @OnClick({R.id.coupon_details_ll, R.id.favorable_ll, R.id.sex_limit_ll, R.id.certificate_ll, R.id.sf_yuyue_ll, R.id.submit_btn, R.id.supply_start_time_tv, R.id.supply_end_time_tv, R.id.effect_start_time_tv, R.id.effect_end_time_tv,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.coupon_details_ll://套餐详情
                startActivityForResult(CouponMealsItemActivity.startIntent(getActivity()), ActivityCode.CODE_REQUEST_COUPON_MEALS_ITEM);
                break;
            case R.id.favorable_ll://同时优惠
                showFavorable();
                break;
            case R.id.sex_limit_ll://性别限制
                showSexLimit();
                break;
            case R.id.certificate_ll://是否要证件
                showCertificate();
                break;
            case R.id.sf_yuyue_ll://是否要需要预约
                showSfyuyue();
                break;
            case R.id.supply_start_time_tv://提供开始时间
                chooseOffTime("提供开始时间", 1, couponMealsParams.offStartTime);
                break;
            case R.id.supply_end_time_tv://提供截至时间
                chooseOffTime("提供截至时间", 2, couponMealsParams.offEndTime);
                break;
            case R.id.effect_start_time_tv://套餐有效开始时间
                chooseTime("有效开始时间", 3, couponMealsParams.effectStartTime);
                break;
            case R.id.effect_end_time_tv://套餐有效截止时间
                chooseTime("有效截止时间", 4, couponMealsParams.effectEndTime);
                break;
            case R.id.submit_btn://提交
                if (verify()) {
                    showLoading();
                    submit();
                }
                break;
        }
    }

    /**
     * 初始化上传多图所需要的东西
     */
    private void initializeImageGridView() {
        gearImageSelector = new GearImageSelector(getActivity());
        imagePublicAdapter = new ImagePublicAdapter(getActivity());
        imagePublicAdapter.setImagesMaxCount(IMAGE_MAX_SIZE);
        picGv.setAdapter(imagePublicAdapter);
        urls = new ArrayList<>();
        urls.add(R.mipmap.ic_image_add );
        imagePublicAdapter.setList(urls);

        imagePublicAdapter.setImageOnClickListener(new ImagePublicAdapter.OnClickImageListener() {
            @Override
            public void onClickAddImage(View v) {

                verifyPermissionForCamera();

            }

            @Override
            public void onClickDeleteImage(View v, int position) {
                urls.remove(position);
                imageUploadResultArrayList.remove(position);
                imagePublicAdapter.notifyDataSetChanged();
            }
        });
        gearImageSelector.setOnImgaeSelectListener(new GearImageSelector.OnImgaeSelectListener() {
            @Override
            public void onSuccessGetBitmap(File file, Bitmap bitmap) {
                showProgressDialog("上传图片");
                couponMealsPublishPresenter.uploadImage(file);
            }

            @Override
            public void onFailedGetBitmap(String msg) {
                ToastUtils.showError(msg, getApplicationContext());

            }
        });
    }

    private boolean verifyImagesCount() {
        boolean canSubmit = true;
        if ((urls.size() - 1) >= IMAGE_MAX_SIZE) {
            canSubmit = false;
            ToastUtils.showShortMessage("抱歉，上传图片数量不能多于12张.", getActivity());
        }
        return canSubmit;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityCode.CODE_REQUEST_COUPON_MEALS_ITEM && resultCode == Activity.RESULT_OK) {
            ArrayList<CouponMealsItemBean> couponMealsItemList = (ArrayList<CouponMealsItemBean>) data.getSerializableExtra("data");
            StringBuilder stringBuilder = new StringBuilder();
            for (CouponMealsItemBean bean : couponMealsItemList) {
                stringBuilder.append(bean.title).append(",");
            }
            couponDetailsTv.setText(stringBuilder.toString());
            couponMealsParams.goodsDetails = new Gson().toJson(couponMealsItemList);

        } else {
            gearImageSelector.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onSuccessUploadImage(ImageUploadResult imageUploadResult) {
        dissDialog();
        dismissProgressDialog();
//        imageUploadResultArrayList.add(imageUploadResult)
        imageUploadResultArrayList.add(imageUploadResult);
// imageUploadResultArrayList.add(0,imageUploadResult);
        //urls.add(0, imageUploadResult.getUrl());

        //新增时插在最后一个前面
        urls.add(urls.size() - 1, imageUploadResult.getUrl());
        imagePublicAdapter.notifyDataSetChanged();
    }

//    @Override
//    public void onSuccessChainList(ArrayList<CouponChainBean> result) {
//        couponChainAdapter.setList(result);
//
//    }

    @Override
    public void onSuccessCouponInfo(CouponMealsInfoResult result) {
        dissDialog();
        couponMealsParams = result.couponMealsParams;
        couponNameEt.setText(result.couponMealsParams.goodsName);
        priceStoreEt.setText(DoubleUtil.formatPrice(result.couponMealsParams.goodsPriceStroe));
        priceCcjEt.setText(DoubleUtil.formatPrice(result.couponMealsParams.goodsPriceCCJ2));
        couponCountLimitEt.setText(result.couponMealsParams.discountLimitCount);
        couponCountDefultEt.setText(result.couponMealsParams.discountDefult);
        switch (result.couponMealsParams.people) {
            case 0:
                personNumOneRb.setChecked(true);
                break;
            case 1:
                personNumTwoRb.setChecked(true);
                break;
            case 2:
                personNumMoreRb.setChecked(true);
                break;
        }

        supplyStartTimeTv.setText(result.couponMealsParams.offStartTime);
        supplyEndTimeTv.setText(result.couponMealsParams.offEndTime);
        effectStartTimeTv.setText(result.couponMealsParams.effectStartTime);
        effectEndTimeTv.setText(result.couponMealsParams.effectEndTime);
        favorableTv.setText(result.couponMealsParams.favorable);
        sexLimitTv.setText(sexArr[result.couponMealsParams.sexType]);
        certificateTv.setText(certificateArr[result.couponMealsParams.certificate]);
        sfYuyueTv.setText(sfyuyueArr[result.couponMealsParams.sfyuyue]);
        describeEt.setText(result.couponMealsParams.remark);

        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<CouponMealsItemBean> couponMealsItemList = result.couponMealsItemArrayList;
        if (couponMealsItemList != null) {
            for (CouponMealsItemBean bean : couponMealsItemList) {
                stringBuilder.append(bean.title).append(",");
            }
            couponMealsParams.goodsDetails = new Gson().toJson(couponMealsItemList);
        }

        ArrayList<CouponMealsImageBean> imageList = result.couponMealsImageArrayList;
        if (imageList != null) {
            for (CouponMealsImageBean bean : imageList) {
                ImageUploadResult imageUploadResult = new ImageUploadResult(bean.storeImageId + "", bean.absImages);
//                imageUploadResultArrayList.add(imageUploadResult);
//                imageUploadResultArrayList.add(0,imageUploadResult);
//                urls.add(0, imageUploadResult.getUrl());//修改时插在最前面

                imageUploadResultArrayList.add(imageUploadResult);
                urls.add(urls.size() - 1, imageUploadResult.getUrl());
                imagePublicAdapter.notifyDataSetChanged();
            }
        }

//        couponChainAdapter.setSelectItems(result.couponMealsParams.chain);
        couponDetailsTv.setText(stringBuilder.toString());
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        dissDialog();
    }

    @Override
    public void onSuccess(String result) {
        dissDialog();
        if (TextUtils.isEmpty(couponId)) {
            ToastUtils.showShortMessage("新增成功, 请等待审核", getApplication());
        } else {
            ToastUtils.showShortMessage("修改成功, 请等待审核", getApplication());
//            Intent intent = new Intent();
//            setResult(101, intent);
        }

        finish();

    }

    GearCustomDialog gearCustomDialog;

    /**
     * 更多优惠
     */
    private void showFavorable() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_coupon_meals_publish_favorable, null);
        EditText contentET = (EditText) view.findViewById(R.id.content_et);
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setTitle("享有更多优惠")
                .setContentView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        couponMealsParams.favorable = contentET.getText().toString();
                        favorableTv.setText(contentET.getText().toString());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        gearCustomDialog.show();

    }

    /**
     * 限性别
     */
    String[] sexArr = new String[]{"不要求", "男性", "女性"};

    private void showSexLimit() {

        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .addListView(sexArr, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;
                        couponMealsParams.sexType = position;
                        sexLimitTv.setText(sexArr[position]);
                    }
                })
                .setTitle("请选择限制性别")
                .create();
        gearCustomDialog.show();
    }

    /**
     * 是否要带证件
     */
    String[] certificateArr = new String[]{"不要求", "身份证"};

    private void showCertificate() {

        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .addListView(certificateArr, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;
                        couponMealsParams.certificate = position;
                        certificateTv.setText(certificateArr[position]);
                    }
                })
                .setTitle("请选择是否要带证件")
                .create();
        gearCustomDialog.show();
    }

    /**
     * 是否需要预约
     */
    String[] sfyuyueArr = new String[]{"不要求", "需要预约"};

    private void showSfyuyue() {

        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .addListView(sfyuyueArr, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;
                        couponMealsParams.sfyuyue = position;
                        sfYuyueTv.setText(sfyuyueArr[position]);
                    }
                })
                .setTitle("请选择是否需要预约")
                .create();
        gearCustomDialog.show();
    }

    private void chooseOffTime(String title, int type, String timeTmp) {
        String str = timeTmp;
        if (TextUtils.isEmpty(str)) {
            str = "18:00";
        }
        str = "2016-10-12 " + str;
        long time = DateUtils.StringToDate(str).getTime();
        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(), time);
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.HOUR_MIN);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
                .setTitle("选择开始服务时间")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        switch (type) {
                            case 1:
                                couponMealsParams.offStartTime = DateUtils.getDate(dateTime, DateStyle.HH_MM);
                                supplyStartTimeTv.setText(DateUtils.getDate(dateTime, DateStyle.HH_MM));
                                break;
                            case 2:
                                couponMealsParams.offEndTime = DateUtils.getDate(dateTime, DateStyle.HH_MM);
                                supplyEndTimeTv.setText(DateUtils.getDate(dateTime, DateStyle.HH_MM));
                                break;

                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        gearCustomDialog.show();
    }

    private void chooseTime(String title, int type, String originalTime) {
        if (TextUtils.isEmpty(originalTime)) {
            originalTime = DateUtils.getCurrentDate();
        }

        final GearDateViewController gearDateViewController = new GearDateViewController(getActivity(), originalTime);
        gearDateViewController.setDateStyle(GearDateViewController.DateStyle.YEAR_MONTH_DAY);
        GearCustomDialog gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(gearDateViewController.getContentView())
                .setTitle("选择" + title)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        long dateTime = gearDateViewController.getSelectDate().getTime();
                        switch (type) {

                            case 3:
                                couponMealsParams.effectStartTime = DateUtils.getDate(dateTime);
                                effectStartTimeTv.setText(DateUtils.getDate(dateTime));
                                break;
                            case 4:
                                couponMealsParams.effectEndTime = DateUtils.getDate(dateTime);
                                effectEndTimeTv.setText(DateUtils.getDate(dateTime));
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        gearCustomDialog.show();
    }


    private void submit() {
        if (TextUtils.isEmpty(couponId)) {
            couponMealsPublishPresenter.add(couponMealsParams);
        } else {
            couponMealsPublishPresenter.edit(couponMealsParams);
        }
    }

    private boolean verify() {
        boolean canSubmit = true;
        String name = couponNameEt.getText().toString();
        String priceStore = priceStoreEt.getText().toString();
        String priceCCJ = priceCcjEt.getText().toString();

        String discountLimitCount = couponCountLimitEt.getText().toString();
        String discountDefult = couponCountDefultEt.getText().toString();

        String describe = describeEt.getText().toString();
        String couponDetails = couponDetailsTv.getText().toString();

        if (TextUtils.isEmpty(name)) {
            couponNameEt.requestFocus();
            couponNameEt.setError("请输入套餐名字");
            canSubmit = false;
        } else if (TextUtils.isEmpty(couponDetails)) {
            couponNameEt.requestFocus();
            ToastUtils.showError("套餐详情不能为空",getActivity());
            canSubmit = false;
        } else if (TextUtils.isEmpty(priceStore)) {
            priceStoreEt.requestFocus();
            priceStoreEt.setError("请输入门店销售价");
            canSubmit = false;
        } else if ("0".equals(priceStore)) {
            priceStoreEt.requestFocus();
            priceStoreEt.setError("门店销售价必须大于0");
            canSubmit = false;
        } else if (!DoubleUtil.isDouble(priceStore)) {
            priceStoreEt.requestFocus();
            priceStoreEt.setError("门店销售价不能含有特殊字符, 且为数字格式");
            canSubmit = false;
        } else if (TextUtils.isEmpty(priceCCJ)) {
            priceCcjEt.requestFocus();
            priceCcjEt.setError("请输入餐餐抢价");
            canSubmit = false;
        } else if (!DoubleUtil.isDouble(priceCCJ)) {
            priceCcjEt.requestFocus();
            priceCcjEt.setError("餐餐抢价不能含有特殊字符, 且为数字格式");
            canSubmit = false;
        }
//        else if(!couponChainAdapter.hasSelected()){
//            ToastUtils.showShortMessage("请选择分店", getApplication());
//            canSubmit = false;
//        }
        else if (TextUtils.isEmpty(couponMealsParams.offStartTime)) {
            ToastUtils.showShortMessage("请选择提供开始时间", getApplication());
            canSubmit = false;
        } else if (TextUtils.isEmpty(couponMealsParams.offEndTime)) {
            ToastUtils.showShortMessage("请选择提供截至时间", getApplication());
            canSubmit = false;
        } else if (TextUtils.isEmpty(couponMealsParams.effectStartTime)) {
            ToastUtils.showShortMessage("请选择套餐有效开始时间", getApplication());
            canSubmit = false;
        } else if (TextUtils.isEmpty(couponMealsParams.effectEndTime)) {
            ToastUtils.showShortMessage("请选择提供套餐有效截至时间", getApplication());
            canSubmit = false;
        }
//        else if (TextUtils.isEmpty(couponMealsParams.favorable)) {
//            ToastUtils.showShortMessage("请输入享有优惠", getApplication());
//            canSubmit = false;
//        }
        else if (couponMealsParams.sexType == -1) {
            ToastUtils.showShortMessage("请选择性别限制", getApplication());
            canSubmit = false;
        } else if (couponMealsParams.certificate == -1) {
            ToastUtils.showShortMessage("请选择是否需要证件", getApplication());
            canSubmit = false;
        } else if (couponMealsParams.sfyuyue == -1) {
            ToastUtils.showShortMessage("请选择是否需要预约", getApplication());
            canSubmit = false;
        } else if (TextUtils.isEmpty(discountLimitCount)) {
            couponCountLimitEt.requestFocus();
            couponCountLimitEt.setError("请输入发售折扣数量");
            canSubmit = false;
        } else if (!DoubleUtil.isDouble(discountLimitCount)) {
            couponCountLimitEt.requestFocus();
            couponCountLimitEt.setError("发售折扣数量不能含有特殊字符, 且为数字格式");
            canSubmit = false;
        }
//        else if (TextUtils.isEmpty(discountDefult)) {
//            couponCountDefultEt.requestFocus();
//            couponCountDefultEt.setError("请输入默认销售数量");
//            canSubmit = false;
//        }
        else if (!TextUtils.isEmpty(discountDefult) && !DoubleUtil.isDouble(discountDefult)) {
            couponCountDefultEt.requestFocus();
            couponCountDefultEt.setError("默认销售数量不能含有特殊字符, 且为数字格式");
            canSubmit = false;
        }
//        else if (TextUtils.isEmpty(describe)) {
//            describeEt.requestFocus();
//            describeEt.setError("请输入默认套餐描述");
//            canSubmit = false;
//        }
        else if (imageUploadResultArrayList.size() <= 0) {
            canSubmit = false;
            ToastUtils.showError("请至少选择一张图片.", getApplication());
        } else if (TextUtils.isEmpty(couponMealsParams.goodsDetails)) {
            canSubmit = false;
            ToastUtils.showError("请选择套餐详细.", getApplication());
        } else {
            couponMealsParams.goodsName = name;
            couponMealsParams.goodsPriceStroe = priceStore;
            couponMealsParams.goodsPriceCCJ = priceCCJ;
            couponMealsParams.discountLimitCount = discountLimitCount;
            couponMealsParams.discountDefult = discountDefult;
            couponMealsParams.remark = describe;
//            couponMealsParams.chain = couponChainAdapter.getSelectedItemsForParams();

            StringBuilder ids = new StringBuilder("[");
            for (int i = 0; i < imageUploadResultArrayList.size(); i++) {
                ImageUploadResult result = imageUploadResultArrayList.get(i);
                ids.append(result.getId());
                if (i < imageUploadResultArrayList.size() - 1) {
                    ids.append(",");
                }
            }
            ids.append("]");
            String tmp = ids.toString();
            couponMealsParams.images = tmp;
            couponMealsParams.originalImages = tmp;
        }


        return canSubmit;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String string = editable.toString();

        if (!TextUtils.isEmpty(string)) {
            int i = string.indexOf(".");
            int length = string.length();
            if (i != -1 && (length - i) > 3) {// 只允许有两位小数
                string = string.substring(0, i + 3);
                if (editable.equals(priceStoreEt)) {
                    priceStoreEt.setText(string);
                } else if (editable.equals(priceCcjEt)) {
                    priceCcjEt.setText(string);
                }
            }
            if (!string.equals("") || string != null) {
                if (editable.equals(priceStoreEt)) {
                    priceStoreEt.setSelection(string.length());//将光标移至文字末尾
                } else if (editable.equals(priceCcjEt)) {
                    priceCcjEt.setSelection(string.length());//将光标移至文字末尾

                }
            }
            if (!TextUtils.isEmpty(priceStoreEt.getText().toString().trim()) && priceStoreEt.getText().toString().trim() != null
                    && !TextUtils.isEmpty(priceCcjEt.getText().toString().trim()) && priceCcjEt.getText().toString().trim() != null) {
                double mendianjia = Double.parseDouble(priceStoreEt.getText().toString().trim());
                double ccjjia = Double.parseDouble(priceCcjEt.getText().toString().trim());
                double zhekou = ccjjia / mendianjia * 10;
                priceDiscountTv.setText("用户折扣:   " + new DecimalFormat("0.0").format(zhekou) + " 折");
            }
        } else {
            if (editable.equals(priceStoreEt)) {
                priceStoreEt.setText("");
            } else if (editable.equals(priceCcjEt)) {
                priceCcjEt.setText("");

            }

        }
    }

    // 加载中动画
    private Dialog loadingDialog;

    // 加载动画
    public void showLoading() {

        View view = LayoutInflater.from(CouponMealsPublishActivity.this).inflate(
                R.layout.loading, null);
        ImageView image = (ImageView) view.findViewById(R.id.iv_loading);
        Animation animation = AnimationUtils.loadAnimation(
                CouponMealsPublishActivity.this, R.anim.loading_anim);
        image.startAnimation(animation);
        loadingDialog = new Dialog(CouponMealsPublishActivity.this,
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
