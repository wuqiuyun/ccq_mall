package ccj.sz28yun.com.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.Bind;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.cache.GlobalDataStorageCache;

public class WebviewActivity extends CCJActivity {

	@Bind(R.id.web_view)
	WebView webView;

	@Override
	public boolean showBackView() {
		return true;
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_webview;
	}

	@Override
	public void setBarTitle(String title) {
		title = getIntent().getExtras().getString("title");
		super.setBarTitle(title);
	}

	@Override
	public void onInitialize(@Nullable Bundle savedInstanceState) {

		String url = getIntent().getExtras().getString("url");
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		});
	}

}
