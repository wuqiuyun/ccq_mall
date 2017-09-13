package clerk.sz28yun.com.activity;

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
import clerk.sz28yun.com.presenter.SettingsPresenter;

import butterknife.Bind;
import butterknife.OnClick;
import per.sue.gear2.utils.ToastUtils;

/**
 * 设置界面
 * Created by sue on 2016/12/22.
 */
public class SettingsActivity extends CCJActivity implements SettingsPresenter.SettingsView {


    @Bind(R.id.orginal_password_et)
    EditText orginalPasswordEt;
    @Bind(R.id.new_password_et)
    EditText newPasswordEt;
    @Bind(R.id.re_password_et)
    EditText rePasswordEt;
    @Bind(R.id.clean_tv)
    TextView cleanTv;

    private SettingsPresenter settingsPresenter;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_settings;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        settingsPresenter = new SettingsPresenter(getActivity(), this);
        settingsPresenter.getCache();
    }

    @OnClick({R.id.submit_btn, R.id.clean_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                if(verify()){
                    submit();
                }
                break;
            case R.id.clean_ll:
                showProgressDialog("清理中...");
                settingsPresenter.cleanCache();
                break;
        }
    }

    @Override
    public void onSuccessGetCache(String sizeStr) {
        cleanTv.setText(sizeStr);
    }

    @Override
    public void onSuccessCleanCache() {
        dismissProgressDialog();
        ToastUtils.showError("清理完毕", getApplication());
        cleanTv.setText("0M");
    }

    @Override
    public void onSuccess(String result) {
        ToastUtils.showError("更新成功", getApplication());
        finish();

    }

    private void submit(){
        String oldPassword = orginalPasswordEt.getText().toString();
        String newPaaword = newPasswordEt.getText().toString();
        showProgressDialog("正在更新密码");
        settingsPresenter.resetPassword(oldPassword, newPaaword);
    }

    private boolean  verify(){
        boolean canSubmit = true;
        String oldPassword = orginalPasswordEt.getText().toString();
        String newPaaword = newPasswordEt.getText().toString();
        String rePassword = rePasswordEt.getText().toString();
        if(TextUtils.isEmpty(oldPassword)){
            orginalPasswordEt.setError("密码不能为空。");
            orginalPasswordEt.requestFocus();
            canSubmit = false;
        }else if(TextUtils.isEmpty(newPaaword)){
            newPasswordEt.setError("新密码不能为空");
            newPasswordEt.requestFocus();
            canSubmit = false;
        }else if(newPaaword.length()<6){
            newPasswordEt.setError("密码不能少于6位");
            newPasswordEt.requestFocus();
            canSubmit = false;
        }else if(newPaaword.length() >= 20){
            newPasswordEt.setError("密码不能大于20位");
            newPasswordEt.requestFocus();
            canSubmit = false;
        }else if(!newPaaword.equals(rePassword)){
            rePasswordEt.setError("两次密码输入不一致");
            rePasswordEt.requestFocus();
            canSubmit = false;
        }
        return canSubmit;
    }


}
