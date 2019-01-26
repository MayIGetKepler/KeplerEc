package com.zwt.kepler_core.net;

import com.zwt.kepler_core.net.callback.IError;
import com.zwt.kepler_core.net.callback.IFailure;
import com.zwt.kepler_core.net.callback.IRequest;
import com.zwt.kepler_core.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.RequestBody;

/**
 * @author ZWT
 * RestClient的Builder
 */
public class RestClientBuilder {
    private  String mUrl;
    private  WeakHashMap<String,Object> mParams = RestCreator.getParams();
    private  ISuccess mISuccess;
    private  IError mIError;
    private  IFailure mIFailure;
    private  IRequest mIRequest;
    private  RequestBody mBody;

    public   RestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }
    /******************************以下为params可选方法**********************************/


    public   RestClientBuilder params(WeakHashMap<String,Object> params){
        this.mParams.putAll(params);
        return this;
    }

    public RestClientBuilder params(String key,Object value){
        this.mParams.put(key,value);
        return this;
    }

    public  RestClientBuilder success(ISuccess success){
        this.mISuccess = success;
        return this;
    }
    public  RestClientBuilder error(IError error){
        this.mIError = error;
        return this;
    }
    public  RestClientBuilder failure(IFailure failure){
        this.mIFailure = failure;
        return this;
    }

    public RestClientBuilder request(IRequest request){
        this.mIRequest = request;
        return this;
    }

    public RestClientBuilder body(RequestBody body){
        this.mBody = body;
        return this;
    }

    public RestClient build(){
        return new RestClient(mUrl,mISuccess,mIError,mIFailure,mIRequest,mBody);
    }
}
