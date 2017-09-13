package clerk.sz28yun.com.fragment;

import clerk.sz28yun.com.R;
import per.sue.gear2.controll.GearImageLoad;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;




public class DemoImagePreviewFragment extends Fragment {
	private String mImageUrl;
	private ImageView mImageView;
	private PhotoViewAttacher mAttacher;


	public static Bundle newInstanceBundle(String imageUrl) {
		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		return args;
	}

	public static DemoImagePreviewFragment newInstance(String imageUrl) {
		final DemoImagePreviewFragment f = new DemoImagePreviewFragment();
		f.setArguments(newInstanceBundle(imageUrl));
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString("url") : null;

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		//final View layout = inflater.inflate(R.layout.fragment_imageview_layout, container, false);
		mImageView = new PhotoView(getActivity());
		mImageView.setScaleType(ScaleType.CENTER_CROP);
		mImageView.setAdjustViewBounds(true);
		//mAttacher = new PhotoViewAttacher(mImageView);
		/*mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {
			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				getActivity().finish();
			}
		});*/

		return mImageView;
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null != mImageUrl && !"".equals(mImageUrl)) {
			GearImageLoad.getSingleton(getActivity()).load( mImageUrl, mImageView, R.drawable.gear_image_default3, R.drawable.gear_image_error);
		}

	}

}
