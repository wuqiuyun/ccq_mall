package clerk.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.base.CCJActivity;
import clerk.sz28yun.com.bean.AccountBean;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;
import clerk.sz28yun.com.presenter.LoginPresenter;


import butterknife.Bind;
import butterknife.OnClick;
import per.sue.gear2.dialog.GearCustomDialog;

/**
 * 登录页面
 * Created by SUE on 2016/7/8 0008.
 */
public class AccountLoginActivity extends CCJActivity implements LoginPresenter.LoginView {

    @Bind(R.id.account_et) EditText accountEt;
    @Bind(R.id.password_et) EditText passwordEt;
    private LoginPresenter loginPresenter;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, AccountLoginActivity.class);
        return intent;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        loginPresenter = new LoginPresenter(this, getApplication());
        AccountBean accountBean = GlobalDataStorageCache.getInstance().getAccountData();
        if(null != accountBean){
            accountEt.setText(accountBean.getAccount());
        }
    }


    @OnClick({R.id.login_btn, R.id.contract_tv, R.id.forget_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                if(verify()){
                    String userName ;
                    String  userPassword ;
                    userName = accountEt.getText().toString();
                    userPassword = passwordEt.getText().toString();
                    showProgressDialog("登录中...");
                    loginPresenter.login(userName, userPassword);
                }
                break;
            case R.id.contract_tv://联系客服
                contactService();
                break;
            case R.id.forget_tv:
                 startActivity(AccountForgetPwdActivity.startIntent(getActivity()));
                break;
        }
    }

    /**
     * 提交验证
     *
     * @return
     */
    private boolean verify() {
        boolean canSubmit = true;
        String userName  ;
        String  userPassword ;
        userName = accountEt.getText().toString();
        userPassword = passwordEt.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            canSubmit = false;
            accountEt.setError(getString(R.string.account_tip_account_empty));
            accountEt.requestFocus();
        } else if (TextUtils.isEmpty(userPassword)) {
            canSubmit = false;
            passwordEt.setError(getString(R.string.account_tip_password_empty));
            passwordEt.requestFocus();
        }
        return canSubmit;
    }

    @Override
    public void onSuccess(UserBean userBean) {
       // Preferences.storeUserBean(getApplication(), userBean);
        //Preferences.storeAccountBean(getApplicationContext(), new AccountBean( accountEt.getText().toString(), passwordEt.getText().toString()));
        finish();
        startActivity(MainActivity.startIntent(getActivity()));
    }

    GearCustomDialog gearCustomDialog;
    private void contactService(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_contact_service, null);
        ((TextView)view.findViewById(R.id.contact_service_tv)).setText(GlobalDataStorageCache.getInstance().getConfigData().MARKETER_SERVICE + "");
        view.findViewById(R.id.contact_service_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != gearCustomDialog){
                    gearCustomDialog.dismiss();
                }
                String phoneNum = GlobalDataStorageCache.getInstance().getConfigData().MARKETER_SERVICE;
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNum));
                //开启系统拨号器
                startActivity(intent);
            }
        });

        view.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != gearCustomDialog){
                    gearCustomDialog.dismiss();
                }

            }
        });
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(view)
                .setBottomUp(true)
                .setUseDefult(false)
                .create();
        gearCustomDialog.show();

    }

}
