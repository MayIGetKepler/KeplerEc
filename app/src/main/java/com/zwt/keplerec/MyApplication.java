package com.zwt.keplerec;

import android.app.Application;

import com.zwt.kepler_core.application.Kepler;
import com.zwt.kepler_core.net.interceptor.DebugInterceptor;

/**
 * @author ZWT
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Kepler.init(this)
                .withApi("http://127.0.0.1/")
                .withInterceptor(new DebugInterceptor("/test",R.raw.debug))
                .configure();
    }
}
