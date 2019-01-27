package com.zwt.keplerec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.zwt.kepler_core.activities.ProxyActivity;
import com.zwt.kepler_core.delegates.KeplerDelegate;
import com.zwt.kepler_core.ui.launcher.ScrollLauncherTag;
import com.zwt.kepler_core.util.storage.KeplerPreference;
import com.zwt.kepler_ec.ec.launcher.LauncherDelegate;
import com.zwt.kepler_ec.ec.launcher.LauncherScrollDelegate;

public class MainActivity extends ProxyActivity {

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
        return KeplerPreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())?
                new LauncherDelegate()
                :new LauncherScrollDelegate();
    }
}
