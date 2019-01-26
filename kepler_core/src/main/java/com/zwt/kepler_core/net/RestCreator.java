package com.zwt.kepler_core.net;

import com.zwt.kepler_core.application.ConfigType;
import com.zwt.kepler_core.application.Kepler;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author ZWT
 */
 class RestCreator {

    /**
     * 请求params单例，避免多并发请求时，生成大量params实例
     */
    private static final class paramsHolder{
       private static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();
    }

    public static  WeakHashMap<String,Object> getParams(){
        return  paramsHolder.PARAMS;
    }

    /**
     * 获取RestService单例
     * @return RestService单例
     */
    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }


    /**
     * Retrofit单例
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL =
                (String) Kepler.getConfigurations().get(ConfigType.HOST_API.name());

        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .build();
    }

    /**
     * OKHttp单例
     */
    private static final class OKHttpHolder{
        private static final int TIMEOUT = 20;
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT,TimeUnit.SECONDS)
                .build();
    }

    /**
     * RestService单例
     */
    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }
}
