package com.zwt.kepler_core.net;

import com.zwt.kepler_core.net.callback.IError;
import com.zwt.kepler_core.net.callback.IFailure;
import com.zwt.kepler_core.net.callback.IRequest;
import com.zwt.kepler_core.net.callback.ISuccess;
import com.zwt.kepler_core.net.callback.RequestCallback;

import java.util.WeakHashMap;

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

    public RestClient(String URL, ISuccess SUCCESS, IError ERROR, IFailure FAILURE, IRequest REQUEST, RequestBody BODY) {
        this.URL = URL;
        this.SUCCESS = SUCCESS;
        this.ERROR = ERROR;
        this.FAILURE = FAILURE;
        this.REQUEST = REQUEST;
        this.BODY = BODY;
    }

    public static RestClientBuilder Builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
       Call<String> call = null;

        switch (method) {
            case GET:
                call = RestCreator.getRestService().get(URL,PARAMS);
                break;
            case POST:
                call = RestCreator.getRestService().post(URL,PARAMS);
                break;
            case PUT:
                call = RestCreator.getRestService().put(URL,PARAMS);
                break;
            case DELETE:
                call = RestCreator.getRestService().delete(URL,PARAMS);
                break;
            case DOWNLOAD:
                call = RestCreator.getRestService().dowmload(URL,PARAMS);
                break;
            case UPLOAD:

                break;
            default:
                break;
        }
        if (call != null){
            call.enqueue(getCallback());
        }
    }

    public void get() {
        request(HttpMethod.GET);
    }

    private RequestCallback getCallback(){
        return new RequestCallback(SUCCESS,ERROR,FAILURE,REQUEST);
    }
}
