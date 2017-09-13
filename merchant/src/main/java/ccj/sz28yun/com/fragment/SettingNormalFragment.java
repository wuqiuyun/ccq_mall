package ccj.sz28yun.com.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.activity.AboutActivity;
import ccj.sz28yun.com.activity.EditpasswordActivity;
import ccj.sz28yun.com.activity.EmployeeListActivity;
import ccj.sz28yun.com.activity.MerchantChainActivity;
import ccj.sz28yun.com.activity.MerchantHomeUpdateActivity;
import ccj.sz28yun.com.activity.MerchantServiceActivity;
import ccj.sz28yun.com.activity.SettingNormalActivity;
import ccj.sz28yun.com.activity.WebviewActivity;
import ccj.sz28yun.com.base.BroadcastAction;
import ccj.sz28yun.com.base.CCJFragment;
import ccj.sz28yun.com.bean.MerchantInfoBean;
import ccj.sz28yun.com.bean.MerchantInfoParams;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.presenter.MerchantSettingPresenter;
import ccj.sz28yun.com.presenter.SettingsNormalPresenter;
import ccj.sz28yun.com.widget.ItemValueEditView;
import per.sue.gear2.controll.GearImageLoad;
import per.sue.gear2.utils.ToastUtils;


/**
 * 通用设置
 */
public class SettingNormalFragment extends CCJFragment implements SettingsNormalPresenter.SettingsView {


    @Bind(R.id.cache_clean_tv)
    TextView cacheCleanTv;

    private SettingsNormalPresenter settingsNormalPresenter;

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, SettingNormalActivity.class);
        return intent;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_setting_normal;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        settingsNormalPresenter = new SettingsNormalPresenter(getActivity(), this);
    }



    @OnClick({R.id.employee_tv, R.id.help_tv, R.id.editpassword_tv, R.id.cache_clean_ll, R.id.login_out_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.employee_tv:
                startActivity(EmployeeListActivity.startIntent(getActivity()));
                break;
            case R.id.help_tv:
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url", GlobalDataStorageCache.getInstance().getConfigData().STOREHELP);
                intent.putExtra("title", "使用帮助");
                startActivity(intent);
                break;
            case R.id.cache_clean_ll:
                showProgressDialog("清理中...");
                settingsNormalPresenter.cleanCache();
                break;
            case R.id.editpassword_tv:
                startActivity(EditpasswordActivity.startIntent(getActivity()));
                break;
            case R.id.login_out_btn:
                GlobalDataStorageCache.getInstance().storeAccountData(null);
                GlobalDataStorageCache.getInstance().storeUserData(null);
                getActivity().sendBroadcast(new Intent(BroadcastAction.ACTION_LOGIN_OUT));
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
        ToastUtils.showError("清理完毕", getActivity());
        cacheCleanTv.setText("0M");
    }

    @Override
    public void onSuccess(String result) {
//        dismissProgressDialog();
//        ToastUtils.showError("更新成功", getActivity());
    }




}
