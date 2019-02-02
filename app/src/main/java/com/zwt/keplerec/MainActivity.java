package com.zwt.keplerec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.zwt.kepler_core.activities.ProxyActivity;
import com.zwt.kepler_core.delegates.KeplerDelegate;
import com.zwt.kepler_core.ui.launcher.ILauncherListener;
import com.zwt.kepler_core.ui.launcher.OnLauncherFinishedTag;
import com.zwt.kepler_ec.ec.main.EcBottomDelegate;
import com.zwt.kepler_ec.ec.sign.ISignListener;
import com.zwt.kepler_ec.ec.sign.SignInDelegate;

public class MainActivity extends ProxyActivity implements
        ISignListener,ILauncherListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if ( actionBar != null){
            actionBar.hide();
        }

    }

    @Override
    public KeplerDelegate setRootDelegate() {
//        return KeplerPreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())?
//                new LauncherDelegate()
//                :new LauncherScrollDelegate();
        return new EcBottomDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this,"登录成功",Toast.LENGTH_LONG).show();
        startWithPop(new EntryDelegate());
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this,"注册成功",Toast.LENGTH_LONG).show();
        startWithPop(new EntryDelegate());
    }

    @Override
    public void onLauncherFinished(OnLauncherFinishedTag tag) {
        switch (tag) {
            case SIGNED:
                Toast.makeText(this,"已经登录，加载主界面",Toast.LENGTH_SHORT).show();
                startWithPop(new EntryDelegate());
                break;
            case NOT_SIGNED:
                Toast.makeText(this,"未登录，加载登录界面",Toast.LENGTH_SHORT).show();
                startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Log.e("KEYCODE_BACK", "===="  );
        }
        return super.onKeyDown(keyCode, event);
    }
}
