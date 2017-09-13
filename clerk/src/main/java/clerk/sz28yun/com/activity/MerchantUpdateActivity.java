package clerk.sz28yun.com.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.base.ActivityCode;
import clerk.sz28yun.com.base.BroadcastAction;
import clerk.sz28yun.com.base.CCJActivity;
import clerk.sz28yun.com.bean.ImageUploadResult;
import clerk.sz28yun.com.bean.MerchantBean;
import clerk.sz28yun.com.bean.MerchantCategoryBean;
import clerk.sz28yun.com.bean.MerchantParams;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.APIParamsCache;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;
import clerk.sz28yun.com.cache.MerchantDraftStorageCache;
import clerk.sz28yun.com.presenter.MerchantUpdatePresenter;
import clerk.sz28yun.com.presenter.MerchantUpdatePresenter.ImageType;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import per.sue.gear2.bean.ImageCutBean;
import per.sue.gear2.controll.GearImageLoad;
import per.sue.gear2.controll.GearImageSelector;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.IDUtils;
import per.sue.gear2.utils.ToastUtils;

/**
 * 新增商家/修改商家
 * Created by sue on 2016/11/23.
 */
public class MerchantUpdateActivity extends CCJActivity implements MerchantUpdatePresenter.MerchantUpdateView, View.OnFocusChangeListener {

    @Bind(R.id.merchant_name_et)
    EditText merchantNameEt;
    @Bind(R.id.merchant_member_account_et)
    EditText merchantMemberAccountEt;
    @Bind(R.id.merchant_category_tv)
    TextView merchantCategoryTv;
    @Bind(R.id.tv_dw_addr)
    TextView dwAddrTv;
    @Bind(R.id.tv_dw_province)
    TextView dwProvinceTv;
    @Bind(R.id.tv_dw_city)
    TextView dwCityTv;
    @Bind(R.id.merchant_qu_et)
    EditText merchantQuEt;
    @Bind(R.id.merchant_street_et)
    EditText merchantStreetEt;
    @Bind(R.id.merchant_address_et)
    EditText merchantAddressEt;
    @Bind(R.id.merchant_fr_name_et)
    EditText merchantFrNameEt;
    @Bind(R.id.merchant_fr_id_et)
    EditText merchantFrIdEt;
    @Bind(R.id.merchant_fr_mobile_connect_et)
    EditText merchantFrMobileConnectEt;
    @Bind(R.id.merchant_fr_mobile_service_et)
    EditText merchantFrMobileServiceEt;
    @Bind(R.id.img_id_front_iv)
    ImageView imgIdFrontIv;
    @Bind(R.id.img_id_back_iv)
    ImageView imgIdBackIv;
    @Bind(R.id.img_license_iv)
    ImageView imgLicenseIv;
    @Bind(R.id.img_zzzj_iv)
    ImageView imgZzzjIv;
    @Bind(R.id.img_banner_iv)
    ImageView imgBannerIv;
    @Bind(R.id.img_snt_iv)
    ImageView imgSntIv;
    @Bind(R.id.img_merchant_first_iv)
    ImageView imgMerchantFirstIv;
    @Bind(R.id.img_merchant_second_iv)
    ImageView imgMerchantSecondIv;
    @Bind(R.id.img_merchant_third_iv)
    ImageView imgMerchantThirdIv;
    @Bind(R.id.img_kitchen_first_iv)
    ImageView imgKitchenFirstIv;
    @Bind(R.id.img_kitchen_second_iv)
    ImageView imgKitchenSecondIv;
    @Bind(R.id.img_kitchen_third_iv)
    ImageView imgKitchenThirdIv;

    private int imageUploadType = 1;
    private boolean sfchexksj_accoubt = false;
    private boolean sftjsj_accoubt = false;
    private boolean isCg = true;

    private MerchantUpdatePresenter merchantUpdatePresenter;
    private GearImageSelector gearImageSelector;

    private MerchantUpdatePresenter.ImageType selsectImageType;
    private MerchantParams merchantParams;

