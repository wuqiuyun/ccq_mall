package ccj.sz28yun.com.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.ImageUploadResult;
import ccj.sz28yun.com.bean.MerchantAdsImageBean;
import ccj.sz28yun.com.bean.MerchantAdsResult;
import ccj.sz28yun.com.bean.MerchantAllPicResult;
import ccj.sz28yun.com.bean.UserBean;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;
import ccj.sz28yun.com.presenter.MerchantHomeUpdatePresenter;
import per.sue.gear2.adapter.ImageSimpleAdapter;
import per.sue.gear2.controll.GearImageLoad;
import per.sue.gear2.controll.GearImageSelector;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.widget.flow.CircleFlowIndicator;
import per.sue.gear2.widget.flow.ViewFlowFixViewPager;

/**
 * Created by sue on 2017/1/11.
 */
public class MerchantHomeUpdateActivity extends CCJActivity implements MerchantHomeUpdatePresenter.MerchantHomeUpdateView {


    @Bind(R.id.view_flow)
    ViewFlowFixViewPager viewFlow;
    @Bind(R.id.circle_indicator)
    CircleFlowIndicator circleIndicator;
    @Bind(R.id.ads_text_et)
    EditText adsTextEt;
    @Bind(R.id.image_hj_first)
    ImageView hj_firstIv;
    @Bind(R.id.image_hj_second)
    ImageView hj_secondIv;
    @Bind(R.id.image_hj_three)
    ImageView hj_threeIv;
    @Bind(R.id.image_hj_four)
    ImageView hj_fourIv;
    @Bind(R.id.image_hj_five)
    ImageView hj_fiveIv;
    @Bind(R.id.image_hj_six)
    ImageView hj_sixIv;
    @Bind(R.id.image_nb_first)
    ImageView nb_firstIv;
    @Bind(R.id.image_nb_second)
    ImageView nb_secondIv;
    @Bind(R.id.image_nb_three)
    ImageView nb_threeIv;
    @Bind(R.id.image_zz_first)
    ImageView zz_firstIv;
    @Bind(R.id.image_zz_second)
    ImageView zz_secondIv;
    @Bind(R.id.image_sn_first)
    ImageView sn_firstIv;
    @Bind(R.id.submit_editpic_btn)
    TextView submit_editpicBtn;

    private MerchantHomeUpdatePresenter merchantHomeUpdatePresenter;

    private MerchantHomeUpdatePresenter.ImageType selsectImageType;

    private GearImageSelector gearImageSelector;
    private int imageUploadType = 1;
    private boolean isPicEdit = false;

    public static Intent startIntent(Context context) {
        Intent intent;
        intent = new Intent(context, MerchantHomeUpdateActivity.class);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return false;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_merchant_home_update;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {

        merchantHomeUpdatePresenter = new MerchantHomeUpdatePresenter(getActivity(), this);
        UserBean userBean = GlobalDataStorageCache.getInstance().getUserData();
//        merchantProListPresenter = new MerchantProListPresenter(getActivity(), this);
        gearImageSelector = new GearImageSelector(getActivity());
        gearImageSelector.setCut(true);
        setImageSelector();

        getHeadBarView().addRightTextItem("预览", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MerchantHomeUpdateActivity.this, WebviewActivity.class);
                String url = GlobalDataStorageCache.getInstance().getConfigData().STORE_PREVIEW + userBean.getStoreId();
                intent.putExtra("url", url);
                intent.putExtra("title", "商家");
                startActivity(intent);
            }
        });
        getHeadBarView().addLeftItem(per.sue.gear2.R.mipmap.ic_arrow_left_white, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPicEdit){
                    ToastUtils.showError("修改图片后未保存，请保存后在提交", getApplication());
                }else {
                    onBackPressed();
                }
            }
        });
        merchantHomeUpdatePresenter.getMerchantAllPic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        merchantHomeUpdatePresenter.getMerchantAds();
