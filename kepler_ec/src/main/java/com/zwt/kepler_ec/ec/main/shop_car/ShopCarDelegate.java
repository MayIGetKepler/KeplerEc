package com.zwt.kepler_ec.ec.main.shop_car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zwt.kepler_core.delegates.bottom.BottomItemDelegate;
import com.zwt.kepler_core.net.RestClient;
import com.zwt.kepler_core.net.callback.ISuccess;
import com.zwt.kepler_core.ui.recycler.MultipleItemEntity;
import com.zwt.kepler_ec.ec.R;

import java.util.ArrayList;

/**
 * @author ZWT
 */
public class ShopCarDelegate extends BottomItemDelegate implements ISuccess,ISelectAll {

    private RecyclerView mRecyclerView = null;
    private AppCompatImageView mIvSelectAll = null;
    private boolean mHasSelectedAll = false;
    private ShopCarRecyclerAdapter mAdapter = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_car;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        findView(rootView);
        initView();
    }

    private void initView() {
        mIvSelectAll.setTag(0);
        mIvSelectAll.setOnClickListener(view -> {
            final int  selected = (int) mIvSelectAll.getTag();
            if (selected == 0){
                mIvSelectAll.setImageResource(R.drawable.ic_checked);
                mIvSelectAll.setTag(1);
            }else {
                mIvSelectAll.setImageResource(R.drawable.ic_uncheck);
                mIvSelectAll.setTag(0);
            }
            mHasSelectedAll = !mHasSelectedAll;
            mAdapter.setSelectedAll(mHasSelectedAll);
            mAdapter.selectAll(mHasSelectedAll);

        });
    }

    private void findView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.rv_shop_car);
        mIvSelectAll = rootView.findViewById(R.id.iv_shop_car_check_all);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        RestClient.Builder()
                .loader(getContext())
                .url("shop_car_data")
                .success(this)
                .build()
                .get();


    }

    @Override
    public void onSuccess(String body) {
        ArrayList<MultipleItemEntity> entities = new ShopCarDataConverter().setJsonData(body).convert();
        mAdapter = new ShopCarRecyclerAdapter(entities);
        mAdapter.setISelectAll(this);
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void unSelected() {
        final int tag = (int) mIvSelectAll.getTag();
        if (tag != 0){
            mIvSelectAll.setImageResource(R.drawable.ic_uncheck);
            mIvSelectAll.setTag(0);
            mHasSelectedAll = !mHasSelectedAll;
        }

    }

    @Override
    public void allSelected() {
        final int tag = (int) mIvSelectAll.getTag();
        if (tag != 1){
            mIvSelectAll.setImageResource(R.drawable.ic_checked);
            mIvSelectAll.setTag(1);
            mHasSelectedAll = !mHasSelectedAll;
        }
    }
}
