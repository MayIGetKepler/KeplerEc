package com.zwt.kepler_core.net.download;

import android.os.AsyncTask;

import com.zwt.kepler_core.net.RestCreator;
import com.zwt.kepler_core.net.callback.IError;
import com.zwt.kepler_core.net.callback.IFailure;
import com.zwt.kepler_core.net.callback.IRequest;
import com.zwt.kepler_core.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author ZWT
 */
public class DownloadHandler {
    private final String URL;
    private final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final IRequest REQUEST;

    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String FULLNAME;

    public DownloadHandler(String url, ISuccess success,
                           IError error, IFailure failure,
                           IRequest request, String download_dir,
                           String extension, String fullname) {
        this.URL = url;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.REQUEST = request;
        this.DOWNLOAD_DIR = download_dir;
        this.EXTENSION = extension;
        this.FULLNAME = fullname;
    }

    public final void handlerDownload(){
        if (REQUEST != null){
            REQUEST.onRequestStart();
        }

        RestCreator.getRestService().download(URL,PARAMS)
        .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                final ResponseBody body = response.body();
                final SaveFileTask saveFileTask = new SaveFileTask(SUCCESS,REQUEST);
                saveFileTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,DOWNLOAD_DIR,EXTENSION,FULLNAME,body);

                //不判断cancelled，可能还没下载完全就执行。
                if (saveFileTask.isCancelled()){
                    if (REQUEST != null){
                        REQUEST.onRequestEnd();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                FAILURE.onFailure(t.getMessage());
            }
        });
    }
}
