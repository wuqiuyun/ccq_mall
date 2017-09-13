package per.sue.gear2.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Toast;
import com.google.zxing.Result;
import per.sue.gear2.R;
import per.sue.gear2.qr.CaptureViewHelper;
import per.sue.gear2.qr.ViewfinderView;
import per.sue.gear2.ui.UIBaseActivity;


/**
 * 扫一扫
 * @author 
 */
public class CaptureActivity extends UIBaseActivity  {

	CaptureViewHelper captureViewHelper;

	public static void  lunch(Context context){
		Intent intent = new Intent(context,CaptureActivity.class);
		context.startActivity(intent);
	}

	@Override
	public int getLayoutResId() {
		return R.layout.capture;
	}

	@Override
	public void onInitialize(Bundle savedInstanceState) {
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		ViewfinderView viewfinderView =  (ViewfinderView) findViewById(R.id.viewfinder_view);
		captureViewHelper = new CaptureViewHelper(this, viewfinderView, surfaceView);
		captureViewHelper.initialzie();

		captureViewHelper.setOnScanListener(new CaptureViewHelper.onScanListener() {
			@Override
			public void handleDecode(Result result, Bitmap barcode) {
				String codeUrl = result.getText();
				if (codeUrl.equals("")) {
					Toast.makeText(getApplication(), "Scan failed!", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplication(), codeUrl, Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		captureViewHelper.onResume();

	}

	@Override
	protected void onPause() {
		super.onPause();
		captureViewHelper.onPause();
	}

	@Override
	protected void onDestroy() {
		captureViewHelper.onDestroy();
		super.onDestroy();
	}

}