package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

import butterknife.Bind;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.MerchantAdsImageAdapter;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.ImageUploadResult;
import ccj.sz28yun.com.bean.MerchantAdsImageBean;
import ccj.sz28yun.com.bean.MerchantAdsResult;
import ccj.sz28yun.com.bean.MerchantAllPicResult;
import ccj.sz28yun.com.presenter.MerchantHomeUpdatePresenter;
import per.sue.gear2.bean.ImageCutBean;
import per.sue.gear2.controll.GearImageSelector;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by sue on 2017/1/3.
 */
public class MerchantHomeUpdateImgActivity extends CCJActivity implements MerchantHomeUpdatePresenter.MerchantHomeUpdateView {


    @Bind(R.id.list_view)
    ListView listView;

    private MerchantHomeUpdatePresenter merchantHomeUpdatePresenter;
    private MerchantAdsImageAdapter merchantAdsImageAdapter;

    private GearImageSelector gearImageSelector;
    private boolean isEdit ;

    private int currentIndex;
    private MerchantAdsImageBean merchantAdsImageBean;

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, MerchantHomeUpdateImgActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_merchant_home_update_img;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        merchantHomeUpdatePresenter = new MerchantHomeUpdatePresenter(getActivity(), this);
        merchantAdsImageAdapter = new MerchantAdsImageAdapter(getActivity());
        listView.setAdapter(merchantAdsImageAdapter);
        merchantAdsImageAdapter.setOnMerchantAdsImageAdapterListener(new MerchantAdsImageAdapter.OnMerchantAdsImageAdapterListener() {
            @Override
            public void onDelete(MerchantAdsImageBean bean, int position) {
                merchantAdsImageBean = bean;
                currentIndex = position;
                merchantHomeUpdatePresenter.deleteMerchantAdsImage(bean.getStoreImageId());
            }

            @Override
            public void onEdit(MerchantAdsImageBean bean, int position) {
                merchantAdsImageBean = bean;
                currentIndex = position;
                isEdit  = true;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean =GearImageSelector.getImageCutBeanByProportion(MerchantHomeUpdateImgActivity.this,750, 350);//设置裁剪范围
//                gearImageSelector.imageCutBean = new ImageCutBean(378,175);
                gearImageSelector.showImageLoadPannel();
            }
        });

        getHeadBarView().addRightTextItem("新增", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEdit  = false;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean =GearImageSelector.getImageCutBeanByProportion(MerchantHomeUpdateImgActivity.this,750, 350);//设置裁剪范围
//                gearImageSelector.imageCutBean = new ImageCutBean(378,175);
                gearImageSelector.showImageLoadPannel();
            }
        });



        gearImageSelector = new GearImageSelector(getActivity());
        gearImageSelector.setOnImgaeSelectListener(new GearImageSelector.OnImgaeSelectListener() {
            @Override
            public void onSuccessGetBitmap(File file, Bitmap bitmap) {
                merchantHomeUpdatePresenter.uploadImage(file);
            }

            @Override
            public void onFailedGetBitmap(String msg) {

            }
        });
        merchantHomeUpdatePresenter.getMerchantAds();
    }


    @Override
    public void onSuccessEditAdsText(String message) {
        ToastUtils.showError("修改入口广告成功", getApplication());
    }

    @Override
    public void onSuccessEditAdsImage(String message) {
        ToastUtils.showError("修改轮播图成功", getApplication());
        merchantHomeUpdatePresenter.getMerchantAds();
    }

    @Override
    public void onSuccessAllPic(MerchantAllPicResult result) {

    }

    @Override
    public void onSuccessDeleteAdsImage(String message) {
        ToastUtils.showError("删除成功", getApplication());
        merchantHomeUpdatePresenter.getMerchantAds();
    }

    @Override
    public void onSuccessUploadImage(ImageUploadResult imageUploadResult) {

        if(isEdit){
            merchantHomeUpdatePresenter.editMerchantAdsImage(merchantAdsImageBean.getStoreImageId(), imageUploadResult.getId());
        }else{
            merchantHomeUpdatePresenter.editMerchantAdsImage(null, imageUploadResult.getId());//新增
        }

    }

    @Override
    public void onSucessLoadImage(MerchantHomeUpdatePresenter.ImageType type, ImageUploadResult result) {

    }

    @Override
    public void onSuccessEditMerchantAllPic(String message) {

    }

    @Override
    public void onSuccess(MerchantAdsResult result) {
        merchantAdsImageAdapter.setList(result.getImageList());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gearImageSelector.onActivityResult(requestCode, resultCode, data);
    }
}
