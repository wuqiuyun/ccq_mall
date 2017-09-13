package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.presenter.SettingsNormalPresenter;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by meihuali on 2017/6/30.
 */

public class EditpasswordActivity extends CCJActivity implements SettingsNormalPresenter.SettingsView {

    private SettingsNormalPresenter settingsNormalPresenter;

    @Bind(R.id.password_original_et)
    EditText passwordOriginalEt;
    @Bind(R.id.password_new_et)
    EditText passwordNewEt;
    @Bind(R.id.password_re_et)
    EditText passwordReEt;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_editpassword;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, EditpasswordActivity.class);
        return intent;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        settingsNormalPresenter = new SettingsNormalPresenter(EditpasswordActivity.this, this);

    }

    @OnClick({R.id.submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                if (verify()) {
                    submit();
                }
                break;
        }
    }

    private boolean verify() {
        boolean canSubmit = true;
        String oldPassword = passwordOriginalEt.getText().toString();
        String newPaaword = passwordNewEt.getText().toString();
        String rePassword = passwordReEt.getText().toString();
        if (TextUtils.isEmpty(oldPassword)) {
            passwordOriginalEt.setError("密码不能为空。");
            passwordOriginalEt.requestFocus();
            canSubmit = false;
        } else if (TextUtils.isEmpty(newPaaword)) {
            passwordNewEt.setError("新密码不能为空");
            passwordNewEt.requestFocus();
            canSubmit = false;
        } else if (newPaaword.length() < 6) {
            passwordNewEt.setError("密码不能少于6位");
            passwordNewEt.requestFocus();
            canSubmit = false;
        } else if (newPaaword.length() >= 20) {
            passwordNewEt.setError("密码不能大于20位");
            passwordNewEt.requestFocus();
            canSubmit = false;
        } else if (!newPaaword.equals(rePassword)) {
            passwordReEt.setError("两次密码输入不一致");
            passwordReEt.requestFocus();
            canSubmit = false;
        }
        return canSubmit;
    }

    private void submit() {
        String oldPassword = passwordOriginalEt.getText().toString();
        String newPaaword = passwordNewEt.getText().toString();
        showProgressDialog("正在更新密码");
        settingsNormalPresenter.resetPassword(oldPassword, newPaaword);
    }

    @Override
    public void onSuccessGetCache(String sizeStr) {

    }

    @Override
    public void onSuccessCleanCache() {

    }

    @Override
    public void onSuccess(String result) {
        dismissProgressDialog();
        ToastUtils.showError("更新成功", getActivity());
        finish();
    }
}
