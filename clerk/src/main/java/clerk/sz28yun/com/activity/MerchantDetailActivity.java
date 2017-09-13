package clerk.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.api.MerchantAPI;
import clerk.sz28yun.com.base.CCJActivity;
import clerk.sz28yun.com.bean.MerchantBean;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;

import butterknife.Bind;
import per.sue.gear2.controll.GearImageLoad;
import per.sue.gear2.presenter.GearPresenter;
import per.sue.gear2.presenter.GearResultView;

/**
 * Created by sue on 2016/12/2.
 */
public class MerchantDetailActivity extends CCJActivity implements GearResultView<MerchantBean> {

    @Bind(R.id.merchant_name_tv)
    TextView merchantNameTv;
    @Bind(R.id.merchant_member_account_tv)
    TextView merchantMemberAccountTv;
    @Bind(R.id.merchant_category_tv)
    TextView merchantCategoryTv;
    @Bind(R.id.tv_dw_province)
    TextView dwProvinceTv;
    @Bind(R.id.tv_dw_city)
    TextView dwCityTv;
    @Bind(R.id.merchant_qu_tv)
    TextView merchantQuTv;
    @Bind(R.id.merchant_street_tv)
    TextView merchantStreetTv;
    @Bind(R.id.merchant_address_tv)
    TextView merchantAddressTv;
    @Bind(R.id.merchant_fr_name_tv)
    TextView merchantFrNameTv;
    @Bind(R.id.merchant_fr_id_tv)
    TextView merchantFrIdTv;
    @Bind(R.id.merchant_fr_mobile_connect_tv)
    TextView merchantFrMobileConnectTv;
    @Bind(R.id.merchant_fr_mobile_service_tv)
    TextView merchantFrMobileServiceTv;
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


    GearPresenter<MerchantBean> gearPresenter;
    private String merchantId;
    private String name;

    public static Intent startIntent(Context context, String name,  String merchantId) {
        Intent intent = new Intent(context, MerchantDetailActivity.class);
        intent.putExtra("merchantId", merchantId);
        intent.putExtra("name", name);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_merchant_detail;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        gearPresenter = new GearPresenter<MerchantBean>(getActivity(), this);
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        merchantId = getIntent().getStringExtra("merchantId");
        name = getIntent().getStringExtra("name");
        setBarTitle(name);
        gearPresenter.setObservable(new MerchantAPI().getMerchant(userBean.getToken(), userBean.getMemberId(), merchantId));
        gearPresenter.request();
    }


    @Override
    public void onSuccess(MerchantBean result) {
        bindView(result);
    }


    private void bindView(MerchantBean bean){
        if(null == bean) return ;

        merchantNameTv.setText(bean.getStoreName());
        merchantMemberAccountTv.setText(bean.getMemberName());
        merchantCategoryTv.setText(bean.getScidName());
        merchantAddressTv.setText(bean.getCompanyAddressDetail());
        merchantFrNameTv.setText(bean.getCompanyMaster());
        merchantFrIdTv.setText(bean.getIdCardNo());
        merchantFrMobileConnectTv.setText(bean.getContactsPhone());
        merchantFrMobileServiceTv.setText(bean.getCompanyPhone());
        dwProvinceTv.setText(bean.getNareaP());
        dwCityTv.setText(bean.getNareaS());
        merchantQuTv.setText(bean.getNareaQ());
        merchantStreetTv.setText(bean.getNareaZ());

        GearImageLoad.getSingleton(getActivity()).load( bean.getAddstoreImage_02(), imgIdFrontIv);
        GearImageLoad.getSingleton(getActivity()).load( bean.getAddstoreImage_01(), imgIdBackIv);
        GearImageLoad.getSingleton(getActivity()).load( bean.getAddstoreImage_03(), imgLicenseIv);
        GearImageLoad.getSingleton(getActivity()).load( bean.getAddstoreImage_04(), imgZzzjIv);

        GearImageLoad.getSingleton(getActivity()).load( bean.getThumbImage(), imgSntIv);
        GearImageLoad.getSingleton(getActivity()).load( bean.getBanner_1(), imgBannerIv);

//        if(null !=  bean.getStoreList() && bean.getStoreList().size() > 0){
//            GearImageLoad.getSingleton(getActivity()).load( bean.getStoreList().get(0), imgMerchantFirstIv);
//            if(bean.getStoreList().size() > 1)
//            GearImageLoad.getSingleton(getActivity()).load( bean.getStoreList().get(1), imgMerchantSecondIv);
//            if(bean.getStoreList().size() > 2)
//            GearImageLoad.getSingleton(getActivity()).load( bean.getStoreList().get(2), imgMerchantThirdIv);
//        }
//
//        if(null !=  bean.getStoreList() && bean.getKitchenList().size() > 0){
//            GearImageLoad.getSingleton(getActivity()).load( bean.getKitchenList().get(0), imgKitchenFirstIv);
//            if(bean.getKitchenList().size() > 1)
//            GearImageLoad.getSingleton(getActivity()).load( bean.getKitchenList().get(1), imgKitchenSecondIv);
//            if(bean.getKitchenList().size() > 12)
//            GearImageLoad.getSingleton(getActivity()).load( bean.getKitchenList().get(2), imgKitchenThirdIv);
//        }

        GearImageLoad.getSingleton(getActivity()).load( bean.getStoreListBean().getStoreList_01(), imgMerchantFirstIv);
        GearImageLoad.getSingleton(getActivity()).load( bean.getStoreListBean().getStoreList_02(), imgMerchantSecondIv);
        GearImageLoad.getSingleton(getActivity()).load( bean.getStoreListBean().getStoreList_03(), imgMerchantThirdIv);

        GearImageLoad.getSingleton(getActivity()).load( bean.getKitchenListBean().getKitchenList_01(), imgKitchenFirstIv);
        GearImageLoad.getSingleton(getActivity()).load( bean.getKitchenListBean().getKitchenList_02(), imgKitchenSecondIv);
        GearImageLoad.getSingleton(getActivity()).load( bean.getKitchenListBean().getKitchenList_03(), imgKitchenThirdIv);

    }
}
