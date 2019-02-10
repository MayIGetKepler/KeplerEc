package com.zwt.kepler_ec.ec.detail;

import android.os.Bundle;
import android.view.View;

import com.zwt.kepler_core.delegates.KeplerDelegate;
import com.zwt.kepler_ec.ec.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author ZWT
 */
public class GoodsDetailDelegate extends KeplerDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {

    }

    public static GoodsDetailDelegate create(){
        return new GoodsDetailDelegate();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
