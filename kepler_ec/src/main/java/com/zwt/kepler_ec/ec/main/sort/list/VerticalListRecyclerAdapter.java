package com.zwt.kepler_ec.ec.main.sort.list;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.zwt.kepler_core.delegates.KeplerDelegate;
import com.zwt.kepler_core.ui.recycler.DataConverter;
import com.zwt.kepler_core.ui.recycler.ItemType;
import com.zwt.kepler_core.ui.recycler.MultipleFields;
import com.zwt.kepler_core.ui.recycler.MultipleItemEntity;
import com.zwt.kepler_core.ui.recycler.MultipleRecyclerViewAdapter;
import com.zwt.kepler_core.ui.recycler.MultipleViewHolder;
import com.zwt.kepler_ec.ec.R;
import com.zwt.kepler_ec.ec.main.sort.SortDelegate;
import com.zwt.kepler_ec.ec.main.sort.content.ContentDelegate;

import java.util.List;

/**
 * @author ZWT
 */
public class VerticalListRecyclerAdapter extends MultipleRecyclerViewAdapter {

    private final SortDelegate DELEGATE;
    private int mPrePosition = 0;

    protected VerticalListRecyclerAdapter(List<MultipleItemEntity> data, SortDelegate delegate) {
        super(data);
        this.DELEGATE = delegate;
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list);
    }

    public static VerticalListRecyclerAdapter create(List<MultipleItemEntity> data, SortDelegate delegate) {
        return new VerticalListRecyclerAdapter(data, delegate);
    }

    public static VerticalListRecyclerAdapter create(DataConverter converter, SortDelegate delegate) {
        return new VerticalListRecyclerAdapter(converter.convert(), delegate);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity item) {
        super.convert(holder, item);
        switch (holder.getItemViewType()) {
            case ItemType.VERTICAL_MENU_LIST:

                final String name = item.getField(MultipleFields.TEXT);
                final boolean isClicked = item.getField(MultipleFields.TAG);
                AppCompatTextView textView = holder.getView(R.id.tv_vertical_item_name);
                View line = holder.getView(R.id.view_line);
                View itemView = holder.itemView;
                textView.setText(name);

                if (isClicked) {
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                    line.setVisibility(View.VISIBLE);
                    itemView.setBackgroundColor(Color.WHITE);
                } else {
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    line.setVisibility(View.INVISIBLE);
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                }

                itemView.setOnClickListener(view -> {
                    if (mPrePosition != holder.getAdapterPosition()) {
                        getData().get(mPrePosition).setField(MultipleFields.TAG, false);
                        notifyItemChanged(mPrePosition);
                        item.setField(MultipleFields.TAG, true);
                        mPrePosition = holder.getAdapterPosition();
                        notifyItemChanged(mPrePosition);

                        final int contentId = getData().get(mPrePosition).getField(MultipleFields.ID);

                        switchContent(contentId);
                    }
                });
                break;

            default:
                break;
        }
    }

    private void showContent(int contentId) {
        final ContentDelegate delegate = ContentDelegate.newInstance(contentId);
        switchContent(delegate);
    }

    private void switchContent(ContentDelegate delegate) {
        final KeplerDelegate contentDelegate = DELEGATE.findChildFragment(ContentDelegate.class);
        if (contentDelegate != null) {
            contentDelegate.replaceFragment(delegate, false);
        }
    }
    private void switchContent(int contentId) {
        final KeplerDelegate contentDelegate = DELEGATE.findChildFragment(ContentDelegate.class);
        if (contentDelegate != null) {
            ((ContentDelegate) contentDelegate).scroll(contentId);
        }
    }
}
