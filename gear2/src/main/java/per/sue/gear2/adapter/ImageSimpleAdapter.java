package per.sue.gear2.adapter;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import per.sue.gear2.R;
import per.sue.gear2.controll.GearImageLoad;


public class ImageSimpleAdapter<T> extends ArrayListAdapter<T> {


	private OnAdapterItemClickLiener mOnAdapterItemClickLiener;
	private int mImageHeight;
	private int mImageWidth;
    private int defultImageResId;
	private boolean isLoob;


	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return getList().get(position% getList().size());
	}

	@Override
	public int getCount() {
		return !isLoob?getList().size():Integer.MAX_VALUE;
	}

	public ImageSimpleAdapter(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub


	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		HoldView holdView;
		if(convertView == null) {
			holdView = new HoldView();
            LinearLayout linearLayout = new LinearLayout(getContext());
            ImageView imageView =   new ImageView(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT );
			//LogUtils.e("ImageSimpleAdapter", "mImageHeight=" + mImageHeight);
			if(mImageHeight != 0)params.height = mImageHeight;
			if(mImageWidth != 0)params.width = mImageWidth;

            linearLayout.addView(imageView, params);
			holdView.mImageView =  imageView;
			convertView  =linearLayout ;
			convertView.setTag(holdView);
		}else {
			holdView = (HoldView) convertView.getTag();
		}
		int resId  = R.drawable.gear_image_default;
		if(0 != defultImageResId)resId = defultImageResId;

			holdView.mImageView.setImageResource(resId);
			holdView.mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
			holdView.mImageView.setOnClickListener(new  OnImageViewClickListener(position% getList().size()));

		T t= getList().get(position% getList().size());
		String url = t.toString();
	    if(null != url && !"".equals(url)) {
			GearImageLoad.getSingleton(context).load(url ,holdView.mImageView, resId);
	    }
		return convertView;
	}


	public static class HoldView
	{
		ImageView mImageView;
	}


	public class OnImageViewClickListener implements OnClickListener{

		private int position;

		public OnImageViewClickListener(int position) {
			// TODO Auto-generated constructor stub
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(mOnAdapterItemClickLiener != null){

				mOnAdapterItemClickLiener.OnImageViewClick(position, getItem(position));
			}
		}

	}

	public void setOnAdapterItemClickLiener(OnAdapterItemClickLiener l){
		mOnAdapterItemClickLiener = l;

	}

	public interface OnAdapterItemClickLiener<T>{

		 public void OnImageViewClick(int position, T t);

	}




	public boolean isLoob() {
		return isLoob;
	}

	public void setIsLoob(boolean isLoob) {
		this.isLoob = isLoob;
	}

	public int getImageHeight() {
		return mImageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.mImageHeight = imageHeight;
	}

	public int getImageWidth() {
		return mImageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.mImageWidth = imageWidth;
	}

	public void setDefultImageResId(int defultImageResId) {
		this.defultImageResId = defultImageResId;
	}
}
