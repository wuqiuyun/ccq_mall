package ccj.sz28yun.com.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.adapter.ImagePublicAdapter;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.ImageUploadResult;
import ccj.sz28yun.com.presenter.MerchantUpdateImgPresenter;
import per.sue.gear2.controll.GearImageSelector;
import per.sue.gear2.utils.ToastUtils;
import per.sue.gear2.widget.NoScrollGridView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 商家图片修改
 * Created by meihuali on 2017/7/5.
 */

public class MerchantUpdateImgActivity extends CCJActivity implements MerchantUpdateImgPresenter.MerchantUpdateImgView {

    @Bind(R.id.pic_gv)
    NoScrollGridView picGv;
    @Bind(R.id.title_tv)
    TextView titleTv;

    private MerchantUpdateImgPresenter merchantUpdateImgPresenter;
    private String sctype;//上传图片类型
    private int IMAGE_MAX_SIZE = 7;
    private GearImageSelector gearImageSelector;
    private ImagePublicAdapter imagePublicAdapter;
    private ArrayList<Object> urls;
    private ArrayList<ImageUploadResult> imageUploadResultArrayList = new ArrayList<>();

    public static Intent startIntent(Context context, String title, String sctype, ArrayList<ImageUploadResult> list) {
        Intent intent;
        intent = new Intent(context, MerchantUpdateImgActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("sctype", sctype);
        intent.putExtra("list", list);
        return intent;
    }
    public static Intent startIntent(Context context, String title, ArrayList<ImageUploadResult> list) {
        Intent intent;
        intent = new Intent(context, MerchantUpdateImgActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("list", list);
        return intent;
    }

    public static Intent startIntent(Context context, String title) {
        Intent intent;
        intent = new Intent(context, MerchantUpdateImgActivity.class);
        intent.putExtra("title", title);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return false;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_merchant_update_img;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        merchantUpdateImgPresenter = new MerchantUpdateImgPresenter(getActivity(), this);

        initializeImageGridView();

//        上传图片类型(1商品图片 2套餐图片 3添加商家基本图片 4添加商家商家相片 5添加商家厨房相片 9商家缩略图)
        sctype = getIntent().getStringExtra("sctype");
        merchantUpdateImgPresenter.setImage_01("");
        merchantUpdateImgPresenter.setImage_02("");
        merchantUpdateImgPresenter.setImage_03("");
        merchantUpdateImgPresenter.setImage_04("");
        merchantUpdateImgPresenter.setImage_05("");
        if ("3".equals(sctype)){
            IMAGE_MAX_SIZE = 2;
            merchantUpdateImgPresenter.setScType("3");
            merchantUpdateImgPresenter.setDeleteType("1");
        }else if ("4".equals(sctype)){
            IMAGE_MAX_SIZE = 6;
            merchantUpdateImgPresenter.setScType("4");
            merchantUpdateImgPresenter.setDeleteType("2");
        }else if ("5".equals(sctype)){
            IMAGE_MAX_SIZE = 3;
            merchantUpdateImgPresenter.setScType("5");
            merchantUpdateImgPresenter.setDeleteType("3");
        }else if ("9".equals(sctype)){
            IMAGE_MAX_SIZE = 1;
            merchantUpdateImgPresenter.setScType("9");
            merchantUpdateImgPresenter.setDeleteType("4");
        }

        if (!showBackView()) {
            TextView textView = new TextView(getActivity());
            textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_arrow_left_white, 0, 0, 0);
            textView.setText("保存");
            textView.setCompoundDrawablePadding(12);
            textView.setPadding(12, 8, 8, 8);
            textView.setTextColor(getActivity().getResources().getColor(R.color.app_white));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isBaocun()){
                        showProgressDialog("保存中");
//                        merchantUpdateImgPresenter.editMerchantAllPic();
                    }
                }
            });
            mLeftView = getHeadBarView().addLeftItem(textView);

            // setMenuItemParams(mLeftView);
            if (mLeftView instanceof ImageView) {
                ((ImageView) mLeftView).setScaleType(ImageView.ScaleType.CENTER);
            }
        }
        setBarTitle(getTitle().toString());
        String title = getIntent().getStringExtra("title");
        titleTv.setText(title);
        ArrayList<ImageUploadResult> imageList = (ArrayList<ImageUploadResult>) getIntent().getSerializableExtra("list");
        if (imageList != null) {
            for (ImageUploadResult bean : imageList) {
                ImageUploadResult imageUploadResult = new ImageUploadResult(bean.getId(), bean.getUrl());
                imageUploadResultArrayList.add(imageUploadResult);
//                imageUploadResultArrayList.add(0,imageUploadResult);
//                urls.add(0, imageUploadResult.getUrl());
                urls.add(urls.size() - 1, imageUploadResult.getUrl());
                imagePublicAdapter.notifyDataSetChanged();
            }
        }
    }

    private boolean isBaocun() {
        boolean canSubmit = true;
        if(imageUploadResultArrayList.size() <= 0) {
            canSubmit = false;
            ToastUtils.showError("请至少有一张图片.", getApplication());
        }
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < imageUploadResultArrayList.size(); i++) {
            ImageUploadResult result = imageUploadResultArrayList.get(i);
            if ("3".equals(sctype)){
                map.put("id"+ (i+3), result.getId());
            }else{
                map.put("id"+ (i+1), result.getId());
            }
        }
        String editId = new Gson().toJson(map);
