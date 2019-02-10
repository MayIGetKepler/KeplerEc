package com.zwt.kepler_core.ui.recycler.divider;

import android.support.annotation.ColorInt;

/**
 * @author ZWT
 */
public class DividerLookupImpl implements DividerItemDecoration.DividerLookup {
    private final int COLOR;
    private final int SIZE;
    public DividerLookupImpl(@ColorInt int color, int size) {
        COLOR = color;
        SIZE =size;
    }

    @Override
    public Divider getVerticalDivider(int position) {
        return new Divider.Builder().color(COLOR).size(SIZE).build();
    }

    @Override
    public Divider getHorizontalDivider(int position) {
        return new Divider.Builder().color(COLOR).size(SIZE).build();
    }
}
