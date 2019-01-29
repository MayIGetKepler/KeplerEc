package com.zwt.kepler_core.ui.launcher;

/**
 * @author ZWT
 */
public interface ILauncherListener {
    /**
     * 启动页面播放完成后的回调
     * @param tag 是否已登录
     */
    void onLauncherFinished(OnLauncherFinishedTag tag);
}
