package com.zwt.kepler_ec.ec.main.sort.content;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zwt.kepler_ec.ec.R;

import java.util.List;

/**
 * @author ZWT
 */
public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean,BaseViewHolder> {

    private RequestOptions mOptions =
            new RequestOptions().fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL);

    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionBean item) {
        helper.setText(R.id.header,item.header);
        helper.setVisible(R.id.more,item.isHasMore());
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionBean item) {
        String name = item.t.getGoodsName();
        String thumb = item.t.getGoodsThumb();

        ImageView ivThumb = helper.getView(R.id.iv_content);
        helper.setText(R.id.tv_content,name);
        Glide.with(mContext)
                .load(thumb)
                .apply(mOptions)
                .into(ivThumb);
    }
}
