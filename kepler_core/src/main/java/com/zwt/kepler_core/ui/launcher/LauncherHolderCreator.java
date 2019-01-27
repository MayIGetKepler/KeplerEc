package com.zwt.kepler_core.ui.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * @author ZWT
 */
public class LauncherHolderCreator implements CBViewHolderCreator<LauncherHolder> {


    @Override
    public LauncherHolder createHolder() {
        return new LauncherHolder();
    }
}
