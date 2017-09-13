package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.BroadcastAction;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.presenter.SettingsNormalPresenter;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2017/1/3.
 */
public class SettingNormalActivity extends CCJActivity implements SettingsNormalPresenter.SettingsView {


    @Bind(R.id.password_original_et)
    EditText passwordOriginalEt;
    @Bind(R.id.password_new_et)
    EditText passwordNewEt;
    @Bind(R.id.password_re_et)
    EditText passwordReEt;
    @Bind(R.id.cache_clean_tv)
    TextView cacheCleanTv;

    private SettingsNormalPresenter settingsNormalPresenter;

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, SettingNormalActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_setting_normal;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        settingsNormalPresenter = new SettingsNormalPresenter(getActivity(), this);
    }



    @OnClick({R.id.employee_tv, R.id.help_tv, R.id.cache_clean_ll, R.id.submit_btn, R.id.login_out_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.employee_tv:
                startActivity(EmployeeListActivity.startIntent(getActivity()));
                break;
            case R.id.help_tv:
                Intent intent = new Intent(SettingNormalActivity.this, WebviewActivity.class);
                intent.putExtra("url", GlobalDataStorageCache.getInstance().getConfigData().STOREHELP);
                intent.putExtra("title", "使用帮助");
                startActivity(intent);
                break;
            case R.id.cache_clean_ll:
                showProgressDialog("清理中...");
                settingsNormalPresenter.cleanCache();
                break;
            case R.id.submit_btn:
                if(verify()){
                    submit();
                }
                break;
            case R.id.login_out_btn:
                GlobalDataStorageCache.getInstance().storeAccountData(null);
                sendBroadcast(new Intent(BroadcastAction.ACTION_LOGIN_OUT));
                finish();
                break;
        }
    }

    @Override
    public void onSuccessGetCache(String sizeStr) {
        cacheCleanTv.setText(sizeStr);
    }

    @Override
    public void onSuccessCleanCache() {
        dismissProgressDialog();
        ToastUtils.showError("清理完毕", getApplication());
        cacheCleanTv.setText("0M");
    }

    @Override
    public void onSuccess(String result) {
        ToastUtils.showError("更新成功", getApplication());
    }

    private void submit(){
        String oldPassword = passwordOriginalEt.getText().toString();
        String newPaaword = passwordNewEt.getText().toString();
        showProgressDialog("正在更新密码");
        settingsNormalPresenter.resetPassword(oldPassword, newPaaword);
    }

    private boolean  verify(){
        boolean canSubmit = true;
        String oldPassword = passwordOriginalEt.getText().toString();
        String newPaaword = passwordNewEt.getText().toString();
        String rePassword = passwordReEt.getText().toString();
        if(TextUtils.isEmpty(oldPassword)){
            passwordOriginalEt.setError("密码不能为空。");
            passwordOriginalEt.requestFocus();
            canSubmit = false;
        }else if(TextUtils.isEmpty(newPaaword)){
            passwordNewEt.setError("新密码不能为空");
            passwordNewEt.requestFocus();
            canSubmit = false;
        }else if(newPaaword.length()<6){
            passwordNewEt.setError("密码不能少于6位");
            passwordNewEt.requestFocus();
            canSubmit = false;
        }else if(newPaaword.length() >= 20){
            passwordNewEt.setError("密码不能大于20位");
            passwordNewEt.requestFocus();
            canSubmit = false;
        }else if(!newPaaword.equals(rePassword)){
            passwordReEt.setError("两次密码输入不一致");
            passwordReEt.requestFocus();
            canSubmit = false;
        }
        return canSubmit;
    }
}
