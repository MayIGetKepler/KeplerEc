package com.zwt.kepler_core.net.callback;

import com.zwt.kepler_core.ui.loader.KeplerLoader;
import com.zwt.kepler_core.ui.loader.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author ZWT
 */
public class RequestCallback implements Callback<String> {

    private final ISuccess mISuccess;
    private final IError mIError;
    private final IFailure mIFailure;
    private final IRequest mIRequest;
    private final LoaderStyle mLoaderStyle;

    public RequestCallback(ISuccess ISuccess, IError IError,
                           IFailure IFailure, IRequest IRequest,
                           LoaderStyle loaderStyle) {
        mISuccess = ISuccess;
        mIError = IError;
        mIFailure = IFailure;
        mIRequest = IRequest;
        mLoaderStyle = loaderStyle;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (mISuccess != null) {
                mISuccess.onSuccess(response.body());
            }
        } else {
            if (mIError != null) {
                mIError.onError(response.message());
            }
        }
        requestStop();
    }



    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (mIFailure != null) {
            mIFailure.onFailure(t.getMessage());
        }
        requestStop();
    }

    private void requestStop() {
        if (mIRequest != null) {
            mIRequest.onRequestEnd();
        }

            KeplerLoader.stopLoading();

    }
}