//        上传图片类型(1商品图片 2套餐图片 3添加商家基本图片 4添加商家商家相片 5添加商家厨房相片 6商家缩略图)
        if ("3".equals(sctype)){
            merchantUpdateImgPresenter.setImage_01(editId);

        }else if ("4".equals(sctype)) {
            merchantUpdateImgPresenter.setImage_02(editId);

        }else if ("5".equals(sctype)) {
            merchantUpdateImgPresenter.setImage_03(editId);

        }else if ("9".equals(sctype)) {
            merchantUpdateImgPresenter.setImage_04(editId);
        }
        return canSubmit;
    }


    final int RC_CAMERA_PERM = 0;
    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void  verifyPermissionForCamera(){
        String[] perms = { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA };
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            if (verifyImagesCount()) {
                gearImageSelector.setCut(true);
                gearImageSelector.imageCutBean =GearImageSelector.getImageCutBeanByProportion(MerchantUpdateImgActivity.this,270, 234);//设置裁剪范围，按比例裁剪
//                    gearImageSelector.imageCutBean = new ImageCutBean(270, 234);//按尺寸裁剪
                gearImageSelector.showImageLoadPannel();
            }
        }else{
            EasyPermissions.requestPermissions(this, "程序需要打开摄像头权限和访问存储空间权限", RC_CAMERA_PERM, perms);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void initializeImageGridView() {
        gearImageSelector = new GearImageSelector(getActivity());
        imagePublicAdapter = new ImagePublicAdapter(getActivity());
        imagePublicAdapter.setImagesMaxCount(IMAGE_MAX_SIZE);
        picGv.setAdapter(imagePublicAdapter);
        urls = new ArrayList<>();
        urls.add(R.mipmap.ic_image_add );
        imagePublicAdapter.setList(urls);

        imagePublicAdapter.setImageOnClickListener(new ImagePublicAdapter.OnClickImageListener() {
            @Override
            public void onClickAddImage(View v) {
                verifyPermissionForCamera();
//                if (verifyImagesCount()) {
//                    gearImageSelector.setCut(true);
//                    gearImageSelector.imageCutBean = GearImageSelector.getImageCutBeanByProportion(MerchantUpdateImgActivity.this, 270, 234);//设置裁剪范围
////                    gearImageSelector.imageCutBean = new ImageCutBean(270, 234);
//                    gearImageSelector.showImageLoadPannel();
//                }
            }

            @Override
            public void onClickDeleteImage(View v, int position) {
                urls.remove(position);
                imageUploadResultArrayList.remove(position);
                imagePublicAdapter.notifyDataSetChanged();
            }
        });
        gearImageSelector.setOnImgaeSelectListener(new GearImageSelector.OnImgaeSelectListener() {
            @Override
            public void onSuccessGetBitmap(File file, Bitmap bitmap) {
                showProgressDialog("上传图片");
                merchantUpdateImgPresenter.uploadImage(file);
            }

            @Override
            public void onFailedGetBitmap(String msg) {
                ToastUtils.showError(msg, getApplicationContext());

            }
        });
    }

    private boolean verifyImagesCount() {
        boolean canSubmit = true;
        if ((urls.size() - 1) >= IMAGE_MAX_SIZE) {
            canSubmit = false;
            ToastUtils.showShortMessage("抱歉，上传图片数量不能多于"+IMAGE_MAX_SIZE+"张.", getActivity());
        }
        return canSubmit;
    }

    @Override
    public void onSuccessUploadImage(ImageUploadResult imageUploadResult) {
        dismissProgressDialog();
//        imageUploadResultArrayList.add(imageUploadResult)
        imageUploadResultArrayList.add(imageUploadResult);
//       imageUploadResultArrayList.add(0,imageUploadResult);
        //urls.add(0, imageUploadResult.getUrl());

        //新增时插在最后一个前面
        urls.add(urls.size() - 1, imageUploadResult.getUrl());
        imagePublicAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(String result) {
        dismissProgressDialog();
        ToastUtils.showShortMessage("修改成功", getActivity());
        finish();
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        dismissProgressDialog();
        ToastUtils.showShortMessage("修改失败，请重试", getActivity());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gearImageSelector.onActivityResult(requestCode, resultCode, data);
    }
}
