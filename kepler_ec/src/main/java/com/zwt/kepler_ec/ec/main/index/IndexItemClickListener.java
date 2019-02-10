package com.zwt.kepler_ec.ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.zwt.kepler_core.delegates.KeplerDelegate;
import com.zwt.kepler_ec.ec.detail.GoodsDetailDelegate;

/**
 * @author ZWT
 */
public class IndexItemClickListener extends SimpleClickListener {

    private final KeplerDelegate DELEGATE;

    private IndexItemClickListener(KeplerDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static IndexItemClickListener create(KeplerDelegate delegate){
        return new IndexItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DELEGATE.start(GoodsDetailDelegate.create());
        
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
