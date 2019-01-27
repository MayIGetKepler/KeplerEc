package com.zwt.kepler_ec.ec.launcher;

import android.os.Bundle;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.zwt.kepler_core.delegates.KeplerDelegate;
import com.zwt.kepler_core.ui.launcher.LauncherHolderCreator;
import com.zwt.kepler_core.ui.launcher.ScrollLauncherTag;
import com.zwt.kepler_core.util.storage.KeplerPreference;
import com.zwt.kepler_ec.ec.R;

import java.util.ArrayList;

/**
 * @author ZWT
 */
public class LauncherScrollDelegate extends KeplerDelegate implements OnItemClickListener {
    private ConvenientBanner<Integer> mBanner = null;
    private final ArrayList<Integer> INTEGERS = new ArrayList<>();

    private void initBanner() {
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        mBanner.setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public Object setLayout() {
        mBanner = new ConvenientBanner<>(getContext());
        return mBanner;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {
        if (position == INTEGERS.size() - 1){
            KeplerPreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(),true);
            //检查用户是否已经登录
        }
    }
}
