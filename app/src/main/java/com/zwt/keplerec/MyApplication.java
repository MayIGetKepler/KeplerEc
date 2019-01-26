package com.zwt.keplerec;

import android.app.Application;

import com.zwt.kepler_core.application.Kepler;

/**
 * @author ZWT
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Kepler.init(this)
                .withApi("http://127.0.0.1/")
                .configure();
    }
}
