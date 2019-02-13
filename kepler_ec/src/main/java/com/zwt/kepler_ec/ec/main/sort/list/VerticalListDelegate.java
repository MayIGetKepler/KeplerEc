package com.zwt.kepler_ec.ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zwt.kepler_core.delegates.KeplerDelegate;
import com.zwt.kepler_core.net.RestClient;
import com.zwt.kepler_core.ui.recycler.MultipleItemEntity;
import com.zwt.kepler_ec.ec.R;

import java.util.ArrayList;

/**
 * @author ZWT
 */
public class VerticalListDelegate extends KeplerDelegate {

    private RecyclerView mRvList;

    @Override
    public Object setLayout() {
        return R.layout.delegate_vertical_list;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        findView(rootView);
        initRecyclerView();
    }

    private void findView(View rootView) {
        mRvList = rootView.findViewById(R.id.rv_sort_vertical);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.Builder()
                .url("sort_list")
                .loader(getContext())
                .success(body -> {
                    final ArrayList<MultipleItemEntity> entities =
                            new VerticalListDataConveter().setJsonData(body).convert();
                    final VerticalListRecyclerAdapter adapter =
                            VerticalListRecyclerAdapter.create(entities, getParentDelegate());
                    mRvList.setAdapter(adapter);
                })
                .build()
                .get();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        //关闭item变化的动画效果
        mRvList.setItemAnimator(null);
        mRvList.setLayoutManager(manager);
    }
}
