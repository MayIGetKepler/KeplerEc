package com.zwt.kepler_ec.ec.main;

import android.graphics.Color;

import com.zwt.kepler_core.delegates.bottom.BaseBottomDelegate;
import com.zwt.kepler_core.delegates.bottom.BottomItemDelegate;
import com.zwt.kepler_core.delegates.bottom.BottomTabBean;
import com.zwt.kepler_ec.ec.R;
import com.zwt.kepler_ec.ec.main.find.FindDelegate;
import com.zwt.kepler_ec.ec.main.index.IndexDelegate;
import com.zwt.kepler_ec.ec.main.mine.MineDelegate;
import com.zwt.kepler_ec.ec.main.shop_car.ShopCarDelegate;
import com.zwt.kepler_ec.ec.main.sort.SortDelegate;

import java.util.LinkedHashMap;

/**
 * @author ZWT
 */
public class EcBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems() {
        LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean(R.drawable.ic_index,R.drawable.ic_index_pressed,"首页"),new IndexDelegate());
        items.put(new BottomTabBean(R.drawable.ic_sort,R.drawable.ic_sort_pressed,"分类"),new SortDelegate());
        items.put(new BottomTabBean(R.drawable.ic_find, R.drawable.ic_find_pressed,"发现"),new FindDelegate());
        items.put(new BottomTabBean(R.drawable.ic_shop_car, R.drawable.ic_shop_car_pressed,"购物车"),new ShopCarDelegate());
        items.put(new BottomTabBean(R.drawable.ic_mine, R.drawable.ic_mine_pressed,"我的"),new MineDelegate());
        return items;
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
