package ccj.sz28yun.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.activity.AboutActivity;
import ccj.sz28yun.com.activity.MerchantChainActivity;
import ccj.sz28yun.com.activity.MerchantHomeUpdateActivity;
import ccj.sz28yun.com.activity.MerchantHomeUpdateImgActivity;
import ccj.sz28yun.com.activity.MerchantServiceActivity;
import ccj.sz28yun.com.activity.SettingNormalActivity;
import ccj.sz28yun.com.base.CCJFragment;
import ccj.sz28yun.com.bean.MerchantInfoBean;
import ccj.sz28yun.com.bean.MerchantInfoParams;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.presenter.MerchantSettingPresenter;
import ccj.sz28yun.com.widget.ItemValueEditView;
import per.sue.gear2.controll.GearImageLoad;
import per.sue.gear2.utils.ToastUtils;


/**
 * Created by sue on 2017/1/3.
 */
public class MerchantSettingFragment extends CCJFragment implements MerchantSettingPresenter.MerchantSettingView, ItemValueEditView.OnValueEditListener {

    @Bind(R.id.merchant_name_ive)
    ItemValueEditView merchantNameIve;
    @Bind(R.id.merchant_address_ive)
    ItemValueEditView merchantAddressIve;
    @Bind(R.id.merchant_frmobile_ive)
    ItemValueEditView merchantFrmobileIve;
    @Bind(R.id.merchant_servicemobile_ive)
    ItemValueEditView merchantServicemobileIve;
    @Bind(R.id.merchant_contract_name_ive)
    ItemValueEditView merchantContractNameIve;
    @Bind(R.id.merchant_bank_account_ive)
    ItemValueEditView merchantBankAccountIve;
    @Bind(R.id.merchant_bank_name_ive)
    ItemValueEditView merchantBankNameIve;
    @Bind(R.id.merchant_member_count_ive)
    ItemValueEditView merchantMemberCountIve;
    @Bind(R.id.merchant_vip_count_ive)
    ItemValueEditView merchantVipCountIve;
    @Bind(R.id.merchant_front_iv)
    ImageView merchantFrontIv;
    @Bind(R.id.merchant_back_iv)
    ImageView merchantBackIv;
    @Bind(R.id.merchant_licence_iv)
    ImageView merchantLicenceIv;

    private MerchantInfoParams merchantInfoParams;

    private MerchantSettingPresenter merchantSettingPresenter;
    private UserBean userBean;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_merchant;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        userBean = GlobalDataStorageCache.getInstance().getUserData();
        merchantInfoParams = new MerchantInfoParams(userBean.getToken(), userBean.getStoreId());
        merchantSettingPresenter = new MerchantSettingPresenter(getActivity(), this);
        merchantMemberCountIve.setCanEdit(false);
        merchantVipCountIve.setCanEdit(false);
        initView();
        merchantSettingPresenter.getMerchantInfo();

    }

    @OnClick({R.id.settting_service_tv, R.id.settting_merchant_home_tv, R.id.settting_chain_tv, R.id.settting_normal_tv, R.id.settting_about_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settting_service_tv:
                startActivity(MerchantServiceActivity.startIntent(getActivity()));
                break;
            case R.id.settting_merchant_home_tv:
                startActivity(MerchantHomeUpdateActivity.startIntent(getActivity()));
                break;
            case R.id.settting_chain_tv:
                startActivity(MerchantChainActivity.startIntent(getActivity()));
                break;
            case R.id.settting_normal_tv:
                startActivity(SettingNormalActivity.startIntent(getActivity()));
                break;
            case R.id.settting_about_tv:
                startActivity(AboutActivity.startIntent(getActivity()));
                break;
        }
    }

    @Override
    public void onSuccessEdit(String message) {
        ToastUtils.showError("修改成功", getActivity());
        userBean.setStoreName(merchantInfoParams.merchantName);

    }

    @Override
    public void onSuccess(MerchantInfoBean result) {
        bindView(result);
    }

    private void  bindView(MerchantInfoBean result){
        merchantNameIve.setValue(result.getStoreName());
        merchantAddressIve.setValue(result.getStoreAddress());
        merchantFrmobileIve.setValue(result.getContactsPhone());
        merchantServicemobileIve.setValue(result.getCompanyPhone());
        merchantContractNameIve.setValue(result.getSettlementBankAccountName());
        merchantBankAccountIve.setValue(result.getSettlementBankAccountNumber());
        merchantBankNameIve.setValue(result.getSettlementBankName());
        merchantMemberCountIve.setValue(String.format("%s名",  result.getInviteMemberCount()) );
        merchantVipCountIve.setValue(String.format("%s名",  result.getSumInviteVipCount()));

       if(null !=  result.getImg() &&  result.getImg().size() > 0){
           MerchantInfoBean.MerchantImage merchantImage = result.getImg().get(0);
           GearImageLoad.getSingleton(getActivity()).load(merchantImage.farenurl, merchantFrontIv);
           GearImageLoad.getSingleton(getActivity()).load(merchantImage.farenFUrl, merchantBackIv);
           GearImageLoad.getSingleton(getActivity()).load(merchantImage.yinyeurl, merchantLicenceIv);
       }
        merchantInfoParams.setValueFromMerchantInfo(result);
    }

    private void initView(){
        merchantNameIve.setOnValueEditListener(this);
        merchantAddressIve.setOnValueEditListener(this);
        merchantFrmobileIve.setOnValueEditListener(this);
        merchantServicemobileIve.setOnValueEditListener(this);
        merchantContractNameIve.setOnValueEditListener(this);
        merchantBankAccountIve.setOnValueEditListener(this);
        merchantBankNameIve.setOnValueEditListener(this);

    }

    @Override
    public void onEditValue(ItemValueEditView view, String content) {
        switch (view.getId()){
            case R.id.merchant_name_ive:
                merchantInfoParams.merchantName = content;
                break;
            case R.id.merchant_address_ive:
                merchantInfoParams.merchantAddress = content;
                break;
            case R.id.merchant_frmobile_ive:
                merchantInfoParams.frMobile = content;
                break;
            case R.id.merchant_servicemobile_ive:
                merchantInfoParams.serviceMobile = content;
                break;
            case R.id.merchant_contract_name_ive:
                merchantInfoParams.contractName = content;
                break;
            case R.id.merchant_bank_account_ive:
                merchantInfoParams.bankAccount = content;
                break;
            case R.id.merchant_bank_name_ive:
                merchantInfoParams.bankName = content;
                break;
            default:
                break;
        }
        merchantSettingPresenter.editMerchantInfo(merchantInfoParams);
    }
}