    private AMapLocation aMapLocation;


    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, MerchantUpdateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    public static Intent startIntent(Context context, String id) {
        Intent intent = new Intent(context, MerchantUpdateActivity.class);
        intent.putExtra("id", id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    public static Intent startIntent(Context context, MerchantParams params) {
        Intent intent = new Intent(context, MerchantUpdateActivity.class);
        intent.putExtra("MerchantParams", params);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_merchant_update;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        getHeadBarView().addRightTextItem("草稿箱 ", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MerchantUpdateDraftActivity.startIntent(getActivity()));
            }
        });

//        aMapLocation = APIParamsCache.getInstance().getaMapLocation();
        merchantUpdatePresenter = new MerchantUpdatePresenter(getActivity(), this);
        gearImageSelector = new GearImageSelector(getActivity());
        gearImageSelector.setCut(true);
        merchantParams = (MerchantParams) getIntent().getSerializableExtra("MerchantParams");
        if (null == merchantParams) {
            isCg = false;
            if (null == getIntent().getStringExtra("id")) {//新增
                merchantParams = new MerchantParams();
                merchantParams.initialize();
            } else {//修改
                String id = getIntent().getStringExtra("id");
                merchantUpdatePresenter.getData(id);//获取数据
            }
        } else { //草稿箱过来的
            isCg = true;
            bindView(merchantParams);
        }

        setImageSelector();
        merchantMemberAccountEt.setOnFocusChangeListener(this);
    }

    @OnClick({R.id.merchant_account_verify_tv, R.id.merchant_category_ll, R.id.merchant_address_tv,
            R.id.img_id_front_btn, R.id.img_id_back_btn, R.id.img_license_btn, R.id.img_zzzj_btn,R.id.img_scbanner_iv,R.id.img_snt_btn,
            R.id.img_merchant_first_btn, R.id.img_merchant_second_btn, R.id.img_merchant_third_btn, R.id.img_kitchen_first_btn,
            R.id.img_kitchen_second_btn, R.id.img_kitchen_third_btn, R.id.submit_draft_btn, R.id.submit_audit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.merchant_category_ll:
                startActivityForResult(MerchantCategoryListActivity.startIntent(getActivity()), ActivityCode.CODE_REQUEST_MERCHANT_CATEGORY);
                break;
            case R.id.merchant_address_tv:
                setMerchantAddressByLocation();
                break;
            case R.id.img_id_front_btn:
                imageUploadType = 3;
                selsectImageType = ImageType.BASE_ID_FRONET;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 243, 153);//设置裁剪范围
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.img_id_back_btn:
                imageUploadType = 3;
                selsectImageType = ImageType.BASE_ID_BACK;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 243, 153);//设置裁剪范围
//                gearImageSelector.imageCutBean = new ImageCutBean(243, 153);
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.img_license_btn:
                imageUploadType = 3;
                selsectImageType = ImageType.BASE_LICENSE;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 282, 218);//设置裁剪范围
//                gearImageSelector.imageCutBean = new ImageCutBean(282, 218);
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.img_zzzj_btn:
                imageUploadType = 3;
                selsectImageType = ImageType.BASE_ZIZI;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 282, 218);//设置裁剪范围
//                gearImageSelector.imageCutBean = new ImageCutBean(282, 218);
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.img_scbanner_iv:
                imageUploadType = 10;
                selsectImageType = ImageType.MERCHANT_BANNER;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 300,140);//设置裁剪范围
//                gearImageSelector.imageCutBean = new ImageCutBean(282, 218);
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.img_snt_btn:
                imageUploadType = 9;
                selsectImageType = ImageType.MERCHANT_SUONUE;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 270, 240);//设置裁剪范围
//                gearImageSelector.imageCutBean = new ImageCutBean(282, 218);
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.img_merchant_first_btn:
                imageUploadType = 4;
                selsectImageType = ImageType.MERCHANT_FIRST;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 220, 160);//设置裁剪范围
//                gearImageSelector.imageCutBean = new ImageCutBean(180, 160);
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.img_merchant_second_btn:
                imageUploadType = 4;
                selsectImageType = ImageType.MERCHANT_SECOND;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 220, 160);//设置裁剪范围
//                gearImageSelector.imageCutBean = new ImageCutBean(180, 160);
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.img_merchant_third_btn:
                imageUploadType = 4;
                selsectImageType = ImageType.MERCHANT_THIRD;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 220, 160);//设置裁剪范围
