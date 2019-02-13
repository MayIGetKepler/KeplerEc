package com.zwt.kepler_ec.ec.main.shop_car;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class ShopCarDelegate extends BottomItemDelegate implements ISuccess {

    private RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_car;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        findView(rootView);
    }

    private void findView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.rv_shop_car);
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
        ShopCarRecyclerAdapter adapter = new ShopCarRecyclerAdapter(entities);
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
    }
}
