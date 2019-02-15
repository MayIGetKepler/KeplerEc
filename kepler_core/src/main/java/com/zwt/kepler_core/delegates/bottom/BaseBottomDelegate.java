package com.zwt.kepler_core.delegates.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.zwt.kepler_core.R;
import com.zwt.kepler_core.delegates.KeplerDelegate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author ZWT
 * 底部导航栏item
 */
public abstract class BaseBottomDelegate extends KeplerDelegate implements View.OnClickListener {

    private final ArrayList<BottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();
    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    private int mCurrentDelegate = 0;
    private int mIndexDelegate = 0;
    private int mClickedColor = Color.RED;
    private LinearLayoutCompat mBottomBar;

    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems();

    public abstract int setIndexDelegate();

    @ColorInt
    public abstract int setClickedColor();

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setIndexDelegate();
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }

        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItems();
        ITEMS.putAll(items);

        for (Map.Entry<BottomTabBean, BottomItemDelegate> item : ITEMS.entrySet()) {
            TAB_BEANS.add(item.getKey());
            ITEM_DELEGATES.add(item.getValue());
        }
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        mBottomBar = rootView.findViewById(R.id.bottom_bar);
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text, mBottomBar);
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            //底部item点击事件
            item.setTag(i);
            item.setOnClickListener(this);

            //
            BottomTabBean bean = TAB_BEANS.get(i);
            final AppCompatImageView itemIcon = (AppCompatImageView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);

            itemIcon.setImageResource(bean.getIcon());
            itemTitle.setText(bean.getTitle());
            if (i == mIndexDelegate) {
                itemIcon.setImageResource(bean.getActiveIconId());
                itemTitle.setTextColor(mClickedColor);
            }
        }

        final SupportFragment[] delegateArray = ITEM_DELEGATES.toArray(new SupportFragment[size]);
        loadMultipleRootFragment(R.id.bottom_delegate_container, mIndexDelegate, delegateArray);

    }

    @Override
    public void onClick(View view) {
        int tag = (int) view.getTag();
        restColor();
        final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(tag);
        final AppCompatImageView itemIcon = (AppCompatImageView) item.getChildAt(0);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemTitle.setTextColor(mClickedColor);
        itemIcon.setImageResource(TAB_BEANS.get(tag).getActiveIconId());
        showHideFragment(ITEM_DELEGATES.get(tag), ITEM_DELEGATES.get(mCurrentDelegate));

        mCurrentDelegate = tag;
    }

    private void restColor() {
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final AppCompatImageView itemIcon = (AppCompatImageView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            itemIcon.setImageResource(TAB_BEANS.get(i).getIcon());
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    public void changeItem(int index){
        restColor();
        final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(index);
        final AppCompatImageView itemIcon = (AppCompatImageView) item.getChildAt(0);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemTitle.setTextColor(mClickedColor);
        itemIcon.setImageResource(TAB_BEANS.get(index).getActiveIconId());
        showHideFragment(ITEM_DELEGATES.get(index), ITEM_DELEGATES.get(mCurrentDelegate));

        mCurrentDelegate = index;
    }
}
