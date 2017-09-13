package ccj.sz28yun.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.activity.AboutActivity;
import ccj.sz28yun.com.activity.ConsumeBillActivity;
import ccj.sz28yun.com.activity.ConsumeBillSettingActivity;
import ccj.sz28yun.com.activity.CouponMealsActivity;
import ccj.sz28yun.com.activity.CouponOnePublistActivity;
import ccj.sz28yun.com.activity.EvaluateManangerActivity;
import ccj.sz28yun.com.activity.FinanceActivity;
import ccj.sz28yun.com.activity.GoodsUploadActivity;
import ccj.sz28yun.com.activity.GoodsUploadListActivity;
import ccj.sz28yun.com.activity.MemberSysActivity;
import ccj.sz28yun.com.activity.MemberSysSmsActivity;
import ccj.sz28yun.com.activity.OperatingDataActivity;
import ccj.sz28yun.com.activity.OrderHistoryActivity;
import ccj.sz28yun.com.activity.VerifyInputActivity;
import ccj.sz28yun.com.activity.VerifyQRActivity;
import ccj.sz28yun.com.adapter.HomeMenuAdapter;
import ccj.sz28yun.com.base.CCJFragment;
import ccj.sz28yun.com.bean.ConfigBean;
import ccj.sz28yun.com.bean.HomeMenuBean;
import ccj.sz28yun.com.bean.ShareConfigBean;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.APIParamsCache;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.presenter.ConfigPresenter;
import ccj.sz28yun.com.third.UmengHelper;
import per.sue.gear2.utils.ToastUtils;


/**
 * Created by sue on 2016/12/4.
 */
public class HomeFragment extends CCJFragment implements UMShareListener, ConfigPresenter.ConfigView {


    @Bind(R.id.home_grid_view)
    GridView homeGridView;
    @Bind(R.id.bar_title_tv)
    TextView barTitleTv;

    private ShareConfigBean shareConfigBean;

    HomeMenuAdapter homeMenuAdapter;
    private UmengHelper umengHelper;

