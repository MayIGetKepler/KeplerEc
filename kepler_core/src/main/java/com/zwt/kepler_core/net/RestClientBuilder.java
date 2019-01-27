package com.zwt.kepler_core.net;

import android.content.Context;

import com.zwt.kepler_core.net.callback.IError;
import com.zwt.kepler_core.net.callback.IFailure;
import com.zwt.kepler_core.net.callback.IRequest;
import com.zwt.kepler_core.net.callback.ISuccess;
import com.zwt.kepler_core.ui.loader.LoaderStyle;

import java.io.File;
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
    private  LoaderStyle mLoaderStyle;
    private Context mContext;
    //下载用  文件夹 扩展名 全名 有全名则不需要文件夹和扩展名
    private  String DOWNLOAD_DIR;
    private  String EXTENSION;
    private  String FULLNAME;
    private File FILE;

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

    public RestClientBuilder loader(Context context, LoaderStyle style){
        this.mLoaderStyle = style;
        this.mContext = context;
        return this;
    }
    public RestClientBuilder loader(Context context){
        this.mContext = context;
        return this;
    }

    public RestClientBuilder extension(String extension){
        this.EXTENSION = extension;
        return this;
    }
    public RestClientBuilder dir(String dir){
        this.DOWNLOAD_DIR = dir;
        return this;
    }
    public RestClientBuilder fullname(String name){
        this.FULLNAME  = name;
        return this;
    }

    public RestClientBuilder file(File file){
        this.FILE = file;
        return this;
    }

    public RestClientBuilder file(String path){
        this.FILE = new File(path);
        return this;
    }

    public RestClient build(){
        return new RestClient(mUrl,mISuccess,mIError,
                mIFailure,mIRequest,mBody,
                mLoaderStyle,mContext,
                DOWNLOAD_DIR,EXTENSION,FULLNAME,
                FILE);
    }
}
