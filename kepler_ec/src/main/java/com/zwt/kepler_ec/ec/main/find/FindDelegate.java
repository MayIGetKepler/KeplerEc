package com.zwt.kepler_ec.ec.main.find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zwt.kepler_core.delegates.bottom.BottomItemDelegate;
import com.zwt.kepler_core.delegates.web.WebDelegateImpl;
import com.zwt.kepler_ec.ec.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author ZWT
 */
public class FindDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_find;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final WebDelegateImpl delegate = WebDelegateImpl.create("index.html");
        delegate.setTopDelegate(getParentDelegate());
        loadRootFragment(R.id.web_discovery_container,delegate);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
