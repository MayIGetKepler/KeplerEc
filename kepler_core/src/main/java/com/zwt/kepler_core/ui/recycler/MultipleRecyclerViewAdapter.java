package com.zwt.kepler_core.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zwt.kepler_core.R;
import com.zwt.kepler_core.ui.banner.BannerCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZWT
 */
public class MultipleRecyclerViewAdapter extends
        BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup,
        OnItemClickListener {

    //banner是否已经初始化的标记
    private boolean mIsInitBanner = false;

    protected MultipleRecyclerViewAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MultipleRecyclerViewAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerViewAdapter(data);
    }

    public static MultipleRecyclerViewAdapter create(DataConverter converter) {
        return new MultipleRecyclerViewAdapter(converter.convert());
    }

    private void init() {
        //设置不同的item布局
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE, R.layout.item_multiple_image);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_image_text);
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner);
        //设置宽度监听
        setSpanSizeLookup(this);

        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);

    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity item) {
        final String text;
        final String imageUrl;
        final ArrayList<String> bannerImages;
        switch (holder.getItemViewType()) {
            case ItemType.TEXT:
                text = item.getField(MultipleFields.TEXT);
                holder.setText(R.id.text_single, text);
                break;

            case ItemType.IMAGE:
                imageUrl = item.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .applyDefaultRequestOptions(new RequestOptions()
                                .centerCrop()
                                .dontAnimate()
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .load(imageUrl)
                        .into((ImageView) holder.getView(R.id.img_single));

                break;
            case ItemType.TEXT_IMAGE:
                text = item.getField(MultipleFields.TEXT);
                imageUrl = item.getField(MultipleFields.IMAGE_URL);

                Glide.with(mContext)
                        .applyDefaultRequestOptions(new RequestOptions()
                                .centerCrop()
                                .dontAnimate()
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .load(imageUrl)
                        .into((ImageView) holder.getView(R.id.img_multiple));
                holder.setText(R.id.tv_multiple,text);
                break;

            case ItemType.BANNER:
                if (!mIsInitBanner){
                    bannerImages = item.getField(MultipleFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.banner_recycler_item);
                    BannerCreator.setDefault(convenientBanner,bannerImages,this);
                    mIsInitBanner = true;
                }
                break;

            default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }
}
