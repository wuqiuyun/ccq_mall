package clerk.sz28yun.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.base.CCJActivity;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;
import clerk.sz28yun.com.help.CodeHelper;
import clerk.sz28yun.com.presenter.MerchantAccountRegistPresenter;

import butterknife.Bind;
import butterknife.OnClick;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.utils.VerifyUtils;

/**
 * Created by sue on 2016/12/1.
 */
public class MerchantAccountRegistActivity extends CCJActivity implements MerchantAccountRegistPresenter.MerchantAccountRegistView {

    @Bind(R.id.mobile_et)
    EditText mobileEt;
    @Bind(R.id.password_et)
    EditText passwordEt;
    @Bind(R.id.password_reset_et)
    EditText passwordResetEt;
    @Bind(R.id.code_et)
    EditText codeEt;
    @Bind(R.id.code_btn)
    TextView codeTv;
    @Bind(R.id.email_et)
    EditText emailEt;
    @Bind(R.id.recommend_name_tv)
    TextView recommendNameTv;
    @Bind(R.id.recommend_id_tv)
    TextView recommendIdTv;

    private CodeHelper codeHelper;
    private MerchantAccountRegistPresenter merchantAccountRegistPresenter;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, MerchantAccountRegistActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_merchant_account_regist;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        codeHelper = new CodeHelper(getApplication(), codeTv);
        merchantAccountRegistPresenter = new MerchantAccountRegistPresenter(getApplication(), this);
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
        recommendNameTv.setText(userBean.getMemberName());
        recommendIdTv.setText(userBean.getMemberId());
    }


    @OnClick({R.id.code_btn, R.id.submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.code_btn:
                getCode();
                break;
            case R.id.submit_btn:
                regist();
                break;
            default:
                break;
        }

    }



    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        codeHelper.enableCodeView();
        dismissProgressDialog();
    }

    @Override
    public void onCode(String code) {
        dismissProgressDialog();
        codeHelper. notEnableCodeView();
    }

    @Override
    public void onSuccess(String result) {
        ToastUtils.showError("注册成功.", getApplicationContext());
        Intent intent = new Intent();
        intent.putExtra("data", mobileEt.getText().toString());
        setResult(Activity.RESULT_OK, intent );
        finish();
    }


    /**
     * 获取验证码
     */
    public void getCode() {
        String phoneNumber = mobileEt.getText().toString();
        if(VerifyUtils.isMobileNo(phoneNumber)){
            if (codeHelper.canGetCode()) {
                showProgressDialog("正在获取验证码...");
                merchantAccountRegistPresenter.sendCode(phoneNumber);

            }else{
                ToastUtils.showError("请稍等.", getApplicationContext());
            }
        }else{
            mobileEt.setError("手机格式错误。");
            mobileEt.requestFocus();
        }
    }


    private void regist(){
        String phoneNumber = mobileEt.getText().toString();
        String phoneCode = codeEt.getText().toString();
        String userPassword = passwordEt.getText().toString();
        String passwordRewrite = passwordResetEt.getText().toString();
        String eamil = emailEt.getText().toString();
        if(vertifyInput(phoneNumber, userPassword, passwordRewrite, phoneCode) ) {
            merchantAccountRegistPresenter.regist(phoneNumber, userPassword,  phoneCode, eamil);
        }
    }

    public boolean vertifyInput(String account , String password, String passwordWrite, String code){
        boolean canSubmit = true;

        if(TextUtils.isEmpty(account)){
            mobileEt.setError("手机号不能为空。");
            mobileEt.requestFocus();
            canSubmit = false;
        }else if(!VerifyUtils.isMobileNo(account)){
            mobileEt.setError("手机格式错误。");
            mobileEt.requestFocus();
            canSubmit = false;
        }else if(TextUtils.isEmpty(code)){
            codeEt.setError("验证码不能为空");
            codeEt.requestFocus();
            canSubmit = false;
        }else if(!VerifyUtils.isCode(code)){
            codeEt.setError("验证码格式错误");
            codeEt.requestFocus();
            canSubmit = false;
        }else if(TextUtils.isEmpty(password)){
            passwordEt.setError("密码不能为空。");
            passwordEt.requestFocus();
            canSubmit = false;
        }else if(password.length()<6){
            passwordEt.setError("密码不能少于6位");
            passwordEt.requestFocus();
            canSubmit = false;
        }else if(password.length()>=20){
            passwordEt.setError("密码不能大于20位");
            passwordEt.requestFocus();
            canSubmit = false;
        }else if(TextUtils.isEmpty(passwordWrite)){
            passwordResetEt.setError("重新密码不能为空");
            passwordResetEt.requestFocus();
            canSubmit = false;
        }else if(passwordWrite.equals(password)){
            passwordResetEt.setError("两次密码输入不一致");
            passwordResetEt.requestFocus();
            canSubmit = false;
        }
        return  canSubmit;
    }
}
