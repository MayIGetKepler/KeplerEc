package com.zwt.kepler_core.delegates.web.client;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zwt.kepler_core.delegates.web.WebDelegate;
import com.zwt.kepler_core.delegates.web.route.Router;

/**
 * @author ZWT
 */
public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE;

    public WebViewClientImpl(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return Router.getInstance().handlerWebUrl(DELEGATE,url);

    }
}
