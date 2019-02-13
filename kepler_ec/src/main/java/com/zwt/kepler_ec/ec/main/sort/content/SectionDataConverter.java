package com.zwt.kepler_ec.ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZWT
 */
public class SectionDataConverter {

    final List<SectionBean> convert(String json) {
        final List<SectionBean> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(json).getJSONArray("data");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject object = dataArray.getJSONObject(i);
            final int id = object.getInteger("id");
            final String title = object.getString("section");

            final SectionBean sectionTitleBean = new SectionBean(true,title);
            sectionTitleBean.setId(id);
            sectionTitleBean.setHasMore(true);
            dataList.add(sectionTitleBean);

            final JSONArray goods = object.getJSONArray("goods");

            final int goodSize = goods.size();
            for (int j = 0; j < goodSize; j++) {
                final JSONObject contentItem = goods.getJSONObject(j);
                final int goodsId = contentItem.getInteger("goods_id");
                final String goodsName = contentItem.getString("goods_name");
                final String goodsThumb = contentItem.getString("goods_thumb");

                final SectionContentItemEntity itemEntity = new SectionContentItemEntity();
                itemEntity.setGoodsId(goodsId);
                itemEntity.setGoodsName(goodsName);
                itemEntity.setGoodsThumb(goodsThumb);

                dataList.add(new SectionBean(itemEntity));

            }

        }

        return dataList;
    }
}
