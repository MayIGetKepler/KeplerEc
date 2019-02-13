package com.zwt.kepler_core.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.zwt.kepler_core.delegates.KeplerDelegate;
import com.zwt.kepler_core.delegates.web.WebDelegate;
import com.zwt.kepler_core.delegates.web.WebDelegateImpl;

/**
 * @author ZWT
 */
public class Router {
    private Router() {
    }

    private static class Holder {
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    public final boolean handlerWebUrl(WebDelegate delegate, String url) {

        //如果是url是电话协议
        if (url.contains("tel:")) {
            callPhone(delegate.getContext(), url);
            return true;
        }
        final KeplerDelegate parentDelegate = delegate.getTopDelegate();
        final WebDelegateImpl webDelegate = WebDelegateImpl.create(url);

        parentDelegate.start(webDelegate);

        return true;
    }

    private void loadWebPage(WebView webView, String url) {
        if (webView != null && url != null) {
            webView.loadUrl(url);
        } else {
            throw new NullPointerException("WebView or Url is Null!");
        }
    }

    private void loadLocalPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    public void loadPage(WebDelegate delegate, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(delegate.getWebView(), url);
        } else {
            loadLocalPage(delegate.getWebView(), url);
        }
    }


    private void callPhone(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(url));
        ContextCompat.startActivity(context, intent, null);

    }
}
