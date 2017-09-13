package clerk.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import clerk.sz28yun.com.R;
import clerk.sz28yun.com.base.CCJActivity;
import clerk.sz28yun.com.base.UpdateManager;
import clerk.sz28yun.com.bean.ConfigBean;
import clerk.sz28yun.com.bean.ShareConfigBean;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;
import clerk.sz28yun.com.presenter.ConfigPresenter;
import clerk.sz28yun.com.third.UmengHelper;
import per.sue.gear2.controll.GearImageLoad;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2017/1/20.
 */
public class AboutActivity extends CCJActivity implements ConfigPresenter.ConfigView, UMShareListener {

    @Bind(R.id.qr_iv)
    ImageView qrIv;
    private ShareConfigBean shareConfigBean;
    private UmengHelper umengHelper;

    private ConfigPresenter configPresenter;

    public static Intent startIntent(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_about;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        configPresenter = new ConfigPresenter(getActivity(), this);
        configPresenter.getShareConfig();
        umengHelper = UmengHelper.getInstance();

//        ConfigBean configBean = GlobalDataStorageCache.getInstance().getConfigData();
    }

    @OnClick({ R.id.down_share_tv, R.id.check_version_tv, R.id.agreemenet_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.down_share_tv:
//                ConfigBean configBean = GlobalDataStorageCache.getInstance().getConfigData();
                if ( null != shareConfigBean) {
                    umengHelper.showBoard(getActivity(), shareConfigBean.rEG_TITLE, shareConfigBean.rEG_MX, shareConfigBean.rEG_IMG, shareConfigBean.rEG, this);
                } else {
                    ToastUtils.showError("数据异常, 请重新打开", getActivity());
                }
                break;
            case R.id.check_version_tv:
                UpdateManager.getInstance().checkUpdate(AboutActivity.this);
                break;
            case R.id.agreemenet_tv:
                break;
        }
    }



    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }


    @Override
    public void onSuccessShareConfig(ShareConfigBean configBean) {
        shareConfigBean = configBean;
        GearImageLoad.getSingleton(getActivity()).load(shareConfigBean.rEG_IMG, qrIv);
    }
}
