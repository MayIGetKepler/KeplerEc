package com.zwt.kepler_core.ui.recycler;

import java.util.WeakHashMap;

/**
 * @author ZWT
 */
public class MultipleItemEntityBuilder {
    public static final WeakHashMap<Object, Object> FIELDS = new WeakHashMap<>();

    //先清除之前的数据
    public MultipleItemEntityBuilder() {
        FIELDS.clear();
    }

    public final MultipleItemEntityBuilder setItemType(int itemType) {
        FIELDS.put(MultipleFields.ITEM_TYPE, itemType);
        return this;
    }

    public final MultipleItemEntityBuilder setField(Object key, Object value) {
        FIELDS.put(key, value);
        return this;
    }

    public final MultipleItemEntityBuilder setFields(WeakHashMap<Object,Object> map){
        FIELDS.putAll(map);
        return this;
    }

    public final MultipleItemEntity build(){
        return new MultipleItemEntity(FIELDS);
    }
}
