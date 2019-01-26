package com.zwt.keplerec;

import com.zwt.kepler_core.activities.ProxyActivity;
import com.zwt.kepler_core.delegates.KeplerDelegate;

public class MainActivity extends ProxyActivity {

    @Override
    public KeplerDelegate setRootDelegate() {
        return new EntryDelegate();
    }
}
