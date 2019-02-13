package com.zwt.kepler_core.delegates.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.zwt.kepler_core.delegates.web.event.Event;
import com.zwt.kepler_core.delegates.web.event.EventManager;

/**
 * @author ZWT
 */
public class KeplerWebInterface {
    private final WebDelegate DELEGATE;

    private KeplerWebInterface(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static KeplerWebInterface create(WebDelegate delegate){
        return new KeplerWebInterface(delegate);
    }

    @SuppressWarnings("unused")
    @JavascriptInterface
    public String event(String params){
        final String action = JSON.parseObject(params).getString("action");
        final Event event = EventManager.getInstance().createEvent(action);
        if (event!=null){
            event.setAction(action);
            event.setWebView(DELEGATE.getWebView());
            event.setContext(DELEGATE.getContext());
            event.setDelegate(DELEGATE);
            event.setUrl(DELEGATE.getUrl());
            return event.execute(params);
        }
        return null;
    }
}
