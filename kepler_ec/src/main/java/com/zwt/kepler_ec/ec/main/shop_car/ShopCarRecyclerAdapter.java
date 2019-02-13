package com.zwt.kepler_ec.ec.main.shop_car;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.zwt.kepler_core.ui.recycler.MultipleFields;
import com.zwt.kepler_core.ui.recycler.MultipleItemEntity;
import com.zwt.kepler_core.ui.recycler.MultipleRecyclerViewAdapter;
import com.zwt.kepler_core.ui.recycler.MultipleViewHolder;
import com.zwt.kepler_ec.ec.R;

import java.util.List;

/**
 * @author ZWT
 */
public class ShopCarRecyclerAdapter extends MultipleRecyclerViewAdapter {

    private final RequestOptions mRequestOption = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter();


    protected ShopCarRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        //添加itemType
        addItemType(ShopCarItemType.SHOP_CAR_ITEM, R.layout.item_shop_car);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity item) {
        super.convert(holder, item);
        switch (holder.getItemViewType()) {
            case ShopCarItemType.SHOP_CAR_ITEM:
                //取出数据
                final String title = item.getField(ShopCarItemFields.TITLE);
                final int count = item.getField(ShopCarItemFields.COUNT);
                final String desc = item.getField(ShopCarItemFields.DESC);
                final double price = item.getField(ShopCarItemFields.PRICE);
                final String thumb =item.getField(MultipleFields.IMAGE_URL);
                final boolean isChecked =item.getField(MultipleFields.TAG);
                //取出view
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_shop_car_item_title);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_shop_car_item_count);
                final AppCompatTextView tvDesc = holder.getView(R.id.tv_shop_car_item_des);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_shop_car_item_price);
                final AppCompatImageView ivAvatar = holder.getView(R.id.iv_shop_car_item_avatar);
                final AppCompatImageView ivCheck = holder.getView(R.id.iv_shop_car_item_check);
                final AppCompatImageView ivMinus = holder.getView(R.id.iv_shop_car_item_minus);
                final AppCompatImageView ivPlus =  holder.getView(R.id.iv_shop_car_item_plus);
                //设置数据
                tvTitle.setText(title);
                tvCount.setText(String.valueOf(count));
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));

                Glide.with(mContext)
                        .load(thumb)
                        .into(ivAvatar);

                if (isChecked){
                    ivCheck.setImageResource(R.drawable.ic_checked);
                }else {
                    ivCheck.setImageResource(R.drawable.ic_uncheck);
                }

                break;

            default:
                break;
        }
    }
}
