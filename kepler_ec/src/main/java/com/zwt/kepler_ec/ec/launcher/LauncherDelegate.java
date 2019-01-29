package com.zwt.kepler_ec.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.zwt.kepler_core.application.AccountManager;
import com.zwt.kepler_core.application.IUserChecker;
import com.zwt.kepler_core.delegates.KeplerDelegate;
import com.zwt.kepler_core.ui.launcher.ILauncherListener;
import com.zwt.kepler_core.ui.launcher.OnLauncherFinishedTag;
import com.zwt.kepler_core.ui.launcher.ScrollLauncherTag;
import com.zwt.kepler_core.util.storage.KeplerPreference;
import com.zwt.kepler_core.util.timer.BaseTimerTask;
import com.zwt.kepler_core.util.timer.ITimerListener;
import com.zwt.kepler_ec.ec.R;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Timer;

/**
 * @author ZWT
 */
public class LauncherDelegate extends KeplerDelegate implements ITimerListener {

    private Timer mTimer = null;
    private AppCompatTextView mTvTimer;
    private int mCounter = 5;

    private ILauncherListener mILauncherListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener){
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initView(rootView);
        initTimer();
    }

    private void initView(View rootView) {
        mTvTimer = rootView.findViewById(R.id.tv_launcher_timer);
        mTvTimer.setOnClickListener(view -> launcherJumper());
    }

    private void initTimer() {

        mTimer = new Timer();
        BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
    }

//    /**
//     * 判断是否第一次进入app，是否需要显示scroll launch页面
//     */
//    private void checkIsShowScroll(){
//        if (!KeplerPreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){
//            start(new LauncherScrollDelegate(),SINGLETASK);
//        }else {
//             //检查用户是否已经登录
//        }
//    }

    @Override
    public void onTimer() {
        Objects.requireNonNull(getActivity()).runOnUiThread(
                () -> {
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCounter));
                    mCounter--;
                    if (mCounter < 0 ) {
                        launcherJumper();

                        //                            checkIsShowScroll();
                    }
                }
        );
    }

    //启动页面结束 ，跳转到其他页面
    private void launcherJumper() {
        if (mTimer != null){
            mTimer.cancel();
            mTimer = null;
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    mILauncherListener.onLauncherFinished(OnLauncherFinishedTag.SIGNED);
                }

                @Override
                public void onNotSignIn() {
                    //如果不是第一次启动APP 跳转登录页面 否则打开轮播启动页
                    if (KeplerPreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){
                        mILauncherListener.onLauncherFinished(OnLauncherFinishedTag.NOT_SIGNED);
                    }else {
                        startWithPop(new LauncherScrollDelegate());
                    }

                }
            });
        }
    }

}
