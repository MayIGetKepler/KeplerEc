package com.zwt.kepler_core.net.interceptor;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * @author ZWT
 */
public abstract class BaseInterceptor implements Interceptor {


    /**
     * 获取请求的params
     * @param chain 请求
     * @return params
     */
    protected LinkedHashMap<String,String> getUrlParams(Chain chain){
        HttpUrl url = chain.request().url();
        int querySize = url.querySize();
        final LinkedHashMap<String,String> params = new LinkedHashMap<>();
        for (int i = 0; i < querySize; i++) {
            params.put(url.queryParameterName(i),url.queryParameterValue(i));
        }
        return params;
    }

    /**
     * 通过key获取param的值
     * @param chain 请求
     * @param key key
     * @return value
     */
    protected String getUrlParams(Chain chain,String key){
        return chain.request().url().queryParameter(key);
    }

    /**
     * 获取请求的body
     * @param chain 请求
     * @return params
     */
    private LinkedHashMap<String,String> getBodyParams(Chain chain){
        FormBody body = (FormBody) chain.request().body();
        LinkedHashMap<String,String> params = new LinkedHashMap<>();
        if (body != null) {
            int size = body.size();
            for (int i = 0; i < size; i++) {
                params.put(body.name(i),body.value(i));
            }
        }
        return params;
    }

    /**
     * 通过key获取body的值
     * @param chain 请求
     * @param key key
     * @return value
     */
    protected String getBodyParams(Chain chain,String key){
        return getBodyParams(chain).get(key);
    }

}
