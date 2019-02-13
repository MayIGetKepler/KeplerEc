package com.zwt.kepler_core.delegates.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.zwt.kepler_core.delegates.KeplerDelegate;
import com.zwt.kepler_core.delegates.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author ZWT
 */
public abstract class WebDelegate extends KeplerDelegate implements IWebViewInitializer {

    private WebView mWebView = null;
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    private String mUrl = null;
    private boolean mIsWebViewAvailable = false;
    private KeplerDelegate mTopDelegate = null;


    public abstract IWebViewInitializer setInitializer();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mUrl = args.getString(RouteKeys.URL.name());
            initWebView();
        }
    }

    private void initWebView() {

        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        } else {
            final IWebViewInitializer initializer = setInitializer();
            if (initializer != null) {
                final WeakReference<WebView> webViewReference =
                        new WeakReference<>(new WebView(getContext()), WEB_VIEW_QUEUE);
                mWebView = webViewReference.get();
                mWebView = initializer.initWebView(mWebView);
                mWebView.setWebViewClient(initializer.initWebViewClient());
                mWebView.setWebChromeClient(initializer.initWebChromeClient());
                mWebView.addJavascriptInterface(KeplerWebInterface.create(this), "kepler");
                mIsWebViewAvailable = true;
            } else {
                throw new NullPointerException("Initialize is null !");
            }
        }
    }

    public void setTopDelegate(KeplerDelegate delegate) {
        mTopDelegate = delegate;
    }

    public KeplerDelegate getTopDelegate() {
        if (mTopDelegate == null) {
            return this;
        }
        return mTopDelegate;
    }

    public WebView getWebView() {
        if (mWebView != null) {
            return mIsWebViewAvailable ? mWebView : null;
        }
        throw new NullPointerException("WebView is Null !");
    }

    public String getUrl() {
        if (mUrl != null) {
            return mUrl;
        }
        throw new NullPointerException("URL is Null!");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAvailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
