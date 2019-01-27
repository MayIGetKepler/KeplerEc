package com.zwt.kepler_core.util.timer;

import java.util.TimerTask;

/**
 * @author ZWT
 */
public class BaseTimerTask extends TimerTask  {
    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener ITimerListener) {
        this.mITimerListener = ITimerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null){
            mITimerListener.onTimer();
        }
    }
}
