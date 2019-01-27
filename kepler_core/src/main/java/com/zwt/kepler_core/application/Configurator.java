package com.zwt.kepler_core.application;

import java.util.ArrayList;
import java.util.WeakHashMap;

import okhttp3.Interceptor;

/**
 * @author ZWT
 */
public final class Configurator {
    private static final WeakHashMap<Object, Object> KEPLER_CONFIGS = new WeakHashMap<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator() {
        KEPLER_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final WeakHashMap<Object, Object> getKeplerConfigs() {
        return KEPLER_CONFIGS;
    }

    public final void configure() {
        KEPLER_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    public final Configurator withApi(String api) {
        KEPLER_CONFIGS.put(ConfigType.HOST_API.name(), api);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        KEPLER_CONFIGS.put(ConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        KEPLER_CONFIGS.put(ConfigType.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    private void checkConfiguration() {
        boolean isReady = (boolean) KEPLER_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Configure is not ready , call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        return (T) KEPLER_CONFIGS.get(key);
    }


}
