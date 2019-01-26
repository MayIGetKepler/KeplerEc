package com.zwt.kepler_core.net.callback;

/**
 * @author ZWT
 * 网络请求各个阶段的回调
 */
public interface IRequest {
    /**
     * 网络请求开始的回调
     */
    void onRequestStart();

    /**
     * 网络请求结束的回调
     */
    void onRequestEnd();
}
