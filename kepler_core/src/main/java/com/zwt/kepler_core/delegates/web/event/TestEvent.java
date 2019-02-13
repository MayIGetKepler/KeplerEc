package com.zwt.kepler_core.delegates.web.event;

import android.webkit.WebView;
import android.widget.Toast;

/**
 * @author ZWT
 */
public class TestEvent extends Event {
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(),params,Toast.LENGTH_SHORT).show();
        if (getAction().equals("test")){
//            final WebView webView =getDelegate().getWebView();
//            webView.post(() -> webView.evaluateJavascript("javascript:nativeCall()",null));
            final WebView webView = getWebView();
            webView.post(() -> webView.evaluateJavascript("display_alert();",null));
        }
        return null;
    }
}
