package clerk.sz28yun.com.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.activity.AboutActivity;
import clerk.sz28yun.com.activity.AccountLoginActivity;
import clerk.sz28yun.com.activity.ChildAccountUpdateActivity;
import clerk.sz28yun.com.activity.PerformanceChildActivity;
import clerk.sz28yun.com.activity.SettingsActivity;
import clerk.sz28yun.com.adapter.ChildAccountAdapter;
import clerk.sz28yun.com.base.CCJFragment;
import clerk.sz28yun.com.bean.ChildAccountBean;
import clerk.sz28yun.com.bean.ConfigBean;
import clerk.sz28yun.com.bean.ShareConfigBean;
import clerk.sz28yun.com.bean.UserBean;
import clerk.sz28yun.com.cache.GlobalDataCache;
import clerk.sz28yun.com.cache.GlobalDataStorageCache;
import clerk.sz28yun.com.presenter.MinePresenter;
import clerk.sz28yun.com.third.UmengHelper;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import per.sue.gear2.controll.GearImageLoad;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2016/11/17.
 */
public class MainMineFragment extends CCJFragment implements View.OnClickListener, MinePresenter.MineView, UMShareListener {

    @Bind(R.id.mine_child_list_view)
    ListView mineChildListView;

    private MineViewHolder mineViewHolder;
    private ChildAccountAdapter childAccountAdapter;
    private MinePresenter minePresenter ;
    private UmengHelper umengHelper;
    private UserBean userBean;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        minePresenter  = new MinePresenter(getActivity(), this);
        getView().findViewById(R.id.mine_setting_iv).setOnClickListener(this);
        getView().findViewById(R.id.mine_share_iv).setOnClickListener(this);
        getView().findViewById(R.id.mine_loginout_iv).setOnClickListener(this);
        View topView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine_top, null);
        mineViewHolder = new MineViewHolder(topView);
        topView.findViewById(R.id.mine_audit_ll).setOnClickListener(this);
        topView.findViewById(R.id.mine_about_tv).setOnClickListener(this);
        topView.findViewById(R.id.mine_share_merchant_tv).setOnClickListener(this);
        topView.findViewById(R.id.mine_share_user_tv).setOnClickListener(this);
        topView.findViewById(R.id.mine_child_add_tv).setOnClickListener(this);
        mineChildListView.addHeaderView(topView);
        childAccountAdapter = new ChildAccountAdapter(getActivity());
        mineChildListView.setAdapter(childAccountAdapter);
        childAccountAdapter.setOnChildAccountAdapterListener(new ChildAccountAdapter.OnChildAccountAdapterListener() {
            @Override
            public void onClickEdit(ChildAccountBean bean) {//编辑
                startActivity(ChildAccountUpdateActivity.startIntent(getActivity(),  bean.getMemberId()));
            }
            @Override
            public void onClickSearch(ChildAccountBean bean) {//查看子账号业绩
                startActivity(PerformanceChildActivity.startIntent(getActivity(),bean.getMemberTruename(),   bean.getMemberId()));
            }
        });

//        if( null == GlobalDataCache.getInstance().getShareConfigData()){
            minePresenter.getShareConfig();
//        }
        bindView();
        umengHelper = UmengHelper.getInstance();

    }

    @Override
    public void onResume() {
        super.onResume();
        minePresenter.requestData();
    }

    private void bindView(){
        if(null == mineViewHolder) return;
        userBean = GlobalDataStorageCache.getInstance().getUserData();
        ConfigBean configBean = GlobalDataStorageCache.getInstance().getConfigData();
        String name = TextUtils.isEmpty(userBean.getMemberTruename()) ? userBean.getMemberName() : userBean.getMemberTruename() ;
//        if( null == GlobalDataCache.getInstance().getShareConfigData()){
//            minePresenter.getShareConfig();
//        }
//        GearImageLoad.getSingleton(getActivity()).load(configBean.MARKETER_QRCODE_IMG_AZ, mineViewHolder.mineQrIv);
        mineViewHolder.nameTv.setText(name);

    }

    @OnClick({R.id.mine_setting_iv, R.id.mine_share_iv, R.id.mine_loginout_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_setting_iv:
                startActivity(SettingsActivity.startIntent(getActivity()));
                break;
            case R.id.mine_share_iv:
                ShareConfigBean shareConfigBean3 = GlobalDataCache.getInstance().getShareConfigData();
                if(null != shareConfigBean3){
                    umengHelper.showBoard(getActivity(), shareConfigBean3.rEG_TITLE , shareConfigBean3.rEG_MX, shareConfigBean3.rEG_IMG,  shareConfigBean3.rEG, this );
                }else{
                    ToastUtils.showError("数据异常, 请重新打开", getActivity());
                }

                break;
            case R.id.mine_loginout_iv:
                GlobalDataStorageCache.getInstance().storeUserData(null);
                getActivity().finish();
                startActivity(AccountLoginActivity.startIntent(getActivity()));
                break;
            case R.id.mine_about_tv:
                startActivity(AboutActivity.startIntent(getActivity()));
                break;
            case R.id.mine_audit_ll:
                contactService();
                break;
            case R.id.mine_share_merchant_tv:
                ShareConfigBean shareConfigBean1 = GlobalDataCache.getInstance().getShareConfigData();
                if(null != shareConfigBean1){
                    umengHelper.showBoard(getActivity(), shareConfigBean1.sTORE_TITLE , shareConfigBean1.sTORE_MX,  shareConfigBean1.sTORE_FX, shareConfigBean1.sTORE_QRCODE_IMG_AZ_URL, this );
                }else{
                    ToastUtils.showError("数据异常, 请重新打开", getActivity());
                }



                break;
            case R.id.mine_share_user_tv:
                ShareConfigBean shareConfigBean2 = GlobalDataCache.getInstance().getShareConfigData();
                if(null != shareConfigBean2){
                    umengHelper.showBoard(getActivity(), shareConfigBean2.mALL_TITLE , shareConfigBean2.mALL_MX,  shareConfigBean2.mALL_FX, shareConfigBean2.mALL_QRCODE_IMG_AZ_URL, this );
                }else{
                    ToastUtils.showError("数据异常, 请重新打开", getActivity());
                }

                break;
            case R.id.mine_child_add_tv:
                if (userBean.getYunyingChild() != 1){
                    startActivity(ChildAccountUpdateActivity.startIntent(getActivity()));
                }else{
                    ToastUtils.showError("无权使用", getActivity());
                }
                break;
        }
    }

    @Override
    public void onSuccess(ArrayList<ChildAccountBean> result) {
        childAccountAdapter.setList(result);
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
        GlobalDataCache.getInstance().setShareConfigData(configBean);
        GearImageLoad.getSingleton(getActivity()).load(configBean.rEG_IMG, mineViewHolder.mineQrIv);
    }

    static class MineViewHolder {
        @Bind(R.id.mine_qr_iv)
        ImageView mineQrIv;
        @Bind(R.id.name_tv)
        TextView nameTv;

        MineViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        minePresenter.destroy();
    }


    GearCustomDialog gearCustomDialog;
    private void contactService(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_contact_service, null);
        ((TextView)view.findViewById(R.id.contact_service_tv)).setText(GlobalDataStorageCache.getInstance().getConfigData().MARKETER_SERVICE_ADMIN + "");
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
