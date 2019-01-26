package com.zwt.kepler_core.application;

import android.content.Context;

import java.util.WeakHashMap;

/**
 * @author ZWT
 */
public final class Kepler {
    public static Configurator init(Context context){
       getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
       return Configurator.getInstance();
    }

    public static WeakHashMap<String,Object> getConfigurations(){
        return Configurator.getInstance().getKeplerConfigs();
    }

    public static Context getApplicationContext(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}
