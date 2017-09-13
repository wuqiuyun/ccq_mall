package per.sue.gear2.controll;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import java.io.File;

import per.sue.gear2.R;
import per.sue.gear2.net.PicassoImageOKHttp3Download;


public class GearImageLoad {

	private Context ctx;
	private static GearImageLoad singleton;

	private int defResId;
	private int errorResId;

	private String baseUrl;

	private OnImageLoadDefultListener defListener;

	public GearImageLoad(Context ctx) {
		this.ctx = ctx;
	}

	public static synchronized GearImageLoad getSingleton(Context ctx) {
		if (singleton == null) {
			singleton = new GearImageLoad(ctx);
		}

		return singleton;
	}



	public void initialize(String imageCacheDir) {
		Picasso picasso = new Picasso.Builder(ctx)
				.downloader(
				new PicassoImageOKHttp3Download(new File(imageCacheDir)))
				.build();
		Picasso.setSingletonInstance(picasso);

	}


	public GearImageLoad setDebug(boolean debug){
		Picasso.with(ctx).setLoggingEnabled(debug);
		Picasso.with(ctx).setIndicatorsEnabled(debug);
		return this;
	}



	public void load(Object target, ImageView iv) {
		load(target, iv, null, 0, 0, null);
	}

	public void load( Object target, ImageView iv,  int def) {
		load( target, iv, null, def, def, null);
	}

	public void load( Object target, ImageView iv,  int def, Callback callback) {
		load( target, iv, null, def, def, callback);
	}


	public void load( Object target, ImageView iv, Transformation transformer) {
		load( target, iv,  transformer, 0 , 0, null);
	}

	public void load( Object target, ImageView iv, int def, int err) {
		load( target, iv,  null, def , err, null);
	}

	private void load( Object target, ImageView iv,  Transformation transformer, int def, int err,  Callback callback) {
		RequestCreator creater;
		if (target instanceof String && !TextUtils.isEmpty((String) target)) {
			String url = (String)target;
			if(!url.startsWith("http") && !TextUtils.isEmpty(baseUrl)){
				if (url.startsWith("/")){
					url = baseUrl + url;
				}else {
					url=baseUrl+"/"+url;
				}
			}
			creater = Picasso.with(ctx).load(url);
		} else if (target instanceof File) {
			creater = Picasso.with(ctx).load((File) target);
		} else if (target instanceof Uri) {
			creater = Picasso.with(ctx).load((Uri) target);
		} else if (target instanceof Integer) {
			creater = Picasso.with(ctx).load((Integer) target);
		} else {
			return;
		}

		creater.config(Bitmap.Config.RGB_565);

		if (transformer != null) {

			creater.transform(transformer);
		}
		if(def == 0)
			def = getOverallDefResId();

		if(err == 0)
			err = getOverallErrorResId();

		if(defListener != null){
			defListener.onLoadDefult(target, def, err );
		}

		creater = creater.error(err);
		creater = creater.placeholder(def);

		if (callback == null) {
			creater.into(iv);
		} else {
			creater.into(iv, callback);
		}
	}



	private int getOverallDefResId(){
		if(defResId == 0){
			return R.drawable.gear_image_default;
		}else{
			return defResId;
		}
	}

	private int getOverallErrorResId(){
		if(errorResId == 0){
			return R.drawable.gear_image_error;
		}else{
			return errorResId;
		}
	}

	public void setDefResId(int defResId) {
		this.defResId = defResId;
	}

	public void setErrorResId(int errorResId) {
		this.errorResId = errorResId;
	}

	public OnImageLoadDefultListener getOnImageLoadDefultlistener() {
		return defListener;
	}

	public void setOnImageLoadDefultlistener(OnImageLoadDefultListener l) {
		this.defListener = l;
	}

	public  interface  OnImageLoadDefultListener{
		public void onLoadDefult(Object target, int def, int err);
	}


	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
}
