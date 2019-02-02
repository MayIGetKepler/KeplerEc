package com.zwt.kepler_core.delegates.bottom;

import android.support.annotation.DrawableRes;

/**
 * @author ZWT
 */
public class BottomTabBean {
    private final int IconId;
    private final int ActiveIconId;
    private final String Title;

    public BottomTabBean(@DrawableRes int iconId, @DrawableRes int activeIconId,String title) {
        IconId = iconId;
        ActiveIconId =activeIconId;
        Title = title;
    }

    public int getIcon() {
        return IconId;
    }

    public String getTitle() {
        return Title;
    }

    public int getActiveIconId() {
        return ActiveIconId;
    }

}
