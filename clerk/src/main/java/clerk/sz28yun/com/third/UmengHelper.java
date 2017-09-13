package clerk.sz28yun.com.third;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;


import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import clerk.sz28yun.com.R;
import per.sue.gear2.dialog.GearCustomDialog;

/*
* 文件名：
* 描 述：
* 作 者：苏昭强
* 时 间：2016/5/4
*/
public class UmengHelper  {

    private static final String TAG = "UmengHelper";

    public static final  UmengHelper umShareListener = new UmengHelper();

    private UmengHelper(){}

    public static UmengHelper getInstance() {
        return umShareListener;
    }
    Dialog dialog = null;


    public void showBoard(Activity activity, String title,  String text, String imageUrl, String targetUrl, UMShareListener umShareListener) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SHARE_MEDIA share_media = SHARE_MEDIA.QQ;
                switch(view.getId()){
                    case R.id.share_qq_iv:
                        share_media = SHARE_MEDIA.QQ;
                        break;
                    case R.id.share_weixin_iv:
                        share_media = SHARE_MEDIA.WEIXIN;
                        break;
                    case R.id.share_weixin_friend_iv:
                        share_media = SHARE_MEDIA.WEIXIN_CIRCLE;
                        break;
                    case R.id.share_sina_iv:
                        share_media = SHARE_MEDIA.SINA;
                        break;
                    default:
                        break;
                }

                if(null != dialog){
                    dialog.dismiss();
                    dialog = null;
                }
                sharePlatform(activity, title, share_media, text, imageUrl, targetUrl, umShareListener);
            }
        };

        View view = LayoutInflater.from(activity).inflate(R.layout.pop_share, null);
        view.findViewById(R.id.share_qq_iv).setOnClickListener(listener);
        view.findViewById(R.id.share_weixin_iv).setOnClickListener(listener);
        view.findViewById(R.id.share_sina_iv).setOnClickListener(listener);
        view.findViewById(R.id.share_weixin_friend_iv).setOnClickListener(listener);
        view.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != dialog){
                    dialog.dismiss();
                }
            }
        });

         dialog = new  GearCustomDialog.Builder(activity)
                .setContentView(view)
                .setBottomUp(true)
                 .setUseDefult(false)
                .create();
        dialog.show();
    }

    public void sharePlatform( Activity activity, String title, SHARE_MEDIA plateform, String text, String imageUrl, String targetUrl, UMShareListener umShareListener) {
       if(TextUtils.isEmpty(imageUrl)){
           new ShareAction(activity).setPlatform(plateform).setCallback(umShareListener)
                   .withText(text)
                   .withTargetUrl(targetUrl)
                   .withTitle(title)
                   .share();
       }else{
           UMImage image = new UMImage(activity, imageUrl);
           new ShareAction(activity).setPlatform(plateform).setCallback(umShareListener)
                   .withText(text)
                   .withTargetUrl(targetUrl)
                   .withTitle(title)
                   .withMedia(image)
                   .share();
       }

    }


}
