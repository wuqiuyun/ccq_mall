package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.MerchantChainParams;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.presenter.MerchantChainAddPresenter;
import per.sue.gear2.utils.MD5Utils;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2017/1/3.
 */
public class MerchantChainAddActivity extends CCJActivity implements MerchantChainAddPresenter.MerchantChainAddView {


    @Bind(R.id.merchant_name_et)
    EditText merchantNameEt;
    @Bind(R.id.merchant_address_et)
    EditText merchantAddressEt;
    @Bind(R.id.merchant_service_mobile_et)
    EditText merchantServiceMobileEt;
    @Bind(R.id.merchant_manager_name_et)
    EditText merchantManagerNameEt;
    @Bind(R.id.merchant_manager_mobile_et)
    EditText merchantManagerMobileEt;
    @Bind(R.id.merchant_manager_password_et)
    EditText merchantManagerPasswordEt;

    private MerchantChainParams merchantChainParams;
    private MerchantChainAddPresenter merchantChainAddPresenter;

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, MerchantChainAddActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_merchant_chain_add;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        merchantChainParams = new MerchantChainParams();
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        merchantChainParams.storeId = userBean.getStoreId();
        merchantChainParams.token = userBean.getToken();

        merchantChainAddPresenter = new MerchantChainAddPresenter(getActivity(), this);
    }



    @OnClick(R.id.submit_btn)
    public void onClick() {
        if(verify()){
            submit();
        }
    }

    private void submit(){
        merchantChainAddPresenter.submit(merchantChainParams);
    }

    private boolean verify(){
        boolean cansubmit = true;
        String merchantName = merchantNameEt.getText().toString();
        String merchantAddress = merchantAddressEt.getText().toString();
        String serviceMobile = merchantServiceMobileEt.getText().toString();
        String managerName = merchantManagerNameEt.getText().toString();
        String managerMobile = merchantManagerMobileEt.getText().toString();
        String password = merchantManagerPasswordEt.getText().toString();
        if(TextUtils.isEmpty(merchantName)){
            merchantNameEt.requestFocus();
            merchantNameEt.setError("请输入店名");
            cansubmit = false;
        }else if(TextUtils.isEmpty(merchantAddress)){
            merchantAddressEt.requestFocus();
            merchantAddressEt.setError("请输入地址");
            cansubmit = false;
        }else if(TextUtils.isEmpty(serviceMobile)){
            merchantAddressEt.requestFocus();
            merchantAddressEt.setError("请输入服务电话");
            cansubmit = false;
        }else if(TextUtils.isEmpty(managerName)){
            merchantAddressEt.requestFocus();
            merchantAddressEt.setError("请输入店长账号");
            cansubmit = false;
        }else if(TextUtils.isEmpty(managerMobile)){
            merchantAddressEt.requestFocus();
            merchantAddressEt.setError("请输入店长电话");
            cansubmit = false;
        }else if(TextUtils.isEmpty(password)){
            merchantAddressEt.requestFocus();
            merchantAddressEt.setError("请输入密码");
            cansubmit = false;
        }else{
            merchantChainParams.address = merchantAddress;
            merchantChainParams.contactTelephone = serviceMobile;
            merchantChainParams.memberName = managerName;
            merchantChainParams.memberPwd = MD5Utils.encrypt(password);
            merchantChainParams.memberTelephone = managerMobile;
            merchantChainParams.storeName = merchantName;
        }
        return cansubmit;
    }

    @Override
    public void onSuccess(String result) {
        ToastUtils.showError("提交成功", getApplication());
        finish();

    }
}
