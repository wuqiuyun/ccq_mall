package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.help.CodeHelper;
import ccj.sz28yun.com.presenter.ForgetPresenter;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.utils.VerifyUtils;

/**
 * Created by SUE on 2016/7/8 0008.
 */
public class AccountForgetPwdActivity extends CCJActivity implements ForgetPresenter.ForgetView {

    @Bind(R.id.account_et) EditText accountEt;
    @Bind(R.id.code_et) EditText codeEt;
    @Bind(R.id.code_get_tv) TextView codeGetTv;
    @Bind(R.id.password_et) EditText passwordEt;
    @Bind(R.id.password_rewrite_et) EditText passwordReWriteEt;

    @Bind(R.id.forget_step_first_pannel) View firstPannelView;
    @Bind(R.id.forget_step_second_pannel) View secondPannelView;

    private CodeHelper codeHelper;
    private ForgetPresenter forgetPresenter;


    private String member_id;


    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, AccountForgetPwdActivity.class);
        return intent;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_forget;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        codeHelper = new CodeHelper(getApplication(), codeGetTv);
        forgetPresenter = new ForgetPresenter(getApplication(), this);
        setBarTitle("重置密码");
        secondPannelView.setVisibility(View.GONE);
    }

    @Override
    public void onCode(String code) {
        dismissProgressDialog();
        codeHelper. notEnableCodeView();
    }

    @Override
    public void onCodeCorrected(String code) {
        dismissProgressDialog();
        nextStep();
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        codeHelper.enableCodeView();
        dismissProgressDialog();
    }

    @Override
    public void onSuccess(String msg) {
        if(msg == null){
            ToastUtils.showError("重置密码成功.", getApplicationContext());
            finish();
        } else if(msg.contains("member_id") && msg.contains("member_mobile")){
            try {
                JSONObject object = new JSONObject(msg);
                member_id = object.getString("member_id");
                String member_mobile = object.getString("member_mobile");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            getCode();
        }
    }

    @OnClick({ R.id.code_get_tv, R.id.submit_btn,  R.id.next_btn, R.id.back_btn, R.id.back_login_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.code_get_tv:

                String phoneNumber = accountEt.getText().toString();
                if(VerifyUtils.isMobileNo(phoneNumber)){
                    forgetPresenter.checkPhone(phoneNumber);
                }else{
                    accountEt.setError("手机格式错误。");
                    accountEt.requestFocus();
                }

//                getCode();
                break;
            case R.id.submit_btn:
                resetPassword();
                break;

            case R.id.back_btn:
            case R.id.back_login_tv:
                finish();
                break;

            case R.id.next_btn:
                if(vertifyNext()){
                    requestVerifyCode();
                }

                break;
            default:
                break;
        }
    }


    /**
     * 校验验证码
     */
    private void requestVerifyCode(){
        String phoneCode = codeEt.getText().toString();
        showProgressDialog("校验验证码");
        forgetPresenter.verifyCode(phoneCode);
    }

    /**
     * 下一步
     */
    private void nextStep(){
        firstPannelView.setVisibility(View.GONE);
        secondPannelView.setVisibility(View.VISIBLE);
    }

    /**
     * 重置密码
     */
    private void resetPassword(){
        String phoneNumber = accountEt.getText().toString();
        String phoneCode = codeEt.getText().toString();
        String userPassword = passwordEt.getText().toString();
        String passwordRewrite = passwordReWriteEt.getText().toString();
        if(vertifyInput(phoneNumber, userPassword, passwordRewrite, phoneCode) ) {
            forgetPresenter.forgetPasswd(userPassword, passwordRewrite ,member_id);
        }

    }

    /**
     * 获取验证码
     */
    public void getCode() {
        String phoneNumber = accountEt.getText().toString();
        if(VerifyUtils.isMobileNo(phoneNumber)){
            if (codeHelper.canGetCode()) {
                showProgressDialog("正在获取验证码...");
                forgetPresenter.sendCode(phoneNumber);

            }else{
                ToastUtils.showError("请稍等.", getApplicationContext());
            }
        }else{
            accountEt.setError("手机格式错误。");
            accountEt.requestFocus();
        }
    }

    /**
     * 下一步前验证下数据
     * @return
     */
    public boolean vertifyNext(){
        boolean canNext = true;
        String code = codeEt.getText().toString();
        String account = accountEt.getText().toString();
        if(TextUtils.isEmpty(account)){
            accountEt.setError("手机号不能为空。");
            accountEt.requestFocus();
            canNext = false;
        }else if(!VerifyUtils.isMobileNo(account)){
            accountEt.setError("手机格式错误。");
            accountEt.requestFocus();
            canNext = false;
        } else if(TextUtils.isEmpty(code)){
            codeEt.setError("验证码不能为空");
            codeEt.requestFocus();
             canNext = false;
        }else if(!VerifyUtils.isCode(code)){
            codeEt.setError("验证码格式错误");
            codeEt.requestFocus();
             canNext = false;
        }
         return canNext;
    }


    public boolean vertifyInput(String account , String password, String passwordWrite, String code){
        boolean canSubmit = true;

        if(TextUtils.isEmpty(account)){
            accountEt.setError("手机号不能为空。");
            accountEt.requestFocus();
            canSubmit = false;
        }else if(!VerifyUtils.isMobileNo(account)){
            accountEt.setError("手机格式错误。");
            accountEt.requestFocus();
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
            passwordReWriteEt.setError("重新密码不能为空");
            passwordReWriteEt.requestFocus();
            canSubmit = false;
        }else if(!passwordWrite.equals(password)){
            passwordReWriteEt.setError("两次密码输入不一致");
            passwordReWriteEt.requestFocus();
            canSubmit = false;
        }
        return  canSubmit;
    }
}
