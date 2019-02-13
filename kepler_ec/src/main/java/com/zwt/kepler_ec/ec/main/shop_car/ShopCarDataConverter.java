package com.zwt.kepler_ec.ec.main.shop_car;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zwt.kepler_core.ui.recycler.DataConverter;
import com.zwt.kepler_core.ui.recycler.MultipleFields;
import com.zwt.kepler_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * @author ZWT
 */
public class ShopCarDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {

        JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject item = dataArray.getJSONObject(i);
            final String thumb = item.getString("thumb");
            final String desc = item.getString("desc");
            final String title = item.getString("title");
            final int id = item.getInteger("id");
            final int count = item.getInteger("count");
            final double price = item.getDouble("price");

            MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ShopCarItemType.SHOP_CAR_ITEM)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.IMAGE_URL, thumb)
                    //是否已经选中
                    .setField(MultipleFields.TAG,true)
                    .setField(ShopCarItemFields.COUNT, count)
                    .setField(ShopCarItemFields.TITLE, title)
                    .setField(ShopCarItemFields.PRICE, price)
                    .setField(ShopCarItemFields.DESC, desc)
                    .build();

            ENTITIES.add(entity);
        }


        return ENTITIES;
    }
}
