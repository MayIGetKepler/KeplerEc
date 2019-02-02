package com.zwt.kepler_core.delegates.bottom;

import android.widget.Toast;

import com.zwt.kepler_core.delegates.KeplerDelegate;

/**
 * @author ZWT
 * 导航栏item对应的fragment
 */
public abstract class BottomItemDelegate extends KeplerDelegate {

    private long TOUCH_TIME = 0;
    private static final long WAIT_TIME = 2000;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "再按一次退出", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


}
