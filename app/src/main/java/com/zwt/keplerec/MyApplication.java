package com.zwt.keplerec;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.zwt.kepler_core.application.Kepler;
import com.zwt.kepler_core.net.interceptor.DebugInterceptor;
import com.zwt.kepler_ec.ec.database.DatabaseManager;

/**
 * @author ZWT
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Kepler.init(this)
                .withApi("http://192.168.1.109:8080/KeplerEc/")
                .withInterceptor(new DebugInterceptor("/test",R.raw.debug))
                .configure();
        DatabaseManager.getInstance().init(this);
        Stetho.initializeWithDefaults(this);
    }
}
