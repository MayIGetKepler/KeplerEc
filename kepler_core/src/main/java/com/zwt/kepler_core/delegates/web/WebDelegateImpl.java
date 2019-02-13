package com.zwt.kepler_core.delegates.web;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zwt.kepler_core.delegates.web.chromeclient.WebChromeClientImpl;
import com.zwt.kepler_core.delegates.web.client.WebViewClientImpl;
import com.zwt.kepler_core.delegates.web.route.RouteKeys;
import com.zwt.kepler_core.delegates.web.route.Router;

/**
 * @author ZWT
 */
public class WebDelegateImpl extends WebDelegate {

    public static WebDelegateImpl create(String url) {
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        if (getUrl() != null) {
            //原生方式模拟web跳转并加载页面
            Router.getInstance().loadPage(this, getUrl());
        }
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().configWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        return new WebViewClientImpl(this);
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }
}
