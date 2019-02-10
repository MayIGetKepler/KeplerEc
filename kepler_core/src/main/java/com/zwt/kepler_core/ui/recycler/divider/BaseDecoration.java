package com.zwt.kepler_core.ui.recycler.divider;

import android.support.annotation.ColorInt;

/**
 * @author ZWT
 */
public class BaseDecoration extends DividerItemDecoration {
    private BaseDecoration(@ColorInt int color,int size) {
        setDividerLookup(new DividerLookupImpl(color,size));
    }

    public static BaseDecoration create(@ColorInt int color,int size){
        return new BaseDecoration(color,size);
    }
}
