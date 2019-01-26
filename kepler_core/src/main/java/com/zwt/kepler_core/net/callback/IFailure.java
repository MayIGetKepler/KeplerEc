package com.zwt.kepler_core.net.callback;

/**
 * @author ZWT
 */
public interface IFailure {
    /**
     * 网络请求失败回调
     */
    void onFailure(String failureMsg);
}
