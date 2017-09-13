package clerk.sz28yun.com.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.activity.DemoGalleryActivity;
import clerk.sz28yun.com.base.CCJFragment;

import java.io.File;
import butterknife.OnClick;
import per.sue.gear2.bean.ImageCutBean;
import per.sue.gear2.controll.GearImageSelector;
import per.sue.gear2.dialog.GearCustomDialog;

/**
 * Created by sue on 2016/11/15.
 */
public class DemoFragment extends CCJFragment {

    GearImageSelector gearImageSelector;
    private GearCustomDialog gearCustomDialog;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_demo;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        gearImageSelector = new GearImageSelector(getActivity());
        gearImageSelector.setCut(true);//是否剪切
        gearImageSelector.imageCutBean = new ImageCutBean(200, 300); //剪切比例大小
        gearImageSelector.setOnImgaeSelectListener(new GearImageSelector.OnImgaeSelectListener() {
            @Override
            public void onSuccessGetBitmap(File file, Bitmap bitmap) {
                //选中图片后会在这里返回来

            }

            @Override
            public void onFailedGetBitmap(String msg) {

            }
        });
    }



    @OnClick({R.id.demo_list_btn, R.id.demo_bottom_pannel_1_btn, R.id.demo_image_selector_btn, R.id.preview_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.demo_list_btn:
                showGenderDialog();
                break;
            case R.id.demo_bottom_pannel_1_btn:
                break;
            case R.id.demo_image_selector_btn:
                gearImageSelector.showImageLoadPannel();
                break;
            case R.id.preview_image:
                DemoGalleryActivity.launch(getActivity());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gearImageSelector.onActivityResult(requestCode,resultCode, data );
    }

    private void showGenderDialog(){
        gearCustomDialog = new GearCustomDialog.Builder(getActivity())
                .addListViewWithSelected(new String[]{"男", "女", "未知"} , 1,new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        gearCustomDialog.dismiss();
                        gearCustomDialog = null;

                    }
                })
                .setTitle("请选择性别")
                .create();
        gearCustomDialog.show();
    }
}
