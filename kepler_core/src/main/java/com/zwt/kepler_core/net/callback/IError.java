package com.zwt.kepler_core.net.callback;

/**
 * @author ZWT
 */
public interface IError {
    /**
     * 网络请求错误回调
     */
    void onError(String errorMsg);
}
