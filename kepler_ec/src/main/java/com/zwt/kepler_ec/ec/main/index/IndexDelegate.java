package com.zwt.kepler_ec.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zwt.kepler_core.delegates.bottom.BottomItemDelegate;
import com.zwt.kepler_core.ui.recycler.divider.BaseDecoration;
import com.zwt.kepler_core.ui.refresh.RefreshHandler;
import com.zwt.kepler_ec.ec.R;

/**
 * @author ZWT
 */
public class IndexDelegate extends BottomItemDelegate {

    private CoordinatorLayout mCdlIndex;
    private SwipeRefreshLayout mSrlIndex;
    private RecyclerView mRvIndex;
    private Toolbar mTbIndex;
    private AppCompatImageView mIvScan;
    private AppCompatEditText mEtSearch;
    private AppCompatImageView mIvBullhorn;

    private RefreshHandler mRefreshHandler = null;


    private void findViews(View rootView) {
        mCdlIndex = rootView.findViewById(R.id.cdl_index);
        mSrlIndex = rootView.findViewById(R.id.srl_index);
        mRvIndex = rootView.findViewById(R.id.rv_index);
        mTbIndex = rootView.findViewById(R.id.tb_index);
        mIvScan = rootView.findViewById(R.id.iv_scan);
        mEtSearch = rootView.findViewById(R.id.et_search);
        mIvBullhorn = rootView.findViewById(R.id.iv_bullhorn);
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefresh();
        initRecyclerView();
        mRefreshHandler.firstPage("index");
    }

    private void initRefresh() {
        mSrlIndex.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light
        );
        mSrlIndex.setProgressViewOffset(true,80,250);
    }

    private void initRecyclerView(){
        final GridLayoutManager manager = new GridLayoutManager(getContext(),4);
        mRvIndex.setLayoutManager(manager);
        mRvIndex.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(getContext(),R.color.app_background),5));
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        findViews(rootView);
        mRefreshHandler =  RefreshHandler.create(mSrlIndex,mRvIndex,new IndexDataConverter());
    }


}
