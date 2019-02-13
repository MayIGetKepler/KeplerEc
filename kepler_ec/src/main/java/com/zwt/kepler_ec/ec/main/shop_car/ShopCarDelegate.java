package com.zwt.kepler_ec.ec.main.shop_car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zwt.kepler_core.delegates.bottom.BottomItemDelegate;
import com.zwt.kepler_core.net.RestClient;
import com.zwt.kepler_core.net.callback.ISuccess;
import com.zwt.kepler_core.ui.recycler.MultipleFields;
import com.zwt.kepler_core.ui.recycler.MultipleItemEntity;
import com.zwt.kepler_ec.ec.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZWT
 */
public class ShopCarDelegate extends BottomItemDelegate implements ISuccess, ISelectAll, View.OnClickListener {

    private RecyclerView mRecyclerView = null;
    private AppCompatImageView mIvSelectAll = null;
    private boolean mHasSelectedAll = false;
    private ShopCarRecyclerAdapter mAdapter = null;
    private AppCompatTextView mTvDelete = null;
    private AppCompatTextView mTvClear = null;

    private int mCurrentCount = 0;
    private int mTotalCount = 0;

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
        mIvSelectAll.setOnClickListener(this);
        mTvDelete.setOnClickListener(this);
        mTvClear.setOnClickListener(this);
    }

    private void findView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.rv_shop_car);
        mIvSelectAll = rootView.findViewById(R.id.iv_shop_car_check_all);
        mTvDelete = rootView.findViewById(R.id.tv_top_shop_car_delete);
        mTvClear = rootView.findViewById(R.id.tv_top_shop_car_clear);
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
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mCurrentCount = mTotalCount = mAdapter.getItemCount();
    }

    @Override
    public void unSelected() {
        final int tag = (int) mIvSelectAll.getTag();
        if (tag != 0) {
            mIvSelectAll.setImageResource(R.drawable.ic_uncheck);
            mIvSelectAll.setTag(0);
            mHasSelectedAll = !mHasSelectedAll;
        }

    }

    @Override
    public void allSelected() {
        final int tag = (int) mIvSelectAll.getTag();
        if (tag != 1) {
            mIvSelectAll.setImageResource(R.drawable.ic_checked);
            mIvSelectAll.setTag(1);
            mHasSelectedAll = !mHasSelectedAll;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mIvSelectAll) {
            final int selected = (int) mIvSelectAll.getTag();
            if (selected == 0) {
                mIvSelectAll.setImageResource(R.drawable.ic_checked);
                mIvSelectAll.setTag(1);
            } else {
                mIvSelectAll.setImageResource(R.drawable.ic_uncheck);
                mIvSelectAll.setTag(0);
            }
            mHasSelectedAll = !mHasSelectedAll;
            mAdapter.setSelectedAll(mHasSelectedAll);
            mAdapter.selectAll(mHasSelectedAll);
        } else if (view == mTvDelete) {
            deleteItem();
        } else if (view == mTvClear) {

        }
    }

    private void deleteItem() {
        List<MultipleItemEntity> entities = mAdapter.getData();
        //要删除的数据
        List<MultipleItemEntity> deleteItems = new ArrayList<>();
        for (MultipleItemEntity entity : entities) {
            final boolean isChecked = entity.getField(MultipleFields.TAG);
            if (isChecked) {
                deleteItems.add(entity);
            }
        }
        final int size = deleteItems.size();
        for (int i = 0; i < size; i++) {
            MultipleItemEntity entity = deleteItems.get(i);

            //item在数据中的position
            final int itemPosition = entity.getField(ShopCarItemFields.POSITION);


            if (itemPosition < mAdapter.getItemCount() && itemPosition >= 0) {
                mAdapter.remove(itemPosition);
                mCurrentCount = mAdapter.getItemCount();
                //更新删除的数据之后的item position
                mAdapter.resetPosition(itemPosition);
                //如果数据删完了，取消全选
                if (mAdapter.getItemCount()==0){
                    unSelected();
                }
            }
        }
    }
}
