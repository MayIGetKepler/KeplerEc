package com.zwt.kepler_core.ui.refresh;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zwt.kepler_core.net.RestClient;
import com.zwt.kepler_core.ui.recycler.DataConverter;
import com.zwt.kepler_core.ui.recycler.MultipleRecyclerViewAdapter;

/**
 * @author ZWT
 */
public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerViewAdapter mAdapter = null;
    private DataConverter CONVERTER;

    private RefreshHandler(@NonNull SwipeRefreshLayout refreshLayout,
                           RecyclerView recyclerView,DataConverter converter,PagingBean bean) {
        this.REFRESH_LAYOUT = refreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.BEAN = bean;
        this.CONVERTER = converter;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(@NonNull SwipeRefreshLayout refreshLayout,
                                        RecyclerView recyclerView, DataConverter converter){
        return new RefreshHandler(refreshLayout,recyclerView,converter,new PagingBean());
    }

    @Override
    public void onRefresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Thread thread = new Thread(
                () -> {
                    try {
                        Thread.sleep(2000);
                        REFRESH_LAYOUT.setRefreshing(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
    }

    public void firstPage(String url) {
        BEAN.setDelayed(1000);
        RestClient
                .Builder()
                .loader(REFRESH_LAYOUT.getContext())
                .url(url)
                .success(body -> {
                    final JSONObject object = JSON.parseObject(body);
                    BEAN.setTotal(object.getInteger("total"));
                    BEAN.setPageSize(object.getInteger("page_size"));
                    //adapter
                    mAdapter = MultipleRecyclerViewAdapter.create(CONVERTER.setJsonData(body));
                    mAdapter.setOnLoadMoreListener(this,RECYCLERVIEW);
                    RECYCLERVIEW.setAdapter(mAdapter);
                    BEAN.addIndex();
                })
                .build()
                .get();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
