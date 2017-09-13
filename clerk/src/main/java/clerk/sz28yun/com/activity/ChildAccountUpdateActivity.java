package clerk.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.base.CCJActivity;
import clerk.sz28yun.com.bean.ChildAccountBean;
import clerk.sz28yun.com.presenter.ChildAccountPresenter;

import butterknife.Bind;
import butterknife.OnClick;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.utils.VerifyUtils;

/**
 * Created by sue on 2016/11/17.
 */
public class ChildAccountUpdateActivity extends CCJActivity implements ChildAccountPresenter.ChildAccountView {

    @Bind(R.id.account_et)
    EditText accountEt;
    @Bind(R.id.passwd_et)
    EditText passwdEt;
    @Bind(R.id.name_et)
    EditText nameEt;
    @Bind(R.id.mobile_et)
    EditText mobileEt;

    private String childId;
    private ChildAccountBean childAccountBean;
    private ChildAccountPresenter childAccountPresenter;
    private boolean xz = true;
    public static Intent startIntent(Context context) {
        return startIntent(context, null);
    }

    public static Intent startIntent(Context context, String childId) {
        Intent intent;
        intent = new Intent(context, ChildAccountUpdateActivity.class);
        intent.putExtra("childId", childId);
        return intent;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_child_account_update;
    }

    @Override
    public boolean showBackView() {
        return true; //显示返回按钮
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        childAccountPresenter = new ChildAccountPresenter(getApplication(), this);
        childId = getIntent().getStringExtra("childId");
        if (TextUtils.isEmpty(childId)) {
            setBarTitle("新增子账号");
            xz = true;
        } else {
            setBarTitle("修改子账号");
            xz = false;
            childAccountPresenter.getChildAccount(childId);
        }
    }

    private void bindView() {
        if (null == childAccountBean) {
            ToastUtils.showError("获取数据出问题, 请推出重试", getApplication());
        }

        accountEt.setText(childAccountBean.getMemberName());
        accountEt.setEnabled(false);
        if (!TextUtils.isEmpty(childAccountBean.getMemberPassword()))
            passwdEt.setText(childAccountBean.getMemberPassword());
        if (!TextUtils.isEmpty(childAccountBean.getMemberTruename()))
            nameEt.setText(childAccountBean.getMemberTruename());
        if (!TextUtils.isEmpty(childAccountBean.getMemberMobile()))
            mobileEt.setText(childAccountBean.getMemberMobile());
    }

    private boolean verifyData() {
        boolean canSubmit = true;
        String account = accountEt.getText().toString();
        String password = passwdEt.getText().toString();
        String nick = nameEt.getText().toString();
        String mobile = mobileEt.getText().toString();

        if (xz){
            if (TextUtils.isEmpty(account)) {
                canSubmit = false;
                accountEt.requestFocus();
                accountEt.setError("登录账号不能为空！");
            } else if (!VerifyUtils.isAccount(account)) {
                canSubmit = false;
                accountEt.requestFocus();
                accountEt.setError("登录账号不能含有特殊字符!");
            }
        }
//        else if (TextUtils.isEmpty(password)) {
//            canSubmit = false;
//            passwdEt.requestFocus();
//            passwdEt.setError("登录密码不能为空!");
//        }
//        else if (password.length() != 32 && password.length() < 6) {
//            canSubmit = false;
//            passwdEt.requestFocus();
//            passwdEt.setError("登录密码最少6位");
//        } else if (password.length() == 32) {
//            canSubmit = false;
//            passwdEt.requestFocus();
//            passwdEt.setError("登录密码不能等于32位");//这个主要防止我后面根据是否32位进行加密
//        }
        if (TextUtils.isEmpty(nick)) {
            canSubmit = false;
            nameEt.requestFocus();
            nameEt.setError("姓名不能为空!");
        } else if (TextUtils.isEmpty(mobile)) {
            canSubmit = false;
            mobileEt.requestFocus();
            mobileEt.setError("电话不能为空!");
        } else if (!VerifyUtils.isMobileNo(mobile)) {
            canSubmit = false;
            mobileEt.requestFocus();
            mobileEt.setError("电话格式不对!");
        }
        return canSubmit;
    }

    @OnClick(R.id.submit_btn)
    public void onClick() {
        if (verifyData()) {
            submit();
        }
    }

    private void submit() {
        String account = accountEt.getText().toString();
        String password = passwdEt.getText().toString();
        String nick = nameEt.getText().toString();
        String mobile = mobileEt.getText().toString();
        if (TextUtils.isEmpty(childId)) {
            if (TextUtils.isEmpty(password)) {
                passwdEt.requestFocus();
                passwdEt.setError("登录密码不能为空!");
            } else if (password.length() != 32 && password.length() < 6) {
                passwdEt.requestFocus();
                passwdEt.setError("登录密码最少6位");
            } else if (password.length() == 32) {
                passwdEt.requestFocus();
                passwdEt.setError("登录密码不能等于32位");//这个主要防止我后面根据是否32位进行加密
            }else{
                childAccountPresenter.addChildAccount(account, password, nick, mobile);
            }
        } else {
            if (!TextUtils.isEmpty(password)) {
                if (password.length() != 32 && password.length() < 6) {
                    passwdEt.requestFocus();
                    passwdEt.setError("登录密码最少6位");
                } else if (password.length() == 32) {
                    passwdEt.requestFocus();
                    passwdEt.setError("登录密码不能等于32位");//这个主要防止我后面根据是否32位进行加密
                }else{
                    childAccountPresenter.updateChildAccount(childId, password, "", "");
                }
            } else if (!nick.equals(childAccountBean.getMemberTruename()) || !mobile.equals(childAccountBean.getMemberMobile())) {
                childAccountPresenter.updateChildAccount(childId, "", nick, mobile);
            }
        }
    }

    @Override
    public void onSubmit(String message) {
        if (TextUtils.isEmpty(childId)) {
            ToastUtils.showError("新增成功", getApplication());
        } else {
            ToastUtils.showError("修改成功", getApplication());
        }
        finish();
    }

    @Override
    public void onSuccess(ChildAccountBean result) {
        this.childAccountBean = result;
        bindView();
    }
}
