package per.sue.gear2.controll;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;

import java.io.File;

import per.sue.gear2.R;
import per.sue.gear2.bean.ImageCutBean;
import per.sue.gear2.dialog.GearCustomDialog;
import per.sue.gear2.utils.BitmapUtils;
import per.sue.gear2.utils.GearLog;

/**
 * Created by SUE on 2016/7/21 0021.
 */
public class GearImageSelector {

    int SDK_VERSION = android.os.Build.VERSION.SDK_INT;


    public static final int CAMERA_CODE = 11100;
    public static final int GALLERY_CODE = 12100;
    public static final int PHOTO_REQUEST_CUT = 13100;
    protected File cameraOutFile;
    protected boolean isCut = false;
    private OnImgaeSelectListener onImgaeSelectListener;

    public ImageCutBean imageCutBean = new ImageCutBean(200, 200);

    private Activity activity;
    private Fragment fragment;


    public GearImageSelector(Activity activity) {
        this.activity = activity;
    }

    public GearImageSelector(Fragment fragment) {
        this.fragment = fragment;
        this.activity = fragment.getActivity();
    }

    public void showImageLoadPannel() {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.view_dialog_avatr, null);
        final Dialog dialog = new GearCustomDialog.Builder(activity)
                .setContentView(view)
                .setBottomUp(true)
                .create();
        view.findViewById(R.id.ViewDialogCamera).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                toCmera();
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.ViewDialogGallery).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                toGallery();
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.ViewDialogDismiss).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void toCmera() {
        cameraOutFile = StorageManager.getInstance().createImgFile(activity);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getOutUriByDiffSysVersion(cameraOutFile));
        if (null != this.fragment) {
            fragment.startActivityForResult(intent, CAMERA_CODE);
        } else {
            activity.startActivityForResult(intent, CAMERA_CODE);
        }

    }

    public void toGallery() {
        cameraOutFile = StorageManager.getInstance().createImgFile(activity);
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getOutUriByDiffSysVersion(cameraOutFile));
        if (null != this.fragment) {
            fragment.startActivityForResult(intent, GALLERY_CODE);
        } else {
            activity.startActivityForResult(intent, GALLERY_CODE);
        }
    }


    private Uri getOutUriByDiffSysVersion(File file) {
        Uri uri;
        if (SDK_VERSION < 24) {
            uri = Uri.fromFile(file);
        } else {
            uri = Uri.fromFile(file);
            //ContentValues contentValues = new ContentValues(1);
            //contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            // uri =  FileProvider.getUriForFile(activity , activity.getPackageName() + ".fileprovider", file);

        }
        return uri;
    }

    /**
     * 裁剪图片
     *
     * @param file
     */
    private void startPhotoZoom(File file) {

        Uri uri = getOutUriByDiffSysVersion(file);


        int sizeX = imageCutBean.sizeX;
        int sizeY = imageCutBean.sizeY;

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        //裁剪的尺寸比例
        intent.putExtra("aspectX", sizeX);
        intent.putExtra("aspectY", sizeY);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", sizeX);
        intent.putExtra("outputY", sizeY);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);


        intent.putExtra("return-data", false);//设置为不返回数据 , 因为如果输出尺寸太大会闪退
        if (null != this.fragment) {
            fragment.startActivityForResult(intent, PHOTO_REQUEST_CUT);
        } else {
            activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {//拍照返回
            if (isCut) {
                startPhotoZoom(cameraOutFile);
            } else {
                dealBitmap();
            }
        } else if (requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK) {//相册返回
            Uri selectedImage = data.getData();
            String scheme =  selectedImage.getScheme();
            String picturePath = null;
            if ("content".equals(scheme)) {
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = this.activity.getContentResolver().query(selectedImage, filePathColumns, MediaStore.Images.Media.SIZE + ">=30720", null, null);//查找出尺寸大于30kb的图片
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                     picturePath = c.getString(columnIndex);
                    c.close();
            } else if ("file".equals(scheme)) {//小米4选择云相册中的图片是根据此方法获得路径
                    picturePath = selectedImage.getPath();
            }
                if(picturePath != null){
                    cameraOutFile = new File(picturePath);
                    //BitmapUtils.bitmapTofile(cameraOutFile, BitmapFactory.decodeFile(picturePath));
                    if (isCut) {
                        startPhotoZoom(cameraOutFile);
                    } else {
                        dealBitmap();
                    }
                } else {
                    onImgaeSelectListener.onFailedGetBitmap("选择图片尺寸不能小于30kb!");
                }
            } else {
                onImgaeSelectListener.onFailedGetBitmap("选择图片尺寸不能小于30kb!");
            }
        } else if (requestCode == PHOTO_REQUEST_CUT && resultCode == Activity.RESULT_OK) {//截图返回
            dealBitmap();
        }
    }

    private void dealBitmap() {
        if (null != onImgaeSelectListener) {
            onImgaeSelectListener.onSuccessGetBitmap(cameraOutFile, BitmapFactory.decodeFile(cameraOutFile.getPath()));
        }
    }

    public Bitmap decodeSampledBitmapFromBitmap(Bitmap bitmap) {
        int sizeX = imageCutBean.sizeX;
        int sizeY = imageCutBean.sizeY;
        return BitmapUtils.decodeSampledBitmapFromBitmap(bitmap, sizeX, sizeY);
    }


    public void setOnImgaeSelectListener(OnImgaeSelectListener onImgaeSelectListener) {
        this.onImgaeSelectListener = onImgaeSelectListener;
    }

    public void setCut(boolean cut) {
        isCut = cut;
    }

    public interface OnImgaeSelectListener {

        public void onSuccessGetBitmap(File file, Bitmap bitmap);

        public void onFailedGetBitmap(String msg);

    }

    public static Bitmap getBitmapFromUri(Uri uri, Context mContext) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ImageCutBean getImageCutBeanByProportion(Activity activity, int proportionWidth, int proportionHeight) {
        double proportion = ((double) proportionHeight) / proportionWidth;

        return getImageCutBeanByProportion(activity, proportion);
    }

    public static ImageCutBean getImageCutBeanByProportion(Activity activity, double proportion) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        if (width > 800) {
            width = 800;// 最大宽度
        }

        int height = (int) (width * proportion);
        if (height > (metric.heightPixels - 48)) {
            height = metric.heightPixels - 48;
        }
//        GearLog.e("GearImageSelector", "proportion = " + proportion + " screen width = " + width + " height=" + height);
        ImageCutBean imageCutBean = new ImageCutBean(width, height);
        return imageCutBean;
    }

}
