package com.zwt.kepler_core.delegates.web;


import android.annotation.SuppressLint;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * @author ZWT
 */
public class WebViewInitializer {

    @SuppressLint("SetJavaScriptEnabled")
    public WebView configWebView(WebView webView) {

        WebView.setWebContentsDebuggingEnabled(true);
        //关闭横向滑动bar
        webView.setHorizontalScrollBarEnabled(false);
        //关闭纵向滑动bar
        webView.setVerticalScrollBarEnabled(false);
        //允许截图
        webView.setDrawingCacheEnabled(true);
        //屏蔽长按事件
        webView.setOnLongClickListener(view -> true);
        //初始化web setting
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        final String ua = settings.getUserAgentString();
        settings.setUserAgentString(ua + "Kepler");
        //隐藏缩放控件
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        //禁止缩放
        settings.setSupportZoom(false);
        //文件权限
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        //缓存相关
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        return webView;

    }
}
