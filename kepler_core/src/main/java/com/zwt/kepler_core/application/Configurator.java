package com.zwt.kepler_core.application;

import java.util.WeakHashMap;

/**
 * @author ZWT
 */
public final class Configurator {
    private static final WeakHashMap<String,Object> KEPLER_CONFIGS = new WeakHashMap<>();

    private Configurator(){
        KEPLER_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
    }

    private static class Holder{
      private static final Configurator INSTANCE = new Configurator();
    }

     static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    final WeakHashMap<String,Object> getKeplerConfigs(){
        return KEPLER_CONFIGS;
    }

    public final void configure(){
        KEPLER_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
    }

    public final Configurator withApi(String api){
        KEPLER_CONFIGS.put(ConfigType.HOST_API.name(),api);
        return this;
    }

    private void checkConfiguration(){
       boolean isReady = (boolean) KEPLER_CONFIGS.get(ConfigType.CONFIG_READY.name());
       if (!isReady){
           throw new RuntimeException("Configure is not ready , call configure");
       }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key){
        checkConfiguration();
        return (T)KEPLER_CONFIGS.get(key);
    }
}
