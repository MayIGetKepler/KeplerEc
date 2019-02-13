package com.zwt.kepler_ec.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zwt.kepler_core.delegates.bottom.BottomItemDelegate;
import com.zwt.kepler_ec.ec.R;
import com.zwt.kepler_ec.ec.main.sort.content.ContentDelegate;
import com.zwt.kepler_ec.ec.main.sort.list.VerticalListDelegate;

/**
 * @author ZWT
 */
public class SortDelegate extends BottomItemDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        loadRootFragment(R.id.vertical_list_container,new VerticalListDelegate());
        loadRootFragment(R.id.sort_content_container,ContentDelegate.newInstance(1));
    }
}
