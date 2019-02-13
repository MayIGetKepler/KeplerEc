package com.zwt.kepler_ec.ec.main.sort.list;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zwt.kepler_core.ui.recycler.DataConverter;
import com.zwt.kepler_core.ui.recycler.ItemType;
import com.zwt.kepler_core.ui.recycler.MultipleFields;
import com.zwt.kepler_core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * @author ZWT
 */
public class VerticalListDataConveter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray =
                JSONObject.parseObject(getJsonData()).getJSONObject("data").getJSONArray("list");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");

            final MultipleItemEntity entity = MultipleItemEntity
                    .builder()
                    .setField(MultipleFields.ITEM_TYPE,ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.TEXT,name)
                    //item是否被选中,默认false
                    .setField(MultipleFields.TAG,false)
                    .build();
            ENTITIES.add(entity);
        }
        ENTITIES.get(0).setField(MultipleFields.TAG,true);
        return ENTITIES;
    }
}
