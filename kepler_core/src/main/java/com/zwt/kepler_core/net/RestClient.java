package com.zwt.kepler_core.net;

import android.content.Context;

import com.zwt.kepler_core.net.callback.IError;
import com.zwt.kepler_core.net.callback.IFailure;
import com.zwt.kepler_core.net.callback.IRequest;
import com.zwt.kepler_core.net.callback.ISuccess;
import com.zwt.kepler_core.net.callback.RequestCallback;
import com.zwt.kepler_core.net.download.DownloadHandler;
import com.zwt.kepler_core.ui.loader.KeplerLoader;
import com.zwt.kepler_core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @author ZWT
 */
public class RestClient {
    private final String URL;
    private final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final IRequest REQUEST;
    private final RequestBody BODY;
    private final LoaderStyle STYLE;
    private final Context CONTEXT;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String FULLNAME;
    private final File FILE;

    public RestClient(String url, ISuccess success, IError error,
                      IFailure failure, IRequest request,
                      RequestBody body, LoaderStyle style,
                      Context context,String download_dir,
                      String extension,String fullname,
                      File file) {
        this.URL = url;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.REQUEST = request;
        this.BODY = body;
        this.STYLE = style;
        this.CONTEXT = context;
        this.DOWNLOAD_DIR = download_dir;
        this.EXTENSION = extension;
        this.FULLNAME = fullname;
        this.FILE = file;
    }

    public static RestClientBuilder Builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        Call<String> call = null;

        switch (method) {
            case GET:
                call = RestCreator.getRestService().get(URL, PARAMS);
                break;
            case POST:
                call = RestCreator.getRestService().post(URL, PARAMS);
                break;
            case POST_RAW:
                call = RestCreator.getRestService().postRaw(URL, BODY);
                break;
            case PUT:
                call = RestCreator.getRestService().put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = RestCreator.getRestService().putRaw(URL, BODY);
                break;
            case DELETE:
                call = RestCreator.getRestService().delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call = RestCreator.getRestService().upload(URL,body);
                break;
            default:
                break;
        }
        if (call != null) {
            call.enqueue(getCallback());
            showLoader();

        }
    }

    private void showLoader() {
        if (CONTEXT != null){
            if (STYLE != null){
                KeplerLoader.showLoading(CONTEXT,STYLE.name());
            }else {
                KeplerLoader.showLoading(CONTEXT);
            }
        }
    }

    public void get() {
        request(HttpMethod.GET);
    }

    public void post(){
        if (BODY == null){
            request(HttpMethod.POST);
        }else if (PARAMS != null){
            throw new RuntimeException("params must be null");
        }
        request(HttpMethod.POST_RAW);
    }

    public  void put(){
        if (BODY == null){
            request(HttpMethod.PUT);
        }else if (PARAMS != null){
            throw  new RuntimeException("params must be null");
        }
        request(HttpMethod.PUT_RAW);
    }

    public void upload(){
        request(HttpMethod.UPLOAD);
    }

    public void download(){
        new DownloadHandler(URL,SUCCESS,ERROR,FAILURE,
                REQUEST,DOWNLOAD_DIR,EXTENSION,FULLNAME).handlerDownload();
    }

    private RequestCallback getCallback() {
        return new RequestCallback(SUCCESS, ERROR, FAILURE, REQUEST, STYLE);
    }
}
