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
    private double mTotalPrice = 0.00;

    private final RequestOptions mRequestOption = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter();

    private ISelectAll mISelectAll = null;
    private IItemSelected mIItemSelected = null;


    ShopCarRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        //添加itemType
        addItemType(ShopCarItemType.SHOP_CAR_ITEM, R.layout.item_shop_car);
        //获取总价
        for (MultipleItemEntity entity : data) {
            //选中的item计算总价
            if (entity.getField(MultipleFields.TAG)) {
                final int price = entity.getField(ShopCarItemFields.PRICE);
                final int count = entity.getField(ShopCarItemFields.COUNT);
                mTotalPrice += (price * count);
            }
        }
        if (mIItemSelected != null) {
            mIItemSelected.totalPrice(mTotalPrice);
        }
    }

    void setSelectedAll(boolean selectedAll) {
        mIsSelectedAll = selectedAll;
    }

    void setISelectAll(ISelectAll ISelectAll) {
        mISelectAll = ISelectAll;
    }

    void setIItemSelected(IItemSelected IItemSelected) {
        mIItemSelected = IItemSelected;
    }

    public double getTotalPrice() {
        return mTotalPrice;
    }

    void resetPosition(int beginPosition) {
        List<MultipleItemEntity> data = getData();
        final int size = data.size();
        for (int i = beginPosition; i < size; i++) {
            data.get(i).setField(ShopCarItemFields.POSITION, i);
        }
        restTotalPrice();
    }

    private void restTotalPrice() {
        mTotalPrice = 0.00;
        if (mIItemSelected != null) {
            mIItemSelected.totalPrice(mTotalPrice);
        }
    }

    void selectAll(boolean selectedAll) {
        double totalPrice = 0.00;
        final List<MultipleItemEntity> entities = getData();
        final int size = entities.size();
        for (int i = 0; i < size; i++) {
            MultipleItemEntity entity = entities.get(i);
            final boolean isSelected = entity.getField(MultipleFields.TAG);

            if (isSelected != selectedAll) {
                entity.setField(MultipleFields.TAG, selectedAll);
                notifyItemChanged(i);
            }
            //全选总价改变 全不选总价为0
            if (mIItemSelected != null) {
                final double price = entity.getField(ShopCarItemFields.PRICE);
                final int count = entity.getField(ShopCarItemFields.COUNT);
                final double itemPrice = price * count;
                if (selectedAll) {
                    totalPrice += itemPrice;
                }
                mTotalPrice = totalPrice;
                mIItemSelected.totalPrice(totalPrice);
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
                        .apply(mRequestOption)
                        .into(ivAvatar);

                //渲染check按钮
                item.setField(MultipleFields.TAG, mIsSelectedAll);
                final boolean isChecked = item.getField(MultipleFields.TAG);
                if (isChecked) {
                    ivCheck.setImageResource(R.drawable.ic_checked);

                } else {
                    ivCheck.setImageResource(R.drawable.ic_uncheck);

                }
                //check按钮点击事件
                ivCheck.setOnClickListener(view -> {
                     double pri = item.getField(ShopCarItemFields.PRICE);
                     int cou = item.getField(ShopCarItemFields.COUNT);
                    if (mISelectAll == null) {
                        throw new NullPointerException("ISelectAll is Null !");
                    }
                    final boolean currentSelected = item.getField(MultipleFields.TAG);
                    if (currentSelected) {
                        ivCheck.setImageResource(R.drawable.ic_uncheck);
                        item.setField(MultipleFields.TAG, false);
                        //取消全选
                        mISelectAll.unSelected();

                        //总价减去此item总价
                        if (mIItemSelected != null) {
                            mTotalPrice -= (pri * cou);
                            mIItemSelected.totalPrice(mTotalPrice);
                        }


                    } else {
                        ivCheck.setImageResource(R.drawable.ic_checked);
                        item.setField(MultipleFields.TAG, true);
                        //判断是否需要全选
                        if (toSelectAll()) {
                            mISelectAll.allSelected();
                        }

                        //总价加上此item总价
                        if (mIItemSelected != null) {
                            mTotalPrice += (pri * cou);
                            mIItemSelected.totalPrice(mTotalPrice);
                        }

                    }
                });

                //减按钮点击事件
                ivMinus.setOnClickListener(view -> {
                    int cou = item.getField(ShopCarItemFields.COUNT);
                    if (cou > 1) {
                        cou--;
                        item.setField(ShopCarItemFields.COUNT, cou);
                        tvCount.setText(String.valueOf(cou));
                        if (mIItemSelected != null && (boolean) item.getField(MultipleFields.TAG)) {
                            mTotalPrice -= price;
                            mIItemSelected.totalPrice(mTotalPrice);
                        }
                    }
                });
                //加按钮点击事件
                ivPlus.setOnClickListener(view -> {
                    int cou = item.getField(ShopCarItemFields.COUNT);
                    cou++;
                    item.setField(ShopCarItemFields.COUNT, cou);
                    tvCount.setText(String.valueOf(cou));
                    if (mIItemSelected != null&& (boolean) item.getField(MultipleFields.TAG)) {
                        mTotalPrice += price;
                        mIItemSelected.totalPrice(mTotalPrice);
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
            if (!isChecked) {
                return false;
            }
        }
        return true;
    }
}