    private ConfigPresenter configPresenter;
    private UserBean userBean;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        userBean = GlobalDataStorageCache.getInstance().getUserData();
        barTitleTv.setText(userBean.getStoreName());
        umengHelper = UmengHelper.getInstance();
        homeMenuAdapter = new HomeMenuAdapter(getActivity());
        homeGridView.setAdapter(homeMenuAdapter);
        homeMenuAdapter.setList(getHomeMenuData());
        homeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://商品仓库列表
                        if ("11".equals(userBean.getSellerGroupId()) || "0".equals(userBean.getSellerGroupId())) {
                            startActivity(GoodsUploadListActivity.startIntent(getActivity()));
                        } else {
                            ToastUtils.showError("没有权限", getActivity());
                        }
                        break;
                    case 1://单品券发布
                        if ("11".equals(userBean.getSellerGroupId()) || "0".equals(userBean.getSellerGroupId())) {
                            startActivity(CouponOnePublistActivity.startIntent(getActivity()));
//                        startActivity(CouponOnePublishActivity.startIntent(getActivity()));
                        } else {
                            ToastUtils.showError("没有权限", getActivity());
                        }
                        break;
                    case 2://套餐券发布
                        if ("11".equals(userBean.getSellerGroupId()) || "0".equals(userBean.getSellerGroupId())) {
                            startActivity(CouponMealsActivity.startIntent(getActivity()));
                        } else {
                            ToastUtils.showError("没有权限", getActivity());
                        }
                        break;
                    case 3: //经营数据
                        if ("1".equals(userBean.getSellerGroupId())) {
                            ToastUtils.showError("没有权限", getActivity());
                        } else {
                            startActivity(OperatingDataActivity.startIntent(getActivity()));
                        }
                        break;
                    case 4://评价管理
                        if ("11".equals(userBean.getSellerGroupId()) || "0".equals(userBean.getSellerGroupId())) {
                            startActivity(EvaluateManangerActivity.startIntent(getActivity()));
                        } else {
                            ToastUtils.showError("没有权限", getActivity());
                        }
                        break;
                    case 5://消费买单设置
                        if ("10".equals(userBean.getSellerGroupId()) || "0".equals(userBean.getSellerGroupId())) {
                            startActivity(ConsumeBillSettingActivity.startIntent(getActivity()));
                        } else {
                            ToastUtils.showError("没有权限", getActivity());
                        }
                        break;
                    case 6://订单历史
                        startActivity(OrderHistoryActivity.startIntent(getActivity()));
                        break;
                    case 7://会员系统
                        if ("11".equals(userBean.getSellerGroupId()) || "0".equals(userBean.getSellerGroupId())) {
                            startActivity(MemberSysActivity.startIntent(getActivity()));
//                            startActivity(MemberSysSmsActivity.startIntent(getActivity()));
                        } else {
                            ToastUtils.showError("没有权限", getActivity());
                        }
                        break;
                    case 8://财务结账
                        if ("10".equals(userBean.getSellerGroupId()) || "0".equals(userBean.getSellerGroupId())) {
                            startActivity(FinanceActivity.startIntent(getActivity()));
                        } else {
                            ToastUtils.showError("没有权限", getActivity());
                        }
                        break;
                    case 9://关于
                        startActivity(AboutActivity.startIntent(getActivity()));
                        break;
                    default:
                        break;
                }
            }
        });

        configPresenter = new ConfigPresenter(getActivity(), this);
        configPresenter.getShareConfig();

    }

    public ArrayList<HomeMenuBean> getHomeMenuData() {
        ArrayList<HomeMenuBean> list = new ArrayList<>();
        list.add(new HomeMenuBean("商品仓库列表", R.mipmap.ic_home_item_goods_upload));
        list.add(new HomeMenuBean("发布单品券", R.mipmap.ic_home_item_ccj_coupon));
        list.add(new HomeMenuBean("发布套餐券", R.mipmap.ic_home_item_project_manage));

        list.add(new HomeMenuBean("经营数据", R.mipmap.ic_home_item_jysj));
        list.add(new HomeMenuBean("评价管理", R.mipmap.ic_home_item_evaluate));
        list.add(new HomeMenuBean("消费买单设置", R.mipmap.ic_home_item_consum));

        list.add(new HomeMenuBean("订单历史", R.mipmap.ic_home_item_order_history));
        list.add(new HomeMenuBean("会员系统", R.mipmap.ic_home_item_vipsystem));
        list.add(new HomeMenuBean("财务结账", R.mipmap.ic_home_item_finance));

//        boolean isSj = APIParamsCache.getInstance().getIsSj();
//        if (isSj){
//            list.add(new HomeMenuBean("关于", R.mipmap.abouticon));
//        }else{
            list.add(new HomeMenuBean("关于", R.mipmap.about));
//        }

        return list;

    }


    @OnClick({R.id.home_top_verify_input, R.id.home_top_verify_qr, R.id.home_top_bill_paied, R.id.share_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_top_verify_input:
                if ("11".equals(userBean.getSellerGroupId())) {
                    ToastUtils.showError("没有权限", getActivity());
                } else {
                    startActivity(VerifyInputActivity.startIntent(getActivity()));
                }
                break;
            case R.id.home_top_verify_qr:
                if ("11".equals(userBean.getSellerGroupId())) {
                    ToastUtils.showError("没有权限", getActivity());
                } else {
                    startActivity(VerifyQRActivity.startIntent(getActivity()));
                }
                break;
            case R.id.home_top_bill_paied:
                if ("11".equals(userBean.getSellerGroupId())) {
                    ToastUtils.showError("没有权限", getActivity());
                } else {
                    startActivity(ConsumeBillActivity.startIntent(getActivity()));
                }
                break;
            case R.id.share_iv:
                ConfigBean configBean = GlobalDataStorageCache.getInstance().getConfigData();
                if (null != shareConfigBean) {
                    umengHelper.showBoard(getActivity(), shareConfigBean.sTORE_TITLE, shareConfigBean.mALL_MX, shareConfigBean.sTORE_FX, shareConfigBean.sTORE_QRCODE_IMG_AZ_URL, this);
                } else {
                    ToastUtils.showError("数据异常, 请重新打开", getActivity());
                }
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
    }


    public void  updataStoreName(){
        barTitleTv.setText(userBean.getStoreName());
    }
}
