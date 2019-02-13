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

    private boolean mIsSelectedAll = false;

    private final RequestOptions mRequestOption = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter();

    private  ISelectAll mISelectAll = null;


    ShopCarRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        //添加itemType
        addItemType(ShopCarItemType.SHOP_CAR_ITEM, R.layout.item_shop_car);
    }

    void setSelectedAll(boolean selectedAll) {
        mIsSelectedAll = selectedAll;
    }

    public void setISelectAll(ISelectAll ISelectAll) {
        mISelectAll = ISelectAll;
    }

    void selectAll(boolean selectedAll) {
        final List<MultipleItemEntity> entities = getData();
        final int size = entities.size();
        for (int i = 0; i < size; i++) {
            MultipleItemEntity entity = entities.get(i);
            final boolean isSelected = entity.getField(MultipleFields.TAG);
            if (isSelected != selectedAll) {
                entity.setField(MultipleFields.TAG, selectedAll);
                notifyItemChanged(i);
            }
        }
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
                final String thumb = item.getField(MultipleFields.IMAGE_URL);

                //取出view
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_shop_car_item_title);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_shop_car_item_count);
                final AppCompatTextView tvDesc = holder.getView(R.id.tv_shop_car_item_des);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_shop_car_item_price);
                final AppCompatImageView ivAvatar = holder.getView(R.id.iv_shop_car_item_avatar);
                final AppCompatImageView ivCheck = holder.getView(R.id.iv_shop_car_item_check);
                final AppCompatImageView ivMinus = holder.getView(R.id.iv_shop_car_item_minus);
                final AppCompatImageView ivPlus = holder.getView(R.id.iv_shop_car_item_plus);
                //绑定数据
                tvTitle.setText(title);
                tvCount.setText(String.valueOf(count));
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));

                Glide.with(mContext)
                        .load(thumb)
                        .into(ivAvatar);

                //渲染check按钮
                item.setField(MultipleFields.TAG, mIsSelectedAll);
                final boolean isChecked = item.getField(MultipleFields.TAG);
                if (isChecked) {
                    ivCheck.setImageResource(R.drawable.ic_checked);

                } else {
                    ivCheck.setImageResource(R.drawable.ic_uncheck);

                }

                ivCheck.setOnClickListener(view -> {
                    if (mISelectAll == null){
                        throw new NullPointerException("ISelectAll is Null !");
                    }
                    final boolean currentSelected = item.getField(MultipleFields.TAG);
                    if (currentSelected) {
                        ivCheck.setImageResource(R.drawable.ic_uncheck);
                        item.setField(MultipleFields.TAG, false);
                        //取消全选
                        mISelectAll.unSelected();
                    } else {
                        ivCheck.setImageResource(R.drawable.ic_checked);
                        item.setField(MultipleFields.TAG, true);
                        //判断是否需要全选
                        if (toSelectAll()){
                            mISelectAll.allSelected();
                        }
                    }
                });

                break;

            default:
                break;
        }
    }

    private boolean toSelectAll() {
        final List<MultipleItemEntity> entities = getData();
        for (MultipleItemEntity entity : entities) {
            final boolean isChecked = entity.getField(MultipleFields.TAG);
        	if (!isChecked){
        	    return false;
            }
        }
        return true;
    }
}