//                gearImageSelector.imageCutBean = new ImageCutBean(180, 160);
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.img_kitchen_first_btn:
                imageUploadType = 5;
                selsectImageType = ImageType.KITCHEN_FIRST;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 220, 160);//设置裁剪范围
//                gearImageSelector.imageCutBean = new ImageCutBean(220, 160);
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.img_kitchen_second_btn:
                imageUploadType = 5;
                selsectImageType = ImageType.KITCHEN_SECOND;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 220, 160);//设置裁剪范围
//                gearImageSelector.imageCutBean = new ImageCutBean(220, 160);
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.img_kitchen_third_btn:
                imageUploadType = 5;
                selsectImageType = ImageType.KITCHEN_THIRD;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 220, 160);//设置裁剪范围
//                gearImageSelector.imageCutBean = new ImageCutBean(220, 160);
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.submit_draft_btn:
                saveDraft();
                break;
            case R.id.submit_audit_btn:
                if (sfchexksj_accoubt) {
                    subimit();
                } else {
                    verifyMerchantAccount();
                    sftjsj_accoubt = true;
                }
                break;
            case R.id.merchant_account_verify_tv://效验会员账号
                verifyMerchantAccount();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityCode.CODE_REQUEST_MERCHANT_CATEGORY && resultCode == Activity.RESULT_OK) {
            MerchantCategoryBean bean = (MerchantCategoryBean) data.getSerializableExtra("data");
            merchantParams.merchantCategoryId = bean.getGcId();
            merchantParams.merchantCategoryName = bean.getGcName();
            merchantCategoryTv.setText(bean.getGcName());
            merchantCategoryTv.setTag(bean.getGcId());
        } else if (requestCode == ActivityCode.CODE_REQUEST_MERCHANT_ACCOUNT_REGIST && resultCode == Activity.RESULT_OK) {
            String account = data.getStringExtra("data");
            merchantMemberAccountEt.setText(account);
        } else {
            gearImageSelector.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onAccountNeedRegist(String message) {
        new GearCustomDialog.Builder(getActivity())
                //.setTitle("效验结果")
                .setMessage("您输入的商家账号还不是28云会员. 是否注册? ")
                .setNegativeButton("重新填写", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sfchexksj_accoubt = false;
                        merchantMemberAccountEt.requestFocus();
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("注册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivityForResult(MerchantAccountRegistActivity.startIntent(getActivity()), ActivityCode.CODE_REQUEST_MERCHANT_ACCOUNT_REGIST);
                    }
                }).create().show();
    }

    @Override
    public void onAccountNotNeedRegist(String message) {
        if (sftjsj_accoubt) {
            subimit();
        } else {
            ToastUtils.showError("商家账号可用", getApplication());
        }

    }

    /**
     * 成功提交数据
     *
     * @param message
     */
    @Override
    public void onSuccessSubmit(String message) {
        dissDialog();
        ToastUtils.showError("提交成功", getApplication());
        sendBroadcast(new Intent(BroadcastAction.ACTION_MIAN_NAV_BUSNESS));
        if (isCg) {
            MerchantDraftStorageCache.getInstance().deleteMarchantParams(getApplication(), merchantParams);
        }
        finish();
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        dissDialog();
    }

    /**
     * 上传图片完成后的处理， 上传图片设置请查看方法setImageSelector（）；
     *
     * @param type
     * @param result
     */
    @Override
    public void onSucessLoadImage(MerchantUpdatePresenter.ImageType type, ImageUploadResult result) {
        dismissProgressDialog();
        switch (type) {
            case BASE_ID_FRONET:
                merchantParams.imageBaseList.set(0, result);
                GearImageLoad.getSingleton(getActivity()).load(result.getUrl(), imgIdFrontIv);
                break;
            case BASE_ID_BACK:
                merchantParams.imageBaseList.set(1, result);
                GearImageLoad.getSingleton(getActivity()).load(result.getUrl(), imgIdBackIv);
                break;
            case BASE_LICENSE:
                merchantParams.imageBaseList.set(2, result);
                GearImageLoad.getSingleton(getActivity()).load(result.getUrl(), imgLicenseIv);
                break;
            case BASE_ZIZI:
                merchantParams.imageBaseList.set(3, result);
                GearImageLoad.getSingleton(getActivity()).load(result.getUrl(), imgZzzjIv);
                break;
            case MERCHANT_BANNER:
                merchantParams.imageMerchantBannerList.set(0, result);
                GearImageLoad.getSingleton(getActivity()).load(result.getUrl(), imgBannerIv);

                break;
            case MERCHANT_SUONUE:
                merchantParams.imageMerchantSnList.set(0, result);
                GearImageLoad.getSingleton(getActivity()).load(result.getUrl(), imgSntIv);
                break;
            case MERCHANT_FIRST:
                merchantParams.imageMerchantList.set(0, result);
                GearImageLoad.getSingleton(getActivity()).load(result.getUrl(), imgMerchantFirstIv);
                break;
            case MERCHANT_SECOND:
                merchantParams.imageMerchantList.set(1, result);
                GearImageLoad.getSingleton(getActivity()).load(result.getUrl(), imgMerchantSecondIv);
                break;
            case MERCHANT_THIRD:
                merchantParams.imageMerchantList.set(2, result);
                GearImageLoad.getSingleton(getActivity()).load(result.getUrl(), imgMerchantThirdIv);
                break;
            case KITCHEN_FIRST:
                merchantParams.imageKitchenList.set(0, result);
                GearImageLoad.getSingleton(getActivity()).load(result.getUrl(), imgKitchenFirstIv);
                break;
            case KITCHEN_SECOND:
                merchantParams.imageKitchenList.set(1, result);
                GearImageLoad.getSingleton(getActivity()).load(result.getUrl(), imgKitchenSecondIv);
                break;
            case KITCHEN_THIRD:
                merchantParams.imageKitchenList.set(2, result);
                GearImageLoad.getSingleton(getActivity()).load(result.getUrl(), imgKitchenThirdIv);
                break;
            default:
                break;

        }
    }

    @Override
    public void onSuccess(MerchantBean result) {
        merchantParams = MerchantParams.fromMerchantBean(result);
        bindView(merchantParams);
    }


    /**
     * 保存草稿
     */
    private void saveDraft() {
        if (TextUtils.isEmpty(merchantNameEt.getText().toString())) {
            ToastUtils.showError("请填写商家名称", getApplication());
        } else {
            String merchantName = merchantNameEt.getText().toString();
            String merchantMemberAccount = merchantMemberAccountEt.getText().toString();
            String categiryId = (String) merchantCategoryTv.getTag();//分类ID 放到tag里面，用ID 做效验
            String merchantAddress = merchantAddressEt.getText().toString();
            String legalName = merchantFrNameEt.getText().toString();
            String legalID = merchantFrIdEt.getText().toString();
            String contractMobile = merchantFrMobileConnectEt.getText().toString();
            String serviceMobile = merchantFrMobileServiceEt.getText().toString();
            UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
            merchantParams.token = userBean.getToken();
            merchantParams.memberName = merchantMemberAccount;
            merchantParams.memberId = userBean.getMemberId();
            merchantParams.merchantName = merchantName;
            merchantParams.merchantLegalPerson = legalName;
            merchantParams.merchantLegalPersonID = legalID;
            merchantParams.constactsMobileContact = contractMobile;
            merchantParams.constactsMobileServicet = serviceMobile;

            merchantParams.addressTown = "0";
            merchantParams.addressDetails = merchantAddress;
            merchantParams.addressProvince = dwProvinceTv.getText().toString().trim();
            merchantParams.addressCity = dwCityTv.getText().toString().trim();
            merchantParams.addressArea = merchantQuEt.getText().toString().trim();
            merchantParams.addressTown = merchantStreetEt.getText().toString().trim();

//                merchantParams.longitude = aMapLocation.getLongitude() + "";
//                merchantParams.latitude = aMapLocation.getLatitude() + "";

            merchantParams.type = "0";
            merchantParams.changeImageListToJson();

            boolean b = MerchantDraftStorageCache.getInstance().storeMerchantParams(getApplication(), merchantParams);
            if (b) {
                ToastUtils.showError("保存草稿成功", getApplication());
                startActivity(MerchantUpdateDraftActivity.startIntent(getActivity()));
                finish();
            } else {
                ToastUtils.showError("保存草稿失败", getApplication());
            }
        }

    }

    /**
     * 绑定数据
     *
     * @param params
     */
    private void bindView(MerchantParams params) {
        merchantNameEt.setText(params.merchantName);
        merchantMemberAccountEt.setText(params.memberName);
        merchantCategoryTv.setText(params.merchantCategoryName);
        merchantAddressEt.setText(params.addressDetails);
        merchantFrNameEt.setText(params.merchantLegalPerson);
        merchantFrIdEt.setText(params.merchantLegalPersonID);
        merchantFrMobileConnectEt.setText(params.constactsMobileContact);
        merchantFrMobileServiceEt.setText(params.constactsMobileServicet);
        dwProvinceTv.setText(params.addressProvince);
        dwCityTv.setText(params.addressCity);
        merchantQuEt.setText(params.addressArea);
        merchantStreetEt.setText(params.addressTown);
        merchantAddressEt.setText(params.addressDetails);

        merchantParams.latitude = params.latitude;
        merchantParams.longitude = params.longitude;

        GearImageLoad.getSingleton(getActivity()).load(params.imageBaseList.get(0).getUrl(), imgIdFrontIv);
        GearImageLoad.getSingleton(getActivity()).load(params.imageBaseList.get(1).getUrl(), imgIdBackIv);
        GearImageLoad.getSingleton(getActivity()).load(params.imageBaseList.get(2).getUrl(), imgLicenseIv);
        GearImageLoad.getSingleton(getActivity()).load(params.imageBaseList.get(3).getUrl(), imgZzzjIv);

        GearImageLoad.getSingleton(getActivity()).load(params.imageMerchantBannerList.get(0).getUrl(), imgBannerIv);
        GearImageLoad.getSingleton(getActivity()).load(params.imageMerchantSnList.get(0).getUrl(), imgSntIv);

        GearImageLoad.getSingleton(getActivity()).load(params.imageMerchantList.get(0).getUrl(), imgMerchantFirstIv);
        GearImageLoad.getSingleton(getActivity()).load(params.imageMerchantList.get(1).getUrl(), imgMerchantSecondIv);
        GearImageLoad.getSingleton(getActivity()).load(params.imageMerchantList.get(2).getUrl(), imgMerchantThirdIv);

        GearImageLoad.getSingleton(getActivity()).load(params.imageKitchenList.get(0).getUrl(), imgKitchenFirstIv);
        GearImageLoad.getSingleton(getActivity()).load(params.imageKitchenList.get(1).getUrl(), imgKitchenSecondIv);
        GearImageLoad.getSingleton(getActivity()).load(params.imageKitchenList.get(2).getUrl(), imgKitchenThirdIv);
    }


    /**
     * 校验会员账号
     */
    private void verifyMerchantAccount() {
        String account = merchantMemberAccountEt.getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showError("请输入会员账号", getApplication());
        } else {
            sfchexksj_accoubt = true;
            merchantUpdatePresenter.verifyMemberAccount(account);
        }
    }


    /**
     * 通过定位设置商家地址
     */
    private void setMerchantAddressByLocation() {
        aMapLocation = APIParamsCache.getInstance().getaMapLocation();
        if (null != aMapLocation) {
//            merchantAddressEt.setText(aMapLocation.getAddress());
            dwAddrTv.setText(aMapLocation.getAddress());
            dwProvinceTv.setText(aMapLocation.getProvince());
            dwCityTv.setText(aMapLocation.getCity());
//            merchantQuEt.setText(aMapLocation.getDistrict());
//            merchantStreetEt.setText(aMapLocation.getStreet());
            merchantParams.longitude = aMapLocation.getLongitude() + "";
            merchantParams.latitude = aMapLocation.getLatitude() + "";

        } else {
            ToastUtils.showError("获取定位失败！ 请检查网络.", getApplication());
        }
    }

    /**
     * 设置图片选择器
     */
    private void setImageSelector() {
        gearImageSelector.setOnImgaeSelectListener(new GearImageSelector.OnImgaeSelectListener() {
            @Override
            public void onSuccessGetBitmap(File file, Bitmap bitmap) {
                //图片上传 imageUploadType 在点击事件那里赋值
                showProgressDialog("上传图片");
                merchantUpdatePresenter.uploadImage(selsectImageType, file, "" + imageUploadType);
            }

            @Override
            public void onFailedGetBitmap(String msg) {
                ToastUtils.showError(msg, getApplication());
            }
        });
    }

    /**
     * 提交数据
     */
    public void subimit() {
        if (verifyInput()) {
            showLoading();
            merchantUpdatePresenter.submit(merchantParams);
        }
    }

    private boolean verifyInput() {
        boolean canSubmit = true;
        String merchantName = merchantNameEt.getText().toString();
        String merchantMemberAccount = merchantMemberAccountEt.getText().toString();
        String categiryId = merchantParams.merchantCategoryId;
        String merchantProvince = dwProvinceTv.getText().toString();
        String merchantCity = dwCityTv.getText().toString();
        String merchantArea = merchantQuEt.getText().toString();
        String merchantStreet = merchantStreetEt.getText().toString();
        String merchantAddress = merchantAddressEt.getText().toString();
        String legalName = merchantFrNameEt.getText().toString();
        String legalID = merchantFrIdEt.getText().toString();
        String contractMobile = merchantFrMobileConnectEt.getText().toString();
        String serviceMobile = merchantFrMobileServiceEt.getText().toString();
        // 商家基本信息验证
        if (TextUtils.isEmpty(merchantName)) {
            canSubmit = false;
            merchantNameEt.requestFocus();
            merchantNameEt.setError("商家名称不能为空！");
        } else if (TextUtils.isEmpty(merchantMemberAccount)) {
            canSubmit = false;
            merchantMemberAccountEt.requestFocus();
            merchantMemberAccountEt.setError("商家账号不能为空!");
        } else if (TextUtils.isEmpty(categiryId)) {
            canSubmit = false;
            ToastUtils.showError("请选择所属行业!", getApplication());
        } else if (TextUtils.isEmpty(merchantProvince)) {
            canSubmit = false;
            dwProvinceTv.requestFocus();
            dwProvinceTv.setError("请点击当前位置获取省市");
        }else if (TextUtils.isEmpty(merchantCity)) {
            canSubmit = false;
            dwProvinceTv.requestFocus();
            dwProvinceTv.setError("请点击当前位置获取省市!");
        }else if (TextUtils.isEmpty(merchantArea)) {
            canSubmit = false;
            merchantQuEt.requestFocus();
            merchantQuEt.setError("区不能为空!");
        }else if (TextUtils.isEmpty(merchantStreet)) {
            canSubmit = false;
            merchantStreetEt.requestFocus();
            merchantStreetEt.setError("镇不能为空!");
        }else if (TextUtils.isEmpty(merchantAddress)) {
            canSubmit = false;
            merchantMemberAccountEt.requestFocus();
            merchantMemberAccountEt.setError("当前地址不能为空!");
        } else if (TextUtils.isEmpty(legalName)) {
            canSubmit = false;
            merchantFrNameEt.requestFocus();
            merchantFrNameEt.setError("商家法人不能为空!");
        } else if (TextUtils.isEmpty(legalID)) {
            canSubmit = false;
            merchantFrIdEt.requestFocus();
            merchantFrIdEt.setError("商家营业编号不能为空!");
        }
//        else if (!IDUtils.isCorrectID(legalID)) {
//            canSubmit = false;
//            merchantFrIdEt.requestFocus();
//            merchantFrIdEt.setError("商家法人身份证格式不正确!");
//        }
        else if (TextUtils.isEmpty(contractMobile)) {
            canSubmit = false;
            merchantFrMobileConnectEt.requestFocus();
            merchantFrMobileConnectEt.setError("联系电话不能为空!");
        } else if (TextUtils.isEmpty(serviceMobile)) {
            canSubmit = false;
            merchantFrMobileServiceEt.requestFocus();
            merchantFrMobileServiceEt.setError("服务电话不能为空!");
        }
        //下面是图片验证
//        else if (TextUtils.isEmpty(merchantParams.imageBaseList.get(0).getId())) {
//            canSubmit = false;
//            ToastUtils.showError("请上传身份证正面!", getApplication());
//        } else if (TextUtils.isEmpty(merchantParams.imageBaseList.get(1).getId())) {
//            canSubmit = false;
//            ToastUtils.showError("请上传身份证反面!", getApplication());
//        }
        else if (TextUtils.isEmpty(merchantParams.imageBaseList.get(2).getId())) {
            canSubmit = false;
            ToastUtils.showError("请上传营业执照!", getApplication());
        }else if (TextUtils.isEmpty(merchantParams.imageBaseList.get(3).getId())) {
            canSubmit = false;
            ToastUtils.showError("请上传资质证件!", getApplication());
        }else if (TextUtils.isEmpty(merchantParams.imageMerchantBannerList.get(0).getId())) {
            canSubmit = false;
            ToastUtils.showError("请上传商家banner图!", getApplication());
        }else if (TextUtils.isEmpty(merchantParams.imageMerchantSnList.get(0).getId())) {
            canSubmit = false;
            ToastUtils.showError("请上传商家缩略图!", getApplication());
        } else if (TextUtils.isEmpty(merchantParams.imageMerchantList.get(0).getId())) {
            canSubmit = false;
            ToastUtils.showError("请上传商家图片1号图!", getApplication());
        }
//        else if (TextUtils.isEmpty(merchantParams.imageMerchantList.get(1).getId())) {
//            canSubmit = false;
//            ToastUtils.showError("请上传商家图片2号图!", getApplication());
//        } else if (TextUtils.isEmpty(merchantParams.imageMerchantList.get(2).getId())) {
//            canSubmit = false;
//            ToastUtils.showError("请上传商家图片3号图!", getApplication());
//        }
        else if (TextUtils.isEmpty(merchantParams.imageKitchenList.get(0).getId())) {
            canSubmit = false;
            ToastUtils.showError("请上传厨房图片1号图!", getApplication());
        }
//        else if (TextUtils.isEmpty(merchantParams.imageKitchenList.get(1).getId())) {
//            canSubmit = false;
//            ToastUtils.showError("请上传厨房图片2号图!", getApplication());
//        } else if (TextUtils.isEmpty(merchantParams.imageKitchenList.get(2).getId())) {
//            canSubmit = false;
//            ToastUtils.showError("请上传厨房图片3号图!", getApplication());
//        }

        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        merchantParams.token = userBean.getToken();
        merchantParams.memberName = merchantMemberAccount;
        merchantParams.memberId = userBean.getMemberId();
        merchantParams.merchantName = merchantName;
        merchantParams.merchantLegalPerson = legalName;
        merchantParams.merchantLegalPersonID = legalID;
        merchantParams.constactsMobileContact = contractMobile;
        merchantParams.constactsMobileServicet = serviceMobile;

        merchantParams.addressDetails = merchantAddress;
        merchantParams.addressProvince = dwProvinceTv.getText().toString().trim();
        merchantParams.addressCity = dwCityTv.getText().toString().trim();
        merchantParams.addressArea = merchantQuEt.getText().toString().trim();
        merchantParams.addressTown = merchantStreetEt.getText().toString().trim();

        merchantParams.changeImageListToJson();
        return canSubmit;
    }

    // 加载中动画
    private Dialog loadingDialog;

    // 加载动画
    public void showLoading() {

        View view = LayoutInflater.from(MerchantUpdateActivity.this).inflate(
                R.layout.loading, null);
        ImageView image = (ImageView) view.findViewById(R.id.iv_loading);
        Animation animation = AnimationUtils.loadAnimation(
                MerchantUpdateActivity.this, R.anim.loading_anim);
        image.startAnimation(animation);
        loadingDialog = new Dialog(MerchantUpdateActivity.this,
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String sj_account = merchantMemberAccountEt.getText().toString().trim();
        if (hasFocus) {
            // 此处为得到焦点时的处理内容
            sfchexksj_accoubt = false;
        } else {
            // 此处为失去焦点时的处理内容
            if (!TextUtils.isEmpty(sj_account)) {
                verifyMerchantAccount();
            }
        }
    }
}