//        merchantHomeUpdatePresenter.getMerchantAllPic();
    }

    @OnClick({R.id.edit_img_iv, R.id.edit_ads_text_btn, R.id.tv_edit_hj, R.id.tv_edit_nb, R.id.tv_edit_zz, R.id.tv_edit_store_sn
            , R.id.image_hj_first, R.id.image_hj_second, R.id.image_hj_three, R.id.image_hj_four, R.id.image_hj_five, R.id.image_hj_six
            , R.id.image_nb_first, R.id.image_nb_second, R.id.image_nb_three, R.id.image_zz_first, R.id.image_zz_second, R.id.image_sn_first
            , R.id.image_hj_first_btn, R.id.image_hj_second_btn, R.id.image_hj_three_btn, R.id.image_hj_four_btn, R.id.image_hj_five_btn, R.id.image_hj_six_btn
            , R.id.image_nb_first_btn, R.id.image_nb_second_btn, R.id.image_nb_three_btn, R.id.image_zz_first_btn, R.id.image_zz_second_btn, R.id.image_sn_first_btn
            , R.id.submit_editpic_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_img_iv:
                startActivity(MerchantHomeUpdateImgActivity.startIntent(getActivity()));
                break;
            case R.id.edit_ads_text_btn:
                if (!TextUtils.isEmpty(adsTextEt.getText().toString())) {
                    merchantHomeUpdatePresenter.editMerchantAdsText(adsTextEt.getText().toString());
                } else {
                    adsTextEt.requestFocus();
                    adsTextEt.setError("请输入内容");
                }
                break;
            case R.id.tv_edit_hj:
                startActivity(MerchantUpdateImgActivity.startIntent(getActivity(), "商家环境图", "4", hjimageList));
                break;
            case R.id.tv_edit_nb:
                startActivity(MerchantUpdateImgActivity.startIntent(getActivity(), "商家内部环境图", "5", nbimageList));
                break;
            case R.id.tv_edit_zz:
                startActivity(MerchantUpdateImgActivity.startIntent(getActivity(), "资质/荣誉", "3", zzimageList));
                break;
            case R.id.tv_edit_store_sn:
                startActivity(MerchantUpdateImgActivity.startIntent(getActivity(), "商家列表缩略图", "9", snimageList));
                break;
            case R.id.image_hj_first:
                showBigPicDialog(hjimageList.get(0).getUrl());
                break;
            case R.id.image_hj_second:
                showBigPicDialog(hjimageList.get(1).getUrl());
                break;
            case R.id.image_hj_three:
                showBigPicDialog(hjimageList.get(2).getUrl());
                break;
            case R.id.image_hj_four:
                showBigPicDialog(hjimageList.get(3).getUrl());
                break;
            case R.id.image_hj_five:
                showBigPicDialog(hjimageList.get(4).getUrl());
                break;
            case R.id.image_hj_six:
                showBigPicDialog(hjimageList.get(5).getUrl());
                break;
            case R.id.image_nb_first:
                showBigPicDialog(nbimageList.get(0).getUrl());
                break;
            case R.id.image_nb_second:
                showBigPicDialog(nbimageList.get(1).getUrl());
                break;
            case R.id.image_nb_three:
                showBigPicDialog(nbimageList.get(2).getUrl());
                break;
            case R.id.image_zz_first:
                showBigPicDialog(zzimageList.get(0).getUrl());
                break;
            case R.id.image_zz_second:
                showBigPicDialog(zzimageList.get(1).getUrl());
                break;
            case R.id.image_sn_first:
                showBigPicDialog(snimageList.get(0).getUrl());
                break;
            //        上传图片类型(1商品图片 2套餐图片 3添加商家基本图片 4添加商家商家相片 5添加商家厨房相片 9商家缩略图)
            case R.id.image_hj_first_btn:
                imageUploadType = 4;
                selsectImageType = MerchantHomeUpdatePresenter.ImageType.MERCHANT_FIRST;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 220, 160);//设置裁剪范围
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.image_hj_second_btn:
                imageUploadType = 4;
                selsectImageType = MerchantHomeUpdatePresenter.ImageType.MERCHANT_SECOND;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 220, 160);//设置裁剪范围
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.image_hj_three_btn:
                imageUploadType = 4;
                selsectImageType = MerchantHomeUpdatePresenter.ImageType.MERCHANT_THIRD;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 220, 160);//设置裁剪范围
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.image_hj_four_btn:
                imageUploadType = 4;
                selsectImageType = MerchantHomeUpdatePresenter.ImageType.MERCHANT_FOUR;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 220, 160);//设置裁剪范围
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.image_hj_five_btn:
                imageUploadType = 4;
                selsectImageType = MerchantHomeUpdatePresenter.ImageType.MERCHANT_FIVE;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 220, 160);//设置裁剪范围
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.image_hj_six_btn:
                imageUploadType = 4;
                selsectImageType = MerchantHomeUpdatePresenter.ImageType.MERCHANT_SIX;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 220, 160);//设置裁剪范围
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.image_nb_first_btn:
                imageUploadType = 5;
                selsectImageType = MerchantHomeUpdatePresenter.ImageType.KITCHEN_FIRST;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 220, 160);//设置裁剪范围
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.image_nb_second_btn:
                imageUploadType = 5;
                selsectImageType = MerchantHomeUpdatePresenter.ImageType.KITCHEN_SECOND;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 220, 160);//设置裁剪范围
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.image_nb_three_btn:
                imageUploadType = 5;
                selsectImageType = MerchantHomeUpdatePresenter.ImageType.KITCHEN_THIRD;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 220, 160);//设置裁剪范围
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.image_zz_first_btn:
                imageUploadType = 3;
                selsectImageType = MerchantHomeUpdatePresenter.ImageType.BASE_LICENSE;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 282, 218);//设置裁剪范围
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.image_zz_second_btn:
                imageUploadType = 3;
                selsectImageType = MerchantHomeUpdatePresenter.ImageType.BASE_ZIZI;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 282, 218);//设置裁剪范围
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.image_sn_first_btn:
                imageUploadType = 9;
                selsectImageType = MerchantHomeUpdatePresenter.ImageType.STORE_SN;
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(this, 270, 240);//设置裁剪范围
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.submit_editpic_btn:
                showProgressDialog("保存中");
                changeImageListToJson();
                merchantHomeUpdatePresenter.editMerchantAllPic();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccessEditAdsText(String message) {
        Toast.makeText(MerchantHomeUpdateActivity.this, "广告语设置成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessEditAdsImage(String message) {

    }

    private ArrayList<ImageUploadResult> hjimageList;
    private ArrayList<ImageUploadResult> nbimageList;
    private ArrayList<ImageUploadResult> zzimageList;
    private ArrayList<ImageUploadResult> snimageList;

    /**
     * 所有图片
     *
     * @param result
     */
    @Override
    public void onSuccessAllPic(MerchantAllPicResult result) {
        dismissProgressDialog();

        Picasso.with(getActivity()).load(result.getExternal_1().getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(hj_firstIv);
        Picasso.with(getActivity()).load(result.getExternal_2().getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(hj_secondIv);
        Picasso.with(getActivity()).load(result.getExternal_3().getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(hj_threeIv);
        Picasso.with(getActivity()).load(result.getExternal_4().getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(hj_fourIv);
        Picasso.with(getActivity()).load(result.getExternal_5().getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(hj_fiveIv);
        Picasso.with(getActivity()).load(result.getExternal_6().getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(hj_sixIv);
        hjimageList = new ArrayList<ImageUploadResult>();
        hjimageList.add(0, result.getExternal_1());
        hjimageList.add(1, result.getExternal_2());
        hjimageList.add(2, result.getExternal_3());
        hjimageList.add(3, result.getExternal_4());
        hjimageList.add(4, result.getExternal_5());
        hjimageList.add(5, result.getExternal_6());

        Picasso.with(getActivity()).load(result.getInside_1().getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(nb_firstIv);
        Picasso.with(getActivity()).load(result.getInside_2().getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(nb_secondIv);
        Picasso.with(getActivity()).load(result.getInside_3().getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(nb_threeIv);

        nbimageList = new ArrayList<ImageUploadResult>();
        nbimageList.add(0, result.getInside_1());
        nbimageList.add(1, result.getInside_2());
        nbimageList.add(2, result.getInside_3());

        Picasso.with(getActivity()).load(result.getDocument_1().getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(zz_firstIv);
        Picasso.with(getActivity()).load(result.getDocument_2().getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(zz_secondIv);
        zzimageList = new ArrayList<ImageUploadResult>();
        zzimageList.add(0, result.getDocument_1());
        zzimageList.add(1, result.getDocument_2());

        Picasso.with(getActivity()).load(result.getThumb_1().getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(sn_firstIv);
        snimageList = new ArrayList<ImageUploadResult>();
        snimageList.add(0, result.getThumb_1());
    }


    @Override
    public void onSuccessDeleteAdsImage(String message) {
        dismissProgressDialog();

    }

    @Override
    public void onSuccessUploadImage(ImageUploadResult imageUploadResult) {
        dismissProgressDialog();

    }

    /**
     * 设置图片选择器
     */
    private void setImageSelector() {
        gearImageSelector.setOnImgaeSelectListener(new GearImageSelector.OnImgaeSelectListener() {
            @Override
            public void onSuccessGetBitmap(File file, Bitmap bitmap) {
                //图片上传 imageUploadType 在点击事件那里赋值
                showProgressDialog("上传图片");
                merchantHomeUpdatePresenter.alluploadImage(selsectImageType, file, "" + imageUploadType);
            }

            @Override
            public void onFailedGetBitmap(String msg) {
                ToastUtils.showError(msg, getApplication());
            }
        });
    }

    /**
     * 上传图片完成后的处理， 上传图片设置请查看方法setImageSelector（）；
     *
     * @param type
     * @param result
     */
    @Override
    public void onSucessLoadImage(MerchantHomeUpdatePresenter.ImageType type, ImageUploadResult result) {
        dismissProgressDialog();
        switch (type) {
            case BASE_ID_FRONET:
//                merchantParams.imageBaseList.set(0, result);
//                GearImageLoad.getSingleton(getActivity()).load(result.getUrl(), imgIdFrontIv);
                break;
            case BASE_ID_BACK:
//                merchantParams.imageBaseList.set(1, result);
//                GearImageLoad.getSingleton(getActivity()).load(result.getUrl(), imgIdBackIv);
                break;
            case BASE_LICENSE:
                isPicEdit = true;
                zzimageList.set(0, result);
                Picasso.with(getActivity()).load(result.getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(zz_firstIv);
                break;
            case BASE_ZIZI:
                isPicEdit = true;
                zzimageList.set(1, result);
                Picasso.with(getActivity()).load(result.getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(zz_secondIv);
                break;
            case MERCHANT_FIRST:
                isPicEdit = true;
                hjimageList.set(0, result);
                Picasso.with(getActivity()).load(result.getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(hj_firstIv);
                break;
            case MERCHANT_SECOND:
                isPicEdit = true;
                hjimageList.set(1, result);
                Picasso.with(getActivity()).load(result.getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(hj_secondIv);
                break;
            case MERCHANT_THIRD:
                isPicEdit = true;
                hjimageList.set(2, result);
                Picasso.with(getActivity()).load(result.getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(hj_threeIv);
                break;
            case MERCHANT_FOUR:
                isPicEdit = true;
                hjimageList.set(3, result);
                Picasso.with(getActivity()).load(result.getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(hj_fourIv);
                break;
            case MERCHANT_FIVE:
                isPicEdit = true;
                hjimageList.set(4, result);
                Picasso.with(getActivity()).load(result.getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(hj_fiveIv);
                break;
            case MERCHANT_SIX:
                isPicEdit = true;
                hjimageList.set(5, result);
                Picasso.with(getActivity()).load(result.getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(hj_sixIv);
                break;
            case KITCHEN_FIRST:
                isPicEdit = true;
                nbimageList.set(0, result);
                Picasso.with(getActivity()).load(result.getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(nb_firstIv);
                break;
            case KITCHEN_SECOND:
                isPicEdit = true;
                nbimageList.set(1, result);
                Picasso.with(getActivity()).load(result.getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(nb_secondIv);
                break;
            case KITCHEN_THIRD:
                isPicEdit = true;
                nbimageList.set(2, result);
                Picasso.with(getActivity()).load(result.getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(nb_threeIv);
                break;
            case STORE_SN:
                isPicEdit = true;
                snimageList.set(0, result);
                Picasso.with(getActivity()).load(result.getUrl()).placeholder(R.drawable.gear_image_default).error(R.drawable.gear_image_default).resize(200, 200).centerCrop().into(sn_firstIv);
                break;
            default:
                break;

        }
    }

    @Override
    public void onSuccessEditMerchantAllPic(String message) {
        dismissProgressDialog();
        isPicEdit = false;
        ToastUtils.showError("保存成功", getApplication());
    }

    public void changeImageListToJson(){
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("id3", zzimageList.get(0).getId());
        map1.put("id4", zzimageList.get(1).getId());

        Map<String, String> map2 = new HashMap<String, String> ();
        map2.put("id1", hjimageList.get(0).getId());
        map2.put("id2", hjimageList.get(1).getId());
        map2.put("id3", hjimageList.get(2).getId());
        map2.put("id4", hjimageList.get(3).getId());
        map2.put("id5", hjimageList.get(4).getId());
        map2.put("id6", hjimageList.get(5).getId());

        Map<String, String> map3 = new HashMap<String, String> ();
        map3.put("id1", nbimageList.get(0).getId());
        map3.put("id2", nbimageList.get(1).getId());
        map3.put("id3", nbimageList.get(2).getId());

        Map<String, String> map4 = new HashMap<String, String> ();
        map4.put("id1", snimageList.get(0).getId());


        String imageBase = new Gson().toJson(map1);
        String imageMerchant = new Gson().toJson(map2);
        String imageKitchen = new Gson().toJson(map3);
        String imageSn = new Gson().toJson(map4);
        merchantHomeUpdatePresenter.setImage_01(imageBase);
        merchantHomeUpdatePresenter.setImage_02(imageMerchant);
        merchantHomeUpdatePresenter.setImage_03(imageKitchen);
        merchantHomeUpdatePresenter.setImage_04(imageSn);
        merchantHomeUpdatePresenter.setImage_05("");
    }

    @Override
    public void onSuccess(MerchantAdsResult result) {
        dismissProgressDialog();
        adsTextEt.setText(result.getAdv());
        refreshADVView(result.getImageList());

    }

    private void refreshADVView(ArrayList<MerchantAdsImageBean> list) {
        ViewFlowFixViewPager viewFlow = (ViewFlowFixViewPager) findViewById(R.id.view_flow);
        CircleFlowIndicator circleFlowIndicator = (CircleFlowIndicator) findViewById(R.id.circle_indicator);
        ImageSimpleAdapter<MerchantAdsImageBean> imageSimpleAdapter = new ImageSimpleAdapter<MerchantAdsImageBean>(getActivity());

        imageSimpleAdapter.setDefultImageResId(R.mipmap.storebanner);
        imageSimpleAdapter.setList(list);
        imageSimpleAdapter.setIsLoob(true);
        viewFlow.setAdapter(imageSimpleAdapter);
        viewFlow.setmSideBuffer(list.size()); // 实际图片张数， 我的ImageAdapter实际图片张数为3
        viewFlow.setFlowIndicator(circleFlowIndicator);
        viewFlow.setTimeSpan(4500);
        //viewFlow.setFillColor(getResources().getColor(R.color.app_green_l));
        viewFlow.setSelection(list.size()); // 设置初始位置
        viewFlow.startAutoFlowTimer(); // 启动自动播放
        viewFlow.requestLayout();// 重新计算该控制在父布局中的位置
    }


    /**
     * 图片放大
     *
     * @param picUrl
     */
    private void showBigPicDialog(String picUrl) {
        View view = LayoutInflater.from(MerchantHomeUpdateActivity.this).inflate(
                R.layout.dialog_bigpic, null);
        ImageView iv_big_pic = (ImageView) view.findViewById(R.id.iv_big_pic);
        ImageView iv_cancel = (ImageView) view.findViewById(R.id.iv_cancel);

        GearImageLoad.getSingleton(getActivity()).load(picUrl, iv_big_pic);
        Dialog bigPicDialog = new Dialog(MerchantHomeUpdateActivity.this,
                R.style.mDialogStyle);
        bigPicDialog.setContentView(view);
        bigPicDialog.setCanceledOnTouchOutside(true);
        bigPicDialog.show();
        iv_big_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigPicDialog.dismiss();
            }
        });
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigPicDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gearImageSelector.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        dismissProgressDialog();
        ToastUtils.showError(message, getApplication());
    }
}
