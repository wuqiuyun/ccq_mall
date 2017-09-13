package per.sue.gear2.fragment;
/*
* 文件名：
* 描 述：
* 作 者：ld
* 时 间：2015/10/23
* 版 权：锐思科技
*/

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import per.sue.gear2.R;
import per.sue.gear2.net.session.PersistentCookieStore;
import per.sue.gear2.ui.UIBaseFragment;
import per.sue.gear2.widget.ProgressWebView;


public class HTMLFragment extends LazyFragment {


    private ProgressWebView mProgressWebView;
    private String mTitle;
    private String mUrl;
    private String mHTMLText;
    private boolean isCode;
    private String mEmptyTip;
    private ProgressWebView.OnWebChromeClientListener onWebChromeClientListener;

    private boolean isAddCookie;

    public static Bundle getBundle(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("html", null);
        bundle.putString("emptyTip", null);
        bundle.putBoolean("isCode", false);
        return bundle;
    }
    public static HTMLFragment getInstance(String url) {
        return getInstance(url, null, null, false);
    }

    public static HTMLFragment getInstance(String html, boolean isCode) {
        return getInstance(null, html, "", true);
    }

    public static HTMLFragment getInstance(String html, String emptyTip, boolean isCode) {
        return getInstance(null, html, emptyTip, true);
    }

    public static HTMLFragment getInstance(String url, String html, String emptyTip, boolean isCode) {
        HTMLFragment fragment = new HTMLFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("html", html);
        bundle.putString("emptyTip", emptyTip);
        bundle.putBoolean("isCode", isCode);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_webview;
    }


    @Override
    protected void onFragmentVisibleLoad(@Nullable Bundle savedInstanceState) {
        super.onFragmentVisibleLoad(savedInstanceState);

        if (isCode) {
            loadHTMLWebView();
        } else {
            loadUrlWebView();
        }
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        mProgressWebView = (ProgressWebView)getView().findViewById(R.id.web_view);
        mProgressWebView.setId(new Random(10000).nextInt());
        mUrl = getArguments().getString("url");
        isCode = getArguments().getBoolean("isCode", false);
        mHTMLText = getArguments().getString("html");
        mEmptyTip = getArguments().getString("emptyTip");
        mProgressWebView.getSettings().setDisplayZoomControls(false);
        initWebViewSettings();
    }

    private void loadUrlWebView() {
        if(null == mProgressWebView)return;
        mProgressWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        syncCookie(getContext(), mUrl);
        mProgressWebView.loadUrl(mUrl);
    }

    private void initWebViewSettings() {
        WebSettings webSettings = mProgressWebView.getSettings();
//        myWebView.getSettings().setSupportZoom(true);
//        myWebView.getSettings().setBuiltInZoomControls(true);
//        myWebView.getSettings().setDefaultFontSize(12);
//        myWebView.getSettings().setLoadWithOverviewMode(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //如果访问的页面中有Javascript，则webview必须设置支持JavascripmyWebView.getSettings().setJavaScriptEnabled(true);
        //myWebView.getSettings().setUserAgentString(MyApplication.getUserAgent());
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        mProgressWebView.getSettings().setJavaScriptEnabled(true);
    }

    private void loadHTMLWebView() {

        if (mHTMLText == null) {
            mHTMLText = "";

        }

        mHTMLText = mHTMLText.replaceAll("<img(.*?)>", "<img style=\"max-width: 100%;\"$1>");
        mHTMLText = mHTMLText.replaceAll("width *= *\".*?\"", "width=\"100%\"");
        mProgressWebView.getSettings().setDefaultTextEncodingName("UTF -8");// 设置默认为utf-8
        mProgressWebView.getSettings().setJavaScriptEnabled(true);
        // mProgressWebView.setWebChromeClient(new ProgressWebView.WebChromeClient());
        mProgressWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mProgressWebView.loadData(mHTMLText, "text/html; charset=UTF-8", null);// 这种写法可以正确解码
    }

    public void setOnWebChromeClientListener(ProgressWebView.OnWebChromeClientListener onWebChromeClientListener) {
        this.onWebChromeClientListener = onWebChromeClientListener;
        if (null != mProgressWebView) {
            mProgressWebView.setOnWebChromeClientListener(onWebChromeClientListener);
        }
    }

    @Override
    public void onPause() {
        mProgressWebView.reload();
        super.onPause();
    }

    /**
     * Sync Cookie
     */
    private void syncCookie(Context context, String url) {
        try {

            if (TextUtils.isEmpty(mUrl) ) {
                return;
            }

            //Log.e("syncCookie.url", url);

            CookieSyncManager.createInstance(context);

            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();// 移除
            cookieManager.removeAllCookie();
            String oldCookie = cookieManager.getCookie(url);
            if (oldCookie != null) {
                ///  Log.e(".oldCookie = ", oldCookie);
            }
            PersistentCookieStore persistentCookieStore = new PersistentCookieStore(getContext());
            String host = mUrl.substring(7, mUrl.lastIndexOf(".") + 4);

            String cookies = CookieManager.getInstance().getCookie(url);
             Log.e("setCookie=", cookies);
            cookieManager.setCookie(url, cookies);
            cookieManager.setCookie(url, cookies.split("; ")[1]);
            CookieSyncManager.getInstance().sync();
          /*  String newCookie = cookieManager.getCookie(url);
            if(newCookie != null){Log.e("newCookie=", newCookie);
            }*/
        } catch (Exception e) {
            // Log.e("syncCookie failed", e.toString());
        }
    }

    public void setAddCookie(boolean addCookie) {
        isAddCookie = addCookie;
    }




}
